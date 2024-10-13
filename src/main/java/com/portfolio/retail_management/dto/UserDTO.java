package com.portfolio.retail_management.dto;

import com.portfolio.retail_management.models.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private String email;
    private Set<OrderDTO> orders;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
