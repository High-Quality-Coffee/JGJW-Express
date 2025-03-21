package com.zgzg.ai.presentation.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponseDTO {

	private UUID id;
	private String receiverId;
	private String messageTitle;
	private String orderNumber;
	private String originHub;
	private String currentLocation;
	private String finalDestination;
	private String estimatedDeliveryTime;
	private String messageContent;
	private LocalDateTime sentAt;
	private String finalDeliveryStartTime;
	private boolean isDelay;
	private String delayReason;
}
