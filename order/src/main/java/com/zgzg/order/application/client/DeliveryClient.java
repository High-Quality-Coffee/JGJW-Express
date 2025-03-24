package com.zgzg.order.application.client;

import java.util.UUID;

import com.zgzg.order.application.dto.req.CreateDeliveryRequestDTO;
import com.zgzg.order.infrastructure.dto.DeliveryResponseDTO;

public interface DeliveryClient {

	DeliveryResponseDTO getDelivery(UUID orderId);

	UUID createDelivery(CreateDeliveryRequestDTO requestDTO);

	boolean cancelDelivery(UUID deliveryId);
}
