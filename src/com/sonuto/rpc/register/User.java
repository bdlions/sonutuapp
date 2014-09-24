package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class User extends RPCHandler{
	public void regiserUser(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerPath("user_registration_login/");
		setMethod("userRegistration");
		
		//super.setControllerPath("qprovider/");
		//super.setMethod("abc");
		
		setParams(params);
		setReturnType(RPCReturnType.STRING);
		execute();
	}
}
