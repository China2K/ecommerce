package com.dk.core.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.dk.core.common.exception.CoreException;
import com.dk.core.common.msg.MsgDescription;

public class CoreExceptionResolver extends SimpleMappingExceptionResolver
{
	private Logger logger = Logger.getLogger(CoreExceptionResolver.class);
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	{
		logger.error(ex.getMessage(), ex);
		String viewName = determineViewName(ex, request);
		if (viewName != null)
		{// JSP格式返回  
			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request
					.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)))
			{
				// 如果不是异步请求  
				// Apply HTTP status code for error views, if specified.  
				// Only apply it if we're processing a top-level request.  
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null)
				{
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else
			{// JSON格式返回  
				MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
				return new ModelAndView(jsonView, toMap(ex));

			}
		} else
		{
			MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
			return new ModelAndView(jsonView, toMap(ex));
		}

	}

	private Map<String, Object> toMap(Exception ex)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);
		if (ex instanceof CoreException)
		{
			result.put("message", MsgDescription.getMsgDesc(((CoreException) ex).getErrorCode(), ((CoreException) ex).getArgs()));
		} else
		{
			result.put("message", "系统异常");
		}
		return result;
	}
}
