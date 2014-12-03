package com.sonutu.constants;

public enum STATUS_CATEGORY {
	NEWSFEED(1), USER_PROFILE(2), BUSINESS_PROFILE(3), HASHTAG(4);
	private int value;
	STATUS_CATEGORY(int value){
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}
}
