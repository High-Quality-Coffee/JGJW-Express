package com.zgzg.order.presentation.dto.req;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.zgzg.order.application.dto.res.OrderDetailDTO;
import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.entity.OrderStatus;

import lombok.Getter;

@Getter
public class CreateOrderRequestDto {

	private UUID supplierCompanyId;
	private UUID receiverCompanyId;
	private String supplierCompanyName;
	private BigDecimal orderTotalPrice;
	private OrderStatus orderStatus;
	private String orderRequest;
	private List<OrderDetailDTO> productList;

	public Order toEntity(CreateOrderRequestDto requestDto) {
		return Order.builder()
			.supplierCompanyId(requestDto.getSupplierCompanyId())
			.receiverCompanyId(requestDto.getReceiverCompanyId())
			.supplierCompanyName(requestDto.getSupplierCompanyName())
			.orderTotalPrice(requestDto.getOrderTotalPrice())
			.orderStatus(requestDto.getOrderStatus())
			.orderRequest(requestDto.getOrderRequest())
			.build();
	}
}
