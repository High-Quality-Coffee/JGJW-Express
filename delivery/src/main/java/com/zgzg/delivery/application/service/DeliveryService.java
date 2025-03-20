package com.zgzg.delivery.application.service;

import static com.zgzg.common.response.Code.*;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.delivery.application.dto.res.DeliveryResponseDTO;
import com.zgzg.delivery.application.dto.res.DeliveryRouteLogsResponseDTO;
import com.zgzg.delivery.application.dto.res.DeliveryRouteResponseDTO;
import com.zgzg.delivery.application.dto.res.PageableResponse;
import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.entity.DeliveryRouteLog;
import com.zgzg.delivery.domain.entity.DeliveryStatus;
import com.zgzg.delivery.domain.repo.DeliveryRepository;
import com.zgzg.delivery.domain.repo.DeliveryRouteLogRepository;
import com.zgzg.delivery.presentation.dto.global.SearchCriteria;
import com.zgzg.delivery.presentation.dto.req.CreateDeliveryRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final DeliveryRouteLogRepository deliveryRouteLogRepository;

	@Transactional
	public UUID createDelivery(CreateDeliveryRequestDTO requestDTO, CustomUserDetails userDetails) {
		Delivery delivery = requestDTO.toEntity();
		Delivery savedDelivery = deliveryRepository.save(delivery);
		// todo. 배송 경로 생성 로직 추가 (허브 경로)
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

	public PageableResponse<DeliveryResponseDTO> searchOrder(SearchCriteria criteria, Pageable pageable) {
		Page<DeliveryResponseDTO> deliveryDTOPage = deliveryRepository.searchDeliveryByCriteria(criteria, pageable);
		return new PageableResponse<>(deliveryDTOPage);
	}

	public DeliveryRouteLogsResponseDTO getDeliveryRoutes(UUID deliveryId) {
		List<DeliveryRouteLog> routeLogs = deliveryRouteLogRepository.findByIdAndNotDeleted(deliveryId);
		List<DeliveryRouteResponseDTO> routeList = routeLogs.stream()
			.map(log -> DeliveryRouteResponseDTO.from(log))
			.toList();

		return new DeliveryRouteLogsResponseDTO(deliveryId, routeList);
	}
}
