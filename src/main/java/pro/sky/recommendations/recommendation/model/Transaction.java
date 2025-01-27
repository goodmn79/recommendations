package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/*
 * Модель, представляющая транзакцию с банковским продуктом.
 * Этот класс содержит информацию о транзакции, включая продукт, пользователя,
 * тип транзакции и сумму.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class Transaction {

    private UUID id;

    private Product product;

    private User user;

    private String type;

    private int amount;
}
