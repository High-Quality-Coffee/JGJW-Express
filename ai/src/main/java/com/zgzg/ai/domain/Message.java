package com.zgzg.ai.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_slack_message")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "receiver_id", length = 100, nullable = false)
	private String receiverId;

	@Column(name = "message_title", length = 255)
	private String messageTitle;

	@Column(name = "order_number", length = 50)
	private String orderNumber;

	@Column(name = "origin_hub", length = 100)
	private String originHub;

	@Column(name = "current_location", length = 100)
	private String currentLocation;

	@Column(name = "final_destination", length = 100)
	private String finalDestination;

	@Column(name = "estimated_delivery_time", length = 100)
	private String estimatedDeliveryTime;

	@Column(name = "message_content", nullable = false, columnDefinition = "TEXT")
	private String messageContent;

	@Column(name = "sender_id", length = 100)
	private String senderId;

	@Column(name = "sent_at", nullable = false)
	private LocalDateTime sentAt;

	public Message(String messageContent, String messageTitle, String orderNumber, String originHub,
		String currentLocation, String finalDestination, String estimatedDeliveryTime) {
		this.messageContent = messageContent;
		this.messageTitle = messageTitle;
		this.orderNumber = orderNumber;
		this.originHub = originHub;
		this.currentLocation = currentLocation;
		this.finalDestination = finalDestination;
		this.estimatedDeliveryTime = estimatedDeliveryTime;

	}

}
