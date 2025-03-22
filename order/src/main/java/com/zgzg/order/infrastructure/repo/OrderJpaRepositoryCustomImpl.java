package com.zgzg.order.infrastructure.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zgzg.order.application.dto.res.OrderResponseDTO;
import com.zgzg.order.domain.entity.QOrder;
import com.zgzg.order.presentation.dto.req.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderJpaRepositoryCustomImpl implements OrderJpaRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<OrderResponseDTO> searchOrderByCriteria(SearchCriteria criteria, Pageable pageable, String role,
		UUID id) {
		OrderSpecifier[] orderSpecifiers = createOrderSpecifiers(pageable.getSort());

		// todo. 권한 별 조회 가능한 condition 적용
		/* 1. 허브관리자 -> 담당 허브. 담당 허브에 해당하는 주문은 어떻게 조회하지?
		 * 		- order 공급 업체의 소속 허브 id ?? 이것도 스냅샷 방식으로 해야하나?
		 * 		- 이 주문을 받은 허브에 대한 정보도 있는 게 맞을지도...?
		 * 		(주문은 공급 업체가 받았지만 어쨌든 재고 발송이 허브에서 이루어지니까..?)
		 * 	=> 어쨋든 필요한 조건 허브 id 일치
		 *
		 * * 3. 업체담당자 -> 본인 주문만.
		 * 		- 요청 업체 ID로 주문 조회
		 * */
		QOrder order = QOrder.order;

		// 1. 검색 조건 + 페이징
		List<OrderResponseDTO> orderList = queryFactory
			.select(Projections.constructor(OrderResponseDTO.class,
				order.orderId,
				order.receiverCompanyId,
				order.supplierCompanyId,
				order.supplierCompanyName,
				order.orderTotalPrice,
				order.orderStatus,
				order.orderRequest
			))
			.from(order)
			.where(betweenDateCondition(criteria).and(authCondition(role, id)))
			.orderBy(orderSpecifiers)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 2. 전체 데이터 개수 조회 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(order.count())
			.from(order)
			.where(betweenDateCondition(criteria));

		return PageableExecutionUtils.getPage(orderList, pageable, countQuery::fetchOne);
	}

	private BooleanBuilder betweenDateCondition(SearchCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getStartDate() != null) {
			builder.and(QOrder.order.createdDateTime.after(criteria.getStartDate()));
		}
		if (criteria.getEndDate() != null) {
			builder.and(QOrder.order.createdDateTime.before(criteria.getEndDate()));
		}
		builder.and(QOrder.order.deletedAt.isNull());
		return builder;
	}

	private BooleanBuilder authCondition(String role, UUID id) {
		BooleanBuilder builder = new BooleanBuilder();
		if (role.equals("ROLE_STORE")) {
			builder.and(QOrder.order.receiverCompanyId.eq(id));
		} else if (role.equals("ROLE_HUB")) {
			builder.and(QOrder.order.receiverHubId.eq(id));
		}
		return builder;
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
							return new OrderSpecifier<>(direction, QOrder.order.createdDateTime);
						case "modifiedDateTime":
							return new OrderSpecifier<>(direction, QOrder.order.modifiedDateTime);
					}
					return null;
				})
			.toArray(OrderSpecifier[]::new);
	}
}
