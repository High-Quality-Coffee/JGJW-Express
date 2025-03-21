package com.zgzg.delivery.domain.repo;

import java.util.List;
import java.util.UUID;

import com.zgzg.delivery.domain.entity.DeliveryRouteLog;

public interface DeliveryRouteLogRepository {
	List<DeliveryRouteLog> findByIdAndNotDeleted(UUID deliveryId);

	DeliveryRouteLog findByIdAndSequence(UUID deliveryId, Integer sequence);
}
