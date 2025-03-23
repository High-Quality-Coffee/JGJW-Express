package com.zgzg.delivery.infrastructure.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HubResDTO {

	private HubDTO hubDTO;

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class HubDTO {

		private UUID hubId;
		private String HubName;
		private String hubAddress;
		private String hubLatitude;
		private String hubLongitude;
		private Long hubAdminId;
		private boolean isMegaHub;
		private UUID parentHubId;
		
	}
}
