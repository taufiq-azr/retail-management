package com.portfolio.retail_management.serviceImpl;

import com.portfolio.retail_management.dto.CartDTO;
import com.portfolio.retail_management.dto.CartItemDTO;
import com.portfolio.retail_management.exception.NotFoundException;
import com.portfolio.retail_management.models.Cart;
import com.portfolio.retail_management.models.CartItem;
import com.portfolio.retail_management.models.Product;
import com.portfolio.retail_management.models.User;
import com.portfolio.retail_management.repository.CartRepository;
import com.portfolio.retail_management.repository.ProductRepository;
import com.portfolio.retail_management.repository.UserRepository;
import com.portfolio.retail_management.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private CartDTO convertCartToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setCartItems(cart.getCartItems().stream()
                .map(this::convertCartItemToDTO)
                .collect(Collectors.toSet()));
        cartDTO.setCreatedAt(cart.getCreatedAt());
        cartDTO.setUpdatedAt(cart.getUpdatedAt());

        return cartDTO;
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

    private Cart convertDTOToCartEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());

        // Mengambil User dari UserRepository
        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + cartDTO.getUserId()));
        cart.setUser(user); // Set User ke Cart

        cart.setCartItems(cartDTO.getCartItems().stream()
                .map(this::convertDTOToCartItemEntity)
                .collect(Collectors.toSet()));
        cart.setCreatedAt(cartDTO.getCreatedAt());
        cart.setUpdatedAt(cartDTO.getUpdatedAt());

        return cart;
    }

    private CartItem convertDTOToCartItemEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemDTO.getQuantity());

        // Set Product jika ada productId di DTO
        if (cartItemDTO.getProductId() != null) {
            Product product = productRepository.findById(cartItemDTO.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found with ID: " + cartItemDTO.getProductId()));
            cartItem.setProduct(product);
        }

        // Set Cart jika ada cartId di DTO
        if (cartItemDTO.getCartId() != null) {
            Cart cart = cartRepository.findById(cartItemDTO.getCartId())
                    .orElseThrow(() -> new NotFoundException("Cart not found with ID: " + cartItemDTO.getCartId()));
            cartItem.setCart(cart);
        }

        return cartItem;
    }

    @Override
    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = convertDTOToCartEntity(cartDTO);
        cart = cartRepository.save(cart);
        return convertCartToDTO(cart);

    }

    @Override
    public CartDTO updateCart(Long id, CartDTO cartDTO) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cart not found with ID: " + id));
        cart.setCartItems(cartDTO.getCartItems().stream()
                .map(this::convertDTOToCartItemEntity)
                .collect(Collectors.toSet()));
        cart = cartRepository.save(cart);
        return convertCartToDTO(cart);
    }

    @Override
    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cart not found with ID: " + id));
        cartRepository.delete(cart);
    }

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cart not found with ID: " + id));
        return convertCartToDTO(cart);
    }

    @Override
    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(this::convertCartToDTO)
                .collect(Collectors.toList());
    }
}
