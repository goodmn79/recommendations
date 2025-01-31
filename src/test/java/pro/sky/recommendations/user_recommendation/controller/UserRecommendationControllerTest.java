package pro.sky.recommendations.user_recommendation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.recommendations.recommendation.dto.DynamicRecommendationRule;
import pro.sky.recommendations.recommendation.dto.QueryData;
import pro.sky.recommendations.recommendation.service.DynamicRecommendationRuleManager;
import pro.sky.recommendations.user_recommendation.dto.UserRecommendation;
import pro.sky.recommendations.user_recommendation.service.UserRecommendationService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRecommendationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private DynamicRecommendationRuleManager dynamicRecommendationRuleManager;
    @Autowired
    private UserRecommendationService userRecommendationService;
    @Autowired
    private TestRestTemplate restTemplate;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.fromString("1f9b149c-6577-448a-bc94-16bea229b71a");

        dynamicRecommendationRuleManager.saveRecommendation(new DynamicRecommendationRule()
                .setProductId(UUID.fromString("08af8d2c-aaee-4ec7-994b-67d42d7b8822"))
                .setProductText("Test text for product")
                .setRule(List.of(
                        new QueryData()
                                .setQuery("USER_OF")
                                .setArguments(new String[]{"CREDIT"})
                                .setNegate(true),
                        new QueryData()
                                .setQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")
                                .setArguments(new String[]{"DEBIT", ">"})
                                .setNegate(false),
                        new QueryData()
                                .setQuery("TRANSACTION_SUM_COMPARE")
                                .setArguments(new String[]{"DEBIT", "DEPOSIT", ">", "100000"})
                                .setNegate(false)))
        );
    }

    @Test
    void testUserRecommendations() {
        UserRecommendation expected = userRecommendationService.getUserRecommendations(userId);
        ResponseEntity<UserRecommendation> response =
                restTemplate.getForEntity("http://localhost:" + port + "/recommendation/" + userId, UserRecommendation.class);

        UserRecommendation actual = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }
}