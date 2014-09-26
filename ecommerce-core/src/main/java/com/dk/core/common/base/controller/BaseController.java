package com.dk.core.common.base.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.dk.core.common.base.sys.SessionContextHolder;
import com.dk.core.common.exception.CoreException;
import com.dk.core.common.msg.MsgDescription;
import com.dk.core.common.utils.CommonUtils;
import com.dk.core.common.utils.GeneralUtils;
import com.dk.core.common.utils.json.JsonHelper;
import com.dk.core.common.utils.search.SearchBean;
import com.dk.core.model.bean.User;

/**
 * Controller基类，所有的Controller必须要继承此类
 * 
 */
public class BaseController {
	protected Logger LOGGER = Logger.getLogger(this.getClass());

	public static final String DEFAULT_JSON_DATA = "data";

	public static final String DEFAULT_JSON_TOTAL_PROPERTY = "totalCount";

	public static final String DEFAULT_JSON_MESSAGE = "message";

	public static final String DEFAULT_JSON_SUCCESS = "success";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * 获取cookie名称
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	protected String getFromCookie(String cookieName, HttpServletRequest request)
	{
		String cookieStr = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals(cookieName))
				{
					cookieStr = cookie.getValue();
				}
			}
		}
		return cookieStr;
	}

	
	protected String getMessage(CoreException e) {
		return MsgDescription.getMsgDesc(e.getErrorCode());
	}

	protected String getMessage(CoreException e, Object... params) {
		return MsgDescription.getMsgDesc(e.getErrorCode(), params);
	}

	protected JSONObject toJSONResult(long count, List data) {
		JSONObject result = new JSONObject();
		result.put(DEFAULT_JSON_TOTAL_PROPERTY, count);
		result.put(DEFAULT_JSON_DATA, data);
		return result;
	}

	protected JSONObject toJSONResult(boolean success, String message) {
		JSONObject result = new JSONObject();
		result.put(DEFAULT_JSON_SUCCESS, success);
		if (CommonUtils.isNotEmpty(message)) {
			result.put(DEFAULT_JSON_MESSAGE, message);
		}
		return result;
	}

	protected JSONObject toJSONResult(boolean success) {
		JSONObject result = new JSONObject();
		result.put(DEFAULT_JSON_SUCCESS, success);
		return result;
	}

	protected JSONObject toJSONResult(boolean success, Object data) {
		JSONObject result = new JSONObject();
		result.put(DEFAULT_JSON_SUCCESS, success);
		result.put(DEFAULT_JSON_DATA, data);
		return result;
	}
	
	protected String toJSONResult(Object data) {
		return JsonHelper.toJSONString(data);
	}

	protected String toStringResultFromJson(boolean success, Object data) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(DEFAULT_JSON_DATA, data);
		map.put(DEFAULT_JSON_SUCCESS, success);

		return JsonHelper.toJSONString(map);
	}

	protected Pageable convert2Pageable(int start, int limit) {
		if (limit == 0) {
			limit = Integer.MAX_VALUE;
		}
		Pageable pageable = new PageRequest(start / limit, limit);
		return pageable;
	}

	/**
	 * 根据前台的param参数，返回searchbean对象
	 * 
	 * @param param
	 * @return
	 */
	protected SearchBean[] convert2SearchBean(String param) {
		if (GeneralUtils.isNullOrZeroLenght(param)) {
			return new SearchBean[] {};
		}

		String[] paramStrs = param.split(",");

		List<SearchBean> list = new ArrayList<SearchBean>();
		for (String paramStr : paramStrs) {
			String[] search = paramStr.split(":");
			// 如果没有填写值，则不生成searchbean
			if (search.length < 3 || GeneralUtils.isNullOrZeroLenght(search[1])) {
				continue;
			}

			SearchBean searchBean = new SearchBean(search[0], search[1],
					search[2]);
			list.add(searchBean);
		}
		return list.toArray(new SearchBean[] {});
	}
	public User getLoginUser(String userConstant){
		Object obj=SessionContextHolder.getSession().getAttribute(userConstant);
		if(CommonUtils.isEmpty(obj)){
			return null;
		}
		User user=User.class.cast(obj);
		return user;
	}

}
