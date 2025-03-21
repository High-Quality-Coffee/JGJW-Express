package com.zgzg.order.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zgzg.order.application.dto.req.CreateDeliveryRequestDTO;

@FeignClient(name = "delivery")
public interface FeignDeliveryClient {

	@PostMapping("/api/v1/deliveries")
	void createDelivery(@RequestBody CreateDeliveryRequestDTO requestDTO);
}
