package com.zgzg.order.infrastructure.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zgzg.order.domain.entity.OrderDetail;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, UUID> {

	OrderDetail save(OrderDetail orderDetail);

	List<OrderDetail> findByOrder_OrderIdAndDeletedAtIsNull(UUID orderId);
}
