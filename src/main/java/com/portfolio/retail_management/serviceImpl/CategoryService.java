package com.portfolio.retail_management.serviceImpl;

import com.portfolio.retail_management.dto.CategoryDTO;
import com.portfolio.retail_management.dto.ProductDTO;
import com.portfolio.retail_management.exception.NotFoundException;
import com.portfolio.retail_management.models.Category;
import com.portfolio.retail_management.models.Product;
import com.portfolio.retail_management.models.Supplier;
import com.portfolio.retail_management.repository.CategoryRepository;
import com.portfolio.retail_management.repository.ProductRepository;
import com.portfolio.retail_management.repository.SupplierRepository;
import com.portfolio.retail_management.service.ICategoryService;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {


    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, SupplierRepository supplierRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }


    private CategoryDTO convertCategoryToDTO(Category category, boolean includeProducts) {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        if (includeProducts) {
            categoryDTO.setProducts(category.getProducts().stream()
                    .map(product -> convertProductToDTO(product, true, true))
                    .collect(Collectors.toSet()));
        }
        categoryDTO.setCreatedAt(category.getCreatedAt());
        categoryDTO.setUpdatedAt(category.getUpdatedAt());
        return categoryDTO;
    }

    private ProductDTO convertProductToDTO(Product product, boolean includeCategory, boolean includeSupplier) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());

        // Sertakan ID supplier jika diminta
        if (includeSupplier && product.getSupplier() != null) {
            productDTO.setSupplierId(product.getSupplier().getId());
        }

        // Sertakan ID kategori jika diminta
        if (includeCategory && product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
        }

        return productDTO;
    }

    private Category convertDTOToCategoryEntity(CategoryDTO categoryDTO, boolean includeProducts) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        if (includeProducts && categoryDTO.getProducts() != null) {
            category.setProducts(categoryDTO.getProducts().stream()
                    .map(this::convertProductToEntity)
                    .collect(Collectors.toSet()));
        }
        return category;
    }

    private Product convertProductToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId()); // Hati-hati juga dengan ID di sini
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCreatedAt(productDTO.getCreatedAt());
        product.setUpdatedAt(productDTO.getUpdatedAt());

        // Sertakan Supplier jika ID ada
        if (productDTO.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new NotFoundException("Supplier not found"));
            product.setSupplier(supplier);
        }

        // Sertakan Category jika ID ada
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            product.setCategory(category);
        }

        return product;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertDTOToCategoryEntity(categoryDTO, true);
        category = categoryRepository.save(category);
        return convertCategoryToDTO(category, true);

    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category not found with ID: " + categoryId));

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        if (categoryDTO.getProducts() != null) {
            category.setProducts(categoryDTO.getProducts().stream()
                    .map(this::convertProductToEntity)
                    .collect(Collectors.toSet()));
        }
        category = categoryRepository.save(category);
        return convertCategoryToDTO(category, true);
    }



    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category not found with ID: " + categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category =  categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category not found with ID: " + categoryId));

        return convertCategoryToDTO(category, true);

    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        return categoryRepository.findAll().stream()
                .map(category -> convertCategoryToDTO(category, true))
                .collect(Collectors.toList());

    }

    @Override
    public List<CategoryDTO> getCategoryByProductName(String productName) {
        List<Product> products = productRepository.findByName(productName);
        return products.stream()
                .map(product -> convertCategoryToDTO(product.getCategory(), false)) // Include categories without products
                .distinct() // Ensure no duplicate categories
                .collect(Collectors.toList());
    }

}
