package com.sonuto;

public class Config {
	/** Whether or not to include logging statements in the application. */
    public final static boolean LOGGING 					= @CONFIG.LOGGING@;
    public final static String SERVER_ROOT_URL 				= "@CONFIG.SERVER_ROOT_URL@";
    public final static String RPC_CONTROLLER_PATH 			= "@CONFIG.RPC_CONTROLLER_PATH@";
	public final static String SERVICE_CONTROLLER_PATH 		= "@CONFIG.SERVICE_CONTROLLER_PATH@";
	public final static String PROFILE_PIC_DIR 				= "@CONFIG.PROFILE_PIC_DIR@";
	public final static String CREATE_BLOG_DIR 				= "@CONFIG.CREATE_BLOG_DIR@";
	public final static String SOCKET_SERVER_URL 			= "@CONFIG.SOCKET_SERVER_URL@";
	public final static String PROFILE_PIC_DIR_MD 			= "@CONFIG.PROFILE_PIC_DIR_MD@";
	public final static String PROFILE_PIC_DIR_LG 			= "@CONFIG.PROFILE_PIC_DIR_LG@";
}