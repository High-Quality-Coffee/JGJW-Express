package com.zgzg.order.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.order.infrastructure.dto.HubResponseDTO;

@FeignClient(name = "hub")
public interface FeignHubClient {

	@GetMapping("/api/v1/hubs/{hubId}")
	ApiResponseData<HubResponseDTO> getHub(@PathVariable(name = "hubId") UUID hubId);
}
