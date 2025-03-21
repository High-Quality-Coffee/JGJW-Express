package com.zgzg.order.infrastructure.client;

import org.springframework.stereotype.Component;

import com.zgzg.order.application.client.DeliveryClient;
import com.zgzg.order.application.dto.req.CreateDeliveryRequestDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryClientImpl implements DeliveryClient {

	private final FeignDeliveryClient feignDeliveryClient;

	@Override
	public void createDelivery(CreateDeliveryRequestDTO requestDTO) {
		System.out.println("order -> delivery");
		feignDeliveryClient.createDelivery(requestDTO);
	}
}
