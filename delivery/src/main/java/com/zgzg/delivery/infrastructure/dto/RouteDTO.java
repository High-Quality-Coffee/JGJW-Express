package com.zgzg.delivery.infrastructure.dto;

import java.util.UUID;

import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.entity.DeliveryRouteLog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

	private UUID startHubId;
	private UUID endHubId;
	private Integer duration;
	private Integer distance;
	private int sequence;

	public DeliveryRouteLog toEntity(Delivery delivery) {
		return DeliveryRouteLog.builder()
			.delivery(delivery)
			.startHubId(startHubId)
			.endHubId(endHubId)
			.estimatedTime(duration)
			.estimatedDistance(distance)
			.sequence(sequence)
			.build();
	}
}
