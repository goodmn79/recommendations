/*
 * Маппер для преобразования объектов {@link Recommendation} в объекты {@link RecommendationData}.
 * Используется для преобразования сущностей рекомендуемых банковских продуктов в объекты,
 * которые могут быть использованы в представлениях (например, для передачи клиенту).
 * @author Powered by ©AYE.team
 * @version 1.0
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

    /**
     * Преобразует объект {@link Recommendation} в объект {@link RecommendationData}.
     * Этот метод используется для передачи данных о рекомендации в формате, который удобен для отображения.
     *
     * @param recommendation объект {@link Recommendation}, который нужно преобразовать.
     * @return объект {@link RecommendationData}, содержащий информацию о рекомендации.
     */
    public RecommendationData fromRecommendation(Recommendation recommendation) {
        return new RecommendationData()
                .setId(recommendation.getId())
                .setProductName(recommendation.getProduct().getName())
                .setProductText(recommendation.getProductText());
    }

    /**
     * Преобразует список объектов {@link Recommendation} в список объектов {@link RecommendationData}.
     * Этот метод используется для преобразования списка рекомендаций в соответствующие данные, которые могут быть отображены.
     *
     * @param recommendations список объектов {@link Recommendation}, которые нужно преобразовать.
     * @return список объектов {@link RecommendationData}.
     */
    public List<RecommendationData> fromRecommendationList(List<Recommendation> recommendations) {
        log.info("Преобразование Recommendation.class в RecommendationData.class");

        return recommendations
                .stream()
                .map(this::fromRecommendation)
                .toList();
    }
}
