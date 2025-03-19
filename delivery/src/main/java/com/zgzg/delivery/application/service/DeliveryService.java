package com.zgzg.delivery.application.service;

import static com.zgzg.common.response.Code.*;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zgzg.common.exception.BaseException;
import com.zgzg.delivery.application.dto.res.DeliveryResponseDTO;
import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.entity.DeliveryStatus;
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

	public DeliveryResponseDTO getDelivery(UUID deliveryId) {
		Delivery delivery = deliveryRepository.findByIdAndNotDeleted(deliveryId);
		// todo. originHubName, destinationHubName, deliveryPersonId, deliveryPersonName 값
		return DeliveryResponseDTO.from(delivery);
	}

	@Transactional
	public void deleteDelivery(UUID deliveryId) {
		Delivery delivery = deliveryRepository.findByIdAndNotDeleted(deliveryId);
		// todo. deletedBy 넣어주는 로직
		delivery.softDelete("temp");
	}

	@Transactional
	public DeliveryResponseDTO cancelDelivery(UUID deliveryId) {
		// todo. 배송 상태 != PREPARING 취소 불가
		Delivery delivery = deliveryRepository.findByIdAndNotDeleted(deliveryId);
		if (!delivery.getDeliveryStatus().equals(DeliveryStatus.PREPARING)) {
			// todo. 예외를 던지는 게 맞나..? 그냥 취소 불가일 뿐인데...
			throw new BaseException(DELIVERY_CANCEL_FAIL);
		}
		delivery.cancelDelivery();
		return DeliveryResponseDTO.from(delivery);
	}
}
