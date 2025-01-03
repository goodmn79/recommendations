/*
Файл конфигурации для подключения к базе данных recommendation.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.configuration;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pro.sky.recommendations.service.InitService;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class RecommendationDataSourceConfiguration {
    private final InitService initService;

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
        return new JdbcTemplate(dataSource);
    }

    // Создание таблиц при запуске приложения
    @PostConstruct
    public void initializeTables() {
        log.info("Initializing tables...");

        initService.createTableRecommendations();

        initService.createTableQueries();
    }
}
