package com.zgzg.delivery.application.dto.res;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class DeliveryRouteLogsResponseDTO {

	private UUID deliveryId;
	private List<DeliveryRouteResponseDTO> routList;
}
