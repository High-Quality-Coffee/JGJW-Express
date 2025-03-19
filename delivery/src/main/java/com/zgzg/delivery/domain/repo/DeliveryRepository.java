package com.zgzg.delivery.domain.repo;

import java.util.UUID;

import com.zgzg.delivery.domain.entity.Delivery;

public interface DeliveryRepository {
	Delivery save(Delivery delivery);

	Delivery findByIdAndNotDeleted(UUID deliveryId);
}
