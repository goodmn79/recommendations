package pro.sky.recommendations.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.recommendations.model.Recommend;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserRecommendationSet {
    private final UUID userId;
    private Set<Recommend> recommends;

    public UserRecommendationSet(UUID userId) {
        this.userId = userId;
        this.recommends = new HashSet<>();
    }

    public void addRecommendation(Recommend recommend) {
        this.recommends.add(recommend);
    }
}
