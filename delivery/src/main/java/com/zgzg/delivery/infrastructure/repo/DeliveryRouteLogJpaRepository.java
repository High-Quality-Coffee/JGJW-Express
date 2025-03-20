package com.zgzg.delivery.infrastructure.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zgzg.delivery.domain.entity.DeliveryRouteLog;

public interface DeliveryRouteLogJpaRepository extends JpaRepository<DeliveryRouteLog, UUID> {
	List<DeliveryRouteLog> findByDrlIdAndDeletedAtIsNull(UUID deliveryId);

	DeliveryRouteLog findByDelivery_IdAndSequenceAndDeletedAtIsNull(UUID deliveryId, Integer sequence);
}
