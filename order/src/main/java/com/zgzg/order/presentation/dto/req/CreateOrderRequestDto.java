package com.zgzg.order.presentation.dto.req;

import java.math.BigDecimal;
import java.util.UUID;

import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.entity.OrderStatus;

import lombok.Getter;

@Getter
public class CreateOrderRequestDto {

	private UUID supplierCompanyId;
	private UUID receiverCompanyId;
	private BigDecimal orderTotalPrice;
	private OrderStatus orderStatus;
	private String orderRequest;

	public Order toEntity(CreateOrderRequestDto requestDto) {
		return Order.builder()
			.supplierCompanyId(requestDto.getSupplierCompanyId())
			.receiverCompanyId(requestDto.getReceiverCompanyId())
			.orderTotalPrice(requestDto.getOrderTotalPrice())
			.orderStatus(requestDto.getOrderStatus())
			.orderRequest(requestDto.getOrderRequest())
			.build();
	}
}
