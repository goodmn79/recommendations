package pro.sky.recommendations.stats.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.recommendation.dto.RecommendationData;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.stats.dto.StatsData;
import pro.sky.recommendations.stats.mapper.StatsMapper;
import pro.sky.recommendations.stats.model.Stats;
import pro.sky.recommendations.stats.repository.StatsRepository;
import pro.sky.recommendations.user_recommendation.dto.UserRecommendation;

import java.lang.reflect.Field;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {
    @Mock
    private StatsMapper statsMapper;
    @Mock
    private StatsRepository statsRepository;
    @InjectMocks
    private StatsService statsService;

    private Recommendation recommendation;
    private Stats stats;
    StatsData statsData;
    Map<UUID, Stats> testStatsCounters;

    @BeforeEach
    void setUp() throws Exception {
        recommendation = new Recommendation()
                .setId(UUID.randomUUID());

        stats = new Stats()
                .setRecommendation(recommendation)
                .setCount(0);

        statsData = new StatsData()
                .setRecommendationId(stats.getRecommendation().getId())
                .setCount(stats.getCount());

        testStatsCounters = new HashMap<>(Map.of(recommendation.getId(), stats));
        Field field = statsService.getClass().getDeclaredField("statsCounters");
        field.setAccessible(true);
        field.set(statsService, testStatsCounters);
    }

    @Test
    void initStatsDataStore_whenValuesExistInDatabase_shouldInitMap() {
        when(statsRepository.findAll()).thenReturn(Collections.singletonList(stats));
        when(statsMapper.toStatsDataList(Collections.singletonList(stats)))
                .thenReturn(Collections.singletonList(statsData));

        statsService.initStatsDataStore();

        verify(statsRepository, times(1)).findAll();

        assertThat(statsService.getAll()).isNotEmpty();
        assertThat(statsService.getAll()).contains(statsData);
    }

    @Test
    void initStatsDataStore_whenNoValuesInDatabase_shouldInitMapWithValues() {
        when(statsRepository.findAll()).thenReturn(Collections.emptyList());

        statsService.initStatsDataStore();

        verify(statsRepository, times(1)).findAll();

        assertTrue(statsService.getAll().isEmpty());
    }

    @Test
    void testSave() {
        doNothing().when(statsRepository).saveAll(anyList());

        statsService.save();

        verify(statsRepository, times(1)).saveAll(anyList());
    }

    @Test
    void statsAccumulatorTest() {
        int count = stats.getCount();
        RecommendationData recommendationData = new RecommendationData()
                .setId(recommendation.getId());
        UserRecommendation userRecommendation = new UserRecommendation()
                .setRecommendations(Collections.singletonList(recommendationData));

        statsService.statsAccumulator(userRecommendation);

        assertThat(stats.getCount()).isEqualTo(++count);
    }

    @Test
    void testGetAll() {
        List<Stats> expected = Collections.singletonList(stats);
        when(statsMapper.toStatsDataList(anyList()))
                .thenReturn(Collections.singletonList(statsData));

        List<StatsData> actual = statsService.getAll();

        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).contains(statsData);
    }

    @Test
    void testCreateCounter() {
        Recommendation newRecommendation = new Recommendation()
                .setId(UUID.randomUUID());

        statsService.createCounter(newRecommendation);

        assertThat(testStatsCounters.size()).isEqualTo(2);
    }

    @Test
    void testDeleteCounter() {

        statsService.deleteCounter(recommendation.getId());

        assertThat(testStatsCounters.get(recommendation.getId())).isNull();
        assertThat(testStatsCounters).isEmpty();
    }
}