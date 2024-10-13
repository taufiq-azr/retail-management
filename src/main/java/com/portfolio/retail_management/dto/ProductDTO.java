package com.portfolio.retail_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ProductDTO {
    private Long id;
    private Long categoryId;
    private Long supplierId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
