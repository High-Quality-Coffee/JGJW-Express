package com.zgzg.order.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeliveryResponseDTO {

	private UUID deliveryId;
	private DeliveryStatus deliveryStatus;
	private UUID originHubId; // 출발 허브
	private String originHubName;
	private UUID destinationHubId; // 목적지 허브
	private String destinationHubName;
	private String receiverAddress;
	private String receiverName;
	private String receiverSlackId;
	private Long deliveryPersonId; // 업체 배송 담당자
	private String deliveryPersonSlackId;
	private UUID orderId;
	private LocalDateTime createDateTime;
}
