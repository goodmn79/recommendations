package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/*
 * Модель, представляющая продукт в системе.
 * Этот класс содержит информацию о продукте, включая его уникальный идентификатор,
 * название и тип.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class Product {

    private UUID id;

    private String name;

    private String type;
}
