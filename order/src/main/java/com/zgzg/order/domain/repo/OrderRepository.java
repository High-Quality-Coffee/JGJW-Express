package com.zgzg.order.domain.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.entity.OrderDetail;

public interface OrderRepository {
	Order save(Order order);

	Optional<Order> findById(UUID orderId);

	OrderDetail saveDetail(OrderDetail orderDetail);

	List<OrderDetail> findByOrderIdAndNotDeleted(UUID orderId);
}
