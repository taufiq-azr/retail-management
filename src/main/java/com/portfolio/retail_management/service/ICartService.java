package com.portfolio.retail_management.service;

import com.portfolio.retail_management.dto.CartDTO;

import java.util.List;

public interface ICartService {

    CartDTO createCart(CartDTO cartDTO);

    CartDTO updateCart(Long id, CartDTO cartDTO);

    void deleteCart(Long id);

    CartDTO getCartById(Long id);

    List<CartDTO> getAllCarts();
}
