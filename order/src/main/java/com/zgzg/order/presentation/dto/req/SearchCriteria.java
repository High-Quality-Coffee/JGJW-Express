package com.zgzg.order.presentation.dto.req;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchCriteria {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
