package com.portfolio.retail_management.service;

import com.portfolio.retail_management.dto.CartItemDTO;

import java.util.List;

public interface ICartItemService {

    CartItemDTO createCartItem(CartItemDTO cartItemDTO);

    CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO);

    void deleteCartItem(Long id);

    CartItemDTO getCartItemById(Long id);

    List<CartItemDTO> getAllCartItems();

    List<CartItemDTO> getCartItemsByProductId(Long productId);
}
