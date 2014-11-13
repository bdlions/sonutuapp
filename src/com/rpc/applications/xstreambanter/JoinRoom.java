package com.rpc.applications.xstreambanter;

import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class JoinRoom extends RPCHandler{
	
	public void getPreviousCode(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_xstream_banter/");
		setMethod("join_chat_room");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}

}