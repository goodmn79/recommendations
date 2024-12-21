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
import pro.sky.recommendations.utility.SimpleCreditRuleSet;
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

@ExtendWith(MockitoExtension.class)
public class SimpleCreditRuleSetTest {

    @Mock
    private RecommendationRepository mockRecommendationRepo;

    @Mock
    private TransactionRepository mockTransactionRepo;

    @Mock
    private TransactionListDataUtility mockUtility;

    @InjectMocks
    private SimpleCreditRuleSet simpleCreditRuleSet;

    private List<Transaction> createMockTransactions() {
        List<Transaction> transactions = new ArrayList<>();


        // Создание mock для продукта
        Product mockProductDebit = Mockito.mock(Product.class);

        // Создание mock для пользователя
        User mockUser = Mockito.mock(User.class);

        // Создание транзакции
        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID());
        transaction1.setProduct(mockProductDebit);
        transaction1.setUser(mockUser);
        transaction1.setType("DEPOSIT");
        transaction1.setAmount(200_000);

        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setId(UUID.randomUUID());
        transaction2.setProduct(mockProductDebit);
        transaction2.setUser(mockUser);
        transaction2.setType("WITHDRAW");
        transaction2.setAmount(150_000);

        transactions.add(transaction2);

        return transactions;
    }

    @Test
    public void testValidateRecommendationRuleSuccess() {
        UUID userId = UUID.randomUUID();
        List<Transaction> mockTransactions = createMockTransactions();

        // Настройка поведения mock-репозитория
        when(mockTransactionRepo.findAllTransactionByUserId(userId)).thenReturn(mockTransactions);
        when(mockRecommendationRepo.findByName("Простой кредит")).thenReturn(Optional.of(new Recommendation()));

        // Настройка поведения mock-utility
        when(mockUtility.productUsage(mockTransactions, CREDIT)).thenReturn(false);
        when(mockUtility.totalDeposit(mockTransactions, DEBIT)).thenReturn(200_000);
        when(mockUtility.totalWithdraw(mockTransactions, DEBIT)).thenReturn(150_000);

        // Вызов тестируемого метода
        Optional<Recommendation> result = simpleCreditRuleSet.validateRecommendationRule(userId);

        // Проверка результата
        assertTrue(result.isPresent());
        assertFalse(mockUtility.productUsage(mockTransactions, CREDIT));
        assertTrue(mockUtility.totalDeposit(mockTransactions, DEBIT) > mockUtility.totalWithdraw(mockTransactions, DEBIT));
        assertTrue(mockUtility.totalWithdraw(mockTransactions, DEBIT) > 100_000);

        // Проверка вызовов
        verify(mockTransactionRepo).findAllTransactionByUserId(userId);
        verify(mockRecommendationRepo).findByName("Простой кредит");
    }

    @Test
    public void testValidateRecommendationRuleFail() {
        UUID userId = UUID.randomUUID();
        List<Transaction> mockTransactions = createMockTransactions();

        // Настройка поведения mock-репозитория
        when(mockTransactionRepo.findAllTransactionByUserId(userId)).thenReturn(mockTransactions);

        // Настройка поведения mock-utility
        when(mockUtility.productUsage(mockTransactions, CREDIT)).thenReturn(true);
        when(mockUtility.totalDeposit(mockTransactions, DEBIT)).thenReturn(200_000);
        when(mockUtility.totalWithdraw(mockTransactions, DEBIT)).thenReturn(150_000);

        // Вызов тестируемого метода
        Optional<Recommendation> result = simpleCreditRuleSet.validateRecommendationRule(userId);

        // Проверка результата
        assertTrue(result.isEmpty());
        assertTrue(mockUtility.productUsage(mockTransactions, CREDIT));
        assertTrue(mockUtility.totalDeposit(mockTransactions, DEBIT) > mockUtility.totalWithdraw(mockTransactions, DEBIT));
        assertTrue(mockUtility.totalWithdraw(mockTransactions, DEBIT) > 100_000);

        // Проверка вызова
        verify(mockTransactionRepo).findAllTransactionByUserId(userId);
    }
}
