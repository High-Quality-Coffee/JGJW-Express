package com.zgzg.order.domain.repo;

import java.util.Optional;
import java.util.UUID;

import com.zgzg.order.domain.entity.Order;

public interface OrderRepository {
	Order save(Order order);

	Optional<Order> findById(UUID orderId);

	Order findByOrderIdAndNotDeleted(UUID orderId);

}
