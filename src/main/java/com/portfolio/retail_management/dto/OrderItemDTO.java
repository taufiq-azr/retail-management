package com.portfolio.retail_management.dto;


import com.portfolio.retail_management.models.Order;
import com.portfolio.retail_management.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
