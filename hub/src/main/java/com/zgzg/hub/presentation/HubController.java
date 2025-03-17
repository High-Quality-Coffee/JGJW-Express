package com.zgzg.hub.presentation;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.hub.application.HubService;
import com.zgzg.hub.application.res.CreateHubResDTO;
import com.zgzg.hub.presentation.req.CreateHubReqDTO;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubController {

  private final HubService hubService;

  @PostMapping
  public ResponseEntity<ApiResponseData<CreateHubResDTO>> createHub(
      @Valid @RequestBody CreateHubReqDTO createHubReqDTO) {

    CreateHubResDTO responseDto = hubService.createHub(createHubReqDTO);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{hubId}")
        .buildAndExpand(responseDto.getHubDTO().getHubId())
        .toUri();
    return ResponseEntity.created(uri).body(ApiResponseData.success(responseDto));
  }
}
