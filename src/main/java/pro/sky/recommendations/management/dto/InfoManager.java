package pro.sky.recommendations.management.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

@Data
@Accessors(chain = true)
public class InfoManager {
    @Value("${build.name}")
    private String name;
    @Value("${build.version}")
    private String version;
}
