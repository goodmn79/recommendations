package pro.sky.recommendations.utility;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.Transaction;
import pro.sky.recommendations.repository.RecommendationRepository;
import pro.sky.recommendations.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static pro.sky.recommendations.utility.constant.ProductType.*;

@Component
@Qualifier("invest500")
@RequiredArgsConstructor
public class Invest500RuleSet implements RecommendationRuleSet {
    private final RecommendationRepository recommendationRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionListDataUtility utility;

    Logger logger = LoggerFactory.getLogger(Invest500RuleSet.class.getName());

    @Override
    public Optional<Recommendation> validateRecommendationRule(UUID userId) {
        logger.info("Invoke method validateRecommendationRule for 'Invest 500' recommendation");
        List<Transaction> transactions = transactionRepository.findAllTransactionByUserId(userId);

        // Проверка 1: Пользователь использует как минимум один продукт с типом DEBIT
        boolean checkRule1 = utility.productUsage(transactions, DEBIT);
        logger.debug("Check rule 1: {}", checkRule1);

        // Проверка 2: Пользователь не использует продукты с типом INVEST
        boolean checkRule2 = !utility.productUsage(transactions, INVEST);
        logger.debug("Check rule 2: {}", checkRule2);

        // Проверка 3: Сумма пополнений продуктов с типом SAVING больше 1000 ₽
        boolean checkRule3 = utility.totalDeposit(transactions, SAVING) > 1000;
        logger.debug("Check rule 3: {}", checkRule3);

        if (checkRule1 && checkRule2 && checkRule3) {
            return recommendationRepository.findByName("Invest 500");
        }
        return Optional.empty();
    }
}

