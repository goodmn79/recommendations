package pro.sky.recommendations.stats.model;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.recommendations.model.Recommendation;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Stats {
    private UUID id;
    private Recommendation recommendation;
    private int count;

    public void increment() {
        this.count++;
    }
}
