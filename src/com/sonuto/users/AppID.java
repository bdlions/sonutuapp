package com.sonuto.users;

public enum AppID {
	XSTREAM_BANTER(1),HEALTHY_RECIPE(2),SERVICE_DIRECTORY(3),NEWS(4),BLOG(5);
	private final int value;
	AppID(int value)
	{this.value = value;}
	public int getValue() { return value; }
}
