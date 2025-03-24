package com.zgzg.company.infrastructure.client.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class HubDTO {
	private UUID hubId;
	private String HubName;
	private String hubAddress;
	private String hubLatitude;
	private String hubLongitude;
	private Long hubAdminId;
	private boolean isMegaHub;
	private UUID parentHubId;
}
