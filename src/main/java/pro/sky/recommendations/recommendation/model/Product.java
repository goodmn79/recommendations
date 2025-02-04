package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * Модель, представляющая продукт в системе.
 * <p>
 * Этот класс содержит информацию о продукте, включая его уникальный идентификатор, название и тип.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Accessors(chain = true)
public class Product {
    private UUID id;

    private String name;

    private String type;
}
