/*
Powered by ©AYE.team
 */

package pro.sky.recommendations.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Product {
    private UUID id;
    private String name;
    private String type;
}
