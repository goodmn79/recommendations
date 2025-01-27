package pro.sky.recommendations.management.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendations.management.dto.InfoManager;
import pro.sky.recommendations.management.service.ManagementService;

/**
 * Контроллер для управления данными приложения.
 * Предоставляет API endpoints для управления кэшем и получения информации о приложении.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

@RestController
@RequestMapping("management")
@RequiredArgsConstructor
public class ManagementController {
    private final ManagementService managementService;
    private final Logger log = LoggerFactory.getLogger(ManagementController.class);

    /**
     * Очищает кэш приложения.
     * Endpoint: POST /management/clear-cache
     */
    @PostMapping("clear-cache")
    public void clearCache() {
        log.info("Запущен процесс очистки кэша.");
        managementService.clearCache();
    }

    /**
     * Получает информацию о текущем состоянии приложения.
     * Endpoint: GET /management/info
     *
     * @return Объект InfoManager, содержащий информацию о приложении
     */
    @GetMapping("info")
    public InfoManager info() {
        log.info("Вызван метод #info.");
        return managementService.infoManager();
    }
}
