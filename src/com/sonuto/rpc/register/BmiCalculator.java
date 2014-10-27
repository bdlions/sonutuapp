package com.sonuto.rpc.register;


import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.RPCHandler;
import com.sonuto.rpc.RPCReturnType;

public class BmiCalculator extends RPCHandler{
	
	public void getBmiHWData(ICallBack callBack, Object...params){
		setCallBack(callBack);
		
		setControllerName("app_bmi_calculator/");
		setMethod("get_bmi_calculator_data");
		
		setParams(params);
		setReturnType(RPCReturnType.JSON_OBJECT);
		execute();
	}
}
