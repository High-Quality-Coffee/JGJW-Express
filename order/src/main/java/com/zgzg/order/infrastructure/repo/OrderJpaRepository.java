package com.zgzg.order.infrastructure.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zgzg.order.domain.entity.Order;

public interface OrderJpaRepository extends JpaRepository<Order, UUID>, OrderJpaRepositoryCustom {

	Order findByOrderIdAndDeletedAtIsNull(UUID orderId);

	Order findByIdAndDeletedAtIsNullAndSupplierHubId(UUID orderId);
}