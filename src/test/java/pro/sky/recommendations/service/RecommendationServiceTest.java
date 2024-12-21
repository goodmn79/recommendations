package pro.sky.recommendations.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import pro.sky.recommendations.dto.UserRecommendationSet;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.User;
import pro.sky.recommendations.repository.UserRepository;
import pro.sky.recommendations.utility.RecommendationRuleSet;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RecommendationServiceTest {
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
    private RecommendationService recommendationService;

    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recommendationService = new RecommendationService(userRepository, invest500, simpleCredit, topSaving);
        userId = UUID.randomUUID();
    }

    @Test
    void testCheckRecommendation_whenUserHasRecommendations() {
        User mockUser = new User()
                .setId(userId);
        Recommendation investRecommendation = new Recommendation()
                .setName("Invest500");
        Recommendation creditRecommendation = new Recommendation()
                .setName("SimpleCredit");
        Recommendation savingRecommendation = new Recommendation()
                .setName("TopSaving");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(invest500.validateRecommendationRule(userId)).thenReturn(Optional.of(investRecommendation));
        when(simpleCredit.validateRecommendationRule(userId)).thenReturn(Optional.of(creditRecommendation));
        when(topSaving.validateRecommendationRule(userId)).thenReturn(Optional.of(savingRecommendation));

        UserRecommendationSet userRecommendationSet = recommendationService.checkRecommendation(userId);

        assertNotNull(userRecommendationSet);
        assertEquals(3, userRecommendationSet.getRecommendations().size());
        assertTrue(userRecommendationSet.getRecommendations().contains(investRecommendation));
        assertTrue(userRecommendationSet.getRecommendations().contains(creditRecommendation));
        assertTrue(userRecommendationSet.getRecommendations().contains(savingRecommendation));
    }

    @Test
    void testCheckRecommendation_whenNoRecommendations() {
        User mockUser = new User()
                .setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(invest500.validateRecommendationRule(userId)).thenReturn(Optional.empty());
        when(simpleCredit.validateRecommendationRule(userId)).thenReturn(Optional.empty());
        when(topSaving.validateRecommendationRule(userId)).thenReturn(Optional.empty());

        UserRecommendationSet userRecommendationSet = recommendationService.checkRecommendation(userId);

        assertNotNull(userRecommendationSet);
        assertTrue(userRecommendationSet.getRecommendations().isEmpty());
    }

    @Test
    void testCheckRecommendation_whenUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> recommendationService.checkRecommendation(userId));
    }
}