package com.zgzg.hub.domain.repository;

import com.zgzg.hub.domain.entity.Route;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {

  @Query("SELECT r FROM Route r where r.parentId IN (SELECT e.routeId FROM Route e "
      + "where e.startHubId = :startId AND e.endHubId = :endId)")
  List<Route> findByStepRoute(@Param("startId") UUID startId, @Param("endId") UUID endId);

  void deleteAll();
}
