package com.zgzg.delivery.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.application.client.DeliveryPersonClient;
import com.zgzg.delivery.infrastructure.client.req.DeliveryUserRequestDTO;
import com.zgzg.delivery.infrastructure.client.res.DeliveryUserResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryPersonClientImpl implements DeliveryPersonClient {

	private final FeignDeliveryPersonClient feignDeliveryPersonClient;

	@Override
	public DeliveryUserResponseDTO getHubDeiveryPerson() {
		log.info("Feign : getDeliveryPerson ");
		ApiResponseData<DeliveryUserResponseDTO> response = feignDeliveryPersonClient.getHubDeliveryPerson();
		log.info("response - code : {}, message : {} ", response.getCode(), response.getMessage());
		return response.getData();
	}

	@Override
	public DeliveryUserResponseDTO getStoreDeiveryPerson(UUID endHubId) {
		ApiResponseData<DeliveryUserResponseDTO> response = feignDeliveryPersonClient.getStoreDeliveryPerson(endHubId);
		return response.getData();
	}

	@Override
	public void completeDeliveryPerson(long userId, DeliveryUserRequestDTO requestDTO) {
		ApiResponseData<DeliveryUserResponseDTO> response = feignDeliveryPersonClient.updateDeliveryPerson(userId,
			requestDTO);
		log.info("Feign deliveryPerson : {}", response.getMessage());
	}
}
