/*
Файл контроллера для управления данными приложения
Powered by ©AYE.team
 */

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

@RestController
@RequestMapping("management")
@RequiredArgsConstructor
public class ManagementController {
    private final ManagementService managementService;

    private final Logger log = LoggerFactory.getLogger(ManagementController.class);

    // Очистка кэша
    @PostMapping("clear-cache")
    public void clearCache() {
        log.info("Calling the cache clearing process");

        managementService.clearCache();
    }

    @GetMapping("info")
    public InfoManager info() {
        return managementService.infoManager();
    }
}
