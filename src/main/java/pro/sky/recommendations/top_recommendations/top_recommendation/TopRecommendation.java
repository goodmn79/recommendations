package pro.sky.recommendations.top_recommendations.top_recommendation;

import java.util.UUID;

public interface TopRecommendation {
    UUID getId();
    String getProductName();
    String getProductText();
    String getQuery();
}
