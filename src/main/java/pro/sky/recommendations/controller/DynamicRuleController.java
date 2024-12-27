package pro.sky.recommendations.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendations.dto.DynamicRuleDTO;
import pro.sky.recommendations.service.DynamicRuleService;

import java.util.UUID;

@RestController
@RequestMapping("rule")
@RequiredArgsConstructor
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleService;

    private final Logger log = LoggerFactory.getLogger(DynamicRuleController.class);

    @PostMapping
    public DynamicRuleDTO createDynamicRule(@RequestBody DynamicRuleDTO dynamicRuleDTO) {
        log.info("Dynamic rule data: {}", dynamicRuleDTO);
        return dynamicRuleService.addDynamicRule(dynamicRuleDTO);
    }

    @GetMapping
    public DynamicRuleDTO getDynamicRule() {
        return null;
    }

    @DeleteMapping("{rule_id}")
    public ResponseEntity<String> deleteDynamicRule(@PathVariable(name = "rule_id") UUID ruleId) {
        dynamicRuleService.deleteDynamicRule(ruleId);
        return ResponseEntity.ok(String.format("Правило рекомендации: id='%s' удалено!", ruleId));
    }
}
