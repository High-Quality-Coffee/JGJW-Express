package com.zgzg.ai.presentation.DTO;

import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageableRequestDTO {

	@Min(0)
	@Schema(name = "페이지 번호", example = "0")
	private int page = 0;

	@Min(1)
	@Schema(name = "페이지 사이즈", example = "10")
	private int size = 10;

	@Schema(name = "정렬 조건", example = "id")
	private String sortBy;

	@Schema(name = "정렬 방향", example = "ASC")
	private Sort.Direction direction = Sort.Direction.ASC;



}
