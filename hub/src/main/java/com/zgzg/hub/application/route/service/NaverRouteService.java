package com.zgzg.hub.application.route.service;

import com.zgzg.hub.application.route.dto.OrganizedRouteDTO;
import com.zgzg.hub.domain.entity.Hub;
import com.zgzg.hub.domain.repository.hub.HubRepository;
import com.zgzg.hub.infrastructure.client.naver.NaverDirectionClient;
import com.zgzg.hub.infrastructure.client.naver.dto.NaverRouteResDTO;
import com.zgzg.hub.infrastructure.client.naver.dto.RouteDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverRouteService {

  private final NaverDirectionClient naverDirectionClient;
  private final HubRepository hubRepository;

  public OrganizedRouteDTO getRoutes() {
    Map<UUID, Hub> megaHubMap = new HashMap<>();
    Map<UUID, List<Hub>> subMap = new HashMap<>();
    Map<String, RouteDTO> routeMap = new ConcurrentHashMap<>();

    List<Hub> hubs = hubRepository.findAll();
    List<Hub> megaHubList = new ArrayList<>();

    setMegaHub(hubs, megaHubMap, megaHubList, subMap);
    setNormalHub(hubs, subMap);

    getMegaToMegaHub(megaHubList, routeMap);
    getNormalToMegaHub(subMap, megaHubMap, routeMap);

    return OrganizedRouteDTO.builder()
        .megaHubMap(megaHubMap)
        .subMap(subMap)
        .routeMap(routeMap)
        .build();
  }

  private void getNormalToMegaHub(Map<UUID, List<Hub>> subMap, Map<UUID, Hub> megaHubMap,
      Map<String, RouteDTO> routeMap) {
    // 일반 허브와 자기 부모 허브간 경로 조회(양방향)
    for (Entry<UUID, List<Hub>> entry : subMap.entrySet()) {
      Hub parent = megaHubMap.get(entry.getKey());
      List<Hub> child = entry.getValue();

      for (Hub hub : child) {
        routeMap.put(makeRoutKey(parent, hub),
            RouteDTO.from(getDirection(parent, hub), hub.getHubName(), parent.getHubName()));
        routeMap.put(makeRoutKey(hub, parent),
            RouteDTO.from(getDirection(hub, parent), hub.getHubName(), parent.getHubName()));
      }
    }
    log.info("일반 허브 - 중앙 허브 경로 조회");
  }

  private void getMegaToMegaHub(List<Hub> megaHubList, Map<String, RouteDTO> routeMap) {
    // 중앙 허브 끼리 경로 조회(양방향)
    for (Hub startHub : megaHubList) {
      for (Hub endhub : megaHubList) {
        if (startHub.getHubId() == endhub.getHubId()) {
          continue;
        }

        routeMap.put(
            makeRoutKey(startHub, endhub),
            RouteDTO.from(getDirection(startHub, endhub), startHub.getHubName(),
                endhub.getHubName())
        );
      }
    }
    log.info("중앙 허브 - 중앙 허브 경로 조회");
  }

  private void setNormalHub(List<Hub> hubs, Map<UUID, List<Hub>> subMap) {
    for (Hub hub : hubs) { // 일반 허브 저장
      if (!hub.isMegaHub()) {
        List<Hub> sub = subMap.get(hub.getParentHubId());
        sub.add(hub);
      }
    }
    log.info("일반 허브 데이터 저장");
  }

  private void setMegaHub(List<Hub> hubs, Map<UUID, Hub> megaHubMap, List<Hub> megaHubList,
      Map<UUID, List<Hub>> subMap) {
    for (Hub hub : hubs) { // 중앙 허브 저장
      if (hub.isMegaHub()) {
        megaHubMap.put(hub.getHubId(), hub);
        megaHubList.add(hub);
        subMap.put(hub.getHubId(), new ArrayList<>());
      }
    }
    log.info("중앙 허브 데이터 저장");
  }

  private NaverRouteResDTO getDirection(Hub startHub, Hub endHub) {
    String goal = startHub.getHubLongitude() + "," + startHub.getHubLatitude();
    String start = endHub.getHubLongitude() + "," + endHub.getHubLatitude();

    return naverDirectionClient.getNaverRoute(goal, start);
  }

  private String makeRoutKey(Hub startHub, Hub endHub) {
    StringBuilder sb = new StringBuilder("hub:");
    return sb.append(startHub.getHubId()).append(":")
        .append(endHub.getHubId()).toString();
  }
}
