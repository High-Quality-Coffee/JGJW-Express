package com.zgzg.order.presentation.controller;

import java.net.URI;
import java.util.UUID;

import com.zgzg.common.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.order.application.service.OrderService;
import com.zgzg.order.presentation.dto.req.CreateOrderRequestDto;

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
}