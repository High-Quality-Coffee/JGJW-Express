package com.zgzg.order.application.dto.res;

import java.math.BigDecimal;
import java.util.UUID;

import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class OrderResponseDTO {

	private UUID orderId;
	private UUID receiverCompanyId;
	private UUID supplierCompanyId;
	private String supplierCompanyName;
	private BigDecimal orderTotalPrice;
	private OrderStatus orderStatus;
	private String orderRequest;

	public static OrderResponseDTO from(Order order) {

		return OrderResponseDTO.builder()
			.orderId(order.getOrderId())
			.receiverCompanyId(order.getReceiverCompanyId())
			.supplierCompanyId(order.getSupplierCompanyId())
			.supplierCompanyName(order.getSupplierCompanyName())
			.orderTotalPrice(order.getOrderTotalPrice())
			.orderStatus(order.getOrderStatus())
			.orderRequest(order.getOrderRequest())
			.build();
	}
}
