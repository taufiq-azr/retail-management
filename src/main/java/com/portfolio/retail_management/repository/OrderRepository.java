package com.portfolio.retail_management.repository;

import com.portfolio.retail_management.models.Order;
import com.portfolio.retail_management.models.enums.OrderStatus;
import com.portfolio.retail_management.models.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Collection<Order> findByOrderStatus(OrderStatus orderStatus);

    Collection<Order> findByPaymentStatus(PaymentStatus paymentStatus);

    Collection<Order> findByOrderDate(Date orderDate);
}
