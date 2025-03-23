package com.zgzg.hub.application.hub;

import static com.zgzg.common.response.Code.EXIST_HUB_NAME;
import static com.zgzg.common.response.Code.FIRST_CHANGE_CONNECTED_HUBS;
import static com.zgzg.common.response.Code.HUB_NOT_FOUND;
import static com.zgzg.common.response.Code.NORMAL_HUB_MUST_HAVE_PARENT_HUB;
import static com.zgzg.common.response.Code.NOT_CHANGES_HUB;
import static com.zgzg.common.response.Code.PARENT_HUB_NOT_FOUND;

import com.zgzg.common.exception.BaseException;
import com.zgzg.hub.application.hub.res.CreateHubResDTO;
import com.zgzg.hub.application.hub.res.HubResDTO;
import com.zgzg.hub.application.hub.res.PageHubsResDTO;
import com.zgzg.hub.application.hub.res.UpdateHubResDTO;
import com.zgzg.hub.domain.entity.Hub;
import com.zgzg.hub.domain.repository.hub.HubRepository;
import com.zgzg.hub.presentation.hub.req.CreateHubReqDTO;
import com.zgzg.hub.presentation.hub.req.UpdateHubReqDTO;
import com.zgzg.hub.util.event.UpdateRouteEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    } else {
      if (!createHubReqDTO.getHubDTO().getIsMegaHub()) {
        throw new BaseException(NORMAL_HUB_MUST_HAVE_PARENT_HUB);
      }
    }

    // TODO : 허브 관리자 검증

    Hub hub = hubRepository.save(CreateHubReqDTO.toEntity(createHubReqDTO));
    eventPublisher.publishEvent(new UpdateRouteEvent(this));

    return CreateHubResDTO.from(hub);
  }

  @Transactional
  @CacheEvict(cacheNames = "hubCache", key = "args[0]")
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

    if (updateHubReqDTO.getHubDTO() == null) {
      throw new BaseException(NOT_CHANGES_HUB);
    }

    if (ValidateUpdateHub(hub, updateHubReqDTO)) {
      eventPublisher.publishEvent(new UpdateRouteEvent(this));
    }

    return UpdateHubResDTO.from(hub);
  }

  @Transactional
  @CacheEvict(cacheNames = "hubCache", key = "args[0]")
  public void deleteHub(UUID hubId, String username) {
    Hub hub = hubRepository.findByHubId(hubId)
        .orElseThrow(() -> new BaseException(HUB_NOT_FOUND));

    hub.softDelete(username);
    eventPublisher.publishEvent(new UpdateRouteEvent(this));

  }

  @Cacheable(cacheNames = "hubCache", key = "args[0]")
  public HubResDTO getHub(UUID hubId) {
    Hub hub = hubRepository.findByHubId(hubId)
        .orElseThrow(() -> new BaseException(HUB_NOT_FOUND));

    return HubResDTO.from(hub);
  }

  public PageHubsResDTO searchHub(String keyword, Pageable pageable) {
    Page<Hub> hubs = hubRepository.searchByHubName(keyword, pageable);
    return PageHubsResDTO.from(hubs);
  }

  private boolean ValidateUpdateHub(Hub hub, UpdateHubReqDTO updateHubReqDTO) {
    boolean check = false;

    if (updateHubReqDTO.getHubDTO().getHubName() != null) {
      hub.setHubName(updateHubReqDTO.getHubDTO().getHubName());
      check = true;
    }

    if (updateHubReqDTO.getHubDTO().getHubAddress() != null) {
      hub.setHubName(updateHubReqDTO.getHubDTO().getHubAddress());
      check = true;
    }

    if (updateHubReqDTO.getHubDTO().getHubLatitude() != null) {
      hub.setHubName(updateHubReqDTO.getHubDTO().getHubLatitude());
      check = true;
    }

    if (updateHubReqDTO.getHubDTO().getHubLongitude() != null) {
      hub.setHubName(updateHubReqDTO.getHubDTO().getHubLongitude());
      check = true;
    }

    hub.setHubAdminId(updateHubReqDTO.getHubDTO().getHubAdminId());

    if (updateHubReqDTO.getHubDTO().getIsMegaHub() != null) {
      if (!updateHubReqDTO.getHubDTO().getIsMegaHub() && // 중앙 -> 자식
          hubRepository.existsByParentHubId(hub.getHubId())) {
        throw new BaseException(FIRST_CHANGE_CONNECTED_HUBS);
      }
      hub.setMegaHub(updateHubReqDTO.getHubDTO().getIsMegaHub());
      check = true;
    }

    /**
     * 변경할 부모가 NULL 이 아닐 때, 존재하지 않는 ID면 예외
     * 변경할 부모가 NULL 일 때, 내가 NORMAL 허브면 에외
     */
    if (updateHubReqDTO.getHubDTO().getParentHubId() != null) {
      if (!hubRepository.existsByHubId(updateHubReqDTO.getHubDTO().getParentHubId())) {
        throw new BaseException(PARENT_HUB_NOT_FOUND);
      }
      hub.setParentHubId(updateHubReqDTO.getHubDTO().getParentHubId());
      check = true;
    } else {
      if (hub.isMegaHub()) {
        throw new BaseException(NORMAL_HUB_MUST_HAVE_PARENT_HUB);
      }
      hub.setParentHubId(null);
      check = true;
    }

    return check;
  }
}
