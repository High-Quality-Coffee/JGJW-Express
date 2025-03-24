package com.zgzg.delivery.presentation.dto.req;

import java.util.UUID;

import com.zgzg.delivery.domain.entity.DeliveryStatus;

public class CreateDeliveryRoute {

	private UUID deliveryId;
	private Integer sequence;
	private UUID startHubId;
	private UUID endHubId;
	private Integer estimatedDistance;
	private Integer estimatedTime;
	private DeliveryStatus deliveryStatus;
	private Long deliveryPersonId;
}
