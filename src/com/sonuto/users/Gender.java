package com.sonuto.users;

public enum Gender {
	MALE(1), FEMALE(2);
	private final int value;
	Gender(int value){this.value = value;}
	public int getValue() { return value; }
}
