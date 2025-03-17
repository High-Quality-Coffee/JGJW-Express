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

@Getter
@Entity
@Table(name = "p_hub")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Hub extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "uuid", nullable = false, updatable = false)
  private UUID hubId;

  @Column(nullable = false)
  private String hubName;

  @Column(nullable = false)
  private String hubAddress;

  @Column(nullable = false)
  private String hubLatitude; // 위도

  @Column(nullable = false)
  private String hubLongitude; // 경도

  @Builder.Default
  private boolean isMegaHub = false;

  private Long hubAdminId;

}
