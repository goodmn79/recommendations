package pro.sky.recommendations.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.exception.ProductNotFoundException;
import pro.sky.recommendations.recommendation.model.Product;
import pro.sky.recommendations.recommendation.repository.ProductRepository;

import java.util.UUID;

/**
 * Сервис для работы с банковскими продуктами.
 * <p>
 * Этот класс предоставляет методы для получения данных о банковских продуктах.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    /**
     * Получение данных о банковском продукте по его идентификатору.
     * <br>Если продукт не найден, выбрасывается исключение {@link ProductNotFoundException}.
     *
     * @param id идентификатор банковского продукта.
     * @return объект {@link Product}, содержащий данные о продукте.
     * @throws ProductNotFoundException если продукт с указанным идентификатором не найден.
     */
    public Product findById(UUID id) {
        log.info("Получение продукта по его идентификатору...");

        Product foundProduct = productRepository.findById(id).orElseThrow(() -> {
            log.error("Продукт не найден!");
            return new ProductNotFoundException();
        });

        log.info("Продукт успешно получен.");
        return foundProduct;
    }
}
