package com.zgzg.user.presentation.request;

import com.zgzg.user.domain.model.DeliveryStatus;
import com.zgzg.user.domain.model.DeliveryType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeliveryUserRequestDTO {

    private UUID hubId;
    private DeliveryType deliveryType;
    private DeliveryStatus deliveryStatus;

}
