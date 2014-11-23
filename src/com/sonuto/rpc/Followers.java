package com.sonuto.rpc;

public class Followers  extends RPCHandler{
	public void show(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("followers/");
		setMethod("show");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
}
