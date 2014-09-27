package com.dk.portal.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dk.core.common.base.sys.SessionContextHolder;
import com.dk.core.common.constant.IPortalConstants;


/**
 * @ClassName: LoginFilter
 * @Description: TODO
 */
public class LoginFilter extends HttpServlet implements Filter {
	
	private FilterConfig filterConfig;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		// 把session放到上下文中
		SessionContextHolder.setSession(session);

		String path = req.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		String requestPath = req.getServletPath();

		boolean flag = false;
		//不用登陆的URL 开头
		String excludeStr = filterConfig.getInitParameter("excludePrefix");
		if(excludeStr != null && !"".equals(excludeStr)){
			String[] excludeUrlBeginnings = excludeStr.split(",");
			for(String str : excludeUrlBeginnings){
				if(requestPath.startsWith(str)){
					flag = true;
					break;
				}
			}
		}
		
		if(!flag){
			flag = ((requestPath.indexOf("login.do") != -1)
					|| (requestPath.indexOf("logout.do") != -1)
					|| requestPath.indexOf("login.html") != -1 || (requestPath.indexOf("register.do") != -1) || (requestPath.indexOf("makeRandCode.do") != -1) || (requestPath.indexOf("common/location/list.do") != -1));
		}
		if (!flag) {
			// 如果session不为空，则可以浏览其他页面
			if (session.getAttribute(IPortalConstants.SESSION_KEY_LOGIN_USER) != null) {
				filterChain.doFilter(request, response);
				return;
			} else {
				// 跳转到登陆页
				res.sendRedirect(basePath + "login.do");
				return;
			}
		} else {
			filterChain.doFilter(request, response);
			return;
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
