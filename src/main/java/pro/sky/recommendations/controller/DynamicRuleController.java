package pro.sky.recommendations.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendations.dto.DynamicRecommendationRule;

@RestController
@RequestMapping("rule-service")
@RequiredArgsConstructor
public class DynamicRuleController {

    @PostMapping
    public ResponseEntity<String> createDynamicRule(@RequestBody DynamicRecommendationRule dynamicRecommendationRule) {
        return ResponseEntity.ok(String.format("Правило рекомендации для продукта: id='%s' успешно создано!", dynamicRecommendationRule.getRecommendation().getId().toString()));
    }

    @GetMapping("{product_id}")
    public DynamicRecommendationRule getDynamicRule(@PathVariable(name = "product_id") int productId) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDynamicRule(@RequestBody DynamicRecommendationRule dynamicRecommendationRule) {
        return ResponseEntity.ok(String.format("Правило рекомендации для продукта: id='%s' удалено!", dynamicRecommendationRule.getRecommendation().getId().toString()));
    }
}
