package com.sonuto.rpc.register;

import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class Tournaments extends RPCHandler{

	public void getTournamentsList(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_xstream_banter/");
		setMethod("get_all_tournaments");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}

}
