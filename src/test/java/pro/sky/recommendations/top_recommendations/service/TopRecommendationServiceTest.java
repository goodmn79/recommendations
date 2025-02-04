package pro.sky.recommendations.top_recommendations.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.recommendation.dto.RecommendationData;
import pro.sky.recommendations.recommendation.repository.TransactionRepository;
import pro.sky.recommendations.top_recommendations.top_recommendation.TopRecommendation;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopRecommendationServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TopRecommendation recommendation1;
    @Mock
    private TopRecommendation recommendation2;
    @Mock
    private TopRecommendation recommendation3;

    private List<TopRecommendation> testList;

    private TopRecommendationService topRecommendationService;

    UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();

        String testQuery = "test query with user id = ?";

        when(recommendation1.getQuery()).thenReturn("First " + testQuery);
        when(recommendation2.getQuery()).thenReturn("Second " + testQuery);
        when(recommendation3.getQuery()).thenReturn("Third " + testQuery);

        testList = List.of(recommendation1, recommendation2, recommendation3);

        topRecommendationService =
                new TopRecommendationService(transactionRepository, testList);
    }

    @Test
    void getTopRecommendationsForUser_whenAllRulesCompliance_shouldReturnAllRecommendations() {
        when(transactionRepository.isCompliance(anyString(), any(UUID.class)))
                .thenReturn(true);

        List<RecommendationData> actual = topRecommendationService.getTopRecommendationsForUser(uuid);

        verify(transactionRepository, times(3)).isCompliance(anyString(), any());

        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(testList.size());
    }

    @Test
    void getTopRecommendationsForUser_whenNoCompliance_shouldReturnEmptyList() {
        when(transactionRepository.isCompliance(anyString(), any(UUID.class)))
                .thenReturn(false);

        List<RecommendationData> actual = topRecommendationService.getTopRecommendationsForUser(uuid);

        verify(transactionRepository, times(3)).isCompliance(anyString(), any());

        assertThat(actual).isNotNull();
        assertThat(actual).isEmpty();
    }

    @Test
    void getTopRecommendationsForUser_whenSomeCompliance_shouldReturnCorrectList() {
        when(transactionRepository.isCompliance(recommendation1.getQuery(), uuid))
                .thenReturn(true);
        when(transactionRepository.isCompliance(recommendation2.getQuery(), uuid))
                .thenReturn(false);
        when(transactionRepository.isCompliance(recommendation3.getQuery(), uuid))
                .thenReturn(true);

        List<RecommendationData> actual = topRecommendationService.getTopRecommendationsForUser(uuid);

        verify(transactionRepository, times(3)).isCompliance(anyString(), any());

        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(2);
    }
}