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

import static pro.sky.recommendations.utility.TransactionListDataUtility.productUsage;
import static pro.sky.recommendations.utility.TransactionListDataUtility.totalDeposit;
import static pro.sky.recommendations.utility.constant.ProductType.*;

@Component
@Qualifier ("invest500")
@RequiredArgsConstructor
public class Invest500RuleSet implements RecommendationRuleSet {
    private final RecommendationRepository recommendationRepository;
    private final TransactionRepository transactionRepository;


    @Override
    public Optional<Recommendation> validateRecommendationRule(UUID userId) {
        List<Transaction> transactions = transactionRepository.findAllTransactionByUserId(userId);

        // Проверка 1: Пользователь использует как минимум один продукт с типом DEBIT
        boolean checkRule1 = productUsage(transactions, DEBIT);

        // Проверка 2: Пользователь не использует продукты с типом INVEST
        boolean checkRule2 = !productUsage(transactions, INVEST);

        // Проверка 3: Сумма пополнений продуктов с типом SAVING больше 1000 ₽
        boolean checkRule3 = totalDeposit(transactions, SAVING) > 1000;

        if (checkRule1 && checkRule2 && checkRule3) {
            return recommendationRepository.findByName("Invest 500");
        }
        return Optional.empty();
    }
}

