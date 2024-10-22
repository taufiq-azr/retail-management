package com.portfolio.retail_management.serviceImpl;

import com.portfolio.retail_management.dto.CartItemDTO;
import com.portfolio.retail_management.exception.NotFoundException;
import com.portfolio.retail_management.models.Cart;
import com.portfolio.retail_management.models.CartItem;
import com.portfolio.retail_management.models.Product;
import com.portfolio.retail_management.repository.CartItemRepository;
import com.portfolio.retail_management.repository.CartRepository;
import com.portfolio.retail_management.repository.ProductRepository;
import com.portfolio.retail_management.service.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService implements ICartItemService {


    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;

    }

    private CartItemDTO convertCartItemToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();

        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setCartId(cartItem.getCart().getId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setCreatedAt(cartItem.getCreatedAt());
        cartItemDTO.setUpdatedAt(cartItem.getUpdatedAt());

        return cartItemDTO;
    }

    private CartItem convertDTOToCartItemEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();

        cartItem.setQuantity(cartItemDTO.getQuantity());

        if (cartItem.getProduct() != null) {
            Product product = productRepository.findById(cartItemDTO.getProductId()).orElseThrow(
                    () -> new NotFoundException("Product not found with ID: " + cartItemDTO.getProductId()));
            cartItem.setProduct(product);
        }

        if (cartItem.getCart() != null) {
           Cart cart = cartRepository.findById(cartItemDTO.getCartId()).orElseThrow(
                   () -> new NotFoundException("Cart not found with ID: " + cartItemDTO.getCartId()));

           cartItem.setCart(cart);
        }

        return cartItem;
    }


    @Override
    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = convertDTOToCartItemEntity(cartItemDTO);
        cartItemRepository.save(cartItem);
        return convertCartItemToDTO(cartItem);
    }

    @Override
    public CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cart item not found with ID: " + id));

        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem = cartItemRepository.save(cartItem);
        return convertCartItemToDTO(cartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cart item not found with ID: " + id));

        cartItemRepository.delete(cartItem);
    }

    @Override
    public CartItemDTO getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cart item not found with ID: " + id));

        return convertCartItemToDTO(cartItem);
    }

    @Override
    public List<CartItemDTO> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .map(this::convertCartItemToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CartItemDTO> getCartItemsByProductId(Long productId) {
        return cartItemRepository.findByProductId(productId).stream()
                .map(this::convertCartItemToDTO)
                .collect(Collectors.toList());
    }
}
