package com.zgzg.hub.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

  private Integer distance;
  private Integer duration;

  public static RouteDTO from(NaverRouteResDTO routeResDTO) {
    return RouteDTO.builder()
        .distance(routeResDTO.getRoute().getTraoptimal().get(0).getSummary().getDistance())
        .duration(routeResDTO.getRoute().getTraoptimal().get(0).getSummary().getDuration())
        .build();
  }
}
