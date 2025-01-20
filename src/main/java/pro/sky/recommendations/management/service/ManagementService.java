/*
Файл сервиса для управления данными приложения
Powered by ©AYE.team
*/

package pro.sky.recommendations.management.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.management.dto.InfoManager;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final InfoManager infoManager;

    private final Logger log = LoggerFactory.getLogger(ManagementService.class);

    // Получениесервмсной информации
    public InfoManager infoManager() {
        log.info("Fetching info data");
        return infoManager;
    }

    // Очистка кэша
    @CacheEvict(value = "userRecommendationCache", allEntries = true)
    public void clearCache() {
        log.info("Clearing cache");
    }
}

