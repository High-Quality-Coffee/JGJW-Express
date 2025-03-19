package com.zgzg.delivery.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID deliveryId;
	@Column(nullable = false)
	private DeliveryStatus deliveryStatus;
	@Column(nullable = false)
	private UUID originHubId; // 출발 허브
	@Column(nullable = false)
	private UUID destinationHubId; // 목적지 허브
	@Column(nullable = false)
	private String receiverAddress;
	@Column(nullable = false)
	private String receiverName;
	@Column(nullable = false)
	private String receiverSlackId;
	private UUID deliveryPersonId;
	@Column(nullable = false)
	private UUID orderId;
	@OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
	@Builder.Default
	private List<DeliveryRouteLog> deliveryRouteLogs = new ArrayList<>();

}
