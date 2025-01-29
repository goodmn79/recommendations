package pro.sky.recommendations.recommendation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.recommendation.exception.ProductNotFoundException;
import pro.sky.recommendations.recommendation.model.Product;
import pro.sky.recommendations.recommendation.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    private final UUID productId = UUID.randomUUID();

    @Test
    void findById_whenProductExists_shouldReturnProduct() {
        Product expected = mock(Product.class);
        when(productRepository.findById(productId)).thenReturn(Optional.of(expected));

        Product actual = productService.findById(productId);

        verify(productRepository).findById(productId);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findById_whenProductNotExists_shouldThrowException() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(productId)).isInstanceOf(ProductNotFoundException.class);
    }
}