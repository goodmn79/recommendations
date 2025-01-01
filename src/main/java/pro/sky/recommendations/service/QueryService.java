package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.QueryData;
import pro.sky.recommendations.mapper.castom_mapper.QueryMapper;
import pro.sky.recommendations.model.Query;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.repository.QueryRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository queryRepository;

    private final Logger log = LoggerFactory.getLogger(QueryService.class);

    public List<Query> createRule(List<QueryData> queryData, Recommendation recommendation) {
        log.info("Invoke method 'QueryService: createRule'");

        List<Query> savedQuery = QueryMapper.toQuery(queryData, recommendation);

        return saveQuery(savedQuery);
    }

    public List<Query> saveQuery(List<Query> queries) {
        log.info("Invoke method 'QueryRepository: saveQuery'");

        return queryRepository.saveAll(queries);
    }

    public boolean isExistByRecommendationId(UUID recommendationId) {
        log.info("Invoke method 'QueryRepository: isExistByRecommendationId'");

        return queryRepository.isExistsByRecommendationId(recommendationId);
    }

    public List<Query> findAllByRecommendationId(UUID recommendationId) {
        log.info("Invoke method 'QueryRepository: findAllByRecommendationId'");

        return queryRepository.findAllByRecommendationId(recommendationId);
    }

    public void deleteBYRecommendationId(UUID recommendationId) {
        log.info("Invoke method 'QueryRepository: deleteBYRecommendationId'");

        queryRepository.deleteAllByRecommendationId(recommendationId);
    }
}
