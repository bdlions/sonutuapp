package com.sonutu.constants;

public enum SHARE_TYPE {
	OTHER_STATUS(1);
	private int value;
	SHARE_TYPE(int value){
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}
}
