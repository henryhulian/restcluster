package com.restcluster.superest.common;

public class ServerConfigCanstant {
	
	/**
	 * 端口偏移
	 * 默认 0
	 */
	public static final String PORT_OFFSET="PORT_OFFSET";

	/**
	 * 公共访问端口
	 * 默认 8081
	 */
	public static final String PUBLIC_PORT="PUBLIC_PORT";
	
	/**
	 * 公共端口绑定地址
	 */
	public static final String PUBLIC_INTERFACE="PUBLIC_INTERFACE";
	
	/**
	 * 管理端口
	 * 默认 8082
	 */
	public static final String ADMIN_PORT="ADMIN_PORT";
	
	/**
	 * 管理端口绑定地址
	 * 默认 127.0.0.1
	 */
	public static final String ADMIN_INTERFACE="ADMIN_INTERFACE";
	
	
	public static final String SERVER_IO_THREAD="SERVER_IO_THREAD";
	public static final String SERVER_IO_MAX_WORKER="SERVER_IO_MAX_WORKER";
	
	/**
	 * 会话加密密钥
	 * 必须配置
	 */
	public static final String SESSION_KEY="SESSION_KEY";
	
	/**
	 * 缓存配置文件路径
	 */
	public static final String CACHE_CONFIG_FILE_PATH = "CACHE_CONFIG_FILE_PATH";
	
	/**
	 * 数据库文件路径
	 */
	public static final String DBD_CONFIG_FILE_PATH = "DBD_CONFIG_FILE_PATH";
	
	/**
	 * 静态资源路径
	 */
	public static final String STATIC_RESOURCE_PATH = "STATIC_RESOURCE_PATH";
}
