package com.zgzg.hub.presentation.hub;

import static com.zgzg.common.response.Code.CREATED_HUB_SUCCESS;
import static com.zgzg.common.response.Code.GET_HUBS_SUCCESS;
import static com.zgzg.common.response.Code.GET_HUB_SUCCESS;
import static com.zgzg.common.response.Code.UPDATE_HUB_SUCCESS;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.hub.application.hub.HubService;
import com.zgzg.hub.application.hub.res.CreateHubResDTO;
import com.zgzg.hub.application.hub.res.HubResDTO;
import com.zgzg.hub.application.hub.res.PageHubsResDTO;
import com.zgzg.hub.application.hub.res.UpdateHubResDTO;
import com.zgzg.hub.presentation.hub.req.CreateHubReqDTO;
import com.zgzg.hub.presentation.hub.req.UpdateHubReqDTO;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubController {

  private final HubService hubService;

  @Secured("ROLE_MASTER")
  @PostMapping
  public ResponseEntity<ApiResponseData<CreateHubResDTO>> createHub(
      @Valid @RequestBody CreateHubReqDTO createHubReqDTO) {

    CreateHubResDTO responseDto = hubService.createHub(createHubReqDTO);
    URI uri = makeUri(responseDto.getHubDTO().getHubId());

    return ResponseEntity.created(uri).body(ApiResponseData.of(
        CREATED_HUB_SUCCESS.getCode(),
        CREATED_HUB_SUCCESS.getMessage(),
        responseDto));
  }

  @Secured("ROLE_MASTER")
  @PutMapping("/{hubId}")
  public ResponseEntity<ApiResponseData<UpdateHubResDTO>> updateHub(
      @PathVariable("hubId") UUID hubId,
      @Valid @RequestBody UpdateHubReqDTO updateHubReqDTO) {

    return ResponseEntity.ok().body(ApiResponseData.of(
        UPDATE_HUB_SUCCESS.getCode(),
        UPDATE_HUB_SUCCESS.getMessage(),
        hubService.updateHub(hubId, updateHubReqDTO)));
  }

  @Secured("ROLE_MASTER")
  @PatchMapping("/{hubId}")
  public ResponseEntity<Void> deleteHub(
      @PathVariable("hubId") UUID hubId) {

    hubService.deleteHub(hubId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{hubId}")
  public ResponseEntity<ApiResponseData<HubResDTO>> getHub(
      @PathVariable("hubId") UUID hubId) {

    return ResponseEntity.ok().body(ApiResponseData.of(
        GET_HUB_SUCCESS.getCode(),
        GET_HUB_SUCCESS.getMessage(),
        hubService.getHub(hubId)));
  }

  @GetMapping
  public ResponseEntity<ApiResponseData<PageHubsResDTO>> searchHub(
      @RequestParam(value = "keyword") String keyword,
      @RequestParam(value = "page", defaultValue = "0") int page,
      Pageable pageable) {

    int correctedPage = (page > 0) ? page - 1 : 0;
    Pageable correctedPageable = PageRequest.of(
        correctedPage, pageable.getPageSize(), pageable.getSort()
    );

    return ResponseEntity.ok().body(ApiResponseData.of(
        GET_HUBS_SUCCESS.getCode(),
        GET_HUBS_SUCCESS.getMessage(),
        hubService.searchHub(keyword, correctedPageable)));
  }

  private URI makeUri(UUID hubId) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{hubId}")
        .buildAndExpand(hubId)
        .toUri();
  }
}
