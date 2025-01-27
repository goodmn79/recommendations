package pro.sky.recommendations.stats.model;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.recommendations.recommendation.model.Recommendation;

import java.util.UUID;

/**
 * Модель статистики для хранения информации о рекомендациях.
 * Содержит идентификатор статистики, связанное правило рекомендации и количество раз, когда правило было использовано.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

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
