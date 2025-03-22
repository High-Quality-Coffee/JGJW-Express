package com.zgzg.order.application.service;

import static com.zgzg.common.response.Code.*;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.order.application.client.CompanyClient;
import com.zgzg.order.application.client.DeliveryClient;
import com.zgzg.order.application.client.HubClient;
import com.zgzg.order.application.dto.global.PageableResponse;
import com.zgzg.order.application.dto.req.CreateDeliveryRequestDTO;
import com.zgzg.order.application.dto.res.OrderDetaiListDTO;
import com.zgzg.order.application.dto.res.OrderDetailDTO;
import com.zgzg.order.application.dto.res.OrderDetailResponseDTO;
import com.zgzg.order.application.dto.res.OrderResponseDTO;
import com.zgzg.order.domain.entity.Order;
import com.zgzg.order.domain.entity.OrderDetail;
import com.zgzg.order.domain.repo.OrderRepository;
import com.zgzg.order.infrastructure.dto.CompanyResponseDTO;
import com.zgzg.order.infrastructure.dto.DeliveryResponseDTO;
import com.zgzg.order.infrastructure.dto.DeliveryStatus;
import com.zgzg.order.infrastructure.dto.HubResponseDTO;
import com.zgzg.order.presentation.dto.req.CreateOrderRequestDto;
import com.zgzg.order.presentation.dto.req.SearchCriteria;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final DeliveryClient deliveryClient;
	private final CompanyClient companyClient;
	private final HubClient hubClient;

	@Transactional
	public UUID createOrder(CreateOrderRequestDto requestDto) {

		CompanyResponseDTO supplier = companyClient.getCompany(requestDto.getSupplierCompanyId());
		CompanyResponseDTO receiver = companyClient.getCompany(requestDto.getReceiverCompanyId());

		Order order = requestDto.toEntity(requestDto, receiver.getHub_id());
		Order savedOrder = orderRepository.save(order);
		for (OrderDetailDTO orderDetailDTO : requestDto.getProductList()) {
			OrderDetail orderDetail = orderDetailDTO.toEntity(order);
			orderRepository.saveDetail(orderDetail);
		}

		CreateDeliveryRequestDTO requestDTO = new CreateDeliveryRequestDTO(savedOrder.getOrderId(), supplier,
			receiver);
		UUID deliveryId = deliveryClient.createDelivery(requestDTO);
		savedOrder.addDeliveryOrder(deliveryId);

		return savedOrder.getOrderId();
	}

	@Transactional
	public void deleteOrder(UUID orderId, CustomUserDetails userDetails) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new BaseException(ORDER_NOT_FOUND));

		if (userDetails.getRole().equals("ROLE_HUB") && !hasHubAuth(orderId,
			userDetails.getId())) {
			throw new BaseException(ORDER_AUTH_FORBIDDEN);
		}
		order.softDelete(userDetails.getUsername());
		orderRepository.softDeleteDetails(orderId);
	}

	public OrderDetaiListDTO getOrder(UUID orderId, CustomUserDetails userDetails) {
		if (userDetails.getRole().equals("ROLE_STORE")) { // STORE(본인 주문만)
			Order order = orderRepository.findByIdAndNotDeleted(orderId);
			if (order == null) {
				throw new BaseException(ORDER_NOT_FOUND);
			}
		} else if (userDetails.getRole().equals("ROLE_HUB") && !hasHubAuth(orderId,
			userDetails.getId())) { //HUB(담당 허브만)
			throw new BaseException(ORDER_AUTH_FORBIDDEN);
		}

		List<OrderDetail> orderDetails = orderRepository.findAllByOrderIdAndNotDeleted(orderId);
		List<OrderDetailResponseDTO> detailList = orderDetails.stream()
			.map(detail -> OrderDetailResponseDTO.from(detail))
			.toList();
		return new OrderDetaiListDTO(orderId, detailList);
	}

	// todo. 권한 확인 - MASTER, HUB(담당 허브만), STORE(본인 주문만)
	public PageableResponse<OrderResponseDTO> searchOrder(SearchCriteria criteria, Pageable pageable,
		CustomUserDetails userDetails) {
		// 업체면 receiverCompanyId

		// 허브면 receiverHubId
		// long id = userDetails.getId();
		// HubResponseDTO hub = hubClient.getHub(id);
		// order가 없는 상태에선 허브관리자 ID로 허브 ID를 찾을 수 없넹..
		UUID id = null;

		Page<OrderResponseDTO> orderDTOPage = orderRepository.searchOrderByCriteria(criteria, pageable,
			userDetails.getRole(), id);
		return new PageableResponse<>(orderDTOPage);
	}

	@Transactional
	public OrderResponseDTO cancelOrder(UUID orderId, CustomUserDetails userDetails) {
		Order order = orderRepository.findByIdAndNotDeleted(orderId);

		if (userDetails.getRole().equals(("ROLE_HUB")) && !hasHubAuth(orderId, userDetails.getId())) {
			throw new BaseException(ORDER_AUTH_FORBIDDEN);
		}
		if (userDetails.getRole().equals("ROLE_STORE")) {

			CompanyResponseDTO company = companyClient.getCompany(order.getReceiverCompanyId());
			// todo. 조회한 업체의 관리자 id == userDetails.getId

			// STORE 의 경우, 배송 상태가 배송 시작 전일 때만 취소 가능
			DeliveryResponseDTO delivery = deliveryClient.getDelivery(order.getDeliveryId());
			if (delivery.getDeliveryStatus().equals(DeliveryStatus.IN_DELIVERY)) {
				throw new BaseException(DELIVERY_CANCEL_FAIL);
			}
		}
		order.cancelOrder();
		return OrderResponseDTO.from(order);
	}

	private boolean hasHubAuth(UUID orderId, Long userId) { // 주문이 들어온 허브의 허브 담당자 id == 조회 id
		Order order = orderRepository.findByIdAndNotDeleted(orderId);
		HubResponseDTO hub = getHubClient(order);
		if (hub.getHubDTO().getHubAdminId().equals(userId)) {
			return false;
		}
		return true;
	}

	private HubResponseDTO getHubClient(Order order) {
		return hubClient.getHub(order.getSupplierHubId());
	}
}