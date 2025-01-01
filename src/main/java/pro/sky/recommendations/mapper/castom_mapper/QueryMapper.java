/*
Файл преобразования объекта передачи данных для правила рекомендации банковского продукта в сущность для сохранения в базу данных
Powered by ©AYE.team
 */

package pro.sky.recommendations.mapper.castom_mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.recommendations.dto.QueryData;
import pro.sky.recommendations.exception.InvalidQueryDataException;
import pro.sky.recommendations.model.Query;
import pro.sky.recommendations.model.Recommendation;

import java.util.List;

public final class QueryMapper {
    private static final Logger log = LoggerFactory.getLogger(QueryMapper.class);

    public static List<Query> toQuery(List<QueryData> queryData, Recommendation recommendation) throws InvalidQueryDataException {
        log.info("Invoke method QueryMapper.toQuery");

        return queryData
                .stream()
                .map(data -> new Query()
                        .setRecommendation(recommendation)
                        .setQuery(data.getQuery())
                        .setArguments(data.getArguments())
                        .setNegate(data.getNegate())).toList();
    }

    public static List<QueryData> toQueryData(List<Query> queries) {
        log.info("Invoke method QueryMapper.toQueryData");

        return queries
                .stream()
                .map(data -> new QueryData()
                        .setQuery(data.getQuery())
                        .setArguments(data.getArguments())
                        .setNegate(data.getNegate())).toList();
    }
}
