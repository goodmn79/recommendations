package pro.sky.recommendations.recommendation.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.recommendations.recommendation.dto.DynamicRecommendationRule;
import pro.sky.recommendations.recommendation.dto.QueryData;
import pro.sky.recommendations.recommendation.model.Product;
import pro.sky.recommendations.recommendation.service.DynamicRecommendationRuleManager;
import pro.sky.recommendations.recommendation.service.ProductService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DynamicRecommendationRuleControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private DynamicRecommendationRuleManager dynamicRecommendationRuleManager;
    @Autowired
    private ProductService productService;
    @Autowired
    private TestRestTemplate restTemplate;

    private DynamicRecommendationRule testDrr;

    private final Logger log = LoggerFactory.getLogger(DynamicRecommendationRuleControllerTest.class);

    @BeforeEach
    void setUp() {
        log.info("ИНИЦИАЛИЗАЦИЯ ДАННЫХ.");

        UUID productId = UUID.fromString("08af8d2c-aaee-4ec7-994b-67d42d7b8822");

        testDrr = new DynamicRecommendationRule()
                .setProductId(productId)
                .setProductText("Test text for product")
                .setRule(List.of(
                        new QueryData()
                                .setQuery("TRANSACTION_SUM_COMPARE")
                                .setArguments(new String[]{"DEBIT", "DEPOSIT", ">", "100000"})
                                .setNegate(false))
                );
    }

    @AfterEach
    void tearDown() {
        log.info("ДЕИНИЦИАЛИЗАЦИЯ ДАННЫХ.");

        try {
            List<DynamicRecommendationRule> drrList = dynamicRecommendationRuleManager.getAll();
            for (DynamicRecommendationRule drr : drrList) {
                UUID drrId = drr.getId();
                dynamicRecommendationRuleManager.deleteById(drrId);
            }
            log.info("ДЕИНИЦИАЛИЗАЦИЯ ДАННЫХ ЗАВЕРШЕНА.");
        } catch (Exception e) {
            log.info("ДАННЫЕ ОТСУТСТВУЮТ!");
        }
    }

    @Test
    void testSaveDynamicRecommendationRule() {
        Product product = productService.findById(testDrr.getProductId());
        ResponseEntity<DynamicRecommendationRule> response =
                restTemplate.postForEntity(getUrl(port), testDrr, DynamicRecommendationRule.class);

        DynamicRecommendationRule actual = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getProductId()).isEqualTo(product.getId());
        assertThat(actual.getProductName()).isEqualTo(product.getName());
        assertThat(actual.getProductText()).isEqualTo(testDrr.getProductText());
        assertThat(actual.getRule()).isEqualTo(testDrr.getRule());
    }

    @Nested
    class ExcludedTest {
        private DynamicRecommendationRule testDrr1;
        private UUID testDrrId1;

        @BeforeEach
        void setUp() {
            testDrr1 = dynamicRecommendationRuleManager.saveRecommendation(testDrr);

            testDrrId1 = testDrr1.getId();
        }

        @Test
        void testGetDynamicRecommendationRule() {
            ResponseEntity<DynamicRecommendationRule> response =
                    restTemplate.getForEntity(getUrl(port, testDrrId1), DynamicRecommendationRule.class);

            DynamicRecommendationRule actual = response.getBody();

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(actual).isNotNull();
            assertThat(actual.getId()).isEqualTo(testDrrId1);
            assertThat(actual.getProductId()).isEqualTo(testDrr1.getProductId());
            assertThat(actual.getProductName()).isEqualTo(testDrr1.getProductName());
            assertThat(actual.getProductText()).isEqualTo(testDrr1.getProductText());
            assertThat(actual.getRule()).isEqualTo(testDrr1.getRule());
        }

        @Test
        void testGetAllDynamicRecommendationRule() {
            Product product = productService.findById(UUID.fromString("d4abe90a-c712-46a2-9834-d030d6d6c88b"));

            DynamicRecommendationRule testDrr2 =
                    dynamicRecommendationRuleManager.saveRecommendation(new DynamicRecommendationRule()
                            .setProductId(product.getId())
                            .setProductText("Test text for expected product")
                            .setRule(List.of(new QueryData()
                                    .setQuery("USER_OF")
                                    .setArguments(new String[]{"CREDIT"})
                                    .setNegate(true))
                            ));
            List<DynamicRecommendationRule> expected = List.of(testDrr1, testDrr2);
            ResponseEntity<List<DynamicRecommendationRule>> response = restTemplate
                    .exchange(getUrl(port), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                    });

            List<DynamicRecommendationRule> actual = response.getBody();

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(actual).isNotEmpty();
            assertThat(actual.size()).isEqualTo(expected.size());
            assertThat(actual).contains(testDrr1);
            assertThat(actual).contains(testDrr2);
        }

        @Test
        void testDeleteDynamicRecommendationRuleById() {
            ResponseEntity<String> response = restTemplate.exchange(getUrl(port, testDrrId1), HttpMethod.DELETE, null, String.class);

            String actual = response.getBody();

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(actual).isNotNull();
            assertThat(actual).isEqualTo(String.format("Правило рекомендации: id='%s' удалено!", testDrrId1));
        }
    }

    private String getUrl(int port) {
        return "http://localhost:" + port + "/rule";
    }

    private String getUrl(int port, UUID uuid) {
        return getUrl(port) + "/" + uuid.toString();
    }
}
