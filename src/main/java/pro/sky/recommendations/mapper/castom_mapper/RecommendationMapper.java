/*
Файл преобразования объекта передачи данных для рекомендуемого банковского продукта в сущность для сохранения в базу данных
Powered by ©AYE.team
 */

package pro.sky.recommendations.mapper.castom_mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.recommendations.dto.RecommendationDTO;
import pro.sky.recommendations.model.Recommendation;

import java.util.List;

public class RecommendationMapper {
    private static final Logger log = LoggerFactory.getLogger(RecommendationMapper.class);

    public static RecommendationDTO fromRecommendation(Recommendation recommendation) {
        log.info("Invoke method RecommendationMapper: 'fromRecommendation'");

        return new RecommendationDTO()
                .setId(recommendation.getId())
                .setProductName(recommendation.getProduct().getName())
                .setProductText(recommendation.getProductText());
    }

    public static List<RecommendationDTO> fromRecommendationList(List<Recommendation> recommendations) {
        log.info("Invoke method RecommendationMapper: 'fromRecommendationList'");

        return recommendations
                .stream()
                .map(RecommendationMapper::fromRecommendation)
                .toList();
    }
}
