package com.zgzg.delivery.domain.entity;

import java.util.UUID;

import com.zgzg.common.utils.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	// todo. @Column(nullable = false)
	private String startHubName;
	@Column(nullable = false)
	private UUID endHubId;
	// todo. @Column(nullable = false)
	private String endHubName;
	@Column(nullable = false)
	private Integer estimatedDistance;
	@Column(nullable = false)
	private Integer estimatedTime;
	private Integer actualDistance;
	private Integer actualTimeSpent;
	private DeliveryStatus deliveryStatus;
	private Long deliveryPersonId;
	private String deliveryPersonSlackId;

	public void startDelivery() {
		this.deliveryStatus = DeliveryStatus.HUB_IN_TRANSIT;
	}

	public void completeDelivery() {
		this.deliveryStatus = DeliveryStatus.DELIVERED;
	}

	public void assignDeliveryPerson(Long personId, String slackId) {
		this.deliveryPersonId = personId;
		this.deliveryPersonSlackId = slackId;
	}
}
