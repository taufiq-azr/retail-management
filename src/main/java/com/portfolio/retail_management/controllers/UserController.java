package com.portfolio.retail_management.controllers;

import com.portfolio.retail_management.dto.UserDTO;
import com.portfolio.retail_management.models.enums.Role;
import com.portfolio.retail_management.response.ApiResponse;
import com.portfolio.retail_management.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUser = userService.createUser(userDTO);
            ApiResponse<UserDTO> response = new ApiResponse<>(true, "User created", 0, createdUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<UserDTO> response = new ApiResponse<>(false, "User not created", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get all admin users
    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllAdmin() {
        try {
            List<UserDTO> admins = userService.getAllUsers(Role.ADMIN);
            ApiResponse<List<UserDTO>> response = new ApiResponse<>(true, "Admin users retrieved", 0, admins);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<UserDTO>> response = new ApiResponse<>(false, "Admin users not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get all customers
    @GetMapping("/customer")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllCustomers() {
        try {
            List<UserDTO> customers = userService.getAllUsers(Role.CUSTOMER);
            ApiResponse<List<UserDTO>> response = new ApiResponse<>(true, "Customer users retrieved", 0, customers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<UserDTO>> response = new ApiResponse<>(false, "Customer users not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get admin by ID
    @GetMapping("/admin/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getAdminById(@PathVariable Long userId) {
        try {
            UserDTO admin = userService.getUserById(userId, Role.ADMIN);
            ApiResponse<UserDTO> response = new ApiResponse<>(true, "Admin retrieved", 0, admin);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<UserDTO> response = new ApiResponse<>(false, "Admin not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get customer by ID
    @GetMapping("/customer/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getCustomerById(@PathVariable Long userId) {
        try {
            UserDTO customer = userService.getUserById(userId, Role.CUSTOMER);
            ApiResponse<UserDTO> response = new ApiResponse<>(true, "Customer retrieved", 0, customer);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<UserDTO> response = new ApiResponse<>(false, "Customer not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get customer by username
    @GetMapping("/customer/username/{username}")
    public ResponseEntity<ApiResponse<UserDTO>> getCustomerByUsername(@PathVariable String username) {
        try {
            UserDTO customer = userService.findByUsername(username);
            ApiResponse<UserDTO> response = new ApiResponse<>(true, "Customer retrieved", 0, customer);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<UserDTO> response = new ApiResponse<>(false, "Customer not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Update admin by ID
    @PutMapping("/admin/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateAdminById(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedAdmin = userService.updateUser(userId, userDTO);
            ApiResponse<UserDTO> response = new ApiResponse<>(true, "Admin updated", 0, updatedAdmin);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<UserDTO> response = new ApiResponse<>(false, "Admin not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Update customer by ID
    @PutMapping("/customer/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateCustomerById(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedCustomer = userService.updateUser(userId, userDTO);
            ApiResponse<UserDTO> response = new ApiResponse<>(true, "Customer updated", 0, updatedCustomer);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<UserDTO> response = new ApiResponse<>(false, "Customer not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Delete admin by ID
    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteAdminById(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            ApiResponse<Void> response = new ApiResponse<>(true, "Admin deleted", 0, null);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Admin not found", 1308, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Delete customer by ID
    @DeleteMapping("/customer/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomerById(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            ApiResponse<Void> response = new ApiResponse<>(true, "Customer deleted", 0, null);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Customer not found", 1308, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
