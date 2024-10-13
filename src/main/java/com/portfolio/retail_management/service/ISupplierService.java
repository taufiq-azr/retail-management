package com.portfolio.retail_management.service;

import com.portfolio.retail_management.dto.SupplierDTO;

import java.util.List;

public interface ISupplierService {

    SupplierDTO createSupplier(SupplierDTO supplierDTO);

    SupplierDTO updateSupplier(Long supplierId, SupplierDTO supplierDTO);

    void deleteSupplier(Long supplierId);

    List<SupplierDTO> getAllSupplier();

    SupplierDTO getSupplierById(Long supplierId);


}
