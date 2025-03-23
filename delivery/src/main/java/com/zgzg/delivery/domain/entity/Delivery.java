package com.zgzg.delivery.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import com.zgzg.common.utils.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Delivery extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID deliveryId;

	@ColumnDefault("'PREPARING'")
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private DeliveryStatus deliveryStatus = DeliveryStatus.PREPARING;

	@Column(nullable = false)
	private UUID originHubId; // 출발 허브

	private String originHubName; // 출발 허브

	@Column(nullable = false)
	private UUID destinationHubId; // 목적지 허브

	private String destinationHubName; // 목적지 허브

	@Column(nullable = false)
	private String receiverAddress;

	@Column(nullable = false)
	private String receiverName;

	@Column(nullable = false)
	private String receiverSlackId;

	private Long deliveryPersonId;

	private String deliveryPersonSlackId;

	@Column(nullable = false)
	private UUID orderId;

	@OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
	@Builder.Default
	private List<DeliveryRouteLog> deliveryRouteLogs = new ArrayList<>();

	public void cancelDelivery() {
		this.deliveryStatus = DeliveryStatus.CANCELED;
	}

	public void startDelivery() {
		this.deliveryStatus = DeliveryStatus.IN_DELIVERY;
	}

	public void completeDelivery() {
		this.deliveryStatus = DeliveryStatus.DELIVERED;
	}

	public void assignDeliveryPerson(Long personId, String slackId) {
		this.deliveryPersonId = personId;
		this.deliveryPersonSlackId = slackId;
	}

	public void addOriginHubName(String originHubName) {
		this.originHubName = originHubName;
	}

	public void addDestinationHubName(String destinationHubName) {
		this.destinationHubName = destinationHubName;
	}
}
