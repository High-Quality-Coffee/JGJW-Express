package com.zgzg.user.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubResDTO implements Serializable {

    private HubDTO hubDTO;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HubDTO implements Serializable {

        private UUID hubId;
        private String hubName;
        private String hubAddress;
        private String hubLatitude;
        private String hubLongitude;
        private Long hubAdminId;

        private boolean megaHubStatus;
        private UUID parentHubId;

    }
}

