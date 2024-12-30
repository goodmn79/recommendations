package pro.sky.recommendations.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.recommendations.dto.UserRecommendationSet;
import pro.sky.recommendations.repository.RecommendRepository;
import pro.sky.recommendations.repository.UserRepository;
import pro.sky.recommendations.service.RecommendService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecommendControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecommendRepository recommendRepository;
    @Autowired
    private RecommendService recommendService;

    @Test
    void testGetRecommendation() {
        UUID userId = UUID.fromString("d4a4d619-9a0c-4fc5-b0cb-76c49409546b");

        ResponseEntity<UserRecommendationSet> userRecommendationSet =
                restTemplate.getForEntity("http://localhost:" + port + "/recommendation/" + userId, UserRecommendationSet.class);

        assertThat(userRecommendationSet).isNotNull();
        assertThat(userRecommendationSet.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserRecommendationSet actual = userRecommendationSet.getBody();

        assertThat(actual).isNotNull();
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.getRecommends().size()).isEqualTo(1);
    }
}