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
	private UUID deliveryPersonId; // 업체 배송 담당자
	private String deliveryPersonName; // todo. 업체 배송 전에는 담당자 이름 대신 "허브 이동중"으로 할까?
	private UUID orderId;
	private LocalDateTime createDateTime;
}
