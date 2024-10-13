package com.portfolio.retail_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class SupplierDTO {
    private Long id;
    private String name;
    private String contractDetails;
    private String address;
    private Set<ProductDTO> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
