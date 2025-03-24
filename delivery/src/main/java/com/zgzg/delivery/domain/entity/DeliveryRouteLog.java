package com.zgzg.delivery.domain.entity;

import java.util.UUID;

import com.zgzg.common.utils.BaseEntity;
import com.zgzg.delivery.infrastructure.client.res.DeliveryUserResponseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery_route_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class DeliveryRouteLog extends BaseEntity { //배송 경로 기록

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID drlId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	@Column(nullable = false)
	private Integer sequence;
	@Column(nullable = false)
	private UUID startHubId;
	@Column(nullable = false)
	private String startHubName;
	@Column(nullable = false)
	private UUID endHubId;
	@Column(nullable = false)
	private String endHubName;
	@Column(nullable = false)
	private Integer estimatedDistance;
	@Column(nullable = false)
	private Integer estimatedTime;
	private Integer actualDistance;
	private Integer actualTimeSpent;
	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;
	private Long deliveryPersonId;
	private String deliveryPersonSlackId;

	public void startDelivery() {
		this.deliveryStatus = DeliveryStatus.HUB_IN_TRANSIT;
	}

	public void completeDelivery(long actualDuration) {
		this.deliveryStatus = DeliveryStatus.DELIVERED;
		this.actualTimeSpent = Math.toIntExact(actualDuration); // todo. 시간 관련 타입 일치
	}

	public void arrivedHub(long actualDuration) {
		this.deliveryStatus = DeliveryStatus.HUB_ARRIVED;
		this.actualTimeSpent = Math.toIntExact(actualDuration); // todo. 시간 관련 타입 일치
	}

	public void startStoreDelivery(long actualDuration) {
		this.deliveryStatus = DeliveryStatus.IN_DELIVERY;
		this.actualTimeSpent = Math.toIntExact(actualDuration); // todo. 시간 관련 타입 일치
	}

	public void assignDeliveryPerson(Long personId, String slackId) {
		this.deliveryPersonId = personId;
		this.deliveryPersonSlackId = slackId;
	}

	public static DeliveryRouteLog addLastRoute(DeliveryRouteLog route, DeliveryUserResponseDTO deliver) {
		return DeliveryRouteLog.builder()
			.delivery(route.getDelivery())
			.sequence(route.getSequence() + 1)
			.startHubId(route.getEndHubId())
			.startHubName(route.getEndHubName())
			.endHubId(route.getEndHubId()) // todo. 업체 id
			.endHubName(route.getEndHubName()) // todo. 업체명
			.estimatedDistance(0)
			.estimatedTime(0)
			.deliveryStatus(DeliveryStatus.IN_DELIVERY) // 업체 배송중
			.deliveryPersonId(deliver.getDeliveryUserId())
			.deliveryPersonSlackId(deliver.getDeliverySlackUsername())
			.build();
	}
}
