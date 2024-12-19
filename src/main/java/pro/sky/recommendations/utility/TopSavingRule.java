package pro.sky.recommendations.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.Transaction;
import pro.sky.recommendations.model.TopSaving;
import pro.sky.recommendations.repository.RecommendationRepository;
import pro.sky.recommendations.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TopSavingRule implements RecommendationRuleSet {
    private final TopSaving saving;
    private final RecommendationRepository recommendationRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Optional <Optional<Recommendation>> validateRecommendationRule(UUID userId) {
        List<Transaction> transactions = transactionRepository.findAllTransactionByUserId(userId);

        // Проверка 1: Пользователь использует как минимум один продукт с типом DEBIT
        boolean checkRule1 = saving.hasProduct(transactions);

        // Проверка 2: Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽ ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
        boolean checkRule2 = saving.totalDeposit(transactions) >= 50_000 || saving.totalDepositForSaving(transactions) >= 50_000;

        // Проверка 3. Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
        boolean checkRule3 = saving.totalWithdraw(transactions) > saving.totalWithdraw(transactions);

        if (checkRule1 && checkRule2 && checkRule3) {
            return Optional.of(recommendationRepository.findByName("Top Saving"));
        }
        return Optional.empty();
    }
}
