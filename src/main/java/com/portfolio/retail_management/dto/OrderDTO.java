package com.portfolio.retail_management.dto;

import com.portfolio.retail_management.models.enums.OrderStatus;
import com.portfolio.retail_management.models.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long userId;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private String shippingAddress;
    private PaymentStatus paymentStatus;
    private Set<OrderItemDTO> orderItems;
    private LocalDateTime orderDate;
    private LocalDateTime updatedAt;
}
