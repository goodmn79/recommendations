package pro.sky.recommendations.recommendation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с таблицей TRANSACTIONS в базе данных.
 * Этот класс предоставляет методы для выполнения операций, связанных с транзакциями, например, проверки соответствия правилам рекомендации.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(TransactionRepository.class);

    /**
     * Конструктор для инициализации репозитория.
     *
     * @param jdbcTemplate объект {@link JdbcTemplate}, используемый для работы с базой данных.
     */
    public TransactionRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Проверка соответствия требованию правила рекомендации банковского продукта для пользователя.
     * Этот метод выполняет SQL-запрос для проверки, соответствует ли пользователь определенному правилу рекомендации.
     *
     * @param query  SQL-запрос в виде строки, который проверяет соответствие.
     * @param userId идентификатор пользователя, который проверяется.
     * @return {@code true}, если пользователь соответствует правилу, иначе {@code false}.
     */
    public boolean isCompliance(String query, UUID userId) {
        log.debug("Проверка соответствия правилу получения рекомендации для пользователя с идентификатором '{}'", userId);

        boolean isCompliance = Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, userId));

        log.debug("Проверка завершена с результатом: '{}'", isCompliance);
        return isCompliance;
    }
}
