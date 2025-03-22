package com.zgzg.order.infrastructure.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zgzg.order.domain.entity.OrderDetail;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, UUID> {

	OrderDetail save(OrderDetail orderDetail);

	@Modifying
	@Query("UPDATE OrderDetail od SET od.deletedAt = CURRENT_TIMESTAMP WHERE od.order.orderId = :orderId")
	void softDeleteDetails(@Param("orderId") UUID orderId);

	List<OrderDetail> findByOrderDetails(UUID orderId);
}
