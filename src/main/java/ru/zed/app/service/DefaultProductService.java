package ru.zed.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.zed.app.JSON.JSONMapper;
import ru.zed.app.model.MappingUtils;
import ru.zed.app.model.entity.ProductDTO;
import ru.zed.app.model.entity.ProductEntity;
import ru.zed.app.model.entity.ProductImage;
import ru.zed.app.repository.ProductRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final MappingUtils mappingUtils;
    private final JSONMapper jsonMapper;

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
    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ProductEntity saveProduct(ProductEntity entity, MultipartFile file) throws IOException {
        ProductImage image;

        if (file.getSize() != 0) {
            image = toImageEntity(file);
            entity.addImageToOwner(image);
        }

        this.productRepository.save(entity);

        String filePath = "jackson.json";
        jsonMapper.appendToFile(entity, filePath);
        return entity;
    }

    public static ProductImage toImageEntity(MultipartFile file) throws IOException {
        ProductImage image = new ProductImage();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
}