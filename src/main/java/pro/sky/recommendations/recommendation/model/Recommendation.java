package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

/**
 * Модель, представляющая рекомендацию банковского продукта.
 * <p>
 * Этот класс содержит информацию о рекомендации, включая продукт, текст описания и связанные правила.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Accessors(chain = true)
public class Recommendation {
    private UUID id;

    private Product product;

    private String productText;

    private List<Query> rule;
}
