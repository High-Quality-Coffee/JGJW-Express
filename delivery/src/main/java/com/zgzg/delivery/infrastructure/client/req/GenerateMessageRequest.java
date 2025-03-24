package com.zgzg.delivery.infrastructure.client.req;

import java.util.List;

import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.presentation.dto.req.CreateDeliveryRequestDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenerateMessageRequest {

	private String orderId;
	private String product;
	private Integer quantity;
	private String request;
	private String originHub;
	private String destinationHub;
	private List<String> intermediateHubs;
	private String workHours;

	public static GenerateMessageRequest generate(CreateDeliveryRequestDTO requestDTO, Delivery delivery,
		List<String> hubs) {
		return GenerateMessageRequest.builder()
			.orderId(delivery.getOrderId().toString())
			.product(requestDTO.getProductName())
			.quantity(requestDTO.getQuantity())
			.request(requestDTO.getRequest())
			.originHub(delivery.getOriginHubName())
			.destinationHub(delivery.getDestinationHubName())
			.intermediateHubs(hubs)
			.workHours("09:00-18:00")
			.build();
	}

}
