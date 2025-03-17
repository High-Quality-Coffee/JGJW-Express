package com.zgzg.order.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		log.info("Order created: " + savedOrder.getReceiverCompanyId());
		return savedOrder.getOrderId();
	}
}
