package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class User extends RPCHandler{
	public void regiserUser(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("auth/");
		setMethod("register");
		
		//super.setControllerName("qprovider/");
		//super.setMethod("abc");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void loginUser(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("auth/");
		setMethod("login");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void countryList(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("auth/");
		setMethod("country_list");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
		
	}
	
	public void updateGenderUser(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("auth/");
		setMethod("update_gender");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void updateUsersProfileInfo(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("auth/");
		setMethod("update_profile_information");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void updateDateOfBirth(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("auth/");
		setMethod("update_dob");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void businessRegistration(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("User_registration_login/");
		setMethod("business_registration");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void userProfile(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("Member_profile/");
		setMethod("show");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void getUserInfo(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("Member_profile/");
		setMethod("info");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
}
