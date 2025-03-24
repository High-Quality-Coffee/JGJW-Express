package com.zgzg.hub.domain.repository.hub;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zgzg.hub.domain.entity.Hub;
import com.zgzg.hub.domain.entity.QHub;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Hub> searchByHubName(String keyword, Pageable pageable) {
    QHub hub = QHub.hub;

    List<Hub> hubs = queryFactory
        .selectFrom(hub)
        .where(
            hub.hubName.containsIgnoreCase(keyword),
            hub.deletedAt.isNull()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(hub.count())
        .from(hub)
        .where(
            hub.hubName.containsIgnoreCase(keyword),
            hub.deletedAt.isNull()
        )
        .fetchOne();

    if (total == null) {
      total = 0L;
    }

    return new PageImpl<>(hubs, pageable, total);
  }
}
