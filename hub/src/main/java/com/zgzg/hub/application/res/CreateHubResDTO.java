package com.zgzg.hub.application.res;

import com.zgzg.hub.domain.entity.Hub;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateHubResDTO {

  private HubDTO hubDTO;

  public static CreateHubResDTO from(Hub hub) {
    return CreateHubResDTO.builder()
        .hubDTO(HubDTO.from(hub))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class HubDTO {

    private UUID hubId;
    private String hubName; // Consider using camelCase for consistency
    private String hubAddress;
    private String hubLatitude;
    private String hubLongitude;
    private Long hubAdminId;
    private boolean isMegaHub;

    public static HubDTO from(Hub hub) {
      return HubDTO.builder()
          .hubId(hub.getHubId())
          .hubName(hub.getHubName())
          .hubAddress(hub.getHubAddress())
          .hubLatitude(hub.getHubLatitude())
          .hubLongitude(hub.getHubLongitude())
          .hubAdminId(hub.getHubAdminId())
          .isMegaHub(hub.isMegaHub())
          .build();
    }
  }
}
