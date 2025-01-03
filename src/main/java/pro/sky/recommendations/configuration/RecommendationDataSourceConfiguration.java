/*
Файл конфигурации для подключения к базе данных recommendation.mv.db
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
public class RecommendationDataSourceConfiguration {

    // Регистрация бина управляющего соединением с базой данных
    @Bean(name = "recommendationDataSource")
    public DataSource recommendationDataSource(
            @Value("${application.recommendation-db.url}") String recommendationUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    // Регистрация бина обеспечивающего взаимодействие с базой данных
    @Bean(name = "recommendationJdbcTemplate")
    public JdbcTemplate recommendationJdbcTemplate(@Qualifier("recommendationDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
