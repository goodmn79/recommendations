/*
Файл преобразования объекта передачи данных для рекомендуемого банковского продукта в сущность для сохранения в базу данных
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.mapper.castom_mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.recommendation.dto.RecommendationData;

import java.util.List;

@Component
public class RecommendationMapper {
    private final Logger log = LoggerFactory.getLogger(RecommendationMapper.class);

    public RecommendationData fromRecommendation(Recommendation recommendation) {
        return new RecommendationData()
                .setId(recommendation.getId())
                .setProductName(recommendation.getProduct().getName())
                .setProductText(recommendation.getProductText());
    }

    public List<RecommendationData> fromRecommendationList(List<Recommendation> recommendations) {
        log.info("Mapping Recommendation.class to RecommendationData.class");

        return recommendations
                .stream()
                .map(this::fromRecommendation)
                .toList();
    }
}
