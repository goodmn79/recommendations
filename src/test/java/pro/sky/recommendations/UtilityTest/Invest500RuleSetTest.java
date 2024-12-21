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
import pro.sky.recommendations.utility.Invest500RuleSet;
import pro.sky.recommendations.utility.TransactionListDataUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pro.sky.recommendations.utility.constant.ProductType.*;


@ExtendWith(MockitoExtension.class)
public class Invest500RuleSetTest {

    @Mock
    private RecommendationRepository mockRecommendationRepo;

    @Mock
    private TransactionRepository mockTransactionRepo;

    @Mock
    private TransactionListDataUtility mockUtility;

    @InjectMocks
    private Invest500RuleSet invest500RuleSet;

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
        transaction1.setAmount(1500);

        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setId(UUID.randomUUID());
        transaction2.setProduct(mockProductSaving);
        transaction2.setUser(mockUser);
        transaction2.setType("DEPOSIT");
        transaction2.setAmount(1500);

        transactions.add(transaction2);

        return transactions;
    }

    @Test
    public void testValidateRecommendationRuleSuccess() {
        UUID userId = UUID.randomUUID();
        List<Transaction> mockTransactions = createMockTransactions();

        // Настройка поведения mock-репозитория
        when(mockTransactionRepo.findAllTransactionByUserId(userId)).thenReturn(mockTransactions);
        when(mockRecommendationRepo.findByName("Invest 500")).thenReturn(Optional.of(new Recommendation()));

        // Настройка поведения mock-utility
        when(mockUtility.productUsage(mockTransactions, DEBIT)).thenReturn(true);
        when(mockUtility.productUsage(mockTransactions, INVEST)).thenReturn(false);
        when(mockUtility.totalDeposit(mockTransactions, SAVING)).thenReturn(1500);

        // Вызов тестируемого метода
        Optional<Recommendation> result = invest500RuleSet.validateRecommendationRule(userId);

        // Проверка результата
        assertTrue(result.isPresent());
        assertTrue(mockUtility.productUsage(mockTransactions, DEBIT));
        assertFalse(mockUtility.productUsage(mockTransactions, INVEST));
        assertTrue(mockUtility.totalDeposit(mockTransactions, SAVING) > 1000);

        // Проверка вызовов
        verify(mockTransactionRepo).findAllTransactionByUserId(userId);
        verify(mockRecommendationRepo).findByName("Invest 500");
    }

    @Test
    public void testValidateRecommendationRuleFail() {
        UUID userId = UUID.randomUUID();
        List<Transaction> mockTransactions = createMockTransactions();

        // Настройка поведения mock-репозитория
        when(mockTransactionRepo.findAllTransactionByUserId(userId)).thenReturn(mockTransactions);

        // Настройка поведения mock-utility
        when(mockUtility.productUsage(mockTransactions, DEBIT)).thenReturn(true);
        when(mockUtility.productUsage(mockTransactions, INVEST)).thenReturn(true);
        when(mockUtility.totalDeposit(mockTransactions, SAVING)).thenReturn(1500);

        // Вызов тестируемого метода
        Optional<Recommendation> result = invest500RuleSet.validateRecommendationRule(userId);

        // Проверка результата
        assertTrue(result.isEmpty());
        assertTrue(mockUtility.productUsage(mockTransactions, DEBIT));
        assertTrue(mockUtility.productUsage(mockTransactions, INVEST));
        assertTrue(mockUtility.totalDeposit(mockTransactions, SAVING) > 1000);

        // Проверка вызова
        verify(mockTransactionRepo).findAllTransactionByUserId(userId);
    }
}


