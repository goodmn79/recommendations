package pro.sky.recommendations.top_recommendations.top_recommendation;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Представление одной из топовых рекомендаций для пользователя, связанной с получением простого кредита.
 * <br> Этот класс представляет рекомендацию по предложению простого кредита с выгодными условиями.
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Component
@Getter
@Accessors(chain = true)
public class SimpleCredit implements TopRecommendation {
    private final UUID id = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");
    private final String productName = "Простой кредит";
    private final String productText =
            "Откройте мир выгодных кредитов с нами! Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту. Почему выбирают нас: Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов. Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении. Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое. Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!";
    private final String query =
            "SELECT EXISTS (SELECT 1 FROM Transactions t JOIN Users u ON  t.USER_ID = u.ID JOIN Products p ON p.ID = t.PRODUCT_ID WHERE u.ID = ? GROUP BY u.ID HAVING SUM(CASE WHEN p.type = 'CREDIT' THEN 1 ELSE 0 END) = 0 AND (SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END)) AND SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END) > 100000)";
}
