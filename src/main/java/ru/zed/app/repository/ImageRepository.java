package ru.zed.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zed.app.model.entity.ProductImage;

@Repository
public interface ImageRepository extends JpaRepository<ProductImage, Long> {}
