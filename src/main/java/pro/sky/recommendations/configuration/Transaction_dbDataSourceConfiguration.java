/*
Файл конфигурации для подключения к базе данных transaction.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class Transaction_dbDataSourceConfiguration {

    // Регистрация бина управляющего соединением с базой данных
    @Bean(name = "transactionDataSource")
    public DataSource transactionDataSource(
            @Value("${application.transaction-db.url}") String transactionUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(transactionUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    // Регистрация бина обеспечивающего взаимодействие с базой данных
    @Bean(name = "transactionJdbcTemplate")
    public JdbcTemplate transactionJdbcTemplate(@Qualifier("transactionDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
