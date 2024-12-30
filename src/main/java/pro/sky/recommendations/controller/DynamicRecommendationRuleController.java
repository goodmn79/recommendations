package pro.sky.recommendations.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendations.dto.DynamicRecommendationRule;
import pro.sky.recommendations.service.DynamicRecommendationRuleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("rule")
@RequiredArgsConstructor
public class DynamicRecommendationRuleController {
    private final DynamicRecommendationRuleService drrService;

    private final Logger log = LoggerFactory.getLogger(DynamicRecommendationRuleController.class);

    @PostMapping
    public DynamicRecommendationRule createRule(@RequestBody DynamicRecommendationRule dynamicRecommendationRule) {
        log.info("Dynamic rule data: {}", dynamicRecommendationRule);
        return drrService.create(dynamicRecommendationRule);
    }

    @GetMapping("{rule_id}")
    public DynamicRecommendationRule getRule(@PathVariable(name = "rule_id") UUID ruleId) {
        return drrService.findById(ruleId);
    }

    @GetMapping
    public List<DynamicRecommendationRule> getAllRules() {
        return drrService.findAll();
    }

    @DeleteMapping("{rule_id}")
    public ResponseEntity<String> deleteRuleById(@PathVariable(name = "rule_id") UUID ruleId) {
        drrService.deleteById(ruleId);
        return ResponseEntity.ok(String.format("Правило рекомендации: id='%s' удалено!", ruleId));
    }
}
