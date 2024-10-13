package com.portfolio.retail_management.service;


import com.portfolio.retail_management.dto.UserDTO;
import com.portfolio.retail_management.models.enums.Role;

import java.util.List;

public interface IUserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);

    UserDTO getUserById(Long userId, Role requestingUserRole);

    List<UserDTO> getAllUsers(Role requestingUserRole);

    UserDTO findByUsername(String username);

}
