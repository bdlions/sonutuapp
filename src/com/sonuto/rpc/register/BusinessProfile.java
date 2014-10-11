package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class BusinessProfile extends RPCHandler{
	public void regiserBusinessProfile(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerPath("auth/");
		setMethod("register");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void getBusinessProfileData(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerPath("business_profile/");
		setMethod("get_business_profile_registration_data");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
}
