package pro.sky.recommendations.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.recommendations.model.Query;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class DynamicRuleDTO {
    private UUID id;
    private String productName;
    private UUID productId;
    private String specification;
    private List<QueryDTO> queries;
}
