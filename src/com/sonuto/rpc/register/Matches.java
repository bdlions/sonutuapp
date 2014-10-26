package com.sonuto.rpc.register;

import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class Matches extends RPCHandler{
	
	public void getMatchesList(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_xstream_banter/");
		setMethod("get_all_matches");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}

}
