package pro.sky.recommendations.UtilityTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.model.Product;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.Transaction;
import pro.sky.recommendations.model.User;
import pro.sky.recommendations.repository.RecommendationRepository;
import pro.sky.recommendations.repository.TransactionRepository;
import pro.sky.recommendations.utility.TopSavingRuleSet;
import pro.sky.recommendations.utility.TransactionListDataUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pro.sky.recommendations.utility.constant.ProductType.*;
import static pro.sky.recommendations.utility.constant.ProductType.SAVING;

@ExtendWith(MockitoExtension.class)
public class TopSavingRuleSetTest {
    @Mock
    private RecommendationRepository mockRecommendationRepo;

    @Mock
    private TransactionRepository mockTransactionRepo;

    @Mock
    private TransactionListDataUtility mockUtility;

    @InjectMocks
    private TopSavingRuleSet topSavingRuleSet;

    private List<Transaction> createMockTransactions() {
        List<Transaction> transactions = new ArrayList<>();


        // Создание mock для продукта
        Product mockProductDebit = Mockito.mock(Product.class);
        Product mockProductSaving = Mockito.mock(Product.class);

        // Создание mock для пользователя
        User mockUser = Mockito.mock(User.class);

        // Создание транзакции
        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID());
        transaction1.setProduct(mockProductDebit);
        transaction1.setUser(mockUser);
        transaction1.setType("DEPOSIT");
        transaction1.setAmount(50_000);

        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setId(UUID.randomUUID());
        transaction2.setProduct(mockProductDebit);
        transaction2.setUser(mockUser);
        transaction2.setType("WITHDRAW");
        transaction2.setAmount(10_000);

        transactions.add(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setId(UUID.randomUUID());
        transaction3.setProduct(mockProductSaving);
        transaction3.setUser(mockUser);
        transaction3.setType("DEPOSIT");
        transaction3.setAmount(20_000);

        transactions.add(transaction3);

        return transactions;
    }

    @Test
    public void testValidateRecommendationRuleSuccess() {
        UUID userId = UUID.randomUUID();
        List<Transaction> mockTransactions = createMockTransactions();

        // Настройка поведения mock-репозитория
        when(mockTransactionRepo.findAllTransactionByUserId(userId)).thenReturn(mockTransactions);
        when(mockRecommendationRepo.findByName("Top Saving")).thenReturn(Optional.of(new Recommendation()));

        // Настройка поведения mock-utility
        when(mockUtility.productUsage(mockTransactions, DEBIT)).thenReturn(true);
        when(mockUtility.totalDeposit(mockTransactions, DEBIT)).thenReturn(50_000);
        when(mockUtility.totalWithdraw(mockTransactions, DEBIT)).thenReturn(10_000);


        // Вызов тестируемого метода
        Optional<Recommendation> result = topSavingRuleSet.validateRecommendationRule(userId);

        // Проверка результата
        assertTrue(result.isPresent());
        assertTrue(mockUtility.productUsage(mockTransactions, DEBIT));
        assertTrue(mockUtility.totalDeposit(mockTransactions, DEBIT) > mockUtility.totalWithdraw(mockTransactions, DEBIT));
        assertTrue(mockUtility.totalDeposit(mockTransactions, DEBIT) >= 50_000 || mockUtility.totalDeposit(mockTransactions, SAVING) >= 50_000);

        // Проверка вызовов
        verify(mockTransactionRepo).findAllTransactionByUserId(userId);
        verify(mockRecommendationRepo).findByName("Top Saving");
    }

    @Test
    public void testValidateRecommendationRuleFail() {
        UUID userId = UUID.randomUUID();
        List<Transaction> mockTransactions = createMockTransactions();

        // Настройка поведения mock-репозитория
        when(mockTransactionRepo.findAllTransactionByUserId(userId)).thenReturn(mockTransactions);

        // Настройка поведения mock-utility
        when(mockUtility.productUsage(mockTransactions, DEBIT)).thenReturn(false);
        when(mockUtility.totalDeposit(mockTransactions, DEBIT)).thenReturn(50_000);
        when(mockUtility.totalWithdraw(mockTransactions, DEBIT)).thenReturn(10_000);


        // Вызов тестируемого метода
        Optional<Recommendation> result = topSavingRuleSet.validateRecommendationRule(userId);

        // Проверка результата
        assertTrue(result.isEmpty());
        assertFalse(mockUtility.productUsage(mockTransactions, DEBIT));
        assertTrue(mockUtility.totalDeposit(mockTransactions, DEBIT) > mockUtility.totalWithdraw(mockTransactions, DEBIT));
        assertTrue(mockUtility.totalDeposit(mockTransactions, DEBIT) >= 50_000 || mockUtility.totalDeposit(mockTransactions, SAVING) >= 50_000);

        // Проверка вызовов
        verify(mockTransactionRepo).findAllTransactionByUserId(userId);
    }
}
