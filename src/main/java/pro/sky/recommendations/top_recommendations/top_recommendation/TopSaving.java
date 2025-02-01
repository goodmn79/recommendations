package pro.sky.recommendations.top_recommendations.top_recommendation;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Представление одной из топовых рекомендаций для пользователя, связанной с продуктом "Копилка" для накопления средств.
 * Этот класс представляет рекомендацию по использованию банковского инструмента для накопления средств на важные цели.
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */

@Component
@Getter
@Accessors(chain = true)
public class TopSaving implements TopRecommendation {
    private final UUID id = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");
    private final String productName = "Top Saving";
    private final String productText =
            "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем! Преимущества «Копилки»: Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет. Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости. Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг. Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!";
    private final String query =
            "SELECT EXISTS (SELECT 1 FROM Transactions t JOIN Users u ON  t.USER_ID = u.ID JOIN Products p ON p.ID = t.PRODUCT_ID WHERE u.ID = ? GROUP BY u.ID HAVING SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 AND (SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) >= 50000 OR SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) >= 50000) AND SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END))";
}
