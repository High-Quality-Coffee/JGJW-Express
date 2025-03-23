package com.zgzg.hub.application.route.service.facade;

import static com.zgzg.common.response.Code.HUB_ROUTE_NOT_FOUND;

import com.zgzg.common.exception.BaseException;
import com.zgzg.hub.application.route.dto.RedisRouteDTO;
import com.zgzg.hub.application.route.dto.RoutesDTO;
import com.zgzg.hub.domain.entity.Route;
import com.zgzg.hub.domain.repository.RouteRepository;
import com.zgzg.hub.infrastructure.redis.RedisHashUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

  private final RedisHashUtil redisHashUtil;
  private final RouteRepository routeRepository;

  @Transactional(readOnly = true)
  public RoutesDTO getRoutes(UUID startHubId, UUID endHubId) {
    String hashKey = makeRouteKey(startHubId, endHubId);

    // Redis 조회
    Map<String, RedisRouteDTO> redisRoutes = redisHashUtil.get(hashKey);
    if (!redisRoutes.isEmpty()) {
      log.info("경로 Cache 조회, 경로 수 : {}", redisRoutes.size());
      return RoutesDTO.fromMap(redisRoutes);
    }

    List<Route> routes = routeRepository.findByStepRoute(startHubId, endHubId);
    if (routes.isEmpty()) {
      throw new BaseException(HUB_ROUTE_NOT_FOUND);
    }
    log.info("경로 수 : {}", routes.size());

    // Redis 저장
    updateCache(routes, hashKey);
    return RoutesDTO.fromEntity(routes);
  }

  private void updateCache(List<Route> routes, String hashKey) {
    Map<String, RedisRouteDTO> saveRouteDTO = new HashMap<>();
    for (Route route : routes) {
      String routeKey = route.getStartHubId() + ":" + route.getEndHubId();
      saveRouteDTO.put(routeKey, RedisRouteDTO.from(route));
    }
    redisHashUtil.save(hashKey, saveRouteDTO);
    log.info("Cache 업데이트 : {}", hashKey);
  }

  private String makeRouteKey(UUID startHubId, UUID endHubId) {
    StringBuilder sb = new StringBuilder();
    return sb.append("hub:").append(startHubId)
        .append(":").append(endHubId).toString();
  }
}
