package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class NewsApp extends RPCHandler{
	public void getHomePageData(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_news/");
		setMethod("load_news_app");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void getNewsList(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_news/");
		setMethod("get_news_list");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
}
