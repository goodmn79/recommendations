package pro.sky.recommendations.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.Transaction;
import pro.sky.recommendations.model.SimpleCredit;
import pro.sky.recommendations.repository.RecommendationRepository;
import pro.sky.recommendations.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SimpleCreditRule implements RecommendationRuleSet {
    private final SimpleCredit credit;
    private final RecommendationRepository recommendationRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Optional <Optional<Recommendation>> validateRecommendationRule(UUID userId) {
        List<Transaction> transactions = transactionRepository.findAllTransactionByUserId(userId);

        // Проверка 1. Пользователь не использует продукты с типом CREDIT.
        boolean checkRule1 = !credit.hasProduct(transactions);

        // Проверка 2. Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
        boolean checkRule2 = credit.totalDeposit(transactions) > credit.totalWithdraw(transactions);

        // Проверка 3. Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.
        boolean checkRule3 = credit.totalWithdraw(transactions) > 100_000;

        if (checkRule1 && checkRule2 && checkRule3) {
            return Optional.of(recommendationRepository.findByName("Простой кредит"));
        }
        return Optional.empty();
    }
}
