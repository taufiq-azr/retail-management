package com.portfolio.retail_management.controllers;

import com.portfolio.retail_management.dto.SupplierDTO;
import com.portfolio.retail_management.response.ApiResponse;
import com.portfolio.retail_management.serviceImpl.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final SupplierServiceImpl supplierService;

    @Autowired
    public SupplierController(SupplierServiceImpl supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierDTO>>> getAllSuppliers() {
        try {
            List<SupplierDTO> suppliers = supplierService.getAllSupplier();
            ApiResponse<List<SupplierDTO>> response = new ApiResponse<>(true, "Suppliers retrieved", 0, suppliers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<SupplierDTO>> response = new ApiResponse<>(false, "Suppliers not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<ApiResponse<SupplierDTO>> getSupplierById(@PathVariable Long supplierId) {
        try {
            SupplierDTO supplier = supplierService.getSupplierById(supplierId);
            ApiResponse<SupplierDTO> response = new ApiResponse<>(true, "Supplier retrieved", 0, supplier);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<SupplierDTO> response = new ApiResponse<>(false, "Supplier not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierDTO>> createSupplier(@RequestBody SupplierDTO supplierDTO) {
        try {
            SupplierDTO createdSupplier = supplierService.createSupplier(supplierDTO);
            ApiResponse<SupplierDTO> response = new ApiResponse<>(true, "Supplier created", 0, createdSupplier);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<SupplierDTO> response = new ApiResponse<>(false, "Supplier not created", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<ApiResponse<SupplierDTO>> updateSupplierById(@PathVariable Long supplierId, @RequestBody SupplierDTO supplierDTO) {
        try {
            SupplierDTO updatedSupplier = supplierService.updateSupplier(supplierId, supplierDTO);
            ApiResponse<SupplierDTO> response = new ApiResponse<>(true, "Supplier updated", 0, updatedSupplier);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<SupplierDTO> response = new ApiResponse<>(false, "Supplier not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<ApiResponse<Void>> deleteSupplierById(@PathVariable Long supplierId) {
        try {
            supplierService.deleteSupplier(supplierId);
            ApiResponse<Void> response = new ApiResponse<>(true, "Supplier deleted", 0, null);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Supplier not found", 404, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
