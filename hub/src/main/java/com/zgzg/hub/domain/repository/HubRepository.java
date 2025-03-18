package com.zgzg.hub.domain.repository;

import com.zgzg.hub.domain.entity.Hub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository extends JpaRepository<Hub, UUID> {

  boolean existsByHubName(String name);

}
