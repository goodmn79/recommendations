package pro.sky.recommendations.recommendation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.recommendation.repository.TransactionRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService transactionService;

    private UUID userId;
    private String query;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        query = "query";
    }

    @Test
    void isCompliance_whenComplianceIsTrue_shouldReturnTrue() {
        when(transactionRepository.isCompliance(anyString(), any(UUID.class))).thenReturn(true);

        boolean actual = transactionService.isCompliance(query, userId);

        verify(transactionRepository).isCompliance(query, userId);

        assertThat(actual).isTrue();
    }

    @Test
    void isCompliance_whenComplianceIsFalse_shouldReturnFalse() {
        when(transactionRepository.isCompliance(anyString(), any(UUID.class))).thenReturn(false);

        boolean actual = transactionService.isCompliance(query, userId);

        verify(transactionRepository).isCompliance(query, userId);

        assertThat(actual).isFalse();
    }
}