package com.zgzg.order.domain.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zgzg.order.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
