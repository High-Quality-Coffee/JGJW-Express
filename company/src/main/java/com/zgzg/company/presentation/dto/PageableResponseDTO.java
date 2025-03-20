package com.zgzg.company.presentation.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageableResponseDTO<T> {

    private List<T> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;

	public static <S, T> PageableResponseDTO<T> from(Page<S> page, Function<S, T> converter) {
		List<T> content = page.getContent().stream()
			.map(converter)
			.collect(Collectors.toList());
		return PageableResponseDTO.<T>builder()
			.content(content)
			.pageNumber(page.getNumber())
			.pageSize(page.getSize())
			.totalElements(page.getTotalElements())
			.totalPages(page.getTotalPages())
			.last(page.isLast())
			.build();
	}

}
