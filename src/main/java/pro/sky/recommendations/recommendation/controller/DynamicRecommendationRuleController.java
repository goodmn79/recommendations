/*
Контроллер для обработки входящих HTTP-запросов и возвращения ответа
Обеспечивает передачу данных для создания, получения и удаления динамических правил для рекомендации банковских продуктов
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendations.recommendation.dto.DynamicRecommendationRule;
import pro.sky.recommendations.recommendation.service.DynamicRecommendationRuleManager;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("rule")
@RequiredArgsConstructor
public class DynamicRecommendationRuleController {
    private final DynamicRecommendationRuleManager dynamicRecommendationRuleManager;

    private final Logger log = LoggerFactory.getLogger(DynamicRecommendationRuleController.class);

    @PostMapping
    public DynamicRecommendationRule saveDynamicRecommendationRule(@RequestBody DynamicRecommendationRule dynamicRecommendationRule) {
        log.info("Invoke method: 'saveDynamicRecommendationRule'");

        return dynamicRecommendationRuleManager.saveRecommendation(dynamicRecommendationRule);
    }

    @GetMapping("{rule_id}")
    public DynamicRecommendationRule getDynamicRecommendationRule(@PathVariable(name = "rule_id") UUID ruleId) {
        log.info("Invoke method: 'getDynamicRecommendationRule'");

        return dynamicRecommendationRuleManager.getById(ruleId);
    }

    @GetMapping
    public List<DynamicRecommendationRule> getAllDynamicRecommendationRule() {
        log.info("Invoke method: 'getAllDynamicRecommendationRule'");

        return dynamicRecommendationRuleManager.getAll();
    }

    @DeleteMapping("{rule_id}")
    public ResponseEntity<String> deleteDynamicRecommendationRuleById(@PathVariable(name = "rule_id") UUID ruleId) {
        log.info("Invoke method: 'deleteDynamicRecommendationRuleById'");

        dynamicRecommendationRuleManager.deleteById(ruleId);
        return ResponseEntity.ok(String.format("Правило рекомендации: id='%s' удалено!", ruleId));
    }
}
