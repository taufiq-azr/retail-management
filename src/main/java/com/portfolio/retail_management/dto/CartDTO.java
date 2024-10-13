package com.portfolio.retail_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class CartDTO {
    private Long id;
    private Long userId;
    private Set<CartItemDTO> cartItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
