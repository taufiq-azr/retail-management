package com.portfolio.retail_management.service;

import com.portfolio.retail_management.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    void deleteCategory(Long categoryId);

    CategoryDTO getCategoryById(Long categoryId);

    List<CategoryDTO> getAllCategory();

    List<CategoryDTO> getCategoryByProductName(String productName);
}
