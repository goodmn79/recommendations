package pro.sky.recommendations.stats.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.UserRecommendation;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.stats.dto.StatsData;
import pro.sky.recommendations.stats.mapper.StatsMapper;
import pro.sky.recommendations.stats.model.Stats;
import pro.sky.recommendations.stats.repository.StatsRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final StatsMapper statsMapper;
    private final StatsRepository statsRepository;
    private Map<UUID, Stats> statsCounters;
    private final Logger log = LoggerFactory.getLogger(StatsService.class);

    @PostConstruct
    public void initStatsDataStore() {
        log.info("Initializing stats data store");
        List<Stats> stats = statsRepository.findAll();
        if (stats.isEmpty()) {
            statsCounters = new HashMap<>();
        } else {
            statsCounters = stats
                    .stream()
                    .collect(HashMap::new, (map, stat) -> map.put(stat.getId(), stat), HashMap::putAll);
        }
        log.info("Stats data store initialize successful");
    }

    public void incrementor(UserRecommendation userRecommendation) {
        userRecommendation.getRecommendations().forEach(recommendation -> {
            Stats stats = statsCounters.get(recommendation.getId());
            if (stats != null) {
                stats.increment();
            }
        });
    }

    public List<StatsData> getAll() {
        List<Stats> statsDataList = this.getStatsList();
        return statsMapper.toStatsDataList(statsDataList);
    }

    public void createCounter(Recommendation recommendation) {
        this.statsCounters.put(recommendation.getId(), new Stats().setRecommendation(recommendation));
    }

    public List<Stats> getStatsList() {
        return new ArrayList<>(statsCounters.values());
    }

    public void deleteCounter(Recommendation recommendation) {
        this.statsCounters.remove(recommendation.getId());
    }

    @PreDestroy
    public void save() {
        List<Stats> statsList = this.getStatsList();
        statsRepository.saveAll(statsList);
    }
}
