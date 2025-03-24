package com.zgzg.delivery.presentation;

import static com.zgzg.common.response.Code.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.delivery.application.dto.res.DeliveryResponseDTO;
import com.zgzg.delivery.application.dto.res.DeliveryRouteLogsResponseDTO;
import com.zgzg.delivery.application.dto.res.PageableResponse;
import com.zgzg.delivery.application.service.DeliveryService;
import com.zgzg.delivery.presentation.dto.global.SearchCriteria;
import com.zgzg.delivery.presentation.dto.req.CreateDeliveryRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
@Slf4j
public class DeliveryController {

	private final DeliveryService deliveryService;

	@PostMapping()
	@Secured("ROLE_MASTER")
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

	@GetMapping("/{deliveryId}")
	public ResponseEntity<ApiResponseData<DeliveryResponseDTO>> getDelivery(@PathVariable UUID deliveryId) {
		DeliveryResponseDTO responseDTO = deliveryService.getDelivery(deliveryId);
		return ResponseEntity.ok()
			.body(ApiResponseData.of(DELIVERY_READ_SUCCESS.getCode(), DELIVERY_READ_SUCCESS.getMessage(), responseDTO));
	}

	@PatchMapping("/{deliveryId}/cancel")
	public ResponseEntity<ApiResponseData<DeliveryResponseDTO>> cancelDelivery(
		@PathVariable UUID deliveryId) {
		DeliveryResponseDTO responseDTO = deliveryService.cancelDelivery(deliveryId);
		return ResponseEntity.ok()
			.body(ApiResponseData.of(DELIVERY_CANCEL_SUCCESS.getCode(), DELIVERY_CANCEL_SUCCESS.getMessage(),
				responseDTO));
	}

	@PatchMapping("/{deliveryId}/delete")
	@Secured({"ROLE_MASTER", "ROLE_HUB"})
	public ResponseEntity<ApiResponseData<String>> deleteDelivery(@PathVariable UUID deliveryId,
		CustomUserDetails userDetails) {
		deliveryService.deleteDelivery(deliveryId, userDetails);
		return ResponseEntity.noContent().build();
	}

	@GetMapping()
	public ResponseEntity<ApiResponseData<PageableResponse<DeliveryResponseDTO>>> searchDelivery(
		@RequestParam(required = false) LocalDateTime startDate,
		@RequestParam(required = false) LocalDateTime endDate,
		@PageableDefault
		@SortDefault.SortDefaults({
			@SortDefault(sort = "createdDateTime", direction = Sort.Direction.DESC),
			@SortDefault(sort = "modifiedDateTime", direction = Sort.Direction.DESC)
		}) Pageable pageable) {

		SearchCriteria criteria = SearchCriteria.builder()
			.startDate(startDate)
			.endDate(endDate)
			.build();
		PageableResponse<DeliveryResponseDTO> deliveryList = deliveryService.searchOrder(criteria, pageable);
		return ResponseEntity.ok()
			.body(
				ApiResponseData.of(DELIVERY_READ_SUCCESS.getCode(), DELIVERY_READ_SUCCESS.getMessage(), deliveryList));
	}

	@GetMapping("/{deliveryId}/routes")
	public ResponseEntity<ApiResponseData<DeliveryRouteLogsResponseDTO>> getDeliveryRoutes(
		@PathVariable UUID deliveryId) {
		DeliveryRouteLogsResponseDTO responseDTO = deliveryService.getDeliveryRoutes(deliveryId);
		return ResponseEntity.ok()
			.body(ApiResponseData.of(DELIVERY_READ_SUCCESS.getCode(), DELIVERY_READ_SUCCESS.getMessage(), responseDTO));
	}

	@PatchMapping("/{deliveryId}/{sequence}/start")
	public ResponseEntity<ApiResponseData<DeliveryRouteLogsResponseDTO>> startDelivery(
		@PathVariable UUID deliveryId, @PathVariable int sequence) {

		deliveryService.startDelivery(deliveryId, sequence);

		return ResponseEntity.ok()
			.body(null);
	}

	@PatchMapping("/{deliveryId}/{sequence}/hub-arrival")
	public ResponseEntity<ApiResponseData<DeliveryRouteLogsResponseDTO>> arriveDelivery(
		@PathVariable UUID deliveryId, @PathVariable int sequence) {

		deliveryService.arriveDelivery(deliveryId, sequence);

		return ResponseEntity.ok()
			.body(null);
	}

	@PatchMapping("/{deliveryId}/{sequence}/complete")
	public ResponseEntity<ApiResponseData<DeliveryRouteLogsResponseDTO>> completeDelivery(
		@PathVariable UUID deliveryId, @PathVariable int sequence) {
		// 업체 배송 완료
		deliveryService.completeDelivery(deliveryId, sequence);

		return ResponseEntity.ok()
			.body(null);
	}
}
