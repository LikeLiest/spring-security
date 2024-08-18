package ru.zed.app.model.entity;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String details;
    private List<ProductImage> productImageList = new ArrayList<>();
}
