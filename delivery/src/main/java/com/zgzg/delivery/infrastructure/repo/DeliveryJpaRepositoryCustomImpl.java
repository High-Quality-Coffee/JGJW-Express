package com.zgzg.delivery.infrastructure.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zgzg.delivery.application.dto.res.DeliveryResponseDTO;
import com.zgzg.delivery.domain.entity.QDelivery;
import com.zgzg.delivery.presentation.dto.global.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveryJpaRepositoryCustomImpl implements DeliveryJpaRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<DeliveryResponseDTO> searchDeliveryByCriteria(SearchCriteria criteria, Pageable pageable) {
		OrderSpecifier[] deliverySpecifiers = createOrderSpecifiers(pageable.getSort());

		QDelivery delivery = QDelivery.delivery;

		List<DeliveryResponseDTO> deliveryList = queryFactory
			.select(Projections.constructor(DeliveryResponseDTO.class,
				delivery.deliveryId,
				delivery.deliveryStatus,
				delivery.originHubId,
				delivery.originHubName,
				delivery.destinationHubId,
				delivery.destinationHubName,
				delivery.receiverAddress,
				delivery.receiverName,
				delivery.receiverSlackId,
				delivery.deliveryPersonId,
				delivery.deliveryPersonSlackId,
				delivery.orderId,
				delivery.createdDateTime
			))
			.from(delivery)
			.where(delivery.deletedAt.isNull())
			.orderBy(deliverySpecifiers)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(delivery.count())
			.from(delivery)
			.where(delivery.deletedAt.isNull());

		return PageableExecutionUtils.getPage(deliveryList, pageable, countQuery::fetchOne);
	}

	private OrderSpecifier<?>[] createOrderSpecifiers(Sort sorts) {

		return sorts.stream()
			.filter(sort -> List.of("createdDateTime", "modifiedDateTime").contains(sort.getProperty()))
			.map(
				sort -> {
					com.querydsl.core.types.Order direction =
						sort.getDirection().isAscending() ? com.querydsl.core.types.Order.ASC :
							com.querydsl.core.types.Order.DESC;
					switch (sort.getProperty()) {
						case "createdDateTime":
							return new OrderSpecifier<>(direction, QDelivery.delivery.createdDateTime);
						case "modifiedDateTime":
							return new OrderSpecifier<>(direction, QDelivery.delivery.modifiedDateTime);
					}
					return null;
				})
			.toArray(OrderSpecifier[]::new);
	}
}
