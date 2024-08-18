package ru.zed.app.service;

import ru.zed.app.model.entity.ProductDTO;
import ru.zed.app.model.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> findAllProducts();

    void saveProduct(ProductEntity productEntity);

    Optional<ProductDTO> findProduct(Long productId);
}