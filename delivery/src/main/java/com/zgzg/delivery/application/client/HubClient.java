package com.zgzg.delivery.application.client;

import java.util.List;
import java.util.UUID;

import com.zgzg.delivery.infrastructure.dto.HubResponseDTO;
import com.zgzg.delivery.infrastructure.dto.RouteDTO;

public interface HubClient {

	List<RouteDTO> getHubRoutes(UUID startHubId, UUID endHubId);

	HubResponseDTO getHub(UUID originHubId);
}
