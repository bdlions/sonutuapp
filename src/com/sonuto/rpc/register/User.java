package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class User extends RPCHandler{
	public void regiserUser(ICallBack callBack, Object...params){
		super.setCallBack(callBack);
		
		super.setControllerPath("user_registration_login/");
		super.setMethod("userRegistration");
		
		//super.setControllerPath("qprovider/");
		//super.setMethod("abc");
		
		super.setParams(params);
		super.setReturnType(RPCReturnType.STRING);
		execute();
	}
}
