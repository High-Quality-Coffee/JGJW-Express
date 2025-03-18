package com.zgzg.hub.application.res;

import com.zgzg.hub.domain.entity.Hub;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubResDTO {

  private HubDTO hubDTO;

  public static HubResDTO from(Hub hub) {
    return HubResDTO.builder()
        .hubDTO(HubDTO.from(hub))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class HubDTO {

    private UUID hubId;
    private String HubName;
    private String hubAddress;
    private String hubLatitude;
    private String hubLongitude;
    private Long hubAdminId;
    private boolean isMegaHub;
    private UUID parentHubId;

    public static HubDTO from(Hub hub) {
      return HubDTO.builder()
          .hubId(hub.getHubId())
          .HubName(hub.getHubName())
          .hubAddress(hub.getHubAddress())
          .hubLatitude(hub.getHubLatitude())
          .hubLongitude(hub.getHubLongitude())
          .hubAdminId(hub.getHubAdminId())
          .isMegaHub(hub.isMegaHub())
          .parentHubId(hub.getParentHubId())
          .build();
    }
  }
}
