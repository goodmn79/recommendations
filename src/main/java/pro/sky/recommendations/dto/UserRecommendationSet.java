package pro.sky.recommendations.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.recommendations.model.Recommendation;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserRecommendationSet {
    private final UUID userId;
    private Set<Recommendation> recommendations;

    public UserRecommendationSet(UUID userId) {
        this.userId = userId;
        this.recommendations = new HashSet<>();
    }

    public void addRecommendation(Recommendation recommendation) {
        this.recommendations.add(recommendation);
    }
}
