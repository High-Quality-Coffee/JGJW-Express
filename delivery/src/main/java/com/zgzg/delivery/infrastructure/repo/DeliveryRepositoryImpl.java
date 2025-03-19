package com.zgzg.delivery.infrastructure.repo;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.repo.DeliveryRepository;

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
		return jpaRepository.findByIdAndDeletedAtIsNull(deliveryId);
	}
}
