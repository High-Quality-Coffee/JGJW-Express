package com.zgzg.hub.application.route.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedRouteDTO {

  private String firstHubName;
  private String lastHubName;
  private List<RouteDTO> routes;

  public void addRoute(RouteDTO route) {
    routes.add(route);
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RouteDTO {

    private String startHubName;
    private String endHubName;
    private UUID startHubId;
    private UUID endHubId;
    private Integer distance;
    private Integer duration;
    private int sequence;
  }
}
