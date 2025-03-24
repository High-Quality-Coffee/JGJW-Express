package com.zgzg.delivery.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.infrastructure.client.req.DeliveryUserRequestDTO;
import com.zgzg.delivery.infrastructure.client.res.DeliveryUserResponseDTO;

@FeignClient(name = "user")
public interface FeignDeliveryPersonClient {

	@GetMapping("/api/v1/delivery/users/internal/hub")
	ApiResponseData<DeliveryUserResponseDTO> getHubDeliveryPerson();

	@GetMapping("/api/v1/delivery/users/internal/store/{hubId}")
	ApiResponseData<DeliveryUserResponseDTO> getStoreDeliveryPerson(@PathVariable(name = "hubId") UUID hubId);

	@PutMapping("/api/v1/delivery/users/internal/{id}")
	ApiResponseData<DeliveryUserResponseDTO> updateDeliveryPerson(@PathVariable(name = "id") Long id, @RequestBody
	DeliveryUserRequestDTO requestDTO);

}
