package ru.zed.app.service;

import org.springframework.web.multipart.MultipartFile;
import ru.zed.app.model.entity.ProductDTO;
import ru.zed.app.model.entity.ProductEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> findAllProducts();

    ProductEntity saveProduct(ProductEntity productEntity, MultipartFile file) throws IOException;

    Optional<ProductDTO> findProduct(Long productId);

    void deleteProductById(Long id);
}