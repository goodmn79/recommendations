package pro.sky.recommendations.recommendation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.recommendation.exception.RecommendationNotFoundException;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.recommendation.repository.RecommendationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {
    @Mock
    private RecommendationRepository recommendationRepository;
    @InjectMocks
    private RecommendationService recommendationService;

    private UUID recommendationId;
    private Recommendation recommendation;

    @BeforeEach
    void setUp() {
        recommendationId = UUID.randomUUID();
        recommendation = mock(Recommendation.class);
    }

    @Test
    void saveRecommendationTest() {

        recommendationService.saveRecommendation(recommendation);

        verify(recommendationRepository).save(recommendation);
    }

    @Test
    void findById_whenRecommendationFound_shouldReturnRecommendation() {
        when(recommendationRepository.findById(recommendationId))
                .thenReturn(Optional.of(recommendation));

        Recommendation actual = recommendationService.findById(recommendationId);

        verify(recommendationRepository).findById(recommendationId);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(recommendation);
    }

    @Test
    void findById_whenRecommendationNotFound_shouldThrowException() {
        when(recommendationRepository.findById(recommendationId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> recommendationService.findById(recommendationId)).isInstanceOf(RecommendationNotFoundException.class);
    }

    @Test
    void findAll_whenRecommendationFound_shouldReturnRecommendations() {
        List<Recommendation> expected = Collections.singletonList(recommendation);
        when(recommendationRepository.findAll()).thenReturn(expected);

        List<Recommendation> actual = recommendationService.findAll();

        verify(recommendationRepository).findAll();

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
        assertThat(actual.size()).isEqualTo(expected.size());
    }

    @Test
    void findAll_whenRecommendationNotFound_shouldReturnEmptyList() {
        when(recommendationRepository.findAll()).thenReturn(Collections.emptyList());

        List<Recommendation> actual = recommendationService.findAll();

        verify(recommendationRepository).findAll();

        assertThat(actual).isNotNull();
        assertThat(actual).isEmpty();
    }

    @Test
    void deleteByIdTest() {
        when(recommendationRepository.findById(recommendationId)).thenReturn(Optional.of(recommendation));

        recommendationService.deleteById(recommendationId);

        verify(recommendationRepository).deleteById(recommendationId);
    }
}