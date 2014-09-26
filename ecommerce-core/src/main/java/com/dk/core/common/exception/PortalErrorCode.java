package com.dk.core.common.exception;

/**
 * Portal模块错误码
 */
public interface PortalErrorCode extends IErrorCode
{

	static final String	USER_LOGIN_NAME_EXIST_ERROR				= "P0001";	// 登录名冲突
	static final String	USER_EMAIL_EXIST_ERROR					= "P0002";	// 邮箱冲突
	static final String	USER_LOGIN_NAME_NOT_EXIST_ERROR			= "P0003";	// 登录名不存在
	static final String	USER_OLD_PASSWORD_NOT_CORRECT_ERROR		= "P0004";	// 旧密码错误
	static final String	USER_PASSWORD_NOT_CORRECT_ERROR			= "P0005";	// 密码错误
	static final String	USER_PASSWORD_ERROR_TIMES_EXCEED_ERROR	= "P0006";	// 密码最大错误超过系统配置值
	static final String	USER_LOGIN_NAME_EMPTY_ERROR				= "P0007";	// 登录名为空
	static final String	USER_PASSWORD_EMPTY_ERROR				= "P0008";	// 密码为空
	static final String	USER_EMAIL_EMPTY_ERROR					= "P0009";	// Email为空
	static final String	USER_MOBILE_PHONE_EXIST_ERROR			= "P0010";	//移动电话已存在
	
	static final String	USER_NOT_ACTIVE_ERROR				= "P0011";	// 未激活

}
