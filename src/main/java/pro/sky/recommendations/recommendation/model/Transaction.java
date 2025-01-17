/*
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Transaction {
    private UUID id;
    private Product product;
    private User user;
    private String type;
    private int amount;
}
