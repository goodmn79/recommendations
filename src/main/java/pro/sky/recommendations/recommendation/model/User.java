/*
 * Модель, представляющая пользователя в системе.
 * Этот класс содержит информацию о пользователе, включая его уникальный идентификатор,
 * имя, фамилию и полное имя.
 * @author Powered by ©AYE.team
 * @version 1.0
 */

package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class User {

    private UUID id;

    private String userName;

    private String lastName;

    private String firstName;

    /**
     * Получает полное имя пользователя, объединяя его имя и фамилию.
     *
     * @return строку, представляющую полное имя пользователя (имя + фамилия).
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
