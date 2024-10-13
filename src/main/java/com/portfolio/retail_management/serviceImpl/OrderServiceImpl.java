package com.portfolio.retail_management.serviceImpl;

import com.portfolio.retail_management.dto.OrderDTO;
import com.portfolio.retail_management.dto.OrderItemDTO;
import com.portfolio.retail_management.exception.NotFoundException;
import com.portfolio.retail_management.models.Order;
import com.portfolio.retail_management.models.OrderItem;
import com.portfolio.retail_management.models.Product;
import com.portfolio.retail_management.models.User;
import com.portfolio.retail_management.models.enums.OrderStatus;
import com.portfolio.retail_management.models.enums.PaymentStatus;
import com.portfolio.retail_management.repository.OrderRepository;
import com.portfolio.retail_management.repository.ProductRepository;
import com.portfolio.retail_management.repository.UserRepository;
import com.portfolio.retail_management.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private OrderDTO convertToDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setPaymentStatus(order.getPaymentStatus());
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setOrderItems(order.getOrderItems().stream()
                .map(this::convertToOrderItemDTO)
                .collect(Collectors.toSet()));
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setUpdatedAt(order.getUpdatedAt());

        return orderDTO;
    }

    private OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
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

    private Order convertToEntity(OrderDTO orderDTO, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setPaymentStatus(orderDTO.getPaymentStatus());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setUpdatedAt(orderDTO.getUpdatedAt());

        if (orderDTO.getOrderItems() != null) {
            order.setOrderItems(orderDTO.getOrderItems().stream()
                    .map(orderItemDTO -> {
                        OrderItem orderItem = new OrderItem();
                        Product product = productRepository.findById(orderItemDTO.getProductId())
                                .orElseThrow(() -> new NotFoundException("Product not found"));
                        orderItem.setProduct(product);
                        orderItem.setQuantity(orderItemDTO.getQuantity());
                        orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
                        orderItem.setOrder(order);
                        return orderItem;
                    })
                    .collect(Collectors.toSet()));
        }

        return order;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Optional<User> userOpt = userRepository.findById(orderDTO.getUserId());
        if (userOpt.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        Order order = convertToEntity(orderDTO, userOpt.get());
        order = orderRepository.save(order);

        return convertToDTO(order);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        Optional<User> userOpt = userRepository.findById(orderDTO.getUserId());
        if (userOpt.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setPaymentStatus(orderDTO.getPaymentStatus());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setUpdatedAt(orderDTO.getUpdatedAt());

        // Update order items
        if (orderDTO.getOrderItems() != null) {
            order.getOrderItems().clear();
            orderDTO.getOrderItems().forEach(orderItemDTO -> {
                OrderItem orderItem = new OrderItem();
                Product product = productRepository.findById(orderItemDTO.getProductId())
                        .orElseThrow(() -> new NotFoundException("Product not found"));
                orderItem.setProduct(product);
                orderItem.setQuantity(orderItemDTO.getQuantity());
                orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
                orderItem.setOrder(order);
                order.getOrderItems().add(orderItem);
            });
        }
        orderRepository.save(order);
        return convertToDTO(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
       Order orderDeleted = orderRepository.findById(orderId).orElseThrow(
               ()-> new NotFoundException("Order Not Found !"));
       orderRepository.deleteById(orderId);
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order orderGetById = orderRepository.findById(orderId).orElseThrow(
                ()-> new NotFoundException("Order Not Found"));
        return convertToDTO(orderGetById);
    }

    @Override
    public List<OrderDTO> getAllOrder() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findOrderByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findOrderByPaymentStatus(PaymentStatus paymentStatus) {
        return orderRepository.findByPaymentStatus(paymentStatus).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findOrderByOrderDate(Date orderDate) {
        return orderRepository.findByOrderDate(orderDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
