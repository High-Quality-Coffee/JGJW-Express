package com.zgzg.hub.application;

import static com.zgzg.common.response.Code.EXIST_HUB_NAME;

import com.zgzg.common.exception.BaseException;
import com.zgzg.hub.application.res.CreateHubResDTO;
import com.zgzg.hub.domain.entity.Hub;
import com.zgzg.hub.domain.repository.HubRepository;
import com.zgzg.hub.presentation.req.CreateHubReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubService {

  private final HubRepository hubRepository;

  @Transactional
  public CreateHubResDTO createHub(CreateHubReqDTO createHubReqDTO) {
    boolean exist = hubRepository.existsByHubName(createHubReqDTO.getHubDTO().getHubName());

    if (exist) {
      throw new BaseException(EXIST_HUB_NAME);
    }
    Hub hub = hubRepository.save(CreateHubReqDTO.toEntity(createHubReqDTO));
    return CreateHubResDTO.from(hub);
  }
}
