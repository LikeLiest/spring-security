package ru.zed.app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.zed.app.model.entity.ProductEntity;
import ru.zed.app.model.entity.ProductDTO;
import ru.zed.app.model.entity.ProductImage;
import ru.zed.app.service.DefaultProductService;

import java.io.IOException;
import java.util.Optional;

@Service
public class MappingUtils {

    public ProductDTO mapToProductDto(ProductEntity productEntity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(productEntity.getId());
        dto.setName(productEntity.getName());
        dto.setDetails(productEntity.getDetails());
        return dto;
    }

    public ProductDTO mapToProduct(ProductEntity productEntity, MultipartFile file) throws IOException {
        ProductDTO dto = new ProductDTO();
        dto.setId(productEntity.getId());
        dto.setName(productEntity.getName());
        dto.setDetails(productEntity.getDetails());
        if (file != null) {
            ProductImage image = DefaultProductService.toImageEntity(file);
            dto.getProductImageList().add(image);
        }
        return dto;
    }

    public Optional<ProductDTO> mapToProductDto(Optional<ProductEntity> productEntity) {
        return productEntity.map(this::mapToProductDto);
    }

    public ProductEntity mapToProductEntity(ProductDTO dto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(dto.getId());
        productEntity.setName(dto.getName());
        productEntity.setDetails(dto.getDetails());
        return productEntity;
    }
}