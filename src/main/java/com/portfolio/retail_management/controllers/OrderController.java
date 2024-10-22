package com.portfolio.retail_management.controllers;

import com.portfolio.retail_management.dto.OrderDTO;
import com.portfolio.retail_management.models.enums.OrderStatus;
import com.portfolio.retail_management.models.enums.PaymentStatus;
import com.portfolio.retail_management.response.ApiResponse;
import com.portfolio.retail_management.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(new ApiResponse<>(true, "Order created", 0, createdOrder));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to create order", 500, null));
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO updatedOrder = orderService.updateOrder(orderId, orderDTO);
            ApiResponse<OrderDTO> response = new ApiResponse<>(true, "Order updated", 0, updatedOrder);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Invalid status", 400, null));
        } catch (Exception e) {
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, "Order not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            ApiResponse<Void> response = new ApiResponse<>(true, "Order deleted", 0, null);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Order not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {
        try {
            List<OrderDTO> orders = orderService.getAllOrder();
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(true, "Orders retrieved", 0, orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(false, "Orders not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDTO order = orderService.getOrderById(orderId);
            ApiResponse<OrderDTO> response = new ApiResponse<>(true, "Order retrieved", 0, order);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, "Order not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrderByStatus(@PathVariable String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            List<OrderDTO> orders = orderService.findOrderByOrderStatus(orderStatus);
            return ResponseEntity.ok(new ApiResponse<>(true, "Orders retrieved", 0, orders));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Invalid status", 400, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to retrieve orders", 500, null));
        }
    }

    @GetMapping("/payment/{paymentStatus}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrderByPaymentStatus(@PathVariable String paymentStatus) {
        try {
            PaymentStatus status = PaymentStatus.valueOf(paymentStatus.toUpperCase());
            List<OrderDTO> orders = orderService.findOrderByPaymentStatus(status);
            return ResponseEntity.ok(new ApiResponse<>(true, "Orders retrieved", 0, orders));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Invalid payment status", 400, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to retrieve orders", 500, null));
        }
    }

    @GetMapping("/date/{orderDate}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrderByDate(@PathVariable String orderDate) {
        try {
            Date parsedDate = parseDate(orderDate);
            List<OrderDTO> orders = orderService.findOrderByOrderDate(parsedDate);
            return ResponseEntity.ok(new ApiResponse<>(true, "Orders retrieved", 0, orders));
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Invalid date format", 400, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Orders not found", 404, null));
        }
    }

    // Metode helper untuk parsing tanggal
    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(date);
    }

}
