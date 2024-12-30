package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.exception.ProductNotFoundException;
import pro.sky.recommendations.model.Product;
import pro.sky.recommendations.repository.ProductRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    public Product findById(UUID id) {
        log.info("Invoke method ProductRepository: 'findById");

        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }
}