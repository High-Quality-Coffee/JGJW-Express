package com.zgzg.hub.domain.repository.hub;

import com.zgzg.hub.domain.entity.Hub;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository extends JpaRepository<Hub, UUID>, HubRepositoryCustom {

  boolean existsByHubName(String name);

  boolean existsByHubId(UUID id);

  Optional<Hub> findByHubId(UUID hubId);

  boolean existsByParentHubId(UUID parentHubId);
//
//  Page<Hub> searchByHubName(String keyword, Pageable pageable);
}
