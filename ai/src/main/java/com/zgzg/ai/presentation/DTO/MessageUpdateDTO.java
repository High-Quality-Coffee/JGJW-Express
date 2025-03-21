package com.zgzg.ai.presentation.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageUpdateDTO {
	private String messageTitle;
	private String originHub;
	private String currentLocation;
	private String finalDestination;
	private String estimatedDeliveryTime;
	private String messageContent;
	private String finalDeliveryStartTime;
	private boolean isDelay;
	private String delayReason;
}
