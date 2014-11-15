package com.sonuto.rpc;

public class AppBMICalculator extends RPCHandler{
	
	public void getBMIQuestionList(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_bmi_calculator/");
		setMethod("get_bmi_question_list");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
}

