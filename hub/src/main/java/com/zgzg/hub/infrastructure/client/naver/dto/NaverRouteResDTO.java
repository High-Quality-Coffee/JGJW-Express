package com.zgzg.hub.infrastructure.client.naver.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class NaverRouteResDTO {

  private LocalDateTime currentDateTime;
  private Route route;

  @Getter
  public static class Route {

    private List<TraOptimal> traoptimal;
  }

  @Getter
  public static class TraOptimal {

    private Summary summary;
  }

  @Getter
  public static class Summary {

    private Integer distance;
    private Integer duration;
  }
}