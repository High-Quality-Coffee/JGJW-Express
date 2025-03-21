package com.zgzg.delivery.application.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.zgzg.delivery.domain.entity.DeliveryRouteLog;
import com.zgzg.delivery.domain.entity.DeliveryStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryRouteResponseDTO {

	private UUID deliveryRouteLogId;
	private Integer sequence;
	private UUID startHubId;
	private String startHubName;
	private UUID endHubId;
	private String endHubName;
	private BigDecimal estimatedDistance;
	private BigDecimal estimatedTime;
	private BigDecimal actualDistance;
	private BigDecimal actualTimeSpent;
	private DeliveryStatus deliveryStatus;
	private UUID deliveryPersonId;
	private String deliveryPersonName;
	private LocalDateTime createDateTime;

	public static DeliveryRouteResponseDTO from(DeliveryRouteLog log) {
		return DeliveryRouteResponseDTO.builder()
			.deliveryRouteLogId(log.getDrlId())
			.sequence(log.getSequence())
			.startHubId(log.getStartHubId())
			.startHubName(log.getStartHubName())
			.endHubId(log.getEndHubId())
			.endHubName(log.getEndHubName())
			.estimatedDistance(log.getEstimatedDistance())
			.estimatedTime(log.getEstimatedTime())
			.actualDistance(log.getActualDistance())
			.actualTimeSpent(log.getActualTimeSpent())
			.deliveryStatus(log.getDeliveryStatus())
			.deliveryPersonId(log.getDeliveryPersonId())
			.deliveryPersonName(log.getDeliveryPersonName())
			.createDateTime(log.getCreatedDateTime())
			.build();
	}
}
