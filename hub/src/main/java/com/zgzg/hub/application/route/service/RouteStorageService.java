package com.zgzg.hub.application.route.service;

import com.zgzg.hub.application.route.dto.ProcessedRouteDTO;
import com.zgzg.hub.application.route.dto.ProcessedRouteDTO.RouteDTO;
import com.zgzg.hub.domain.entity.Route;
import com.zgzg.hub.domain.repository.RouteRepository;
import java.util.ArrayList;
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
public class RouteStorageService {

  private final RouteRepository routeRepository;

  // Redis 에 저장.
  public void saveCache(Map<String, ProcessedRouteDTO> route) {

  }

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
          .startHubId(startId)
          .endHubId(endId)
          .interTime(0)
          .interDistance(0)
          .sequence(0)
          .parentId(null)
          .build());

      ProcessedRouteDTO processedRouteDTO = entry.getValue();

      for (RouteDTO routeDTO : processedRouteDTO.getRoutes()) {
        totalTime += routeDTO.getDuration();
        totalDistance += routeDTO.getDistance();

        routes.add(Route.builder()
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
  }
}
