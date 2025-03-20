package com.zgzg.hub.domain.repository;

import com.zgzg.hub.domain.entity.Route;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {


}
