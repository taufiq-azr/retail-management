package com.portfolio.retail_management.service;

import com.portfolio.retail_management.dto.OrderDTO;
import com.portfolio.retail_management.models.enums.OrderStatus;
import com.portfolio.retail_management.models.enums.PaymentStatus;

import java.util.Date;
import java.util.List;

public interface IOrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(Long orderId, OrderDTO orderDTO);

    void  deleteOrder(Long orderId);

    OrderDTO getOrderById(Long orderId);

    List<OrderDTO> getAllOrder();

    List<OrderDTO> findOrderByOrderStatus(OrderStatus OrderStatus);

    List<OrderDTO> findOrderByPaymentStatus(PaymentStatus PaymentStatus);

    List<OrderDTO> findOrderByOrderDate(Date orderDate);
}
