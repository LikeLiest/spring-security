package ru.zed.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.zed.app.JSON.JSONMapper;
import ru.zed.app.model.MappingUtils;
import ru.zed.app.model.entity.ProductDTO;
import ru.zed.app.model.entity.ProductEntity;
import ru.zed.app.model.entity.ProductImage;
import ru.zed.app.repository.ProductRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    public void saveProduct(ProductEntity entity, List<MultipartFile> files) throws IOException {
        List<ProductImage> images = null;

        if (!files.isEmpty()) {
            images = toImageEntity(files);
            if (!images.isEmpty()) {
                entity.addImageToOwner(images);
                log.info("Изображение {} добавлена к сущности {}", images.getFirst().getName(), entity.getName());
            } else {
                log.warn("No images were added to the product entity because the image list is empty or null.");
            }
        }

        log.info("Сохраняем сущность в БД {}", entity.getName());
        this.productRepository.save(entity);

        String filePath = "jackson.json";
        jsonMapper.appendToFile(entity, filePath);
    }


    @Transactional
    @Override
    public void saveProduct(ProductEntity productEntity) {
        if (productEntity != null) {
            log.info("Сохраняем объект {} с ID: {} в базу данных", productEntity.getName(), productEntity.getId());
            this.productRepository.save(productEntity);
        }
        throw new IllegalArgumentException();
    }

    public static List<ProductImage> toImageEntity(List<MultipartFile> files) throws IOException {
        List<ProductImage> productImages = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    ProductImage image = new ProductImage();
                    image.setName(file.getOriginalFilename());
                    image.setOriginalFileName(file.getOriginalFilename());
                    image.setContentType(file.getContentType());
                    image.setSize(file.getSize());
                    image.setBytes(file.getBytes());

                    productImages.add(image);
                    log.info("File '{}' successfully converted to ProductImage.", file.getOriginalFilename());
                } catch (IOException e) {
                    log.error("Error reading file '{}': {}", file.getOriginalFilename(), e.getMessage());
                    throw e;
                }
            } else {
                log.warn("Empty file '{}' skipped.", file.getOriginalFilename());
            }
        }

        if (productImages.isEmpty()) {
            log.warn("No valid images were found in the provided files.");
        }

        return productImages;
    }

}