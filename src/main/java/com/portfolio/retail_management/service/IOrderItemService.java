package com.portfolio.retail_management.service;


import com.portfolio.retail_management.dto.OrderItemDTO;
import com.portfolio.retail_management.models.OrderItem;

import java.util.List;

public interface IOrderItemService {

    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);

    OrderItemDTO updateOrderItem(Long orderItemId, OrderItemDTO orderItemDTO);

    void deleteOrderItem(Long orderItemId);

    OrderItemDTO getOrderItemById(Long orderItemId);

    List<OrderItemDTO> getAllOrderItem();

    List<OrderItemDTO> getOrderItemByProductId(Long productId);

}
