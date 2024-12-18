package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.UserRecommendationSet;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.repository.RecommendationRepository;
import pro.sky.recommendations.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;

    public UserRecommendationSet getUserRecommendationSet(UUID userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserRecommendationSet userRecommendationSet = new UserRecommendationSet(userId);
        userRecommendationSet.addRecommendation(new Recommendation());
        return userRecommendationSet;
    }
}
