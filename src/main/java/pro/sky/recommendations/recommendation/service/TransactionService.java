/*
Файл сервиса для получения данных о транзакциях
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.repository.TransactionRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final Logger log = LoggerFactory.getLogger(TransactionRepository.class);

    // Проверка соответствия требованию правила рекомендации банковского продукта
    public boolean isCompliance(String query, UUID userId) {
        log.debug("Invoke method: 'isCompliance'");

        return transactionRepository.isCompliance(query, userId);
    }
}
