package com.zgzg.delivery.infrastructure.repo;

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
}
