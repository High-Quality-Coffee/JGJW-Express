package com.zgzg.order.application.dto.res;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetaiListDTO {
	private UUID orderId;
	private List<OrderDetailResponseDTO> orderDetails;

}
