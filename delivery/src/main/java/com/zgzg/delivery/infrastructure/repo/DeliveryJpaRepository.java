package com.zgzg.delivery.infrastructure.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zgzg.delivery.domain.entity.Delivery;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, UUID>, DeliveryJpaRepositoryCustom {
	Delivery findByDeliveryIdAndDeletedAtIsNull(UUID deliveryId);
}
