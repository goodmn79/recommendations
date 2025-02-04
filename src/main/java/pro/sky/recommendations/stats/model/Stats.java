package pro.sky.recommendations.stats.model;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.recommendations.recommendation.model.Recommendation;

import java.util.UUID;

/**
 * Модель статистики для хранения информации о рекомендациях.
 * <p>
 * Содержит идентификатор статистики, связанное правило рекомендации и количество раз, когда правило было использовано.
 * </P>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Accessors(chain = true)
public class Stats {
    private UUID id;

    private Recommendation recommendation;

    private int count;

    /**
     * Инкрементирование счётчика.
     */
    public void increment() {
        this.count++;
    }
}
