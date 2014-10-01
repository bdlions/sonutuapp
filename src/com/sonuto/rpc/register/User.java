package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class User extends RPCHandler{
	public void regiserUser(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerPath("auth/");
		setMethod("register");
		
		//super.setControllerPath("qprovider/");
		//super.setMethod("abc");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void loginUser(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerPath("auth/");
		setMethod("login");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void updateGenderUser(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerPath("auth/");
		setMethod("update_gender");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void updateUsersProfileInfo(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerPath("auth/");
		setMethod("update_profile_information");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void updateDateOfBirth(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerPath("auth/");
		setMethod("update_dob");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
}
