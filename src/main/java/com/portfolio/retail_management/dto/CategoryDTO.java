package com.portfolio.retail_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Set<ProductDTO> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
