package pro.sky.recommendations.database.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Конфигурационный класс для настройки подключения к базе данных transaction.mv.db
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

@Configuration
public class TransactionDataSourceConfiguration {

    /**
     * Создает и настраивает источник данных для базы данных транзакций.
     *
     * @param transactionUrl URL базы данных транзакций из конфигурации
     * @return Настроенный источник данных HikariDataSource
     */
    @Bean(name = "transactionDataSource")
    public DataSource transactionDataSource(
            @Value("${application.transaction-db.url}") String transactionUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(transactionUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    /**
     * Создает JdbcTemplate для работы с базой данных транзакций.
     *
     * @param dataSource Источник данных для базы транзакций
     * @return Настроенный экземпляр JdbcTemplate
     */
    @Bean(name = "transactionJdbcTemplate")
    public JdbcTemplate transactionJdbcTemplate(@Qualifier("transactionDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
