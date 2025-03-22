package com.zgzg.order.infrastructure.dto;

public enum DeliveryStatus {

	PREPARING,          // 배송 준비  (배송 생성 초기값)
	HUB_PENDING,        // 허브 대기 중  (각 허브 도착)
	HUB_IN_TRANSIT,     // 허브 이동 중  (허브 간 이동)
	HUB_ARRIVED,        // 허브 도착 (허브 간 배송 완료)
	IN_DELIVERY,        // 배송 중  (업체 배송중)
	DELIVERED,           // 배송 완료
	CANCELED            // 배송 취소
}
