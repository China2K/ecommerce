package com.dk.admin.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dk.core.common.base.controller.BaseController;
import com.dk.core.common.base.sys.SessionContextHolder;
import com.dk.core.common.constant.IAdminConstants;
import com.dk.core.common.constant.IConstants;
import com.dk.core.common.exception.CoreException;
import com.dk.core.common.exception.PortalErrorCode;
import com.dk.core.common.utils.CommonUtils;
import com.dk.core.common.utils.search.SearchBean;
import com.dk.core.model.bean.User;
import com.dk.core.service.user.IUserService;

@Controller
@RequestMapping("/user")
public class SysUserController extends BaseController{
	@Resource
	private IUserService userService;
	
	
	@RequestMapping("/login.do")
	public @ResponseBody
	JSONObject login(String loginName, String password, ModelMap model, HttpServletResponse response)
	{

		try
		{
			// 登陆校验
			User loginUser=userService.loginCheck(loginName, password);
			if(loginUser.getIsAdmin()==null||!loginUser.getIsAdmin()){
				return this.toJSONResult(false, "非管理员账户");
			}
			// 记录session
			SessionContextHolder.getSession().setAttribute(IAdminConstants.SESSION_KEY_LOGIN_USER, loginUser);
			return this.toJSONResult(true);

		} catch (CoreException e)
		{
			if (e.getErrorCode() == PortalErrorCode.USER_PASSWORD_ERROR_TIMES_EXCEED_ERROR)
			{
				return this.toJSONResult(false, this.getMessage(e, e.getArgs()));
			} else
			{

				return this.toJSONResult(false, this.getMessage(e));
			}
		}
	}

	
	
	
	@RequestMapping("/list.do")
	public String list(@RequestParam(value = "searchKey", required = false) String searchKey,
			@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize, Model model)
	{
		
		int page=offset/pageSize;


		List<SearchBean> searchBeans = new ArrayList<SearchBean>();
		searchBeans.add(new SearchBean("status", IConstants.USER_STATUS.DELETED, "!="));
		
		if (CommonUtils.isNotEmpty(searchKey))
		{
			try
			{
				searchKey = new String(searchKey.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			searchBeans.add(new SearchBean("name", "%" + searchKey + "%", "like"));
		}

		Page<User> userPage = userService.findByPage(new PageRequest(page, pageSize), searchBeans.toArray(new SearchBean[] {}));

		List<User> results = userPage.getContent();
		long total = userPage.getTotalElements();
		model.addAttribute("counts", total);
		model.addAttribute("users", results);
		return "/user/list.jsp";
	}
	
	@RequestMapping("/listAll.do")
	public String listAll(Model model)
	{
		List<User> results =userService.findAll(new Sort(Sort.Direction.ASC, "name"),new SearchBean("status", IConstants.USER_STATUS.DELETED, "!="));
		if(CommonUtils.isEmpty(results)){
			model.addAttribute("users", new ArrayList<User>());
			return "/user/list.jsp";
		}
		model.addAttribute("counts", results.size());
		model.addAttribute("users", results);
		return "/user/list.jsp";
	}

	
	@RequestMapping("/manage.do")
	public @ResponseBody
	JSONObject manage(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "status", required = true) String status)
	{
		
		User updateuser=userService.findOne(id);
		if(CommonUtils.isEmpty(updateuser)){
			return this.toJSONResult(false, "未找到该用户");
		}
		
		User user=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(user==null){
			return this.toJSONResult(false, "登陆失效，请重新登陆");
		}
		if(status.equals(IConstants.USER_STATUS.ACTIVE)){
			//激活
			if(updateuser.getStatus().equals(status)){
				return this.toJSONResult(false, "已激活，无需再重复操作");
			}
			updateuser.setStatus(status);
			updateuser.setUpdatedBy(user.getName());
			updateuser.setUpdatedTime(new Date());
			userService.update(updateuser);
			
		}else if(status.equals(IConstants.USER_STATUS.INACTIVE)){
			if(updateuser.getStatus().equals(status)){
				return this.toJSONResult(false, "拒绝，无需再重复操作");
			}
			updateuser.setStatus(status);
			updateuser.setUpdatedBy(user.getName());
			updateuser.setUpdatedTime(new Date());
			userService.update(updateuser);
		}else if(status.equals(IConstants.USER_STATUS.DELETED)){
			//删除操作
			if(updateuser.getStatus().equals(status)){
				return toJSONResult(true);
			}
			updateuser.setStatus(status);
			updateuser.setUpdatedBy(user.getName());
			updateuser.setUpdatedTime(new Date());
			userService.update(updateuser);
		}
		
		return toJSONResult(true);
	}
	
	
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String setupuser(@RequestParam(value = "id", required = false) String id ,Model model)
	{
		User edituser=null;
		if(CommonUtils.isNotEmpty(id)){
			edituser=userService.findOne(id);
		}else{
			edituser=new User();
		}
		
		model.addAttribute("user", edituser);
		
		return "/user/form.jsp";
	}

	@RequestMapping(value = "/edit.do", method = RequestMethod.POST)
	public @ResponseBody JSONObject setupuser(HttpServletRequest request, User user)
	{
		
		User loginUser=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(CommonUtils.isNotEmpty(user.getId())){
			//执行修改 TODO 关闭sessioninview  加入dto

			user.setStatus(IConstants.USER_STATUS.ACTIVE);
			user.setUpdatedBy(loginUser.getName());
			user.setUpdatedTime(new Date());
			
			userService.update(user);
		}else{
			//新增
			user.setCreatedBy(loginUser.getName());
			user.setCreatedTime(new Date());
			user.setStatus(IConstants.USER_STATUS.ACTIVE);
			userService.save(user);
		}
		
		return toJSONResult(true);
	}
}
