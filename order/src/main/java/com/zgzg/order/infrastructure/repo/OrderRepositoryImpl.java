package com.zgzg.order.infrastructure.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zgzg.order.application.dto.res.OrderResponseDTO;
import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.entity.OrderDetail;
import com.zgzg.order.domain.repo.OrderRepository;
import com.zgzg.order.presentation.dto.req.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
	private final OrderJpaRepository jpaRepository;
	private final OrderDetailJpaRepository detailJpaRepository;

	@Override
	public Order save(Order order) {
		return jpaRepository.save(order);
	}

	@Override
	public Optional<Order> findById(UUID orderId) {
		return jpaRepository.findById(orderId);
	}

	@Override
	public OrderDetail saveDetail(OrderDetail orderDetail) {
		return detailJpaRepository.save(orderDetail);
	}

	@Override
	public List<OrderDetail> findByOrderIdAndNotDeleted(UUID orderId) {
		return detailJpaRepository.findByOrder_OrderIdAndDeletedAtIsNull(orderId);
	}

	@Override
	public Page<OrderResponseDTO> searchOrderByCriteria(SearchCriteria criteria, Pageable pageable) {
		return jpaRepository.searchOrderByCriteria(criteria, pageable);
	}
	
}
