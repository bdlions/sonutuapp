package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class StatusFeed extends RPCHandler{
	public void get_statuses(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("status_feed/");
		setMethod("get_statuses");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	

	
}
