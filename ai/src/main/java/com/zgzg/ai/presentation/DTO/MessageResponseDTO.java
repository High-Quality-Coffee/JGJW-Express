package com.zgzg.ai.presentation.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import com.zgzg.ai.domain.Message;

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

	public static MessageResponseDTO toDto(Message message){
		return MessageResponseDTO.builder()
			.id(message.getId())
			.receiverId(message.getReceiverId())
			.messageTitle(message.getMessageTitle())
			.orderNumber(message.getOrderNumber())
			.originHub(message.getOriginHub())
			.currentLocation(message.getCurrentLocation())
			.finalDestination(message.getFinalDestination())
			.estimatedDeliveryTime(message.getEstimatedDeliveryTime())
			.messageContent(message.getMessageContent())
			.sentAt(message.getSentAt())
			.finalDeliveryStartTime(message.getFinalDeliveryStartTime())
			.isDelay(message.isDelay())
			.delayReason(message.getDelayReason())
			.build();
	}
}
