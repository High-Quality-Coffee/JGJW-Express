package com.zgzg.delivery.infrastructure.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zgzg.delivery.domain.entity.DeliveryRouteLog;

public interface DeliveryRouteLogJpaRepository extends JpaRepository<DeliveryRouteLog, UUID> {
	List<DeliveryRouteLog> findByDelivery_DeliveryIdAndDeletedAtIsNull(UUID deliveryId);

	DeliveryRouteLog findByDelivery_DeliveryIdAndSequenceAndDeletedAtIsNull(UUID deliveryId, Integer sequence);
}
