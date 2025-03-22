package com.zgzg.order.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.order.application.dto.req.CreateDeliveryRequestDTO;
import com.zgzg.order.infrastructure.dto.DeliveryResponseDTO;

@FeignClient(name = "delivery")
public interface FeignDeliveryClient {

	@PostMapping("/api/v1/deliveries")
	ApiResponseData<UUID> createDelivery(@RequestBody CreateDeliveryRequestDTO requestDTO);

	@GetMapping("/api/v1/deliveries/{deliveryId}")
	DeliveryResponseDTO getDelivery(@PathVariable(name = "deliveryId") UUID deliveryId);
}
