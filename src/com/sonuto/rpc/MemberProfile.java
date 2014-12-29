package com.sonuto.rpc;

public class MemberProfile extends RPCHandler{
	
	public void getMemberProfileInfo(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("member_profile/");
		setMethod("get_member_profile_info");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void getSpecialInterests(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("member_profile/");
		setMethod("get_special_interests");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	} 
	
	public void storeSpecialInterests(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("member_profile/");
		setMethod("store_special_interests");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	} 
}

