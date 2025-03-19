package com.zgzg.product.infrastructure.repository;

import com.zgzg.product.domain.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<Product, UUID> {
    @NotNull
    List<Product> findAll();

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.productName = :productName AND p.storeId = :storeId AND p.hubId = :hubId")
    boolean existsProduct(
            @Param("productName") String productName,
            @Param("storeId") UUID storeId,
            @Param("hubId") UUID hubId
    );


}
