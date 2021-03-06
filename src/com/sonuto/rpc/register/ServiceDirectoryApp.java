package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class ServiceDirectoryApp extends RPCHandler{
	public void getServiceCategory(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_service_directory/");
		setMethod("get_services_by_city_or_postcode");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void postServiceComments(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_comments/");
		setMethod("post_comment");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void serviceShare(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_comments/");
		setMethod("share_recipe");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
}
