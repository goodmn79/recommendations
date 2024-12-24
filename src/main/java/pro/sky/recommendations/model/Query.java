package pro.sky.recommendations.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Query {
    private UUID id;
    private String query;
    private String[] args;
    private boolean negate;
}
