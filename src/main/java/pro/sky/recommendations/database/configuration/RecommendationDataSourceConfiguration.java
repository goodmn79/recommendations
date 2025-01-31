package pro.sky.recommendations.database.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Конфигурационный класс для настройки источника данных рекомендаций.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Configuration
public class RecommendationDataSourceConfiguration {

    /**
     * Создает и настраивает источник данных для базы данных рекомендаций.
     *
     * @param recommendationUrl URL базы данных рекомендаций из конфигурации
     * @return Настроенный источник данных HikariDataSource
     */
    @Bean(name = "recommendationDataSource")
    public DataSource recommendationDataSource(
            @Value("${application.recommendation-db.url}") String recommendationUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    /**
     * Создает JdbcTemplate для работы с базой данных рекомендаций.
     *
     * @param dataSource Источник данных для базы рекомендаций
     * @return Настроенный экземпляр JdbcTemplate
     */
    @Bean(name = "recommendationJdbcTemplate")
    public JdbcTemplate recommendationJdbcTemplate(@Qualifier("recommendationDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
