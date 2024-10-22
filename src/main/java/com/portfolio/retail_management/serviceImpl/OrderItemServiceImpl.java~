package com.portfolio.retail_management.serviceImpl;

import com.portfolio.retail_management.dto.OrderItemDTO;
import com.portfolio.retail_management.exception.NotFoundException;
import com.portfolio.retail_management.models.Order;
import com.portfolio.retail_management.models.OrderItem;
import com.portfolio.retail_management.models.Product;
import com.portfolio.retail_management.repository.OrderItemRepository;
import com.portfolio.retail_management.repository.OrderRepository;
import com.portfolio.retail_management.repository.ProductRepository;
import com.portfolio.retail_management.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService implements IOrderItemService {


    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setOrderId(orderItem.getOrder().getId());
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setUnitPrice(orderItem.getUnitPrice());
        orderItemDTO.setCreatedAt(orderItem.getCreatedAt());
        orderItemDTO.setUpdatedAt(orderItem.getUpdatedAt());

        return orderItemDTO;
    }

    private OrderItem convertToEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();

        if(orderItemDTO.getOrderId() != null) {
            Order order = orderRepository.findById(orderItemDTO.getOrderId())
                    .orElseThrow(() -> new NotFoundException("Order not found"));
            orderItem.setOrder(order);
        }

        if (orderItemDTO.getProductId() != null) {
            Product product = productRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));
            orderItem.setProduct(product);
        }
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
        return orderItem;
    }

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = convertToEntity(orderItemDTO);
        orderItem = orderItemRepository.save(orderItem);
        return convertToDTO(orderItem);

    }

    @Override
    public OrderItemDTO updateOrderItem(Long orderItemId, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item not found"));

        if(orderItemDTO.getProductId() != null) {
            Product product = productRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));
            orderItem.setProduct(product);
        }

        if(orderItemDTO.getOrderId() != null) {
            Order order = orderRepository.findById(orderItemDTO.getOrderId())
                    .orElseThrow(() -> new NotFoundException("Order not found"));
            orderItem.setOrder(order);
        }

        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
        orderItem = orderItemRepository.save(orderItem);
        return convertToDTO(orderItem);
    }


    @Override
    public void deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item not found"));
        orderItemRepository.delete(orderItem);
    }

    @Override
    public OrderItemDTO getOrderItemById(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item not found"));
        return convertToDTO(orderItem);
    }

    @Override
    public List<OrderItemDTO> getAllOrderItem() {
        return orderItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItemDTO> getOrderItemByProductId(Long productId) {
        return orderItemRepository.findByProductId(productId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
