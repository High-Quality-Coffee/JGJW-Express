package com.zgzg.delivery.application.dto.res;

import java.util.UUID;

import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.entity.DeliveryStatus;

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
			.deliveryPersonName(delivery.getDeliveryPersonName())
			.orderId(delivery.getOrderId())
			.build();
	}
}
