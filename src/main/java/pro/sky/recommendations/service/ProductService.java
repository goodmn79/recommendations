/*
Файл сервиса для получения данных о банковском продукте
Powered by ©AYE.team
 */

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

    // Получение данных о банковском продукте по его идентификатору
    public Product findById(UUID id) {
        log.info("Fetching product by id...");

        Product foundProduct = productRepository.findById(id).orElseThrow(() -> {
            log.error("Product not found");
            return new ProductNotFoundException();
        });
        log.info("Product successfully found");
        return foundProduct;
    }
}
