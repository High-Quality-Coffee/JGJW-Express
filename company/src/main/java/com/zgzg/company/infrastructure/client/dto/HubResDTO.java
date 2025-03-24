package com.zgzg.company.infrastructure.client.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HubResDTO {
	private UUID hubId;
	private String hubName;
	private String description;
	private String createdAt;
	private String updatedAt;
	private Long hubAdminId;

	public HubResDTO(HubDTO mockHubDTO) {
	}
}