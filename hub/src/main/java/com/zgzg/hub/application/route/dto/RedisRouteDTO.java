package com.zgzg.hub.application.route.dto;

import com.zgzg.hub.domain.entity.Route;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisRouteDTO {

  private String startHubName;
  private String endHubName;
  private UUID startHubId;
  private UUID endHubId;
  private Integer duration;
  private Integer distance;
  private int sequence;

  public static RedisRouteDTO from(Route route) {
    return RedisRouteDTO.builder()
        .startHubName(route.getStartHubName())
        .endHubName(route.getEndHubName())
        .startHubId(route.getStartHubId())
        .endHubId(route.getEndHubId())
        .duration(route.getInterTime())
        .distance(route.getInterDistance())
        .sequence(route.getSequence())
        .build();
  }
}
