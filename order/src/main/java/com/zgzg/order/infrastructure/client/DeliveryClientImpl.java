package com.zgzg.order.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.order.application.client.DeliveryClient;
import com.zgzg.order.application.dto.req.CreateDeliveryRequestDTO;
import com.zgzg.order.infrastructure.dto.DeliveryResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryClientImpl implements DeliveryClient {

	private final FeignDeliveryClient feignDeliveryClient;

	@Override
	public DeliveryResponseDTO getDelivery(UUID deliveryId) {
		return feignDeliveryClient.getDelivery(deliveryId);
	}

	@Override
	public UUID createDelivery(CreateDeliveryRequestDTO requestDTO) {
		log.info("Feign client : createDelivery");
		// todo. 예외 처리
		ApiResponseData<UUID> response = feignDeliveryClient.createDelivery(requestDTO);
		return response.getData();
	}
}
