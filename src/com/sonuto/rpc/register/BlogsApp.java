package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class BlogsApp extends RPCHandler{
	public void getHomePageData(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_blog/");
		setMethod("load_blog_app");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	
	public void getBlogDetails(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_blog/");
		setMethod("get_blog_info");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void getBlogCategoryList(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_blog/");
		setMethod("get_blog_category_list");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void getMyBlogList(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_blog/");
		setMethod("get_my_blogs");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
}
