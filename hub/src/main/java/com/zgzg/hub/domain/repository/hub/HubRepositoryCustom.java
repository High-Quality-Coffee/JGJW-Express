package com.zgzg.hub.domain.repository.hub;

import com.zgzg.hub.domain.entity.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRepositoryCustom {

  Page<Hub> searchByHubName(String keyword, Pageable pageable);
}
