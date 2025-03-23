package com.zgzg.company.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.company.infrastructure.client.dto.HubResDTO;

@FeignClient(name = "user", url = "http://localhost:19091")
public interface FeginServiceClient {

	@GetMapping("/api/v1/hubs/{hubId}")
	HubResDTO getHub(@PathVariable("hubId") UUID hubId, CustomUserDetails userDetails);


}
