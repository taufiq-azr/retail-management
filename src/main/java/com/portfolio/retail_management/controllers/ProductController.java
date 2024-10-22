package com.portfolio.retail_management.controllers;

import com.portfolio.retail_management.dto.ProductDTO;
import com.portfolio.retail_management.response.ApiResponse;
import com.portfolio.retail_management.serviceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productServiceImpl.createProduct(productDTO);
            ApiResponse<ProductDTO> response = new ApiResponse<>(true, "Product created", 0, createdProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<ProductDTO> response = new ApiResponse<>(false, "Product not created", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productServiceImpl.updateProduct(productId, productDTO);
            ApiResponse<ProductDTO> response = new ApiResponse<>(true, "Product updated", 0, updatedProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<ProductDTO> response = new ApiResponse<>(false, "Product not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        try {
            productServiceImpl.deleteProduct(productId);
            ApiResponse<Void> response = new ApiResponse<>(true, "Product deleted", 0, null);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Product not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        try {
            List<ProductDTO> products = productServiceImpl.getAllProduct();
            ApiResponse<List<ProductDTO>> response = new ApiResponse<>(true, "Products retrieved", 0, products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<ProductDTO>> response = new ApiResponse<>(false, "Products not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long productId) {
        try {
            ProductDTO product = productServiceImpl.getProductById(productId);
            ApiResponse<ProductDTO> response = new ApiResponse<>(true, "Product retrieved", 0, product);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<ProductDTO> response = new ApiResponse<>(false, "Product not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductByCategoryId(@PathVariable Long categoryId) {
        try {
            List<ProductDTO> products = productServiceImpl.getProductByCategoryId(categoryId);
            ApiResponse<List<ProductDTO>> response = new ApiResponse<>(true, "Products retrieved", 0, products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<ProductDTO>> response = new ApiResponse<>(false, "Products not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductBySupplierId(@PathVariable Long supplierId) {
        try {
            List<ProductDTO> products = productServiceImpl.getProductSupplierId(supplierId);
            ApiResponse<List<ProductDTO>> response = new ApiResponse<>(true, "Products retrieved", 0, products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<ProductDTO>> response = new ApiResponse<>(false, "Products not found", 404, null);
            return ResponseEntity.status(404).body(response);
        }
    }


}
