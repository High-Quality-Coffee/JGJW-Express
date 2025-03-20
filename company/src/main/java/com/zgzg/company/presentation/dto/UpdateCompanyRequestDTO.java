package com.zgzg.company.presentation.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyRequestDTO {
	@Schema(description = "업체 ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
	private UUID id;

	@Schema(description = "업체명", example = "test2025")
	private String name;

	private String type;

	private String address;

}
