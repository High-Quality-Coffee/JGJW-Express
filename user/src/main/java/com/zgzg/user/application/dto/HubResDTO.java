package com.zgzg.user.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubResDTO {
    private UUID hubId;
    private String HubName;
    private String hubAddress;
    private String hubLatitude;
    private String hubLongitude;
    private Long hubAdminId;
    private boolean isMegaHub;
    private UUID parentHubId;
}
