package com.zgzg.delivery.infrastructure.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zgzg.delivery.application.dto.res.DeliveryResponseDTO;
import com.zgzg.delivery.presentation.dto.global.SearchCriteria;

public interface DeliveryJpaRepositoryCustom {

	Page<DeliveryResponseDTO> searchDeliveryByCriteria(SearchCriteria criteria, Pageable pageable);
}
