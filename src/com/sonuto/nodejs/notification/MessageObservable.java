package com.sonuto.nodejs.notification;
public interface MessageObservable {
	public void registerObserver(MessageObserver observer);
	public void removeObserver(MessageObserver observer);
	public void notifyObservers();
}