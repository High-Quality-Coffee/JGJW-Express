package com.zgzg.company.presentation.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyResponseDTO {

	private UUID id;

	private UUID hub_id;

	@Schema(description = "업체명", example = "test2025")
	private String name;

	private String type;

	private String address;
}
