package pro.sky.recommendations.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class DynamicRule {
    private UUID id;
    private Product product;
    private String specification;
}
