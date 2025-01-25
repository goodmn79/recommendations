package pro.sky.recommendations.recommendation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.recommendation.dto.DynamicRecommendationRule;
import pro.sky.recommendations.recommendation.dto.QueryData;
import pro.sky.recommendations.recommendation.exception.RecommendationNotFoundException;
import pro.sky.recommendations.recommendation.exception.TransactionExecuteException;
import pro.sky.recommendations.recommendation.mapper.castom_mapper.QueryMapper;
import pro.sky.recommendations.recommendation.model.Product;
import pro.sky.recommendations.recommendation.model.Query;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.stats.service.StatsService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamicRecommendationRuleManagerTest {
    @Mock
    private RecommendationService recommendationService;
    @Mock
    private QueryService queryService;
    @Mock
    private StatsService statsService;
    @Mock
    QueryMapper queryMapper;
    @Mock
    private ProductService productService;


    @InjectMocks
    private DynamicRecommendationRuleManager dynamicRecommendationRuleManager;

    private QueryData queryData;
    private List<QueryData> rule;
    private DynamicRecommendationRule drr;
    private Product product;
    private Recommendation recommendation;
    private UUID recommendationId;

    @BeforeEach
    void setUp() {
        recommendationId = UUID.randomUUID();

        queryData = new QueryData()
                .setQuery("TRANSACTION_SUM_COMPARE")
                .setArguments(new String[]{"DEBIT", "DEPOSIT", ">", "100000"})
                .setNegate(true);

        rule = List.of(queryData);

        product = new Product()
                .setId(UUID.randomUUID())
                .setName("Тестовый продукт");

        drr = new DynamicRecommendationRule()
                .setProductId(product.getId())
                .setProductName(product.getName())
                .setProductText("Тестовый текст продукта")
                .setRule(rule);

        recommendation = new Recommendation()
                .setId(recommendationId)
                .setProduct(product)
                .setProductText(drr.getProductText())
                .setRule(List.of(
                        new Query()
                                .setId(UUID.randomUUID())
                                .setRecommendation(recommendation)
                                .setQuery(queryData.getQuery())
                                .setArguments(queryData.getArguments())
                                .setNegate(queryData.getNegate())
                ));
    }

    @Test
    void testSaveRecommendationSuccess() {
        List<Query> queries = recommendation.getRule();

        when(productService.findById(drr.getProductId())).thenReturn(product);
        when(queryMapper.toQuery(anyList(), any(Recommendation.class))).thenReturn(queries);
        doNothing().when(recommendationService).saveRecommendation(any(Recommendation.class));
        doNothing().when(queryService).saveRule(any(Recommendation.class));
        doNothing().when(statsService).createCounter(any(Recommendation.class));

        DynamicRecommendationRule savedDrr = dynamicRecommendationRuleManager.saveRecommendation(drr);

        verify(recommendationService, times(1)).saveRecommendation(any(Recommendation.class));
        verify(queryService, times(1)).saveRule(any(Recommendation.class));
        verify(statsService, times(1)).createCounter(any(Recommendation.class));

        assertThat(savedDrr).isNotNull();
        assertThat(product.getName()).isEqualTo(savedDrr.getProductName());
        assertThat(product.getId()).isEqualTo(savedDrr.getProductId());
        assertThat(drr.getProductText()).isEqualTo(savedDrr.getProductText());
    }

    @Test
    void testSaveRecommendationFailure() {
        doThrow(new TransactionExecuteException()).when(recommendationService).saveRecommendation(any());

        assertThatThrownBy(() -> dynamicRecommendationRuleManager.saveRecommendation(drr))
                .isInstanceOf(TransactionExecuteException.class);

        verify(productService).findById(drr.getProductId());
        verify(recommendationService).saveRecommendation(any());
        verify(queryService, never()).saveRule(any());
        verify(statsService, never()).createCounter(any());
    }

    @Test
    void getById() {
        drr.setId(recommendationId);
        when(recommendationService.findById(recommendationId)).thenReturn(recommendation);
        when(queryMapper.toQueryData(anyList())).thenReturn(rule);

        DynamicRecommendationRule actual = dynamicRecommendationRuleManager.getById(recommendationId);

        verify(recommendationService).findById(recommendationId);
        verify(queryMapper).toQueryData(recommendation.getRule());

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(drr);
        assertThat(drr.getRule()).contains(queryData);
    }

    @Test
    void getAll_whenRecommendationsFound_shouldReturnDynamicRecommendationRuleList() {
        List<Recommendation> recommendationList = List.of(recommendation);
        List<DynamicRecommendationRule> expectedList = List.of(drr.setId(recommendationId));
        when(recommendationService.findAll()).thenReturn(recommendationList);
        when(queryMapper.toQueryData(anyList())).thenReturn(rule);

        List<DynamicRecommendationRule> actualList = dynamicRecommendationRuleManager.getAll();

        verify(recommendationService).findAll();
        verify(queryMapper, times(expectedList.size())).toQueryData(recommendation.getRule());

        assertThat(actualList).isNotNull();
        assertThat(actualList).isEqualTo(expectedList);
        assertThat(drr.getRule()).contains(queryData);
    }

    @Test
    void getAll_whenNoRecommendationsFound_shouldThrowException() {
        when(recommendationService.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> dynamicRecommendationRuleManager.getAll()).isInstanceOf(RecommendationNotFoundException.class);
    }

    @Test
    void deleteById() {
        doNothing().when(recommendationService).deleteById(recommendationId);
        doNothing().when(queryService).deleteBYRecommendationId(recommendationId);
        doNothing().when(statsService).deleteCounter(recommendationId);

        dynamicRecommendationRuleManager.deleteById(recommendationId);

        verify(recommendationService, times(1)).deleteById(recommendationId);
        verify(queryService, times(1)).deleteBYRecommendationId(recommendationId);
        verify(statsService, times(1)).deleteCounter(recommendationId);
    }

    @Test
    void deleteByIdFailure() {
        doThrow(new TransactionExecuteException()).when(recommendationService).deleteById(recommendationId);

        assertThatThrownBy(() -> dynamicRecommendationRuleManager.deleteById(recommendationId))
                .isInstanceOf(TransactionExecuteException.class);

        verify(recommendationService, times(1)).deleteById(recommendationId);
        verify(queryService, never()).deleteBYRecommendationId(recommendationId);
        verify(statsService, never()).deleteCounter(recommendationId);
    }
}