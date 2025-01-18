package pro.sky.recommendations.stats.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.sky.recommendations.dto.UserRecommendation;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.stats.dto.StatsData;
import pro.sky.recommendations.stats.mapper.StatsMapper;
import pro.sky.recommendations.stats.model.Stats;
import pro.sky.recommendations.stats.repository.StatsRepository;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class StatsServiceTest {
    @Mock
    private StatsMapper statsMapper;

    @Mock
    private StatsRepository statsRepository;

    @InjectMocks
    private StatsService statsService;

    private Recommendation recommendation;
    private Stats stats;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recommendation = new Recommendation();
        recommendation.setId(UUID.randomUUID());

        stats = new Stats();
        stats.setRecommendation(recommendation);
        stats.setCount(0);
    }

    @Test
    void testInitStatsDataStore_WhenNoDataInRepository() {
        when(statsRepository.findAll()).thenReturn(Collections.emptyList());

        statsService.initStatsDataStore();

        assertTrue(statsService.getStatsList().isEmpty());

    }

    @Test
    void testIncrementor(){
        UserRecommendation userRecommendation = mock(UserRecommendation.class);
        when(userRecommendation.getRecommendations()).thenReturn(List.of(recommendation));

        statsService.initStatsDataStore();
        statsService.createCounter(recommendation);
        statsService.incrementor(userRecommendation);

        assertEquals(1, statsService.getStatsList().stream()
                .filter(s -> s.getRecommendation().equals(recommendation))
                .findFirst()
                .map(Stats::getCount)
                .orElse(0));
    }

    @Test
    void testCreateCounter(){
        statsService.createCounter(recommendation);

        assertEquals(1, statsService.getStatsList().size());
    }

    @Test
    void testDeleteCounter(){
        statsService.createCounter(recommendation);

        statsService.deleteCounter(recommendation);

        assertTrue(statsService.getStatsList().isEmpty());
    }

    @Test
    void testGetAll(){
        List<Stats> statsList = Collections.singletonList(stats);
        when(statsMapper.toStatsDataList(statsList)).thenReturn(Collections.singletonList(new StatsData()));

        statsService.initStatsDataStore();

        List<StatsData> statsDataList = statsService.getAll();

        assertNotNull(statsDataList);
        assertEquals(1, statsDataList.size());
    }

    @Test
    void testSave(){
        statsService.createCounter(recommendation);

        statsService.save();

        verify(statsRepository, times(1)).saveAll(anyList());
    }
}