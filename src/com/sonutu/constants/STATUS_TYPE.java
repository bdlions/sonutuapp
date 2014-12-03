package com.sonutu.constants;

public enum STATUS_TYPE {
	GENERAL(1), PROFILE_PIC_CHANGE(2), IMAGE_ATTACHMENT(3);
	private int value;
	STATUS_TYPE(int value){
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}
}
