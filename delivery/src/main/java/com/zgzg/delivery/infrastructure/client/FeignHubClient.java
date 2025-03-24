package com.zgzg.delivery.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.infrastructure.dto.HubResponseDTO;
import com.zgzg.delivery.infrastructure.dto.RoutesDTO;

@FeignClient(name = "hub")
public interface FeignHubClient {

	@GetMapping("/api/v1/hubs/route")
	ApiResponseData<RoutesDTO> getHubRoutes(@RequestParam UUID startHubId, @RequestParam UUID endHubId);

	@GetMapping("/api/v1/hubs/{hubId}")
	ApiResponseData<HubResponseDTO> getHub(@PathVariable(name = "hubId") UUID hubId);

}