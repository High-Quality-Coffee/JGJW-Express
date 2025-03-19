package com.zgzg.delivery.presentation.dto.req;

import java.math.BigDecimal;
import java.util.UUID;

import com.zgzg.delivery.domain.entity.DeliveryStatus;

public class CreateDeliveryRoute {

	private UUID deliveryId;
	private Integer sequence;
	private UUID startHubId;
	private UUID endHubId;
	private BigDecimal estimatedDistance;
	private BigDecimal estimatedTime;
	private DeliveryStatus deliveryStatus;
	private UUID deliveryPersonId;
}
