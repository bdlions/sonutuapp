package com.sonuto.rpc;

public class BusinessProfile extends RPCHandler{
	
	public void getBusinessProfileRegistrationData(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("business_profile/");
		setMethod("get_business_profile_registration_data");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void createBusinessProfile(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("business_profile/");
		setMethod("create_business_profile");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void getBusinessProfileInfo(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("business_profile/");
		setMethod("get_business_profile_info");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void updateBusinessProfileInfo(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("business_profile/");
		setMethod("update_business_profile_info");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
}

