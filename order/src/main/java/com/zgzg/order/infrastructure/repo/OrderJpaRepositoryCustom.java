package com.zgzg.order.infrastructure.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zgzg.order.application.dto.res.OrderResponseDTO;
import com.zgzg.order.presentation.dto.req.SearchCriteria;

public interface OrderJpaRepositoryCustom {
	Page<OrderResponseDTO> searchOrderByCriteria(SearchCriteria criteria, Pageable pageable);
}
