package com.portfolio.retail_management.serviceImpl;


import com.portfolio.retail_management.dto.OrderDTO;
import com.portfolio.retail_management.dto.OrderItemDTO;
import com.portfolio.retail_management.dto.UserDTO;
import com.portfolio.retail_management.exception.NotFoundException;
import com.portfolio.retail_management.models.Order;
import com.portfolio.retail_management.models.OrderItem;
import com.portfolio.retail_management.models.User;
import com.portfolio.retail_management.models.enums.Role;
import com.portfolio.retail_management.repository.UserRepository;
import com.portfolio.retail_management.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    // Konversi dari User Entity ke UserDTO
    private UserDTO convertToDTO(User user, boolean includeOrders, boolean includeRole) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        // Hanya sertakan role jika diinginkan
        if (includeRole) {
            dto.setRole(user.getRole());
        }

        // Hanya sertakan orders jika diminta (untuk admin)
        if (includeOrders) {
            dto.setOrders(user.getOrders().stream()
                    .map(this::convertToOrderDTO)
                    .collect(Collectors.toSet()));
        } else {
            dto.setOrders(null);
        }
        return dto;
    }

    // Konversi dari Order Entity ke OrderDTO
    private OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();

        // Konversi data Order ke OrderDTO
        orderDTO.setId(order.getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setPaymentStatus(order.getPaymentStatus());
        orderDTO.setShippingAddress(order.getShippingAddress());

        // Konversi Set<OrderItem> menjadi Set<OrderItemDTO>
        orderDTO.setOrderItems(order.getOrderItems().stream()
                .map(this::convertToOrderItemDTO) // Mengonversi OrderItem ke OrderItemDTO
                .collect(Collectors.toSet()));

        return orderDTO;
    }

    // Metode tambahan untuk konversi OrderItem ke OrderItemDTO
    private OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setOrderId(orderItem.getOrder().getId());
        orderItemDTO.setProductId(orderItem.getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setUnitPrice(orderItem.getUnitPrice());
        orderItemDTO.setCreatedAt(orderItem.getCreatedAt());
        orderItemDTO.setUpdatedAt(orderItem.getUpdatedAt());

        return orderItemDTO;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        // Set semua nilai dari userDTO ke user Entity
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(userDTO.getPassword());
        user.setCreatedAt(userDTO.getCreatedAt());
        userRepository.save(user);
        return convertToDTO(user, false, true); // Biasanya tidak ada orders saat create
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);
        return convertToDTO(user, false, true); // Update user biasanya tidak melibatkan orders
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUserById(Long userId, Role requestingUserRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        boolean isAdmin = requestingUserRole == Role.ADMIN;
        return convertToDTO(user, isAdmin, isAdmin); // Hanya admin yang melihat orders
    }

    @Override
    public List<UserDTO> getAllUsers(Role requestingUserRole) {
        List<User> users = userRepository.findAll();
        boolean isAdmin = requestingUserRole == Role.ADMIN;

        return users.stream()
                .map(user -> convertToDTO(user, isAdmin, isAdmin))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        return convertToDTO(user, true, true); // Misalnya, ini admin yang mencari user lain
    }
}
