package com.zgzg.order.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.repo.OrderRepository;
import com.zgzg.order.presentation.dto.req.CreateOrderRequestDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	@Transactional
	public UUID createOrder(CreateOrderRequestDto requestDto) {
		Order order = requestDto.toEntity(requestDto);
		log.info("Order creat request: " + order.getReceiverCompanyId());
		Order savedOrder = orderRepository.save(order);
		return savedOrder.getOrderId();
	}

	@Transactional
	public void deleteOrder(UUID orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new BaseException(Code.ORDER_NOT_FOUND));
		// todo. 권한 유효성 검사
		// todo. 사용자 정보 대입, 임시 데이터 제거
		order.softDelete("temp1");
	}
}
