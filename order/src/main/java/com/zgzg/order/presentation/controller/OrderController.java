package com.zgzg.order.presentation.controller;

import static com.zgzg.common.response.Code.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
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
import com.zgzg.order.application.dto.global.PageableResponse;
import com.zgzg.order.application.dto.res.OrderDetaiListDTO;
import com.zgzg.order.application.dto.res.OrderDetailResponseDTO;
import com.zgzg.order.application.dto.res.OrderResponseDTO;
import com.zgzg.order.application.service.OrderService;
import com.zgzg.order.presentation.dto.req.CreateOrderRequestDto;
import com.zgzg.order.presentation.dto.req.SearchCriteria;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping()
	public ResponseEntity<ApiResponseData<CreateOrderRequestDto>> createOrder(
		@RequestBody @Validated CreateOrderRequestDto requestDto) {

		UUID orderId = orderService.createOrder(requestDto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{orderId}")
			.buildAndExpand(orderId)
			.toUri();
		return ResponseEntity.created(uri).build();
	}

	// todo. 권한 확인 - MASTER, HUB, STORE
	@PatchMapping("/{orderId}")
	public ResponseEntity<ApiResponseData<OrderDetailResponseDTO>> deleteOrder(@PathVariable UUID orderId) {
		orderService.deleteOrder(orderId);
		return ResponseEntity.noContent().build();
	}

	// todo. 권한 확인 - MASTER, HUB, DELIVERY, STORE
	@GetMapping("/{orderId}")
	public ResponseEntity<ApiResponseData<OrderDetaiListDTO>> getOrder(@PathVariable UUID orderId) {
		OrderDetaiListDTO orderDetails = orderService.getOrder(orderId);
		return ResponseEntity.ok()
			.body(
				ApiResponseData.of(ORDER_GET_SUCCESS.getCode(), ORDER_GET_SUCCESS.getMessage(), orderDetails));
	}

	// 주문 검색
	@GetMapping()
	public ResponseEntity<ApiResponseData<PageableResponse<OrderResponseDTO>>> searchOrder(
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

		PageableResponse<OrderResponseDTO> orderList = orderService.searchOrder(criteria, pageable);
		return ResponseEntity.ok()
			.body(ApiResponseData.of(ORDER_GET_SUCCESS.getCode(), ORDER_GET_SUCCESS.getMessage(), orderList));
	}

	@PatchMapping("/{orderId}/cancel")
	public ResponseEntity<ApiResponseData<OrderResponseDTO>> cancelOrder(@PathVariable UUID orderId) {
		OrderResponseDTO responseDTO = orderService.cancelOrder(orderId);

		return ResponseEntity.ok().body(ApiResponseData.of(ORDER_CANCEL_SUCCESS.getCode(),
			ORDER_CANCEL_SUCCESS.getMessage(), responseDTO));
	}

}