package com.zgzg.order.application.dto.req;

import java.util.UUID;

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

	public CreateDeliveryRequestDTO(UUID orderId) {

	}
}
