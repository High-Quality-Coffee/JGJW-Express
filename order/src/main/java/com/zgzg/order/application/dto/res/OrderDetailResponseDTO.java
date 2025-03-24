package com.zgzg.order.application.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.zgzg.order.domain.entity.OrderDetail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDetailResponseDTO { // 단건 상세 조회

	private UUID odId;
	private UUID productId;
	private String productName;
	private BigDecimal productPrice;
	private Integer productQuantity;
	private LocalDateTime createDateTime;

	public static OrderDetailResponseDTO from(OrderDetail orderDetail) {
		return OrderDetailResponseDTO.builder()
			.odId(orderDetail.getOdId())
			.productId(orderDetail.getProductId())
			.productName(orderDetail.getProductName())
			.productPrice(orderDetail.getProductPrice())
			.productQuantity(orderDetail.getQuantity())
			.createDateTime(orderDetail.getCreatedDateTime())
			.build();
	}
}