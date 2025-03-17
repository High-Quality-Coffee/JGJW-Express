package com.zgzg.company.presentation.dto;

import com.zgzg.company.domain.Company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateCompanyRequestDTO {

	@Schema(description = "업체명", example = "test2025")
	private String name;

	private String type;

	private String address;

	public Company toEntity() {
		return Company.builder()
			.name(this.name)
			.type(this.type)
			.address(this.address)
			.build();
	}

}
