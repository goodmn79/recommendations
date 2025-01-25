package pro.sky.recommendations.stats.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.stats.dto.StatsData;
import pro.sky.recommendations.stats.mapper.StatsMapper;
import pro.sky.recommendations.stats.model.Stats;
import pro.sky.recommendations.stats.repository.StatsRepository;
import pro.sky.recommendations.user_recommendation.dto.UserRecommendation;

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
        log.info("Инициализация хранилища данных статистики.");
        List<Stats> stats = statsRepository.findAll();
        if (stats.isEmpty()) {
            statsCounters = new HashMap<>();
        } else {
            statsCounters = stats
                    .stream()
                    .collect(HashMap::new, (map, stat) -> map.put(stat.getId(), stat), HashMap::putAll);
        }
        log.info("Хранилища данных статистики успешно инициализировано.");
    }

    @PreDestroy
    public void save() {
        log.info("Сохранение данных статистики выдачи рекомендаций в базе данных.");

        List<Stats> statsList = this.getStatsList();
        statsRepository.saveAll(statsList);
    }

    public void incrementor(UserRecommendation userRecommendation) {
        log.info("Инкремент счётчика выдачи рекомендаций продукта.");

        userRecommendation.getRecommendations().forEach(recommendation -> {
            Stats stats = statsCounters.get(recommendation.getId());
            if (stats != null) {
                stats.increment();
            }
        });
    }

    public List<StatsData> getAll() {
        log.info("Получение данных статистики выдачи рекомендаций.");

        List<Stats> statsDataList = this.getStatsList();
        return statsMapper.toStatsDataList(statsDataList);
    }

    public void createCounter(Recommendation recommendation) {
        log.info("Создание счётчика выдачи рекомендаций.");

        this.statsCounters.put(recommendation.getId(), new Stats().setRecommendation(recommendation));
    }

    public void deleteCounter(UUID recommendationId) {
        log.info("Удаление данных статистики выдачи рекомендаций из хранилища.");

        this.statsCounters.remove(recommendationId);
    }

    private List<Stats> getStatsList() {
        log.info("Извлечение данных статистики выдачи рекомендаций из хранилища.");

        return new ArrayList<>(statsCounters.values());
    }
}
