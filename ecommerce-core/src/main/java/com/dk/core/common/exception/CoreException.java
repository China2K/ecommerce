package com.dk.core.common.exception;


/**
 * 业务异常类
 */
public abstract class CoreException extends Exception
{
	private static final long	serialVersionUID	= 5534682856432097803L;

	/**
	 * 错误码
	 */
	private String					errorCode;
	private String					errorMsg;
	

	private Object[]			args;

	public CoreException()
	{
		super();
		this.errorCode = IErrorCode.ERROR;
	}

	public CoreException(String errorCode)
	{
		super();
		this.errorCode = errorCode;
	}

	public CoreException(String errorCode, Object[] args)
	{
		super();
		this.errorCode = errorCode;
		this.args = args;
	}

	/**
	 * <默认构造函数>
	 * 
	 * @param errorCode
	 *            错误码
	 * @param th
	 *            Throwable
	 */
	public CoreException(String errorCode, Throwable cause)
	{
		super(cause);
		this.errorCode = errorCode;
	}

	public CoreException(String errorCode, String message, Throwable cause)
	{
		super(message, cause);
		this.errorMsg = message;
		this.errorCode = errorCode;
	}
	
	public CoreException(String errorCode, String message)
	{
		super(message);
		this.errorMsg = message;
		this.errorCode = errorCode;
	}

	public CoreException(String errorCode, Object[] args, Throwable cause)
	{
		super(cause);
		this.errorCode = errorCode;
		this.args = args;
	}

	public CoreException(String errorCode, Object[] args, String message, Throwable cause)
	{
		super(message, cause);
		this.errorCode = errorCode;
		this.args = args;
	}

	public CoreException(String errorCode, Object[] args, String message)
	{
		super(message);
		this.errorCode = errorCode;
		this.args = args;
	}

	/**    
	 * errorCode    
	 *    
	 * @return  the errorCode      
	 */

	public String getErrorCode()
	{
		return errorCode;
	}

	public Object[] getArgs()
	{
		return args;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}
	

}