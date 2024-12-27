package pro.sky.recommendations.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class QueryDTO {
    private UUID id;
    private UUID dynamicRuleId;
    private String query;
    private String[] arguments;
    private Boolean negate;
}
