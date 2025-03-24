package com.zgzg.order.infrastructure.client.req;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ProductStockRequestDTO {
	private UUID id;
	private Integer productStock;

	public ProductStockRequestDTO(UUID id, Integer productStock) {
		this.id = id;
		this.productStock = productStock;
	}
}
