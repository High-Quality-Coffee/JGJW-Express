package com.zgzg.hub.application.route.dto;

import com.zgzg.hub.domain.entity.Route;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutesDTO {

  private List<RouteDTO> routes;

  public static RoutesDTO fromEntity(List<Route> routes) {
    return RoutesDTO.builder()
        .routes(routes.stream()
            .map(RouteDTO::fromEntity)
            .toList())
        .build();
  }

  public static RoutesDTO fromMap(Map<String, RedisRouteDTO> map) {
    return RoutesDTO.builder()
        .routes(map.values().stream()
            .map(RouteDTO::fromRedis)
            .toList())
        .build();
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
    private Integer duration;
    private Integer distance;
    private int sequence;

    public static RouteDTO fromEntity(Route route) {
      return RouteDTO.builder()
          .startHubName(route.getStartHubName())
          .endHubName(route.getEndHubName())
          .startHubId(route.getStartHubId())
          .endHubId(route.getEndHubId())
          .distance(route.getInterDistance())
          .duration(route.getInterTime())
          .sequence(route.getSequence())
          .build();
    }

    public static RouteDTO fromRedis(RedisRouteDTO redisRouteDTO) {
      return RouteDTO.builder()
          .startHubName(redisRouteDTO.getStartHubName())
          .endHubName(redisRouteDTO.getEndHubName())
          .startHubId(redisRouteDTO.getStartHubId())
          .endHubId(redisRouteDTO.getEndHubId())
          .duration(redisRouteDTO.getDuration())
          .distance(redisRouteDTO.getDistance())
          .sequence(redisRouteDTO.getSequence())
          .build();
    }
  }
}
