package com.zgzg.order.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.order.application.dto.res.OrderDetaiListDTO;
import com.zgzg.order.application.dto.res.OrderDetailDTO;
import com.zgzg.order.application.dto.res.OrderResponseDto;
import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.entity.OrderDetail;
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
		Order savedOrder = orderRepository.save(order);
		for (OrderDetailDTO orderDetailDTO : requestDto.getProductList()) {
			OrderDetail orderDetail = orderDetailDTO.toEntity(order);
			orderRepository.saveDetail(orderDetail);
		}
		return savedOrder.getOrderId();
	}

	@Transactional
	public void deleteOrder(UUID orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new BaseException(Code.ORDER_NOT_FOUND));
		// todo. 권한 유효성 검사
		// todo. 사용자 정보 대입, 임시 데이터 제거
		order.softDelete("temp1");
		// todo. 주문이 삭제될 때 연관된 데이터도 함께 관리 - orderDetail
	}

	public OrderDetaiListDTO getOrder(UUID orderId) {
		// todo. 권한 확인 - MASTER, HUB(담당 허브만), DELIVERY(본인 주문만), STORE(본인 주문만)
		List<OrderDetail> orderDetails = orderRepository.findByOrderIdAndNotDeleted(orderId);
		List<OrderResponseDto> detailList = orderDetails.stream()
			.map(detail -> OrderResponseDto.from(detail))
			.toList();

		return new OrderDetaiListDTO(orderId, detailList);
	}
}
