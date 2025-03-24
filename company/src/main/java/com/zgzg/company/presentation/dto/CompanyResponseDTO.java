package com.zgzg.company.presentation.dto;

import java.util.UUID;

import com.zgzg.company.domain.Company;

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

	private Long companyAdminId;

	public static CompanyResponseDTO toDto(Company company) {
		return CompanyResponseDTO.builder()
			.id(company.getId())
			.name(company.getName())
			.type(company.getType())
			.address(company.getAddress())
			.hub_id(company.getHub_id())
			.companyAdminId(company.getCompanyAdminId())
			.build();
	}
}
