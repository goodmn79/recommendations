/*
Файл конфигурации для подключения к базе данных recommendation.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RecommendationDataSourceConfiguration {
    private final Logger log= LoggerFactory.getLogger(RecommendationDataSourceConfiguration.class);

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
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        initializeRecommendations(jdbcTemplate);
        initializeQueries(jdbcTemplate);
        return jdbcTemplate;
    }

    public void initializeRecommendations(JdbcTemplate jdbcTemplate) {
        log.info("Creating a table RECOMMENDATIONS");

        String createTableSQL = "CREATE TABLE IF NOT EXISTS RECOMMENDATIONS (ID UUID PRIMARY KEY, PRODUCT_ID   UUID NOT NULL UNIQUE, PRODUCT_TEXT TEXT NOT NULL)";
        jdbcTemplate.execute(createTableSQL);
    }

    public void initializeQueries(JdbcTemplate jdbcTemplate) {
        log.info("Creating a table QUERIES");

        String createTableSQL = "CREATE TABLE IF NOT EXISTS QUERIES (ID UUID PRIMARY KEY, RECOMMENDATION_ID   UUID NOT NULL, QUERY VARCHAR(100) NOT NULL, ARGUMENTS VARCHAR(255), NEGATE BOOLEAN NOT NULL)";
        jdbcTemplate.execute(createTableSQL);
    }
}
