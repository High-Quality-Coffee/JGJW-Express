package com.zgzg.user.presentation.request;

import com.zgzg.user.domain.model.DeliveryStatus;
import com.zgzg.user.domain.model.DeliveryType;

import java.util.UUID;

public class DeliveryUserRequestDTO {

    private UUID hubId;
    private DeliveryType deliveryType;
    private DeliveryStatus deliveryStatus;

}
