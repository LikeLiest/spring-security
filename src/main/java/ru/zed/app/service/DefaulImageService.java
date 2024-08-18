package ru.zed.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zed.app.model.entity.ProductImage;
import ru.zed.app.repository.ImageRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaulImageService implements ImageService{
    private final ImageRepository repository;

    @Override
    public Optional<ProductImage> getImageById(Long id) {
        return this.repository.findById(id);
    }
}
