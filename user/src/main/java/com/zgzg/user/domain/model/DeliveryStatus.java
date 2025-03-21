package com.zgzg.user.domain.model;

import java.io.Serializable;

public enum DeliveryStatus implements Serializable {
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
