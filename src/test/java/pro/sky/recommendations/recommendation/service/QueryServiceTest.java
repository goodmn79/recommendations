package pro.sky.recommendations.recommendation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.recommendation.exception.RecommendationRuleNotExistException;
import pro.sky.recommendations.recommendation.model.Query;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.recommendation.repository.QueryRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueryServiceTest {
    @Mock
    private QueryRepository queryRepository;
    @InjectMocks
    private QueryService queryService;

    private List<Query> queries;
    private UUID recommendationId;

    @BeforeEach
    void setUp() {
        queries = List.of(
                mock(Query.class),
                mock(Query.class),
                mock(Query.class));

        recommendationId = UUID.randomUUID();
    }

    @Test
    void saveRuleTest() {
        Recommendation recommendation = mock(Recommendation.class);
        when(recommendation.getRule()).thenReturn(queries);

        queryService.saveRule(recommendation);

        verify(queryRepository).saveAll(queries);
    }

    @Test
    void findAllByRecommendationId_whenRecommendationIdIsCorrect_shouldReturnQuery() {
        when(queryRepository.findAllByRecommendationId(recommendationId)).thenReturn(queries);

        List<Query> actual = queryService.findAllByRecommendationId(recommendationId);

        verify(queryRepository).findAllByRecommendationId(recommendationId);
        assertThat(actual).isNotEmpty();
        assertThat(actual).containsAll(queries);
        assertThat(actual).hasSize(queries.size());
    }

    @Test
    void findAllByRecommendationId_whenEmptyQueriesList_shouldTrowException() {
        when(queryRepository.findAllByRecommendationId(recommendationId))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() ->
                queryService.findAllByRecommendationId(recommendationId))
                .isInstanceOf(RecommendationRuleNotExistException.class);
    }

    @Test
    void deleteBYRecommendationIdTest() {
        queryService.deleteBYRecommendationId(recommendationId);

        verify(queryRepository).deleteAllByRecommendationId(recommendationId);
    }
}