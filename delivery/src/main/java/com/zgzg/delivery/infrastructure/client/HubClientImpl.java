package com.zgzg.delivery.infrastructure.client;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.application.client.HubClient;
import com.zgzg.delivery.infrastructure.dto.HubResponseDTO;
import com.zgzg.delivery.infrastructure.dto.RouteDTO;
import com.zgzg.delivery.infrastructure.dto.RoutesDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HubClientImpl implements HubClient {

	private final FeignHubClient feignHubClient;

	@Override
	public List<RouteDTO> getHubRoutes(UUID startHubId, UUID endHubId) {
		ApiResponseData<RoutesDTO> response = feignHubClient.getHubRoutes(startHubId, endHubId);
		log.info("message : {} , data : {}", response.getMessage(), response.getData().getRoutes().get(0));
		return response.getData().getRoutes();
	}

	@Override
	public HubResponseDTO getHub(UUID originHubId) {
		ApiResponseData<HubResponseDTO> response = feignHubClient.getHub(originHubId);
		return response.getData();
	}
}
