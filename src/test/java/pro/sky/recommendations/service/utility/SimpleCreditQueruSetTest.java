package pro.sky.recommendations.service.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.model.Recommend;
import pro.sky.recommendations.model.Transaction;
import pro.sky.recommendations.repository.RecommendRepository;
import pro.sky.recommendations.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pro.sky.recommendations.constant.ProductType.CREDIT;
import static pro.sky.recommendations.constant.ProductType.DEBIT;

@ExtendWith(MockitoExtension.class)
class SimpleCreditQueruSetTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RecommendRepository recommendRepository;

    @Mock
    private TransactionListDataUtility utility;

    @InjectMocks
    private SimpleCreditRuleSet simpleCreditRuleSet;

    private UUID userId;
    private String recommendationName;
    private Recommend simpleCredit;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        recommendationName = "Простой кредит";
        simpleCredit = mock(Recommend.class);
        transactions = List.of(mock(Transaction.class));
    }

    @Test
    void validateRecommendationRule_whenAllRulesFollowed_shouldReturnCorrectRecommendations() {
        when(transactionRepository.findAllTransactionByUserId(any(UUID.class))).thenReturn(transactions);
        when(recommendRepository.findByName(recommendationName)).thenReturn(Optional.of(simpleCredit));
        when(utility.productUsage(transactions, CREDIT)).thenReturn(false);
        when(utility.totalDeposit(transactions, DEBIT)).thenReturn(200_000);
        when(utility.totalWithdraw(transactions, DEBIT)).thenReturn(150_000);

        Optional<Recommend> actualOption = simpleCreditRuleSet.validateRecommendationRule(userId);

        verify(transactionRepository).findAllTransactionByUserId(userId);
        verify(recommendRepository).findByName(recommendationName);

        assertThat(actualOption.isPresent()).isTrue();
        assertThat(actualOption.get()).isEqualTo(simpleCredit);
    }

    @Test
    void validateRecommendationRule_whenUsageProductTypeCREDIT_shouldReturnOptionalEmpty() {
        when(transactionRepository.findAllTransactionByUserId(any(UUID.class))).thenReturn(transactions);
        when(utility.productUsage(transactions, CREDIT)).thenReturn(true);
        when(utility.totalDeposit(transactions, DEBIT)).thenReturn(200_000);
        when(utility.totalWithdraw(transactions, DEBIT)).thenReturn(150_000);

        Optional<Recommend> actualOption = simpleCreditRuleSet.validateRecommendationRule(userId);

        verify(transactionRepository).findAllTransactionByUserId(userId);

        assertThat(actualOption.isEmpty()).isTrue();
    }

    @Test
    void validateRecommendationRule_whenInvalidTotalDepositDEBITAmount_shouldReturnOptionalEmpty() {
        when(transactionRepository.findAllTransactionByUserId(any(UUID.class))).thenReturn(transactions);
        when(utility.productUsage(transactions, CREDIT)).thenReturn(false);
        when(utility.totalDeposit(transactions, DEBIT)).thenReturn(200_000);
        when(utility.totalWithdraw(transactions, DEBIT)).thenReturn(250_000);

        Optional<Recommend> actualOption = simpleCreditRuleSet.validateRecommendationRule(userId);

        verify(transactionRepository).findAllTransactionByUserId(userId);

        assertThat(actualOption.isEmpty()).isTrue();
    }

    @Test
    void validateRecommendationRule_whenInvalidTotalWithdrawDEBITAmount_shouldReturnOptionalEmpty() {
        when(transactionRepository.findAllTransactionByUserId(any(UUID.class))).thenReturn(transactions);
        when(utility.productUsage(transactions, CREDIT)).thenReturn(false);
        when(utility.totalDeposit(transactions, DEBIT)).thenReturn(200_000);
        when(utility.totalWithdraw(transactions, DEBIT)).thenReturn(100_000);

        Optional<Recommend> actualOption = simpleCreditRuleSet.validateRecommendationRule(userId);

        verify(transactionRepository).findAllTransactionByUserId(userId);

        assertThat(actualOption.isEmpty()).isTrue();
    }
}
