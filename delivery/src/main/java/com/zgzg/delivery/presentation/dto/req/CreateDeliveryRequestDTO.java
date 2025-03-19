package com.zgzg.delivery.presentation.dto.req;

import java.util.UUID;

import com.zgzg.delivery.domain.entity.DeliveryStatus;

public class CreateDeliveryRequestDTO {

	private UUID orderId;
	private DeliveryStatus orderStatus;
	private UUID originHubId; // 출발 허브
	private UUID destinationHubId; // 목적지 허브
	private String receiverAddress;
	private String receiverName;
	private String receiverSlackId;
	// private UUID deliveryPersonId;
}
