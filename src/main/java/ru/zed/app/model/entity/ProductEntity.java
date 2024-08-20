package ru.zed.app.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ProductEntity {
    private static final Logger log = LoggerFactory.getLogger(ProductEntity.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String details;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImageList = new ArrayList<>();

    public void addImageToOwner(List<ProductImage> images) {
        if (images != null) {
            for (ProductImage image : images) {
                log.info("К списку изображении добавляется новое изображение");
                productImageList.add(image);
                log.info("К изображению добавляется продукт");
                image.setProduct(this);
            }
        } else {
            log.info("К изображению не добавлся продукт");
        }
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
