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
import static pro.sky.recommendations.constant.ProductType.DEBIT;
import static pro.sky.recommendations.constant.ProductType.SAVING;

@ExtendWith(MockitoExtension.class)
class TopSavingQueruSetTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RecommendRepository recommendRepository;

    @Mock
    private TransactionListDataUtility utility;

    @InjectMocks
    private TopSavingRuleSet topSavingRuleSet;

    private UUID userId;
    private String recommendationName;
    private Recommend topSaving;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        recommendationName = "Top Saving";
        topSaving = mock(Recommend.class);
        transactions = List.of(mock(Transaction.class));
    }

    @Test
    void validateRecommendationRule_whenCorrectTotalDepositDEBITAmountAndOtherRulesFollowed_shouldReturnOptionalWithCorrectRecommendations() {
        when(transactionRepository.findAllTransactionByUserId(any(UUID.class))).thenReturn(transactions);
        when(recommendRepository.findByName(recommendationName)).thenReturn(Optional.of(topSaving));
        when(utility.productUsage(transactions, DEBIT)).thenReturn(true);
        when(utility.totalDeposit(transactions, DEBIT)).thenReturn(50_000);
        when(utility.totalWithdraw(transactions, DEBIT)).thenReturn(20_000);

        Optional<Recommend> actualOption = topSavingRuleSet.validateRecommendationRule(userId);

        verify(transactionRepository).findAllTransactionByUserId(userId);
        verify(recommendRepository).findByName(recommendationName);

        assertThat(actualOption.isPresent()).isTrue();
        assertThat(actualOption.get()).isEqualTo(topSaving);
    }

    @Test
    void validateRecommendationRule_whenCorrectTotalDepositSAVINGAmountAndOtherRulesFollowed_shouldReturnOptionalWithCorrectRecommendations() {
        when(transactionRepository.findAllTransactionByUserId(any(UUID.class))).thenReturn(transactions);
        when(recommendRepository.findByName(recommendationName)).thenReturn(Optional.of(topSaving));
        when(utility.productUsage(transactions, DEBIT)).thenReturn(true);
        when(utility.totalDeposit(transactions, DEBIT)).thenReturn(20_000);
        when(utility.totalDeposit(transactions, SAVING)).thenReturn(50_000);
        when(utility.totalWithdraw(transactions, DEBIT)).thenReturn(10_000);

        Optional<Recommend> actualOption = topSavingRuleSet.validateRecommendationRule(userId);

        verify(transactionRepository).findAllTransactionByUserId(userId);
        verify(recommendRepository).findByName(recommendationName);

        assertThat(actualOption.isPresent()).isTrue();
        assertThat(actualOption.get()).isEqualTo(topSaving);
    }

    @Test
    void validateRecommendationRule_whenNotUsageProductTypeDEBIT_shouldReturnOptionalEmpty() {
        when(transactionRepository.findAllTransactionByUserId(any(UUID.class))).thenReturn(transactions);
        when(utility.productUsage(transactions, DEBIT)).thenReturn(false);
        when(utility.totalDeposit(transactions, DEBIT)).thenReturn(50_000);
        when(utility.totalWithdraw(transactions, DEBIT)).thenReturn(10_000);

        Optional<Recommend> actualOption = topSavingRuleSet.validateRecommendationRule(userId);

        verify(transactionRepository).findAllTransactionByUserId(userId);

        assertThat(actualOption.isEmpty()).isTrue();
    }

    @Test
    void validateRecommendationRule_whenInvalidTotalDepositDEBITAmount_shouldReturnOptionalEmpty() {
        when(transactionRepository.findAllTransactionByUserId(any(UUID.class))).thenReturn(transactions);
        when(utility.productUsage(transactions, DEBIT)).thenReturn(true);
        when(utility.totalDeposit(transactions, DEBIT)).thenReturn(20_000);
        when(utility.totalDeposit(transactions, SAVING)).thenReturn(50_000);
        when(utility.totalWithdraw(transactions, DEBIT)).thenReturn(10_000);

        Optional<Recommend> actualOption = topSavingRuleSet.validateRecommendationRule(userId);

        verify(transactionRepository).findAllTransactionByUserId(userId);

        assertThat(actualOption.isEmpty()).isTrue();
    }

    @Test
    void validateRecommendationRule_whenInvalidTotalWithdraw_shouldReturnOptionalEmpty() {
        when(transactionRepository.findAllTransactionByUserId(any(UUID.class))).thenReturn(transactions);
        when(utility.productUsage(transactions, DEBIT)).thenReturn(true);
        when(utility.totalDeposit(transactions, DEBIT)).thenReturn(20_000);
        when(utility.totalDeposit(transactions, SAVING)).thenReturn(50_000);
        when(utility.totalWithdraw(transactions, DEBIT)).thenReturn(25_000);

        Optional<Recommend> actualOption = topSavingRuleSet.validateRecommendationRule(userId);

        verify(transactionRepository).findAllTransactionByUserId(userId);

        assertThat(actualOption.isEmpty()).isTrue();
    }
}
