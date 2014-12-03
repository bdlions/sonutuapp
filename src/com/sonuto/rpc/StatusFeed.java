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
	
	public void deleteStatus(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("status_feed/");
		setMethod("delete_status");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void get_statuses(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("status_feed/");
		setMethod("get_statuses");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void update_status_like(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("status_feed/");
		setMethod("get_statuses");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void postStatusComment(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("status_feed/");
		setMethod("post_feedback");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
}
