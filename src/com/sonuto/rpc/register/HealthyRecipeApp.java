package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class HealthyRecipeApp extends RPCHandler{
	public void getHomePageData(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_healthy_recipe/");
		setMethod("load_recipe_app");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	
	public void getRecipeDetails(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_healthy_recipe/");
		setMethod("get_recipe_info");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void postrecipeComments(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_comments/");
		setMethod("post_comment");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
	public void recipeShare(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_comments/");
		setMethod("share_recipe");

		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
	
}
