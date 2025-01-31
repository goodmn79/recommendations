package pro.sky.recommendations.stats.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pro.sky.recommendations.stats.dto.StatsData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatsControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetStats() {
        ResponseEntity<List<StatsData>> response =
                restTemplate.exchange("http://localhost:" + port + "/rule/stats", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        List<StatsData> actual = response.getBody();

        assertThat(actual).isNotNull();
        assertThat(actual).isEmpty();
    }
}