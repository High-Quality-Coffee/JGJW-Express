package com.zgzg.delivery.infrastructure.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.zgzg.delivery.domain.entity.DeliveryRouteLog;
import com.zgzg.delivery.domain.repo.DeliveryRouteLogRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryRouteLogRepositoryImpl implements DeliveryRouteLogRepository {

	private final DeliveryRouteLogJpaRepository deliveryRouteLogJpaRepository;

	@Override
	public List<DeliveryRouteLog> findByIdAndNotDeleted(UUID deliveryId) {
		return deliveryRouteLogJpaRepository.findByDelivery_DeliveryIdAndDeletedAtIsNull(deliveryId);
	}

	@Override
	public DeliveryRouteLog findByIdAndSequence(UUID deliveryId, Integer sequence) {
		return deliveryRouteLogJpaRepository.findByDelivery_DeliveryIdAndSequenceAndDeletedAtIsNull(deliveryId,
			sequence);
	}

	@Override
	public void save(DeliveryRouteLog entity) {
		deliveryRouteLogJpaRepository.save(entity);
	}

	@Override
	public void softDeleteRoutes(UUID deliveryId) {
		deliveryRouteLogJpaRepository.softDeleteRoutes(deliveryId);
	}
}
