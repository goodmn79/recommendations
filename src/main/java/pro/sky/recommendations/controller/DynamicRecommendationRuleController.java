/*
Контроллер для обработки входящих HTTP-запросов и возвращения ответа
Обеспечивает передачу данных для создания, получения и удаления динамических правил для рекомендации банковских продуктов
Powered by ©AYE.team
 */

package pro.sky.recommendations.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendations.dto.DynamicRecommendationRule;
import pro.sky.recommendations.service.RecommendationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("rule")
@RequiredArgsConstructor
public class DynamicRecommendationRuleController {
    private final RecommendationService recommendationService;

    private final Logger log = LoggerFactory.getLogger(DynamicRecommendationRuleController.class);

    @PostMapping
    public DynamicRecommendationRule createRule(@RequestBody DynamicRecommendationRule dynamicRecommendationRule) {
        log.info("Invoke method 'DynamicRecommendationRuleController: createRule'");

        return recommendationService.createRecommendation(dynamicRecommendationRule);
    }

    @GetMapping("{rule_id}")
    public DynamicRecommendationRule getRule(@PathVariable(name = "rule_id") UUID ruleId) {
        log.info("Invoke method 'DynamicRecommendationRuleController: getRule'");

        return recommendationService.findById(ruleId);
    }

    @GetMapping
    public List<DynamicRecommendationRule> getAllRules() {
        log.info("Invoke method 'DynamicRecommendationRuleController: getAllRules'");

        return recommendationService.findAll();
    }

    @DeleteMapping("{rule_id}")
    public ResponseEntity<String> deleteRuleById(@PathVariable(name = "rule_id") UUID ruleId) {
        log.info("Invoke method 'DynamicRecommendationRuleController: deleteRuleById'");

        recommendationService.deleteById(ruleId);
        return ResponseEntity.ok(String.format("Правило рекомендации: id='%s' удалено!", ruleId));
    }
}
