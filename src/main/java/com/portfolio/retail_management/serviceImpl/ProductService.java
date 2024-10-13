package com.portfolio.retail_management.serviceImpl;

import com.portfolio.retail_management.dto.ProductDTO;
import com.portfolio.retail_management.exception.NotFoundException;
import com.portfolio.retail_management.models.Category;
import com.portfolio.retail_management.models.Product;
import com.portfolio.retail_management.models.Supplier;
import com.portfolio.retail_management.repository.CategoryRepository;
import com.portfolio.retail_management.repository.ProductRepository;
import com.portfolio.retail_management.repository.SupplierRepository;
import com.portfolio.retail_management.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
    }


    private ProductDTO convertProductToDTO(Product product, boolean includeCategory, boolean includeSupplier) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        if (includeSupplier && product.getSupplier() != null) {
            productDTO.setSupplierId(product.getSupplier().getId());
        }
        if (includeCategory && product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
        }
        return productDTO;
    }

    private Product convertDTOToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        // Tidak mengatur createdAt dan updatedAt dari DTO

        // Validasi dan set Category
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found with ID: " + productDTO.getCategoryId()));
            product.setCategory(category);
        }

        // Validasi dan set Supplier
        if (productDTO.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new NotFoundException("Supplier not found with ID: " + productDTO.getSupplierId()));
            product.setSupplier(supplier);
        }

        return product;
    }


    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertDTOToProduct(productDTO);
        product = productRepository.save(product);
        return convertProductToDTO(product, true, true);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());

        // Update Category jika disediakan
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found with ID: " + productDTO.getCategoryId()));
            existingProduct.setCategory(category);
        }

        // Update Supplier jika disediakan
        if (productDTO.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new NotFoundException("Supplier not found with ID: " + productDTO.getSupplierId()));
            existingProduct.setSupplier(supplier);
        }
        productRepository.save(existingProduct);

        return convertProductToDTO(existingProduct, true, true);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository.findAll().stream()
                .map(product -> convertProductToDTO(product, true, true))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new NotFoundException("Product not found"));
        return convertProductToDTO(product, true, true);
    }

    @Override
    public List<ProductDTO> getProductByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new NotFoundException("No products found for Category ID: " + categoryId);
        }
        return products.stream()
                .map(product -> convertProductToDTO(product, true, true))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductSupplierId(Long supplierId) {
        List<Product> products = productRepository.findBySupplierId(supplierId);
        if (products.isEmpty()) {
            throw new NotFoundException("No products found for Supplier ID: " + supplierId);
        }
        return products.stream()
                .map(product -> convertProductToDTO(product, true, true))
                .collect(Collectors.toList());
    }
}
