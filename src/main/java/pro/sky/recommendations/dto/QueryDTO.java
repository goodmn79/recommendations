package pro.sky.recommendations.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class QueryDTO {
    private UUID id;
    private String query;
    private String[] args;
    private Boolean negate;
}
