/*
Файл сервиса для управления данными приложения
Powered by ©AYE.team
 */

package pro.sky.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
    private final Logger log = LoggerFactory.getLogger(ManagementService.class);

    // Очистка кэша
    @CacheEvict(value = "userRecommendationCache", allEntries = true)
    public void clearCache() {
        log.info("Clearing cache");
    }
}
