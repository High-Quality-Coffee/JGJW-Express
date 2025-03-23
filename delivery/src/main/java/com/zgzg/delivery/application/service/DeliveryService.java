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
import com.zgzg.delivery.application.client.DeliveryPersonClient;
import com.zgzg.delivery.application.client.HubClient;
import com.zgzg.delivery.application.dto.res.DeliveryResponseDTO;
import com.zgzg.delivery.application.dto.res.DeliveryRouteLogsResponseDTO;
import com.zgzg.delivery.application.dto.res.DeliveryRouteResponseDTO;
import com.zgzg.delivery.application.dto.res.PageableResponse;
import com.zgzg.delivery.domain.entity.Delivery;
import com.zgzg.delivery.domain.entity.DeliveryRouteLog;
import com.zgzg.delivery.domain.entity.DeliveryStatus;
import com.zgzg.delivery.domain.repo.DeliveryRepository;
import com.zgzg.delivery.domain.repo.DeliveryRouteLogRepository;
import com.zgzg.delivery.infrastructure.client.res.DeliveryUserResponseDTO;
import com.zgzg.delivery.infrastructure.dto.HubResponseDTO;
import com.zgzg.delivery.infrastructure.dto.RouteDTO;
import com.zgzg.delivery.presentation.dto.global.SearchCriteria;
import com.zgzg.delivery.presentation.dto.req.CreateDeliveryRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final DeliveryRouteLogRepository deliveryRouteLogRepository;
	private final HubClient hubClient;
	private final DeliveryPersonClient deliveryPersonClient;

	@Transactional
	public UUID createDelivery(CreateDeliveryRequestDTO requestDTO) {
		Delivery delivery = requestDTO.toEntity();
		Delivery savedDelivery = deliveryRepository.save(delivery);

		List<RouteDTO> hubRoutes = hubClient.getHubRoutes(delivery.getOriginHubId(),
			delivery.getDestinationHubId());
		// todo. hubName 추가 요청 -> 상욱님
		for (RouteDTO hubRoute : hubRoutes) {
			log.info("Hub route: {}", hubRoute.getDistance());
			deliveryRouteLogRepository.save(hubRoute.toEntity(savedDelivery));
		}

		return savedDelivery.getDeliveryId();
	}

	public DeliveryResponseDTO getDelivery(UUID deliveryId) {
		Delivery delivery = deliveryRepository.findByIdAndNotDeleted(deliveryId);
		return DeliveryResponseDTO.from(delivery);
	}

	@Transactional
	public void deleteDelivery(UUID deliveryId, CustomUserDetails userDetails) {
		Delivery delivery = deliveryRepository.findByIdAndNotDeleted(deliveryId);

		if (userDetails.getRole().equals("ROLE_HUB") && !hasHubAuth(delivery, userDetails)) {
			throw new BaseException(DELIVERY_AUTH_FORBIDDEN);
		}
		delivery.softDelete(userDetails.getUsername());
		deliveryRouteLogRepository.softDeleteRoutes(deliveryId);
	}

	@Transactional
	public DeliveryResponseDTO cancelDelivery(UUID deliveryId) {
		Delivery delivery = deliveryRepository.findByIdAndNotDeleted(deliveryId);
		if (!delivery.getDeliveryStatus().equals(DeliveryStatus.PREPARING)) {
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

	@Transactional
	public void startDelivery(UUID deliveryId, int sequence) {
		DeliveryRouteLog route = deliveryRouteLogRepository.findByIdAndSequence(deliveryId, sequence);

		log.info("seq : {} , delivery id : {}", sequence, deliveryId);
		if (sequence == 1) {
			Delivery delivery = deliveryRepository.findByIdAndNotDeleted(deliveryId);
			if (delivery == null) {
				throw new BaseException(DELIVERY_NOT_FOUND);
			}
			
			delivery.startDelivery(); // 배송 상태 변경

			DeliveryUserResponseDTO deliver = deliveryPersonClient.getDeiveryPerson();
			route.assignDeliveryPerson(deliver.getDeliveryUserId(), deliver.getDeliverySlackUsername());
		}
		route.startDelivery(); // 각 시퀀스 배송 상태 변경
	}

	@Transactional
	public void arriveDelivery(UUID deliveryId, int sequence) {
		// 허브 도착(담당자 할당, 실제 거리, 실제 소요 시간
		// 실제 소요 시간 계산
		// todo. 실제 거리 계산은 근데 어떻게 하지..? 배송담당자가 운전한 거리 추적을 어케하란말임

		//분기) 마지막 허브인 경우
		// 업체 배송 담당자 할당(이전 hubId, 현재 hubId, 배송타입) + "IN_DELIVERY" + sequence += 1
		//분기) 아닌 경우
		// 허브 배송 담당자 할당(이전 hubId, 현재 hubId, 배송타입) + "HUB_ARRIVED"

	}

	@Transactional
	public void completeDelivery(UUID deliveryId, int sequence) {
		// 배송 완료 (요청 업체 수령 완료)
		Delivery delivery = deliveryRepository.findByIdAndNotDeleted(deliveryId);
		delivery.completeDelivery();
		// todo. 배송 담당자 배송 가능 상태로 변경

		// 경로 기록
		DeliveryRouteLog route = deliveryRouteLogRepository.findByIdAndSequence(deliveryId, sequence);
		route.completeDelivery();
		// todo. 실제 거리, 실제 소요 시간
	}

	private boolean hasHubAuth(Delivery delivery, CustomUserDetails userDetails) {
		HubResponseDTO response = hubClient.getHub(delivery.getOriginHubId());
		log.info("Hub Client : 허브 담당자 id - {}", response.getHubDTO().getHubAdminId());
		if (response.getHubDTO().getHubAdminId().equals(userDetails.getId())) {
			return true;
		}
		return false;
	}
}
