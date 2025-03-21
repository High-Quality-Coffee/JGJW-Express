package com.zgzg.delivery.domain.repo;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zgzg.delivery.application.dto.res.DeliveryResponseDTO;
import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.presentation.dto.global.SearchCriteria;

public interface DeliveryRepository {
	Delivery save(Delivery delivery);

	Delivery findByIdAndNotDeleted(UUID deliveryId);

	Page<DeliveryResponseDTO> searchDeliveryByCriteria(SearchCriteria criteria, Pageable pageable);
}
