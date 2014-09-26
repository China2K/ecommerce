package com.dk.core.common.base.sys;



import javax.servlet.http.HttpSession;

/**
 * 用户Session信息全局上下文
 * 
 */
public class SessionContextHolder
{
	private static ThreadLocal<HttpSession>	contextHolder	= new ThreadLocal<HttpSession>();

	public static HttpSession getSession()
	{
		return contextHolder.get();
	}

	public static void setSession(HttpSession session)
	{
		contextHolder.set(session);
	}

/*	public static SysUserDto getCurrentUser()
	{
		if (SessionContextHolder.getSession() != null)
		{
			Object user = SessionContextHolder.getSession().getAttribute(IVeopConstants.SESSION_KEY_CURRENT_USER);
			return user == null ? null : (SysUserDto) user;
		} else
		{
			return null;
		}
	}*/
}
