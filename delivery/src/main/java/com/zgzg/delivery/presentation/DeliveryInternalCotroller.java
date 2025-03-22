package com.zgzg.delivery.presentation;

import static com.zgzg.common.response.Code.*;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.application.service.DeliveryService;
import com.zgzg.delivery.presentation.dto.req.CreateDeliveryRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries/internal")
@Slf4j
public class DeliveryInternalCotroller {

	private final DeliveryService deliveryService;

	@PostMapping()
	public ResponseEntity<ApiResponseData<UUID>> createDelivery(
		@RequestBody @Validated CreateDeliveryRequestDTO requestDTO) {

		UUID deliveryID = deliveryService.createDelivery(requestDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{deliveryId}")
			.buildAndExpand(deliveryID)
			.toUri();
		return ResponseEntity.created(uri)
			.body(ApiResponseData.success(deliveryID, DELIVERY_CREATE_SUCCESS.getMessage()));
	}
}
