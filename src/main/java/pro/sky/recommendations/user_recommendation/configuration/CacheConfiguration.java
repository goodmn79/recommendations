package pro.sky.recommendations.user_recommendation.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для кэширования запросов с использованием библиотеки Caffeine.
 * <p>
 * Этот класс настраивает кэширование данных с помощью Caffeine, что позволяет ускорить обработку повторных запросов,
 * храня данные в памяти и улучшая производительность системы.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */


@Configuration
@EnableCaching
public class CacheConfiguration {
    /**
     * Регистрирует бин для управления кэшированием данных с использованием Caffeine.
     * <p>
     * Конфигурируется максимальный размер кэша в 100 элементов, а также включение статистики кэширования.
     * </p>
     *
     * @return {@link CacheManager} объект для управления кэшированием
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(100)
                .recordStats());
        return cacheManager;
    }
}
