package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.payload;

public class MessageResponse {
	private String message;

	public MessageResponse(String message) {
	    this.message = message;
	  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
