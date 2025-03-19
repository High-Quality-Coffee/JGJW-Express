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

  private List<RouteDTO> routes;

  public void addRoute(RouteDTO route) {
    routes.add(route);
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RouteDTO {

    private UUID startHubId;
    private UUID endHubId;
    private Integer distance;
    private Integer duration;
    private int sequence;

    public static RouteDTO from(UUID startHubId, UUID endHubId,
        Integer distance, Integer duration, int sequence) {

      return RouteDTO.builder()
          .startHubId(startHubId)
          .endHubId(endHubId)
          .distance(distance)
          .duration(duration)
          .sequence(sequence)
          .build();
    }
  }
}
