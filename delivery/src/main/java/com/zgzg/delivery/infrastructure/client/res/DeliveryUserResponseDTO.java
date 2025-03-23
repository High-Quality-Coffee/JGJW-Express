package com.zgzg.delivery.infrastructure.client.res;

import java.util.UUID;

import lombok.Getter;

@Getter
public class DeliveryUserResponseDTO {

	private Long deliveryUserId;
	private UUID hubId;
	private String deliverySlackUsername;
	private DeliveryType deliveryType;
	private Long deliveryOrder;
	private DeliveryStatus deliveryStatus;
}
