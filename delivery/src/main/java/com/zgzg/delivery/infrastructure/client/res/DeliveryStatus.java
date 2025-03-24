package com.zgzg.delivery.infrastructure.client.res;

public enum DeliveryStatus {
	CAN_DELIVER("배송가능"),       // 배송 가능 상태
	ASSIGNED("할당됨");     // 배송 불가 상태

	private final String description;

	DeliveryStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
