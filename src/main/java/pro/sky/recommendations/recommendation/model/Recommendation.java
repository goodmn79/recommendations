/*
 * Модель, представляющая рекомендацию банковского продукта.
 * Этот класс содержит информацию о рекомендации, включая продукт, текст описания и связанные правила.
 * @author Powered by ©AYE.team
 * @version 1.0
 */

package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class Recommendation {

    private UUID id;

    private Product product;

    private String productText;

    private List<Query> rule;
}
