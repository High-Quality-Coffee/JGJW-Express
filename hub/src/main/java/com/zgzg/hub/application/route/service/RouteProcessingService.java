package com.zgzg.hub.application.route.service;

import com.zgzg.hub.application.route.dto.OrganizedRouteDTO;
import com.zgzg.hub.application.route.dto.ProcessedRouteDTO;
import com.zgzg.hub.domain.entity.Hub;
import com.zgzg.hub.domain.repository.hub.HubRepository;
import com.zgzg.hub.infrastructure.client.naver.dto.RouteDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteProcessingService {

  private final HubRepository hubRepository;

  public Map<String, ProcessedRouteDTO> processRoute(OrganizedRouteDTO routeDTO) {
    List<Hub> hubs = hubRepository.findAll();
    Map<UUID, Hub> megaHubMap = routeDTO.getMegaHubMap();
    Map<String, RouteDTO> routeMap = routeDTO.getRouteMap();

    Map<String, ProcessedRouteDTO> processedMap = new HashMap<>();

    /**
     * 출발 허브에서 도착 허브까지의 경로 구하기
     * 출발허브 - 도착허브 기준 나올 수 있는 경우
     * 1. Mega - Mega
     * 2. Mega - Normal
     * 3. Mega - Mega (같은 허브)
     * 4. Normal - Mega
     * 5. Normal - Normal
     * 6. Normal - Normal (같은 허브)
     */

    for (Hub startHub : hubs) {
      for (Hub endHub : hubs) {
        String routeKey = makeRouteKey(startHub, endHub);
        ProcessedRouteDTO processedRouteDTO =
            ProcessedRouteDTO.builder()
                .firstHubName(startHub.getHubName())
                .lastHubName(endHub.getHubName())
                .routes(new ArrayList<>())
                .build();

        if (startHub.getHubId().equals(endHub.getHubId())) { // 자기자신
          /**
           *  Mega - Mega (같은 허브)
           *  이동 경로 0, 시간 0, sequence 1
           */
          if (startHub.isMegaHub()) {
            processedRouteDTO.addRoute(ProcessedRouteDTO.RouteDTO.builder()
                .startHubName(startHub.getHubName())
                .endHubName(endHub.getHubName())
                .startHubId(startHub.getHubId())
                .endHubId(endHub.getHubId())
                .duration(0)
                .distance(0)
                .sequence(1)
                .build());

            processedMap.put(routeKey, processedRouteDTO);
            continue;
          }

          /**
           * Normal - Normal (같은 허브)
           * Normal -> 출발허브의 Mega(1) -> Normal(2)
           */

          Hub parent = megaHubMap.get(startHub.getParentHubId());
          normalToMegaHub(startHub, megaHubMap, routeMap, processedRouteDTO, 1);
          megaToNormalHub(parent, startHub, routeMap, processedRouteDTO, 2);
          processedMap.put(routeKey, processedRouteDTO);
          continue;
        }

        if (startHub.isMegaHub()) { // Mega 시작
          /**
           * 1. Mega - Mega
           * 출발허브 - 도착허브 조회, sequence = 1
           */
          if (endHub.isMegaHub()) {
            megaToMegaHub(startHub, endHub, routeMap, processedRouteDTO, 1);
            processedMap.put(routeKey, processedRouteDTO);
            continue;
          }

          /**
           * 2. Mega - Normal
           * 자기 자식일 경우  Mega -> Normal(1)
           * Mega -> 도착허브의 Mega(1) -> Normal(2)
           */
          Hub endParent = megaHubMap.get(endHub.getParentHubId());
          if (startHub.getHubId().equals(endParent.getHubId())) { // 자기 자식
            megaToNormalHub(startHub, endHub, routeMap, processedRouteDTO, 1);
            processedMap.put(routeKey, processedRouteDTO);
            continue;
          }

          megaToMegaHub(startHub, endParent, routeMap, processedRouteDTO, 1);
          megaToNormalHub(endParent, endHub, routeMap, processedRouteDTO, 2);
          processedMap.put(routeKey, processedRouteDTO);
          continue;
        }

        /**
         * 3. Normal - Mega
         * 자기부모 :  Normal -> 출발허브 Mega(1)
         * Normal -> 출발허브의 Mega(1) -> Mega(2)
         */
        if (endHub.isMegaHub()) {
          Hub startParent = megaHubMap.get(startHub.getParentHubId());
          if (endHub.getHubId().equals(startParent.getHubId())) {
            normalToMegaHub(startHub, megaHubMap, routeMap, processedRouteDTO, 1);
            processedMap.put(routeKey, processedRouteDTO);
            continue;
          }

          normalToMegaHub(startHub, megaHubMap, routeMap, processedRouteDTO, 1);
          megaToMegaHub(startParent, endHub, routeMap, processedRouteDTO, 2);
          processedMap.put(routeKey, processedRouteDTO);
          continue;
        }

        /**
         * 4. Normal - Normal
         * 부모가 같으면 Normal -> 출발허브 Mega(1) -> Normal(2)
         * Normal -> 출발허브의 Mega(1) -> 도착허브의 Mega(2) -> Normal(3)
         */
        Hub startParent = megaHubMap.get(startHub.getParentHubId());
        Hub endParent = megaHubMap.get(endHub.getParentHubId());

        if (startHub.getParentHubId().equals(endHub.getParentHubId())) { // 같은 부모
          normalToMegaHub(startHub, megaHubMap, routeMap, processedRouteDTO, 1);
          megaToNormalHub(startParent, endHub, routeMap, processedRouteDTO, 2);
          processedMap.put(routeKey, processedRouteDTO);
          continue;
        }

        normalToMegaHub(startHub, megaHubMap, routeMap, processedRouteDTO, 1);
        megaToMegaHub(startParent, endParent, routeMap, processedRouteDTO, 2);
        megaToNormalHub(endParent, endHub, routeMap, processedRouteDTO, 3);
        processedMap.put(routeKey, processedRouteDTO);
      }
    }
    return processedMap;
  }

  private String makeRouteKey(Hub startHub, Hub endHub) {
    StringBuilder sb = new StringBuilder("hub:");
    return sb.append(startHub.getHubId()).append(":")
        .append(endHub.getHubId()).toString();
  }

  private void normalToMegaHub(Hub startHub, Map<UUID, Hub> megaHubMap,
      Map<String, RouteDTO> routeMap, ProcessedRouteDTO processedRouteDTO, int sequence) {

    Hub parent = megaHubMap.get(startHub.getParentHubId()); // 부모 조회
    String key = makeRouteKey(startHub, parent);
    RouteDTO route = routeMap.get(key); // 자식 - 부모 경로 조회

    processedRouteDTO.addRoute(ProcessedRouteDTO.RouteDTO.builder()
        .startHubName(startHub.getHubName())
        .endHubName(parent.getHubName())
        .startHubId(startHub.getHubId())
        .endHubId(parent.getHubId())
        .distance(route.getDistance())
        .duration(route.getDuration())
        .sequence(sequence)
        .build());

    log.info("normal to mega : {} sequence : {}", startHub.getHubName(), sequence);
  }

  private void megaToMegaHub(Hub startHub, Hub endHub, Map<String, RouteDTO> routeMap,
      ProcessedRouteDTO processedRouteDTO, int sequence) {

    RouteDTO route = routeMap.get(makeRouteKey(startHub, endHub));
    processedRouteDTO.addRoute(
        ProcessedRouteDTO.RouteDTO.builder()
            .startHubName(startHub.getHubName())
            .endHubName(endHub.getHubName())
            .startHubId(startHub.getHubId())
            .endHubId(endHub.getHubId())
            .distance(route.getDistance())
            .duration(route.getDuration())
            .sequence(sequence)
            .build());

    log.info("mega to mega : {} -> {} sequence : {}", startHub.getHubName(), endHub.getHubName(),
        sequence);
  }

  private void megaToNormalHub(Hub startHub, Hub endHub, Map<String, RouteDTO> routeMap,
      ProcessedRouteDTO processedRouteDTO, int sequence) {

    RouteDTO route = routeMap.get(makeRouteKey(startHub, endHub));
    processedRouteDTO.addRoute(
        ProcessedRouteDTO.RouteDTO.builder()
            .startHubName(startHub.getHubName())
            .endHubName(endHub.getHubName())
            .startHubId(startHub.getHubId())
            .endHubId(endHub.getHubId())
            .distance(route.getDistance())
            .duration(route.getDuration())
            .sequence(sequence)
            .build());

    log.info("mega to normal: {} -> {} sequence : {}", startHub.getHubName(), endHub.getHubName(),
        sequence);
  }
}
