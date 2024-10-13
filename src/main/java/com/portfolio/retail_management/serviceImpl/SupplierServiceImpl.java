package com.portfolio.retail_management.serviceImpl;

import com.portfolio.retail_management.dto.ProductDTO;
import com.portfolio.retail_management.dto.SupplierDTO;
import com.portfolio.retail_management.exception.NotFoundException;
import com.portfolio.retail_management.models.Product;
import com.portfolio.retail_management.models.Supplier;
import com.portfolio.retail_management.repository.SupplierRepository;
import com.portfolio.retail_management.service.IOrderService;
import com.portfolio.retail_management.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    private SupplierDTO convertSupplierToDTO(Supplier supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setName(supplier.getName());
        supplierDTO.setAddress(supplier.getAddress());
        supplierDTO.setContractDetails(supplier.getContractDetails());
        if (supplier.getProducts() != null) {
            supplierDTO.setProducts(supplier.getProducts().stream()
                    .map(product -> convertProductToDTO(product, true, true)) // Konversi Entity ke DTO
                    .collect(Collectors.toSet()));
        }
        supplierDTO.setCreatedAt(supplier.getCreatedAt());
        supplierDTO.setUpdatedAt(supplier.getUpdatedAt());

        return supplierDTO;
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

    private Supplier convertToEntity(SupplierDTO supplierDTO){
        Supplier supplier = new Supplier();

        supplier.setId(supplierDTO.getId());
        supplier.setName(supplierDTO.getName());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setContractDetails(supplierDTO.getContractDetails());


        // Cek apakah ada produk dalam DTO, lalu konversi ke entitas
        if (supplierDTO.getProducts() != null && !supplierDTO.getProducts().isEmpty()) {
            Set<Product> products = supplierDTO.getProducts().stream()
                    .map(productDTO -> {
                        Product product = new Product();
                        product.setName(productDTO.getName());
                        product.setDescription(productDTO.getDescription());
                        product.setPrice(productDTO.getPrice());
                        product.setStockQuantity(productDTO.getStockQuantity());
                        product.setSupplier(supplier);  // Set hubungan ke supplier
                        return product;
                    }).collect(Collectors.toSet());

            supplier.setProducts(products);
        }

        return supplier;

    }

    @Override
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        Supplier suppliers = convertToEntity(supplierDTO);

        suppliers = supplierRepository.save(suppliers);

        return convertSupplierToDTO(suppliers);

    }

    @Override
    public SupplierDTO updateSupplier(Long supplierId, SupplierDTO supplierDTO) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        // Update detail supplier
        supplier.setName(supplierDTO.getName());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setContractDetails(supplierDTO.getContractDetails());


        // Update produk
        if (supplierDTO.getProducts() != null && !supplierDTO.getProducts().isEmpty()) {
            final Supplier supplierFinal = supplier;
            Set<Product> products = supplierDTO.getProducts().stream()
                    .map(productDTO -> {
                        Product product = new Product();
                        product.setName(productDTO.getName());
                        product.setDescription(productDTO.getDescription());
                        product.setPrice(productDTO.getPrice());
                        product.setStockQuantity(productDTO.getStockQuantity());
                        product.setSupplier(supplierFinal);  // Set hubungan ke supplier
                        return product;
                    })
                    .collect(Collectors.toSet());

            supplier.setProducts(products);
        }

        // Simpan supplier yang diperbarui
        supplier = supplierRepository.save(supplier);
        return convertSupplierToDTO(supplier);
    }



    @Override
    public void deleteSupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier Not Found"));
        supplierRepository.delete(supplier);

    }

    @Override
    public List<SupplierDTO> getAllSupplier() {
        return supplierRepository.findAll().stream()
                .map(this::convertSupplierToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO getSupplierById(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(
                () -> new NotFoundException("Supplier Not Found"));
        return convertSupplierToDTO(supplier);
    }
}
