package com.zgzg.delivery.presentation.dto.req;

import java.util.UUID;

import com.zgzg.delivery.domain.entity.Delivery;

import lombok.Getter;

@Getter
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

	public Delivery toEntity() {
		return Delivery.builder()
			.orderId(orderId)
			.originHubId(originHubId)
			.destinationHubId(destinationHubId)
			.receiverAddress(receiverAddress)
			.receiverName(receiverName)
			.receiverSlackId(receiverSlackId)
			.build();
	}
}
