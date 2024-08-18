package ru.zed.app.model.entity;


import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String details;
}
