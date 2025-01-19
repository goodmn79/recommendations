/*
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class Recommendation {
    private UUID id;
    private Product product;
    private String productText;
    private List<Query> rule;
}
