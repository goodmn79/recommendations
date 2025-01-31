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

/**
 * Сервис для управления статистикой рекомендаций.
 * Хранит и обрабатывает информацию о том, сколько раз были выданы рекомендации для каждого банковского продукта.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository statsRepository;

    private final StatsMapper statsMapper;

    private Map<UUID, Stats> statsCounters;

    private static final Logger log = LoggerFactory.getLogger(StatsService.class);

    /**
     * Инициализация хранилища данных статистики при запуске приложения.
     * Загружает существующую статистику из базы данных в память.
     */
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
        log.info("Хранилище данных статистики успешно инициализировано.");
    }

    /**
     * Сохранение данных статистики при завершении работы приложения.
     * Сохраняет все текущие данные статистики в базу данных.
     */
    @PreDestroy
    public void save() {
        log.info("Сохранение данных статистики выдачи рекомендаций в базе данных.");

        List<Stats> statsList = this.getStatsList();
        statsRepository.saveAll(statsList);
    }

    /**
     * Обновление счётчиков статистики для рекомендаций.
     * Увеличивает количество показов каждой рекомендации на 1.
     *
     * @param userRecommendation объект, содержащий рекомендации для конкретного пользователя
     */
    public void statsAccumulator(UserRecommendation userRecommendation) {
        log.info("Инкремент счётчика выдачи рекомендаций продукта.");

        userRecommendation.getRecommendations().forEach(recommendation -> {
            Stats stats = statsCounters.get(recommendation.getId());
            if (stats != null) {
                stats.increment();
            }
        });
    }

    /**
     * Получение всех данных статистики выдачи рекомендаций.
     *
     * @return список объектов StatsData, содержащих информацию о рекомендациях и их частоте
     */
    public List<StatsData> getAll() {
        log.info("Получение данных статистики выдачи рекомендаций.");

        List<Stats> statsDataList = this.getStatsList();
        return statsMapper.toStatsDataList(statsDataList);
    }

    /**
     * Создание нового счётчика для рекомендации.
     * Добавляет новую запись в хранилище статистики для указанной рекомендации.
     *
     * @param recommendation объект рекомендации, для которого создаётся счётчик
     */
    public void createCounter(Recommendation recommendation) {
        log.info("Создание счётчика выдачи рекомендаций.");

        this.statsCounters.put(recommendation.getId(), new Stats().setRecommendation(recommendation));
    }

    /**
     * Удаление счётчика для рекомендации.
     * Удаляет запись из хранилища статистики для указанной рекомендации.
     *
     * @param recommendationId идентификатор рекомендации, для которой удаляется счётчик
     */
    public void deleteCounter(UUID recommendationId) {
        log.info("Удаление данных статистики выдачи рекомендаций из хранилища.");

        this.statsCounters.remove(recommendationId);
    }

    /**
     * Преобразование хранилища статистики в список объектов Stats.
     *
     * @return список всех статистик в хранилище
     */
    private List<Stats> getStatsList() {
        log.info("Извлечение данных статистики выдачи рекомендаций из хранилища.");

        return new ArrayList<>(statsCounters.values());
    }
}
