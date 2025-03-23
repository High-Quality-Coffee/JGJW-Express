package com.zgzg.hub.domain.entity;

import com.zgzg.common.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "p_route")
@Builder
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Route extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "uuid", nullable = false, updatable = false)
  private UUID routeId;

  @Column(nullable = false)
  private UUID startHubId;

  @Column(nullable = false)
  private String startHubName;

  @Column(nullable = false)
  private UUID endHubId;

  @Column(nullable = false)
  private String endHubName;

  @Setter
  private Integer interTime;

  @Setter
  private Integer interDistance;

  private int sequence;

  @Setter
  private UUID parentId;
}
