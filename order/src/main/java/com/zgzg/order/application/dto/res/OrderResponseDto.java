package com.zgzg.order.application.dto.res;

import java.math.BigDecimal;
import java.util.UUID;

import com.zgzg.order.domain.entity.OrderDetail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDto { // 단건 상세 조회

	private UUID odId;
	private UUID productId;
	private String productName;
	private BigDecimal productPrice;
	private Integer productQuantity;
	private UUID deliveryId;
	// todo. deliveryId 여기에서 가지고 있어야할까?

	public static OrderResponseDto from(OrderDetail orderDetail) {
		return OrderResponseDto.builder()
			.odId(orderDetail.getOdId())
			.productId(orderDetail.getProductId())
			.productName(orderDetail.getProductName())
			.productPrice(orderDetail.getProductPrice())
			.productQuantity(orderDetail.getQuantity())
			.build();
	}

}