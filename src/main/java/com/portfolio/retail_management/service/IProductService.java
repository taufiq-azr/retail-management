package com.portfolio.retail_management.service;

import com.portfolio.retail_management.dto.ProductDTO;

import java.util.List;

public interface IProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    void deleteProduct(Long productId);

    List<ProductDTO> getAllProduct();

    ProductDTO getProductById(Long productId);

    List<ProductDTO> getProductByCategoryId(Long categoryId);

    List<ProductDTO> getProductSupplierId(Long supplierId);
}
