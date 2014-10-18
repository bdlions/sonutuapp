package com.sonuto.rpc;
import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams.Versions;

import com.sonuto.Config;

import android.os.AsyncTask;

public abstract class RPCHandler extends AsyncTask<String, String, Object> {
	private JSONRPCClient client ;
	private Object params[];
	private ICallBack callBack;
	private String method;
	private RPCReturnType returnType;
	private String controllerName;

	
	public void setReturnType(RPCReturnType returnType) {
		this.returnType = returnType;
	}
	public void setCallBack(ICallBack callBack) {
		this.callBack = callBack;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setParams(Object... params) {
		this.params = params;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	@Override
	protected Object doInBackground(String... params) {
		// TODO Auto-generated method stub
		client = JSONRPCClient.create(Config.RPC_CONTROLLER_PATH + controllerName, Versions.VERSION_2);
        client.setConnectionTimeout(10000);
        client.setSoTimeout(10000);
        Object result = null;
		try {
			
			if(returnType == RPCReturnType.INTEGER){
				result = client.callInt(method, this.params);
			}
			else if(returnType == RPCReturnType.LONG){
				result = client.callLong(method, this.params);
			}
			else if(returnType == RPCReturnType.STRING){
				result = client.callString(method, this.params);
			}
			else if(returnType == RPCReturnType.DOUBLE){
				result = client.callDouble(method, this.params);
			}
			else if(returnType == RPCReturnType.JSON_OBJECT){
				result = client.callJSONObject(method, this.params);
			}else if(returnType == RPCReturnType.JSON_ARRAY){
				result = client.callJSONArray(method, this.params);
			}
			else{
				result = client.call(method, this.params);
			}
			
		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			callBack.callBackErrorHandler(e.getMessage());
		}
		return result;
	}
	@Override
   protected void onPostExecute(Object result) {
		callBack.callBackResultHandler(result);
   }

}
