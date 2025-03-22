package com.zgzg.order.domain.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zgzg.order.application.dto.res.OrderResponseDTO;
import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.entity.OrderDetail;
import com.zgzg.order.presentation.dto.req.SearchCriteria;

public interface OrderRepository {
	Order save(Order order);

	Optional<Order> findById(UUID orderId);

	OrderDetail saveDetail(OrderDetail orderDetail);

	List<OrderDetail> findAllByOrderIdAndNotDeleted(UUID orderId);

	Page<OrderResponseDTO> searchOrderByCriteria(SearchCriteria criteria, Pageable pageable, String role, UUID id);

	Order findByIdAndNotDeleted(UUID orderId);

	void softDeleteDetails(UUID orderId);

	Order findByIdAndDeletedAtIsNullAndSupplierHubId(UUID orderId);
}
