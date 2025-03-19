package com.zgzg.delivery.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.zgzg.common.utils.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery_route_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRouteLog extends BaseEntity { //배송 경로 기록

	@Id
	private UUID deliveryRouteLogId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Delivery delivery;

	@Column(nullable = false)
	private Integer sequence;
	@Column(nullable = false)
	private UUID startHubId;
	@Column(nullable = false)
	private UUID endHubId;
	@Column(nullable = false)
	private BigDecimal estimatedDistance;
	@Column(nullable = false)
	private BigDecimal estimatedTime;

	private BigDecimal actualDistance;
	private BigDecimal actualTimeSpent;
	@Column(nullable = false)
	private DeliveryStatus deliveryStatus;
	private UUID deliveryPersonId;
}
