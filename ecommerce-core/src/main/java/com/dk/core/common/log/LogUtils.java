package com.dk.core.common.log;

import com.dk.core.common.exception.CoreException;
import com.dk.core.common.utils.GeneralUtils;
import com.dk.core.common.log.impl.Log4jImpl;

/**
 * 日志工具类LogUtils
 */
public class LogUtils {

	public final static String MODULE_VECO = "veco";
	public final static String MODULE_PORTAL = "portal";
	public final static String MODULE_APPSERVER = "appserver";
	public final static String MODULE_WEICHAT = "weichat";

	// Log实现类
	private static Log serviceLogger;
	private static Log systemLogger;

	private static LogUtils instance = new LogUtils();

	public static LogUtils getInstance() {
		return instance;
	}

	static {
		systemLogger = new Log4jImpl("system");
		serviceLogger = new Log4jImpl("service");
	}

	/**
	 * 记录业务的debug日志
	 */
	public void debugService(String module, String msg, Object... args) {
		serviceLogger.debug(this.buildMsg(module, msg, args));
	}

	/**
	 * 记录业务的info日志
	 */
	public void infoService(String module, String msg, Object... args) {
		serviceLogger.info(this.buildMsg(module, msg, args));
	}

	/**
	 * 记录业务的错误日志
	 */
	public void errorService(String module, String msg, Object... args) {
		serviceLogger.error(this.buildMsg(module, msg, args));
	}

	/**
	 * 记录业务错误日志
	 */
	public void errorService(String module, Exception ex, Object... args) {
		StringBuffer logMsgBuffer = new StringBuffer(this.buildMsg(module, ex
				.getMessage(), args));

		if (ex instanceof CoreException) {
			logMsgBuffer.append("Error Code: ");
			logMsgBuffer.append(((CoreException) ex).getErrorCode());
			logMsgBuffer.append("Error message: ");
			logMsgBuffer.append(ex.getMessage());
		} else {
			logMsgBuffer.append(ex.getClass().getName());
		}

		serviceLogger.error(logMsgBuffer.toString());
	}

	/**
	 * 记录系统debug日志
	 */
	public void debugSystem(String module, String msg, Object... args) {
		systemLogger.debug(this.buildMsg(module, msg, args));
	}

	/**
	 * 记录系统info日志
	 */
	public void infoSystem(String module, String msg, Object... args) {
		systemLogger.info(this.buildMsg(module, msg, args));
	}

	/**
	 * 记录系统error日志
	 */
	public void errorSystem(String module, String msg, Object... args) {
		systemLogger.error(this.buildMsg(module, msg, args));
	}

	/**
	 * 记录系统error日志
	 */
	public void errorSystem(String module, Throwable t, Object... args) {
		systemLogger.error(this.buildMsg(module, t.getMessage(), args), t);
	}

	/**
	 * 同时记录业务和系统错误日志
	 */
	public void errorServiceSystem(String module, String msg, Exception ex,
			Object... args) {
		errorService(module, msg, args);

		if (ex == null) {
			this.errorSystem(module, msg, args);
		} else {
			this.errorSystem(module, ex, args);
		}
	}

	/**
	 * 组装日志消息体
	 * 
	 * @param module
	 * @param msg
	 * @param args
	 * @return
	 */
	private String buildMsg(String module, String msg, Object... args) {
		StringBuffer logMsgBuffer = new StringBuffer();
		logMsgBuffer.append("|");
		logMsgBuffer.append(module);
		logMsgBuffer.append("|");
		logMsgBuffer.append(msg);

		if (GeneralUtils.isNotNull(args)) {
			logMsgBuffer.append("|arguments:");
			for (Object arg : args) {
				logMsgBuffer.append(arg);
				logMsgBuffer.append(",");
			}
		}
		return logMsgBuffer.toString();
	}

}