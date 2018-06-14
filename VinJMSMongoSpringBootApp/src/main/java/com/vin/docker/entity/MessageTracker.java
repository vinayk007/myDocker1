package com.vin.docker.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tracker")
public class MessageTracker {

	@Id
	private String msgId;
	private String msgStatus;
	
	public MessageTracker(String msgId, String msgStatus) {
		super();
		this.msgId = msgId;
		this.msgStatus = msgStatus;
	}
	
}
