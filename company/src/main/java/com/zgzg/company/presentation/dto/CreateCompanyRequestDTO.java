package com.zgzg.company.presentation.dto;

import java.util.UUID;

import com.zgzg.company.domain.Company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CreateCompanyRequestDTO {

	@Schema(description = "업체명", example = "test2025")
	private String name;

	private String type;

	private String address;

	@Schema(description = "허브 아이디", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
	private UUID hub_id;

	@Schema(description = "업체 관리자 아이디", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
	private Long companyAdminId;

	// public CreateCompanyRequestDTO(String name, String type, String address, String hubId, String admin) {
	// 	this.name = name;
	// 	this.type = type;
	// 	this.address = address;
	// 	this.hub_id = UUID.fromString(hubId);
	// 	this.companyAdminId = Long.parseLong(admin);
	// }

	public Company toEntity() {
		return Company.builder()
			.name(this.name)
			.type(this.type)
			.address(this.address)
			.hub_id(this.hub_id)
			.companyAdminId(this.companyAdminId)
			.build();
	}

}
