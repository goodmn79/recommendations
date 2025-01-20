package pro.sky.recommendations.stats.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendations.stats.dto.StatsData;
import pro.sky.recommendations.stats.service.StatsService;

import java.util.List;

@RestController
@RequestMapping("rule")
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    private final Logger log = LoggerFactory.getLogger(StatsController.class);

    @GetMapping("stats")
    public List<StatsData> getStats() {
        log.info("Invoke method 'getStats'");
        return statsService.getAll();
    }
}
