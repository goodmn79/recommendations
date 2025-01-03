/*
Файл сервиса для инициализации таблиц в базе данных recommendation.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.repository.InitRepository;

import static pro.sky.recommendations.constant.RecommendationDataBase.QUERIES;
import static pro.sky.recommendations.constant.RecommendationDataBase.RECOMMENDATIONS;

@Service
@RequiredArgsConstructor
public class InitService {
    private final InitRepository initRepository;

    private final Logger log = LoggerFactory.getLogger(InitService.class);

    // Создание таблицы RECOMMENDATIONS
    public void createTableRecommendations() {
        log.info("Invoke method 'createTableRecommendations'");

        initRepository.createTableIfNotExists(RECOMMENDATIONS);
    }

    // Создание таблицы QUERIES
    public void createTableQueries() {
        log.info("Invoke method 'createTableQueries'");

        initRepository.createTableIfNotExists(QUERIES);
    }
}
