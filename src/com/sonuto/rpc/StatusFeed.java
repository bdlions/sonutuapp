package com.sonuto.rpc;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class StatusFeed extends RPCHandler{
	public void postStatus(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("status_feed/");
		setMethod("post_status");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	


	
}
