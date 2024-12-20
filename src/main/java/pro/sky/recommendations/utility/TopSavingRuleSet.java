package pro.sky.recommendations.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.Transaction;
import pro.sky.recommendations.repository.RecommendationRepository;
import pro.sky.recommendations.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pro.sky.recommendations.utility.TransactionListDataUtility.*;
import static pro.sky.recommendations.utility.constant.ProductType.DEBIT;
import static pro.sky.recommendations.utility.constant.ProductType.SAVING;

@Component
@Qualifier("topSaving")
@RequiredArgsConstructor
public class TopSavingRuleSet implements RecommendationRuleSet {
    private final RecommendationRepository recommendationRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Optional<Recommendation> validateRecommendationRule(UUID userId) {
        List<Transaction> transactions = transactionRepository.findAllTransactionByUserId(userId);

        // Проверка 1: Пользователь использует как минимум один продукт с типом DEBIT
        boolean checkRule1 = productUsage(transactions, DEBIT);

        // Проверка 2: Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽ ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
        boolean checkRule2 = totalDeposit(transactions, DEBIT) >= 50_000 || totalDeposit(transactions, SAVING) >= 50_000;

        // Проверка 3. Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
        boolean checkRule3 = totalDeposit(transactions, DEBIT) > totalWithdraw(transactions, DEBIT);

        if (checkRule1 && checkRule2 && checkRule3) {
            return recommendationRepository.findByName("Top Saving");
        }
        return Optional.empty();
    }
}
