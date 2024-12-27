package pro.sky.recommendations.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class Query {
    private UUID id;
    private DynamicRule dynamicRule;
    private String query;
    private String arguments;
    private Boolean negate;
}
