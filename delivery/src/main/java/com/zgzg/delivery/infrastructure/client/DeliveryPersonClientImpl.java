package com.zgzg.delivery.infrastructure.client;

import org.springframework.stereotype.Component;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.application.client.DeliveryPersonClient;
import com.zgzg.delivery.infrastructure.client.res.DeliveryUserResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryPersonClientImpl implements DeliveryPersonClient {

	private final FeignDeliveryPersonClient feignDeliveryPersonClient;

	@Override
	public DeliveryUserResponseDTO getDeiveryPerson() {
		log.info("Feign : getDeliveryPerson ");
		ApiResponseData<DeliveryUserResponseDTO> response = feignDeliveryPersonClient.getDeliveryPerson();
		log.info("response - code : {}, message : {} ", response.getCode(), response.getMessage());
		return response.getData();
	}
}
