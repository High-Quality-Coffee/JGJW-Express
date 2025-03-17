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

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class HubDTO {

    private UUID hubId;
    private String HubName;
    private String hubAddress;
    private String hubLatitude;
    private String hubLongitude;
    private Long hubAdminId;
    private boolean isMegaHub;
  }

  public static CreateHubResDTO from(Hub hub) {
    HubDTO hubDTO = HubDTO.builder()
        .hubId(hub.getHubId())
        .HubName(hub.getHubName())
        .hubAddress(hub.getHubAddress())
        .hubLatitude(hub.getHubLatitude())
        .hubLongitude(hub.getHubLongitude())
        .hubAdminId(hub.getHubAdminId())
        .isMegaHub(hub.isMegaHub())
        .build();

    return CreateHubResDTO.builder()
        .hubDTO(hubDTO)
        .build();
  }
}
