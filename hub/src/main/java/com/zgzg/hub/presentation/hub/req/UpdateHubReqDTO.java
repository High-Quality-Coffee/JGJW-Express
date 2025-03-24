package com.zgzg.hub.presentation.hub.req;

import com.zgzg.hub.util.annotation.NullAndNotEmpty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHubReqDTO {

  @Valid
  private HubDTO hubDTO;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class HubDTO {

    @NullAndNotEmpty(message = "공백은 입력할 수 있습니다.")
    private String hubName;

    @NullAndNotEmpty(message = "공백은 입력할 수 있습니다.")
    private String hubAddress;

    @NullAndNotEmpty(message = "공백은 입력할 수 있습니다.")
    private String hubLatitude;

    @NullAndNotEmpty(message = "공백은 입력할 수 있습니다.")
    private String hubLongitude;

    @Min(value = 1, message = "허브 관리자는 1이상의 값을 입력해야 합니다.")
    private Long hubAdminId;

    private Boolean megaHubStatus;

    private UUID parentHubId;
  }
}
