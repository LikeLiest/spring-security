package ru.zed.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zed.app.model.MappingUtils;
import ru.zed.app.model.entity.ProductDTO;
import ru.zed.app.model.entity.ProductEntity;
import ru.zed.app.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final MappingUtils mappingUtils;

    @Override
    public List<ProductDTO> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(mappingUtils::mapToProductDto)
                .toList();
    }

    @Override
    public Optional<ProductDTO> findProduct(Long productId) {
        return this.productRepository.findById(productId)
                .map(mappingUtils::mapToProductDto);
    }

    @Transactional
    @Override
    public void saveProduct(ProductEntity entity) {
        this.productRepository.save(entity);
    }
}
