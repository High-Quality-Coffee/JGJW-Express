package com.zgzg.order.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.order.infrastructure.client.req.ProductStockRequestDTO;
import com.zgzg.order.infrastructure.client.res.ProductResponseDTO;

@FeignClient(name = "product")
public interface FeignProductClient {

	// todo. 특정 상품 조회
	@GetMapping("/api/v1/products/{productId}")
	ApiResponseData<ProductResponseDTO> getProduct(@PathVariable UUID productId);

	// todo. 상품 재고 차감 (/api/v1/products/reduce/{id}
	@PutMapping("/api/v1/products/reduce")
	ApiResponseData<String> reduceProduct(@RequestBody ProductStockRequestDTO requestDTO);

	// todo. 상품 재고 추가 (/api/v1/products/reduce/{id}
	@PutMapping("/api/v1/products/add")
	ApiResponseData<String> increaseProduct(@RequestBody ProductStockRequestDTO requestDTO);

}
