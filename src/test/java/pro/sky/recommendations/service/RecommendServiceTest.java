package pro.sky.recommendations.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import pro.sky.recommendations.dto.UserRecommendationSet;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.model.Recommend;
import pro.sky.recommendations.model.User;
import pro.sky.recommendations.repository.UserRepository;
import pro.sky.recommendations.service.utility.RecommendationRuleSet;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RecommendServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    @Qualifier("invest500")
    private RecommendationRuleSet invest500;

    @Mock
    @Qualifier("simpleCredit")
    private RecommendationRuleSet simpleCredit;

    @Mock
    @Qualifier("topSaving")
    private RecommendationRuleSet topSaving;

    @InjectMocks
    private RecommendService recommendService;

    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recommendService = new RecommendService(userRepository, invest500, simpleCredit, topSaving);
        userId = UUID.randomUUID();
    }

    @Test
    void testCheckRecommendation_whenUserHasRecommendations() {
        User mockUser = new User()
                .setId(userId);
        Recommend investRecommend = new Recommend()
                .setName("Invest500");
        Recommend creditRecommend = new Recommend()
                .setName("SimpleCredit");
        Recommend savingRecommend = new Recommend()
                .setName("TopSaving");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(invest500.validateRecommendationRule(userId)).thenReturn(Optional.of(investRecommend));
        when(simpleCredit.validateRecommendationRule(userId)).thenReturn(Optional.of(creditRecommend));
        when(topSaving.validateRecommendationRule(userId)).thenReturn(Optional.of(savingRecommend));

        UserRecommendationSet userRecommendationSet = recommendService.checkRecommendation(userId);

        assertNotNull(userRecommendationSet);
        assertEquals(3, userRecommendationSet.getRecommends().size());
        assertTrue(userRecommendationSet.getRecommends().contains(investRecommend));
        assertTrue(userRecommendationSet.getRecommends().contains(creditRecommend));
        assertTrue(userRecommendationSet.getRecommends().contains(savingRecommend));
    }

    @Test
    void testCheckRecommendation_whenNoRecommendations() {
        User mockUser = new User()
                .setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(invest500.validateRecommendationRule(userId)).thenReturn(Optional.empty());
        when(simpleCredit.validateRecommendationRule(userId)).thenReturn(Optional.empty());
        when(topSaving.validateRecommendationRule(userId)).thenReturn(Optional.empty());

        UserRecommendationSet userRecommendationSet = recommendService.checkRecommendation(userId);

        assertNotNull(userRecommendationSet);
        assertTrue(userRecommendationSet.getRecommends().isEmpty());
    }

    @Test
    void testCheckRecommendation_whenUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> recommendService.checkRecommendation(userId));
    }
}