package com.zgzg.hub.domain.repository;

import com.zgzg.hub.domain.entity.Hub;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository extends JpaRepository<Hub, UUID> {

  boolean existsByHubName(String name);

  boolean existsByHubId(UUID id);

  Optional<Hub> findByHubId(UUID hubId);

  Page<Hub> findByHubNameContaining(String keyword, Pageable pageable);

}
