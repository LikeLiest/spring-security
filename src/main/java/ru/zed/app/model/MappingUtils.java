package ru.zed.app.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zed.app.model.entity.ProductEntity;
import ru.zed.app.model.entity.ProductDTO;

import java.util.Optional;

@Slf4j
@Service
public class MappingUtils {

    public ProductDTO mapToProductDto(ProductEntity productEntity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(productEntity.getId());
        dto.setName(productEntity.getName());
        dto.setDetails(productEntity.getDetails());
        dto.setProductImageList(productEntity.getProductImageList());
        log.info("""
                        ID: {}
                        Name: {}
                        Details: {}
                        List: {}
                        """,
                dto.getId(),
                dto.getName(),
                dto.getDetails(),
                dto.getProductImageList() != null ? dto.getProductImageList().toString() : "No images");

        return dto;
    }

//    public ProductDTO mapToProduct(ProductEntity productEntity, List<MultipartFile> file) throws IOException {
//        ProductDTO dto = new ProductDTO();
//        dto.setId(productEntity.getId());
//        dto.setName(productEntity.getName());
//        dto.setDetails(productEntity.getDetails());
//        if (file != null) {
//            ProductImage image = DefaultProductService.toImageEntity(file);
//            dto.getProductImageList().add(image);
//        }
//        return dto;
//    }

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