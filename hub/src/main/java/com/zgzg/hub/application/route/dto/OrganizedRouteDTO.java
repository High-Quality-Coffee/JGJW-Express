package com.zgzg.hub.application.route.dto;

import com.zgzg.hub.domain.entity.Hub;
import com.zgzg.hub.infrastructure.client.naver.dto.RouteDTO;
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
public class OrganizedRouteDTO {

  private Map<UUID, Hub> megaHubMap;
  private Map<UUID, List<Hub>> subMap;
  private Map<String, RouteDTO> routeMap;
}
