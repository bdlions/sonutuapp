package com.sonuto.rpc;

public class Share extends RPCHandler{
	
	public void shareStatus(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("share/");
		setMethod("share_status");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
}

