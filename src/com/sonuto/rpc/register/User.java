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
}
