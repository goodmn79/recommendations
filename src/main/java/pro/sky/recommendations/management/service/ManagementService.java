package pro.sky.recommendations.management.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.management.dto.InfoManager;

/**
 * Сервис для управления данными приложения.
 * Предоставляет методы для работы с информацией о приложении и управления кэшем.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final InfoManager infoManager;

    private final Logger log = LoggerFactory.getLogger(ManagementService.class);

    /**
     * Получает информацию о названии и версии приложения.
     *
     * @return Объект InfoManager, содержащий информацию о приложении
     */
    public InfoManager infoManager() {
        log.info("Получение данных о названии и версии приложения.");
        return infoManager;
    }

    /**
     * Очищает кэш рекомендаций пользователей.
     * Удаляет все записи из кэша 'userRecommendationCache'.
     */
    @CacheEvict(value = "userRecommendationCache", allEntries = true)
    public void clearCache() {
        log.info("Очистка кэша");
    }
}

