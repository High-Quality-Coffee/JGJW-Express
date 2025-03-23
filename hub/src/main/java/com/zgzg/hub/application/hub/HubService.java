package com.zgzg.hub.application.hub;

import static com.zgzg.common.response.Code.EXIST_HUB_NAME;
import static com.zgzg.common.response.Code.HUB_NOT_FOUND;
import static com.zgzg.common.response.Code.PARENT_HUB_NOT_FOUND;

import com.zgzg.common.exception.BaseException;
import com.zgzg.hub.application.hub.res.CreateHubResDTO;
import com.zgzg.hub.application.hub.res.HubResDTO;
import com.zgzg.hub.application.hub.res.PageHubsResDTO;
import com.zgzg.hub.application.hub.res.UpdateHubResDTO;
import com.zgzg.hub.domain.entity.Hub;
import com.zgzg.hub.domain.repository.HubRepository;
import com.zgzg.hub.presentation.hub.req.CreateHubReqDTO;
import com.zgzg.hub.presentation.hub.req.UpdateHubReqDTO;
import com.zgzg.hub.util.event.UpdateRouteEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubService {

  private final HubRepository hubRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public CreateHubResDTO createHub(CreateHubReqDTO createHubReqDTO) {
    boolean existsByHubName = hubRepository.existsByHubName(
        createHubReqDTO.getHubDTO().getHubName());

    if (existsByHubName) {
      throw new BaseException(EXIST_HUB_NAME);
    }

    if (createHubReqDTO.getHubDTO().getParentHubId() != null) {
      boolean existHub = hubRepository.existsByHubId(createHubReqDTO.getHubDTO().getParentHubId());
      if (!existHub) {
        throw new BaseException(PARENT_HUB_NOT_FOUND);
      }
    }
    // TODO : 허브 관리자 검증
    Hub hub = hubRepository.save(CreateHubReqDTO.toEntity(createHubReqDTO));

    eventPublisher.publishEvent(new UpdateRouteEvent(this));

    return CreateHubResDTO.from(hub);
  }

  @Transactional
  public UpdateHubResDTO updateHub(UUID hubId, UpdateHubReqDTO updateHubReqDTO) {
    Hub hub = hubRepository.findByHubId(hubId)
        .orElseThrow(() -> new BaseException(HUB_NOT_FOUND));

    if (updateHubReqDTO.getHubDTO().getParentHubId() != null) {
      boolean existHub = hubRepository.existsByHubId(updateHubReqDTO.getHubDTO().getParentHubId());
      if (!existHub) {
        throw new BaseException(PARENT_HUB_NOT_FOUND);
      }
    }

    // TODO : 허브 관리자 검증
    ValidateUpdateHub(hub, updateHubReqDTO);
    eventPublisher.publishEvent(new UpdateRouteEvent(this));
    return UpdateHubResDTO.from(hub);
  }

  @Transactional
  public void deleteHub(UUID hubId, String username) {
    Hub hub = hubRepository.findByHubId(hubId)
        .orElseThrow(() -> new BaseException(HUB_NOT_FOUND));

    hub.softDelete(username);
  }

  public HubResDTO getHub(UUID hubId) {
    Hub hub = hubRepository.findByHubId(hubId)
        .orElseThrow(() -> new BaseException(HUB_NOT_FOUND));

    eventPublisher.publishEvent(new UpdateRouteEvent(this));
    return HubResDTO.from(hub);
  }

  public PageHubsResDTO searchHub(String keyword, Pageable pageable) {
    Page<Hub> hubs = hubRepository.findByHubNameContaining(keyword, pageable);
    return PageHubsResDTO.from(hubs);
  }

  private void ValidateUpdateHub(Hub hub, UpdateHubReqDTO updateHubReqDTO) {
    if (updateHubReqDTO.getHubDTO() == null) {
      return;
    }

    if (updateHubReqDTO.getHubDTO().getHubName() != null) {
      hub.setHubName(updateHubReqDTO.getHubDTO().getHubName());
    }

    if (updateHubReqDTO.getHubDTO().getHubAddress() != null) {
      hub.setHubName(updateHubReqDTO.getHubDTO().getHubAddress());
    }

    if (updateHubReqDTO.getHubDTO().getHubLatitude() != null) {
      hub.setHubName(updateHubReqDTO.getHubDTO().getHubLatitude());
    }

    if (updateHubReqDTO.getHubDTO().getHubLongitude() != null) {
      hub.setHubName(updateHubReqDTO.getHubDTO().getHubLongitude());
    }

    hub.setHubAdminId(updateHubReqDTO.getHubDTO().getHubAdminId());

    if (updateHubReqDTO.getHubDTO().getIsMegaHub() != null) {
      hub.setMegaHub(updateHubReqDTO.getHubDTO().getIsMegaHub());
    }

    hub.setParentHubId(updateHubReqDTO.getHubDTO().getParentHubId());
  }
}
