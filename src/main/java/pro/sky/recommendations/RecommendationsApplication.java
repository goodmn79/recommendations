package pro.sky.recommendations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Главный класс приложения для рекомендаций, который запускает Spring Boot приложение.
 * <p>
 * Аннотирован:
 * <br>- {@link SpringBootApplication} для конфигурации Spring Boot приложения.
 * <br>- {@link EnableCaching} для включения кеширования в приложении.
 * <br>- {@link OpenAPIDefinition} для описания API с использованием OpenAPI.
 * <br>Этот класс является точкой входа в приложение.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@SpringBootApplication
@EnableCaching
@OpenAPIDefinition
public class RecommendationsApplication {

    /**
     * Точка входа в приложение. Запускает Spring Boot приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(RecommendationsApplication.class, args);
    }
}
