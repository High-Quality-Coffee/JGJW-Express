package com.zgzg.delivery.infrastructure.client.req;

import java.util.UUID;

import com.zgzg.delivery.infrastructure.client.res.DeliveryStatus;
import com.zgzg.delivery.infrastructure.client.res.DeliveryType;

import lombok.Getter;

@Getter
public class DeliveryUserRequestDTO {

	private UUID hubId;
	private DeliveryType deliveryType;
	private DeliveryStatus deliveryStatus;

	public DeliveryUserRequestDTO completeDelivery(UUID hubId) {
		this.hubId = hubId;
		this.deliveryStatus = DeliveryStatus.CAN_DELIVER;
		this.deliveryType = DeliveryType.STORE_DELIVERY;
		return this;
	}
}
