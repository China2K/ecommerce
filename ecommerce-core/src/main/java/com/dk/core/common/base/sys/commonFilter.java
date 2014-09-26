package com.dk.core.common.base.sys;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class commonFilter extends HttpServlet implements Filter {
	
	private static final long serialVersionUID = 4697428616502607585L;
	
	private FilterConfig config;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		//存放session 到SessionContextHolder，以便controller中取出
		SessionContextHolder.setSession(httpRequest.getSession());
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
