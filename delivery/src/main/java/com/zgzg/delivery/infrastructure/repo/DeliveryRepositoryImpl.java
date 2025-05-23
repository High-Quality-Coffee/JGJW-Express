package com.zgzg.delivery.infrastructure.repo;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zgzg.delivery.application.dto.res.DeliveryResponseDTO;
import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.repo.DeliveryRepository;
import com.zgzg.delivery.presentation.dto.global.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {
	private final DeliveryJpaRepository jpaRepository;

	@Override
	public Delivery save(Delivery delivery) {
		return jpaRepository.save(delivery);
	}

	@Override
	public Delivery findByIdAndNotDeleted(UUID deliveryId) {
		return jpaRepository.findByDeliveryIdAndDeletedAtIsNull(deliveryId);
	}

	@Override
	public Page<DeliveryResponseDTO> searchDeliveryByCriteria(SearchCriteria criteria, Pageable pageable) {
		return jpaRepository.searchDeliveryByCriteria(criteria, pageable);
	}
}
