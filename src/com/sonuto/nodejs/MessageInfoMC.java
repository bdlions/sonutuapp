package com.sonuto.nodejs;

public class MessageInfoMC {
	private int senderId;
	private int receiverId;
	private String message;
	private String time;
	private String senderFirstName;
	private String senderLastName;
	private String receiverFirstName;
	private String receiverLastName;
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSenderFirstName() {
		return senderFirstName;
	}
	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}
	public String getSenderLastName() {
		return senderLastName;
	}
	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}
	public String getReceiverFirstName() {
		return receiverFirstName;
	}
	public void setReceiverFirstName(String receiverFirstName) {
		this.receiverFirstName = receiverFirstName;
	}
	public String getReceiverLastName() {
		return receiverLastName;
	}
	public void setReceiverLastName(String receiverLastName) {
		this.receiverLastName = receiverLastName;
	}
	@Override
	public String toString() {
		return "(" + time + ")\n" + senderFirstName+' '+senderLastName
				+":" + message+"\n\n";
	}
	
}
