package pro.sky.recommendations.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.repository.TransactionRepository;

import java.util.UUID;

/**
 * Сервис для работы с транзакциями.
 * Этот класс предоставляет метод для проверки соответствия правилам рекомендаций банковских продуктов.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final Logger log = LoggerFactory.getLogger(TransactionRepository.class);

    /**
     * Проверка соответствия требованию правила рекомендации банковского продукта для пользователя.
     * Метод выполняет запрос к базе данных, чтобы проверить соответствие транзакций заданному запросу.
     *
     * @param query  SQL запрос, который представляет собой правило для проверки.
     * @param userId идентификатор пользователя, для которого выполняется проверка.
     * @return {@code true}, если транзакции соответствуют правилу; {@code false} в противном случае.
     */
    public boolean isCompliance(String query, UUID userId) {
        log.debug("Invoke method: 'isCompliance'");

        return transactionRepository.isCompliance(query, userId);
    }
}
