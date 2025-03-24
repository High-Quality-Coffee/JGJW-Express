package com.zgzg.product.presentation.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.product.application.dto.ProductResponseDTO;
import com.zgzg.product.application.service.ProductService;
import com.zgzg.product.presentation.request.ProductStockRequestDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products/internal")
@RequiredArgsConstructor
public class ProductInternalController {

	private final ProductService productService;

	//특정 상품 조회
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseData<ProductResponseDTO>> read_one(@PathVariable("id") UUID id) {
		return ResponseEntity.ok()
			.body(ApiResponseData.of(Code.PRODUCT_EXISTS.getCode(), Code.PRODUCT_EXISTS.getMessage(),
				productService.readOne(id)));

	}

	//재고 추가
	@PutMapping("/add")
	public ResponseEntity<ApiResponseData<String>> addProduct(
		@RequestBody ProductStockRequestDTO productStockRequestDTO) {
		return ResponseEntity.ok().body(productService.addProduct(productStockRequestDTO));
	}

	//재고 감소
	@PutMapping("/reduce")
	public ResponseEntity<ApiResponseData<String>> reduceProduct(
		@RequestBody ProductStockRequestDTO productStockRequestDTO) {
		return ResponseEntity.ok().body(productService.reduceProduct(productStockRequestDTO));
	}

}
