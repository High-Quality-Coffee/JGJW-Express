package com.zgzg.order.application.client;

import com.zgzg.order.application.dto.req.CreateDeliveryRequestDTO;

public interface DeliveryClient {

	void createDelivery(CreateDeliveryRequestDTO requestDTO);
}
