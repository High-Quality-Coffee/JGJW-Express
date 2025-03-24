package com.zgzg.delivery.application.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.entity.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
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

	public static DeliveryResponseDTO from(Delivery delivery) {
		return DeliveryResponseDTO.builder()
			.deliveryId(delivery.getDeliveryId())
			.deliveryStatus(delivery.getDeliveryStatus())
			.originHubId(delivery.getOriginHubId())
			.originHubName(delivery.getOriginHubName())
			.destinationHubId(delivery.getDestinationHubId())
			.destinationHubName(delivery.getDestinationHubName())
			.receiverAddress(delivery.getReceiverAddress())
			.receiverName(delivery.getReceiverName())
			.receiverSlackId(delivery.getReceiverSlackId())
			.deliveryPersonId(delivery.getDeliveryPersonId())
			.deliveryPersonSlackId(delivery.getDeliveryPersonSlackId())
			.orderId(delivery.getOrderId())
			.createDateTime(delivery.getCreatedDateTime())
			.build();
	}
}
