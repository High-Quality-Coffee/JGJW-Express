package com.zgzg.hub.domain.entity;

import com.zgzg.common.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "p_hub", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"hub_name", "deleted_at"})
})
@Builder
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Hub extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "uuid", nullable = false, updatable = false)
  private UUID hubId;

  @Setter
  @Column(nullable = false)
  private String hubName;

  @Setter
  @Column(nullable = false)
  private String hubAddress;

  @Setter
  @Column(nullable = false)
  private String hubLatitude; // 위도

  @Setter
  @Column(nullable = false)
  private String hubLongitude; // 경도

  @Setter
  @Builder.Default
  private boolean isMegaHub = false;

  @Setter
  private Long hubAdminId;

  @Setter
  private UUID parentHubId;
}
