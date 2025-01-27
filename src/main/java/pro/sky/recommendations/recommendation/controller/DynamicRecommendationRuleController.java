/**
 * Контроллер для управления динамическими правилами рекомендаций банковских продуктов.
 * Обрабатывает HTTP-запросы для создания, получения и удаления правил рекомендаций.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
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

    /**
     * Сохраняет новое правило рекомендации.
     * Endpoint: POST /rule
     *
     * @param dynamicRecommendationRule Объект правила рекомендации для сохранения
     * @return Сохраненное правило рекомендации
     */
    @PostMapping
    public DynamicRecommendationRule saveDynamicRecommendationRule(@RequestBody DynamicRecommendationRule dynamicRecommendationRule) {
        log.info("Вызван метод #saveDynamicRecommendationRule.");
        return dynamicRecommendationRuleManager.saveRecommendation(dynamicRecommendationRule);
    }

    /**
     * Получает правило рекомендации по его идентификатору.
     * Endpoint: GET /rule/{rule_id}
     *
     * @param ruleId Уникальный идентификатор правила
     * @return Найденное правило рекомендации
     */
    @GetMapping("{rule_id}")
    public DynamicRecommendationRule getDynamicRecommendationRule(@PathVariable(name = "rule_id") UUID ruleId) {
        log.info("Вызван метод #getDynamicRecommendationRule.");
        return dynamicRecommendationRuleManager.getById(ruleId);
    }

    /**
     * Получает список всех правил рекомендаций.
     * Endpoint: GET /rule
     *
     * @return Список всех правил рекомендаций
     */
    @GetMapping
    public List<DynamicRecommendationRule> getAllDynamicRecommendationRule() {
        log.info("Вызван метод #getAllDynamicRecommendationRule.");
        return dynamicRecommendationRuleManager.getAll();
    }

    /**
     * Удаляет правило рекомендации по его идентификатору.
     * Endpoint: DELETE /rule/{rule_id}
     *
     * @param ruleId Уникальный идентификатор правила для удаления
     * @return ResponseEntity с сообщением об успешном удалении
     */
    @DeleteMapping("{rule_id}")
    public ResponseEntity<String> deleteDynamicRecommendationRuleById(@PathVariable(name = "rule_id") UUID ruleId) {
        log.info("Вызван метод #deleteDynamicRecommendationRuleById.");
        dynamicRecommendationRuleManager.deleteById(ruleId);
        return ResponseEntity.ok(String.format("Правило рекомендации: id='%s' удалено!", ruleId));
    }
}
