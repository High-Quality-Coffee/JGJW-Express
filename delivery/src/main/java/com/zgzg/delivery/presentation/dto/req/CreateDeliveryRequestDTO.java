package com.zgzg.delivery.presentation.dto.req;

import java.util.UUID;

import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.entity.DeliveryStatus;

import lombok.Getter;

@Getter
public class CreateDeliveryRequestDTO {

	private UUID orderId;
	private DeliveryStatus deliveryStatus;
	private UUID originHubId; // 출발 허브
	private UUID destinationHubId; // 목적지 허브
	private String receiverAddress;
	private String receiverName;
	private String receiverSlackId;
	// private UUID deliveryPersonId;

	public Delivery toEntity() {
		return Delivery.builder()
			.orderId(orderId)
			.deliveryStatus(deliveryStatus)
			.originHubId(originHubId)
			.destinationHubId(destinationHubId)
			.receiverAddress(receiverAddress)
			.receiverName(receiverName)
			.receiverSlackId(receiverSlackId)
			.build();
	}
}
