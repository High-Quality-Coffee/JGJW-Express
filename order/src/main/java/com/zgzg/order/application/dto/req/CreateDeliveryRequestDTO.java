package com.zgzg.order.application.dto.req;

import java.util.UUID;

import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.infrastructure.dto.CompanyResponseDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateDeliveryRequestDTO {

	private UUID orderId;
	private UUID originHubId; // 출발 허브
	private UUID destinationHubId; // 목적지 허브
	private String receiverAddress;
	private String receiverName;
	private String receiverSlackId;
	private String productName;
	private Integer quantity;
	private String request;

	public CreateDeliveryRequestDTO(Order order, CompanyResponseDTO supplier, CompanyResponseDTO receiver,
		String productName, Integer quantity) {
		this.orderId = order.getOrderId();
		this.originHubId = supplier.getHub_id(); // 공급
		this.destinationHubId = receiver.getHub_id(); // 요청
		this.receiverAddress = receiver.getAddress();
		this.receiverName = receiver.getName();
		this.receiverSlackId = order.getSlackId();
		this.productName = productName;
		this.quantity = quantity;
		this.request = order.getOrderRequest();
	}
}
