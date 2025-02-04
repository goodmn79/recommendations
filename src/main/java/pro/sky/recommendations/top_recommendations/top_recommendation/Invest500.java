package pro.sky.recommendations.top_recommendations.top_recommendation;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Представление одной из топовых рекомендаций для пользователя, связанной с инвестициями.
 * <br> Этот класс представляет конкретную рекомендацию по открытию индивидуального инвестиционного счета (ИИС).
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Component
@Getter
@Accessors(chain = true)
public class Invest500 implements TopRecommendation {
    private final UUID id = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");
    private final String productName = "Invest 500";
    private final String productText =
            "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!";
    private final String query =
            "SELECT EXISTS (SELECT 1 FROM Transactions t JOIN Users u ON  t.USER_ID = u.ID JOIN Products p ON p.ID = t.PRODUCT_ID WHERE u.ID = ? GROUP BY u.ID HAVING SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 AND SUM(CASE WHEN p.type = 'INVEST' THEN 1 ELSE 0 END) = 0 AND SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > 1000)";
}
