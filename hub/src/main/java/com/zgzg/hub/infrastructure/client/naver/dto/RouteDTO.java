package com.zgzg.hub.infrastructure.client.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

  private String startHubName;
  private String endHubName;
  private Integer distance;
  private Integer duration;

  public static RouteDTO from(NaverRouteResDTO routeResDTO, String startHubName,
      String endHubName) {
    return RouteDTO.builder()
        .startHubName(startHubName)
        .endHubName(endHubName)
        .distance(routeResDTO.getRoute().getTraoptimal().get(0).getSummary().getDistance())
        .duration(routeResDTO.getRoute().getTraoptimal().get(0).getSummary().getDuration())
        .build();
  }
}
