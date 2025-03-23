package com.zgzg.hub.domain.repository.route;

import com.zgzg.hub.domain.entity.Route;
import java.util.List;
import java.util.UUID;

public interface RouteRepositoryCustom {

  List<Route> findByStepRoute(UUID startId, UUID endId);

}
