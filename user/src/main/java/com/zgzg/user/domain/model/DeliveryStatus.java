package com.zgzg.user.domain.model;

public enum DeliveryStatus {
    OFFLINE("오프라인"),       // 근무 중이 아닌 상태
    DELIVERED("배송완료"),     // 배송이 완료된 상태, WAITING(대기중)으로 변경해야 배송담당자 할당이 가능, 그 전 까지는 휴식 가능
    WAITING("대기중"),        // 배송 업무를 기다리는 상태
    ASSIGNED("배정됨"),       // 배송이 할당된 상태
    PREPARING("출발준비중"),   // 출발 준비 중인 상태
    VACATION("휴가중"),       // 휴가 중인 상태
    DELIVERING("배송중");     // 배송 중인 상태

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
