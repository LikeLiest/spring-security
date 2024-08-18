package ru.zed.app.service;

import ru.zed.app.model.entity.ProductImage;

import java.util.Optional;

public interface ImageService {
    Optional<ProductImage> getImageById(Long id);
}
