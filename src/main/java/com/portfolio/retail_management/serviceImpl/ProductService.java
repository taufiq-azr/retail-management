package com.portfolio.retail_management.serviceImpl;

import com.portfolio.retail_management.repository.CategoryRepository;
import com.portfolio.retail_management.repository.ProductRepository;
import com.portfolio.retail_management.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CategoryRepository categoryRepository;



}
