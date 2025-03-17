package com.zgzg.hub.presentation.req;

import com.zgzg.hub.domain.entity.Hub;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHubReqDTO {

  @Valid
  private HubDTO hubDTO;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class HubDTO {

    @NotBlank(message = "허브명은 필수로 입력해야 합니다.")
    private String hubName;

    @NotBlank(message = "허브 주소는 필수로 입력해야 합니다.")
    private String hubAddress;

    @NotBlank(message = "위도는 필수로 입력해야 합니다.")
    private String hubLatitude;

    @NotBlank(message = "경도는 필수로 입력해야 합니다.")
    private String hubLongitude;

    @NotNull(message = "허브 관리자는 필수로 입력해야 합니다.")
    private Long hubAdminId;

    private Boolean isMegaHub;
  }

  public static Hub toEntity(CreateHubReqDTO createHubReqDTO) {
    return Hub.builder()
        .hubName(createHubReqDTO.getHubDTO().getHubName())
        .hubAddress(createHubReqDTO.getHubDTO().getHubAddress())
        .hubLatitude(createHubReqDTO.getHubDTO().getHubLatitude())
        .hubLongitude(createHubReqDTO.getHubDTO().getHubLongitude())
        .hubAdminId(createHubReqDTO.getHubDTO().getHubAdminId())
        .isMegaHub(createHubReqDTO.getHubDTO().getIsMegaHub())
        .build();
  }
}
