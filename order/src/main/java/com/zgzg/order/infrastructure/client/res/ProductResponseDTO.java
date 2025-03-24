package com.zgzg.order.infrastructure.client.res;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseDTO {
	private UUID id;
	private String productName;
	private Integer productStock;
	private UUID storeId;
	private UUID hubId;

}
