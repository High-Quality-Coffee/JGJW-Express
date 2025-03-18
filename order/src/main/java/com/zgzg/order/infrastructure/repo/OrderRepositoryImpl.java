package com.zgzg.order.infrastructure.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.repo.OrderRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
	private final OrderJpaRepository jpaRepository;

	@Override
	public Order save(Order order) {
		return jpaRepository.save(order);
	}

	@Override
	public Optional<Order> findById(UUID orderId) {
		return jpaRepository.findById(orderId);
	}

	@Override
	public Order findByOrderIdAndNotDeleted(UUID orderId) {
		return jpaRepository.findByOrderIdAndDeletedAtIsNull(orderId);
	}
}
