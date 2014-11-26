package com.sonuto.nodejs.notification;
public interface MessageObserver {
	public void onUpdate(NodeEvent event, String from, String message);
}
