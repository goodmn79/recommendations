package pro.sky.recommendations.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Recommend {
    private UUID id;
    private String name;
    private String specification;
}
