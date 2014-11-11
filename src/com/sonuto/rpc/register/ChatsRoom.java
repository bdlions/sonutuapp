package com.sonuto.rpc.register;

import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class ChatsRoom extends RPCHandler{
	
	public void enterRoom(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_xstream_banter/");
		setMethod("enter_chat_room");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}

}
