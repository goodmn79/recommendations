package pro.sky.recommendations.serviceRecommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.Transaction;
import pro.sky.recommendations.modelRecommendation.Invest;
import pro.sky.recommendations.repository.RecommendationRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InvestRule implements RecommendationRuleSet {
    private final Invest invest;
    private final RecommendationRepository recommendationRepository;

    @Override
    public Optional <Optional<Recommendation>> validateRecommendationRule(List<Transaction> transactions) {
        // Проверка 1: Пользователь использует как минимум один продукт с типом DEBIT
        boolean checkRule1 = invest.hasProduct(transactions);

        // Проверка 2: Пользователь не использует продукты с типом INVEST
        boolean checkRule2 = !invest.hasInvestProduct(transactions);

        // Проверка 3: Сумма пополнений продуктов с типом SAVING больше 1000 ₽
        boolean checkRule3 = invest.totalDeposit(transactions) > 1000;

        if (checkRule1 && checkRule2 && checkRule3) {
            return Optional.of(recommendationRepository.findByName("Invest 500"));
        }
        return Optional.empty();
    }
}

