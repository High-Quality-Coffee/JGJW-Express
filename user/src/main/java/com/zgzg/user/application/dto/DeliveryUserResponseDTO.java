package com.zgzg.user.application.dto;

import com.zgzg.user.domain.model.DeliveryStatus;
import com.zgzg.user.domain.model.DeliveryType;
import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
public class DeliveryUserResponseDTO implements Serializable {

    private Long deliveryUserId;
    private UUID hubId;
    private String deliverySlackUsername;
    private DeliveryType deliveryType;
    private Long deliveryOrder;
    private DeliveryStatus deliveryStatus;

}
