package com.zgzg.hub.domain.repository.route;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zgzg.hub.domain.entity.QRoute;
import com.zgzg.hub.domain.entity.Route;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Route> findByStepRoute(UUID startId, UUID endId) {
    QRoute route = QRoute.route;
    QRoute subRoute = QRoute.route;

    return queryFactory
        .select(route)
        .where(route.parentId.in(
            JPAExpressions
                .select(subRoute.routeId)
                .from(subRoute)
                .where(subRoute.startHubId.eq(startId),
                    subRoute.endHubId.eq(endId)
                )
        ))
        .fetch();
  }
}
