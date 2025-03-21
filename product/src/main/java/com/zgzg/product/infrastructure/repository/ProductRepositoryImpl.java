package com.zgzg.product.infrastructure.repository;

import com.zgzg.product.domain.model.Product;
import com.zgzg.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    public Optional<Product> save(Product product) {
        return Optional.of(jpaProductRepository.save(product));
    }

    @Override
    public List<Product> findAll() {return jpaProductRepository.findAll();}

    public boolean existsProduct( @Param("productName") String productName,
                                  @Param("storeId") UUID storeId,
                                  @Param("hubId") UUID hubId) {return jpaProductRepository.existsProduct(productName,storeId,hubId);}

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaProductRepository.findById(id);
    }

}
