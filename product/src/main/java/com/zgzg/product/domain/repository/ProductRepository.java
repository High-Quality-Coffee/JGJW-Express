package com.zgzg.product.domain.repository;

import com.zgzg.product.domain.model.Product;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<Product> save(Product product);
    List<Product> findAll();

    boolean existsProduct(@Param("productName") String productName, @Param("productName") UUID storeId, @Param("productName") UUID hubId);

    Optional<Product> findById(UUID id);
}
