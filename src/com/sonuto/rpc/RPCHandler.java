package com.sonuto.rpc;
import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams.Versions;
import android.os.AsyncTask;

public abstract class RPCHandler extends AsyncTask<String, String, String> {

	private final String SERVER_ADDRESS = "http://192.168.0.103/sportzweb/rpc/";
	//private final String SERVER_ADDRESS = "http://172.17.132.122/webinventory/androidrpc/";
	
	
	private JSONRPCClient client ;
	private Object params[];
	private ICallBack callBack;
	private String method;
	private RPCReturnType returnType;
	private String controllerPath;

	
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
	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}
	
//	public RPCHandler(String url, String method, RPCReturnType returnType, ICallBack callBack, Object ... params){
//		client = JSONRPCClient.create(url, Versions.VERSION_2);
//        client.setConnectionTimeout(2000);
//        client.setSoTimeout(2000);
//        
//        this.params = params;
//        this.method = method;
//        this.returnType = returnType;
//        
//	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		client = JSONRPCClient.create(SERVER_ADDRESS + controllerPath, Versions.VERSION_2);
        client.setConnectionTimeout(2000);
        client.setSoTimeout(2000);
		try {
			if(returnType == RPCReturnType.INTEGER){
				callBack.callBackResultHandler(client.callInt(method, this.params));
			}
			else if(returnType == RPCReturnType.LONG){
				callBack.callBackResultHandler(client.callLong(method, this.params));
			}
			else if(returnType == RPCReturnType.STRING){
				String res = client.callString(method, this.params);
				callBack.callBackResultHandler(res);
			}
			else if(returnType == RPCReturnType.DOUBLE){
				callBack.callBackResultHandler(client.callDouble(method, this.params));
			}
			else{
				callBack.callBackResultHandler(client.call(method, this.params));
			}
		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			callBack.callBackErrorHandler(e.getMessage());
		}
		return null;
	}

}
