package com.zgzg.hub.application.route.service;

import com.zgzg.hub.application.route.dto.ProcessedRouteDTO;
import com.zgzg.hub.application.route.dto.ProcessedRouteDTO.RouteDTO;
import com.zgzg.hub.application.route.dto.RedisRouteDTO;
import com.zgzg.hub.domain.entity.Route;
import com.zgzg.hub.domain.repository.route.RouteRepository;
import com.zgzg.hub.infrastructure.redis.RedisHashUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteStorageService {

  private final RouteRepository routeRepository;
  private final RedisHashUtil redisHashUtil;

  // Redis 에 저장.
  @Async
  public void saveCache(Map<String, ProcessedRouteDTO> processedMap) {
    for (Map.Entry<String, ProcessedRouteDTO> entry : processedMap.entrySet()) {
      String[] parsingHub = entry.getKey().split(":");
      UUID startId = UUID.fromString(parsingHub[1]);
      UUID endId = UUID.fromString(parsingHub[2]);

      Integer totalTime = 0;
      Integer totalDistance = 0;

      Map<String, RedisRouteDTO> map = new HashMap<>();

      for (RouteDTO routeDTO : entry.getValue().getRoutes()) {
        String hashField = makeHashField(routeDTO.getStartHubId(), routeDTO.getEndHubId());
        RedisRouteDTO redisRouteDTO = RedisRouteDTO.builder()
            .startHubName(routeDTO.getStartHubName())
            .endHubName(routeDTO.getEndHubName())
            .startHubId(routeDTO.getStartHubId())
            .endHubId(routeDTO.getEndHubId())
            .duration(routeDTO.getDuration())
            .distance(routeDTO.getDistance())
            .sequence(routeDTO.getSequence())
            .build();

        totalTime += routeDTO.getDuration();
        totalDistance += routeDTO.getDistance();

        map.put(hashField, redisRouteDTO);
      }
      // 루트 경로 저장.
      RedisRouteDTO rootDTO = RedisRouteDTO.builder()
          .startHubName(entry.getValue().getFirstHubName())
          .endHubName(entry.getValue().getLastHubName())
          .startHubId(startId)
          .endHubId(endId)
          .duration(totalTime)
          .distance(totalDistance)
          .sequence(0)
          .build();
      map.put(entry.getKey(), rootDTO);
      redisHashUtil.save(entry.getKey(), map);
    }
    log.info("Redis 경로 데이터 업데이트");
  }

  @Async
  @Transactional
  public void save(Map<String, ProcessedRouteDTO> processedMap) {
    List<Route> routes = new ArrayList<>();

    for (Map.Entry<String, ProcessedRouteDTO> entry : processedMap.entrySet()) {
      String[] parsingHub = entry.getKey().split(":");
      UUID startId = UUID.fromString(parsingHub[1]);
      UUID endId = UUID.fromString(parsingHub[2]);

      Integer totalTime = 0;
      Integer totalDistance = 0;
      Route route = routeRepository.save(Route.builder()
          .startHubName(entry.getValue().getFirstHubName())
          .endHubName(entry.getValue().getLastHubName())
          .startHubId(startId)
          .endHubId(endId)
          .interTime(0)
          .interDistance(0)
          .sequence(0)
          .parentId(null)
          .build());
      route.setParentId(route.getRouteId());

      ProcessedRouteDTO processedRouteDTO = entry.getValue();

      for (RouteDTO routeDTO : processedRouteDTO.getRoutes()) {
        totalTime += routeDTO.getDuration();
        totalDistance += routeDTO.getDistance();

        routes.add(Route.builder()
            .startHubName(routeDTO.getStartHubName())
            .endHubName(routeDTO.getEndHubName())
            .startHubId(routeDTO.getStartHubId())
            .endHubId(routeDTO.getEndHubId())
            .interTime(routeDTO.getDuration())
            .interDistance(routeDTO.getDistance())
            .sequence(routeDTO.getSequence())
            .parentId(route.getRouteId())
            .build());
      }
      route.setInterTime(totalTime);
      route.setInterDistance(totalDistance);
    }
    routeRepository.saveAll(routes);
    log.info("DB 경로 데이터 업데이트");
  }

  private String makeHashField(UUID startId, UUID endId) {
    return startId + ":" + endId;
  }
}
