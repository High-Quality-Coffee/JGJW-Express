package com.zgzg.delivery.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.application.service.DeliveryService;
import com.zgzg.delivery.presentation.dto.req.CreateDeliveryRequestDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

	private final DeliveryService deliveryService;

	@PostMapping()
	public ResponseEntity<ApiResponseData<String>> createDelivery(
		@RequestBody @Validated CreateDeliveryRequestDTO requestDTO) {
		// deliveryService.createDelivery(requestDTO);
		return null;
	}

	@GetMapping("/{deliveryId}")
	public ResponseEntity<ApiResponseData<String>> getDelivery() {
		return null;
	}

	@PatchMapping("/{deliveryId}/cancel")
	public ResponseEntity<ApiResponseData<String>> cancelDelivery(
		@RequestBody @Validated CreateDeliveryRequestDTO requestDTO) {
		return null;
	}

	@PatchMapping("/{deliveryId}/delete")
	public ResponseEntity<ApiResponseData<String>> deleteDelivery() {
		return null;
	}

	@GetMapping()
	public ResponseEntity<ApiResponseData<String>> searchDelivery() {
		return null;
	}
}
