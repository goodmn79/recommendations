package pro.sky.recommendations.recommendation.mapper.castom_mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.dto.QueryData;
import pro.sky.recommendations.recommendation.exception.InvalidQueryDataException;
import pro.sky.recommendations.recommendation.model.Query;
import pro.sky.recommendations.recommendation.model.Recommendation;

import java.util.List;

/**
 * Маппер для преобразования объектов {@link QueryData} в {@link Query} и обратно.
 * <p>
 * Используется для конвертации данных правила рекомендации в сущности, которые могут быть сохранены в базу данных, а также для извлечения данных из базы в представление.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Component
public final class QueryMapper {

    private final Logger log = LoggerFactory.getLogger(QueryMapper.class);

    /**
     * Преобразует список объектов {@link QueryData} в список объектов {@link Query}.
     * <br> Это преобразование выполняется с использованием информации о рекомендации, к которой относится каждое правило.
     *
     * @param queryData      список объектов {@link QueryData}, которые нужно преобразовать.
     * @param recommendation объект {@link Recommendation}, к которому относится правило.
     * @return преобразованный список объектов {@link Query}.
     * @throws InvalidQueryDataException если данные запроса некорректны.
     */
    public List<Query> toQuery(List<QueryData> queryData, Recommendation recommendation) throws InvalidQueryDataException {
        log.info("Преобразование QueryData.class в Query.class");

        return queryData
                .stream()
                .map(data -> new Query()
                        .setRecommendation(recommendation)
                        .setQuery(data.getQuery())
                        .setArguments(data.getArguments())
                        .setNegate(data.getNegate()))
                .toList();
    }

    /**
     * Преобразует список объектов {@link Query} в список объектов {@link QueryData}.
     * <br>Это преобразование используется для извлечения данных из базы в представление.
     *
     * @param queries список объектов {@link Query}, которые нужно преобразовать.
     * @return преобразованный список объектов {@link QueryData}.
     */
    public List<QueryData> toQueryData(List<Query> queries) {
        log.info("Преобразование Query.class в QueryData.class");

        return queries
                .stream()
                .map(data -> new QueryData()
                        .setQuery(data.getQuery())
                        .setArguments(data.getArguments())
                        .setNegate(data.getNegate()))
                .toList();
    }
}
