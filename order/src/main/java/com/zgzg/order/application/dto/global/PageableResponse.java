package com.zgzg.order.application.dto.global;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;

@Getter
public class PageableResponse<T> {
	private List<T> contents;

	private boolean first;
	private boolean last;
	private boolean empty;
	private int numberOfElements;
	private long totalElements;
	private int totalPages;

	private int pageNumber;
	private int pageSize;
	private long offset;

	public PageableResponse(Page<T> page) {
		Pageable pageable = page.getPageable();

		this.contents = page.getContent();
		this.last = page.isLast();
		this.first = page.isFirst();
		this.empty = page.isEmpty();
		this.numberOfElements = page.getNumberOfElements();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();

		this.pageNumber = pageable.getPageNumber();
		this.pageSize = pageable.getPageSize();
		this.offset = pageable.getOffset();
	}
}
