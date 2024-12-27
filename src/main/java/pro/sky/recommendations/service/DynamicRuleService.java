package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.recommendations.dto.DynamicRuleDTO;
import pro.sky.recommendations.dto.QueryDTO;
import pro.sky.recommendations.exception.ProductNotFoundException;
import pro.sky.recommendations.mapper.model_mapper.DynamicRuleMapper;
import pro.sky.recommendations.model.DynamicRule;
import pro.sky.recommendations.model.Product;
import pro.sky.recommendations.repository.DynamicRuleRepository;
import pro.sky.recommendations.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DynamicRuleService {
    private final QueryService queryService;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final ProductRepository productRepository;
    private final DynamicRuleMapper dynamicRuleMapper;

    private final Logger log = LoggerFactory.getLogger(DynamicRuleService.class);

    @Transactional
    public DynamicRuleDTO addDynamicRule(DynamicRuleDTO dynamicRuleDTO) {
        List<QueryDTO> queryDTO = dynamicRuleDTO.getQueries();

        Product product = productRepository
                .findById(dynamicRuleDTO.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        DynamicRule dynamicRule = dynamicRuleMapper.fromDTO(dynamicRuleDTO, product);

        List<QueryDTO> savedQueryDTOD = queryService.create(dynamicRule, queryDTO);

        dynamicRuleRepository.save(dynamicRule);

        return dynamicRuleMapper.toDto(dynamicRule, savedQueryDTOD);
    }

    public void deleteDynamicRule(UUID ruleId) {

    }

    private String convertArgsList(List<String> args) {
        StringBuilder builder = new StringBuilder();
        String delimiter = ",";
        for (int i = 0; i < args.size(); i++) {
            if (i < args.size() - 1) {
                builder.append(delimiter);
            }
        }
        return builder.toString();
    }
}
