package com.portfolio.retail_management.repository;

import com.portfolio.retail_management.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Collection<OrderItem> findByProductId(Long productId);
}
