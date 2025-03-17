package com.zgzg.order.domain.entity;

public enum OrderStatus {
	FAILED,  // 주문 실패
	CANCELED, // 주문 취소
	WAITING,  // 주문 대기(입금 대기 등)
	COMPLETED; // 주문 완료
}
