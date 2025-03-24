package com.zgzg.order.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.order.application.client.HubClient;
import com.zgzg.order.infrastructure.dto.HubResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HubClientImpl implements HubClient {

	private final FeignHubClient feignHubClient;

	@Override
	public HubResponseDTO getHub(UUID receiverHubId) {
		log.info("Feign client : getHub");
		// todo. 예외처리
		ApiResponseData<HubResponseDTO> response = feignHubClient.getHub(receiverHubId);
		return response.getData();
	}
}
