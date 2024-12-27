package pro.sky.recommendations.mapper.model_mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.dto.DynamicRuleDTO;
import pro.sky.recommendations.dto.QueryDTO;
import pro.sky.recommendations.model.DynamicRule;
import pro.sky.recommendations.model.Product;

import java.util.List;
import java.util.UUID;

@Component
public class DynamicRuleMapper {
    private final ModelMapper mapper = new ModelMapper();

    private final Logger log = LoggerFactory.getLogger(DynamicRuleMapper.class);

    public DynamicRule fromDTO(DynamicRuleDTO dto, Product product) {
        log.info("Invoke method fromDTO");
        return mapper.addMappings(new PropertyMap<DynamicRuleDTO, DynamicRule>() {
                    @Override
                    protected void configure() {
                        map(destination.setId(UUID.randomUUID()));
                    }
                }).map(dto)
                .setProduct(product);
    }

    public DynamicRuleDTO toDto(DynamicRule dynamicRule, List<QueryDTO> queryDTOS) {
        log.info("Invoke method toDto");
        return mapper.addMappings(new PropertyMap<DynamicRule, DynamicRuleDTO>() {
                    @Override
                    protected void configure() {
                        map(source.getProduct().getId(), destination.getProductId());
                        map(source.getProduct().getName(), destination.getProductName());
                    }
                }).map(dynamicRule)
                .setQueries(queryDTOS);
    }
}
