package com.zgzg.delivery.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.repo.DeliveryRepository;
import com.zgzg.delivery.presentation.dto.req.CreateDeliveryRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;

	@Transactional
	public UUID createDelivery(CreateDeliveryRequestDTO requestDTO) {
		Delivery delivery = requestDTO.toEntity();
		Delivery savedDelivery = deliveryRepository.save(delivery);
		return savedDelivery.getDeliveryId();
	}
}
