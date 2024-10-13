package com.portfolio.retail_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AdminUserDTO {
    private Set<OrderDTO> orders;
}
