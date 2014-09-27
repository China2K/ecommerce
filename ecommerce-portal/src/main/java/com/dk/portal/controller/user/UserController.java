package com.dk.portal.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dk.core.common.base.controller.BaseController;
import com.dk.core.common.base.sys.SessionContextHolder;
import com.dk.core.common.constant.IPortalConstants;
import com.dk.core.common.exception.CoreException;
import com.dk.core.common.exception.PortalErrorCode;
import com.dk.core.common.msg.MsgDescription;
import com.dk.core.common.utils.CommonUtils;
import com.dk.core.common.utils.PropertiesLoader;
import com.dk.core.common.utils.mail.Email;
import com.dk.core.common.utils.mail.MailUtils;
import com.dk.core.model.bean.User;
import com.dk.core.service.user.IUserService;

@RequestMapping("/user")
public class UserController extends BaseController{
	@Resource
	private IUserService userService;
	
	@Resource
	private JavaMailSender mailSender;
	
	@RequestMapping("/login.do")
	public @ResponseBody
	JSONObject login(String loginName, String password, ModelMap model, HttpServletResponse response)
	{

		try
		{
			// 登陆校验
			User loginUser=userService.loginCheck(loginName, password);
			// 记录session
			SessionContextHolder.getSession().setAttribute(IPortalConstants.SESSION_KEY_LOGIN_USER, loginUser);
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

	@RequestMapping("/logout.do")
	public @ResponseBody
	JSONObject logout(String loginName, ModelMap model, HttpServletResponse response)
	{
		// 删除session
		model.remove(IPortalConstants.SESSION_KEY_LOGIN_USER);

		SessionContextHolder.getSession().removeAttribute(IPortalConstants.SESSION_KEY_LOGIN_USER);
		
		return this.toJSONResult(true);
	}
	

	@RequestMapping("/register.do")
	public @ResponseBody
	JSONObject register(@ModelAttribute User user, String randCode, HttpServletResponse response, HttpServletRequest request)
	{

		Object _randCode = SessionContextHolder.getSession().getAttribute(IPortalConstants.SESSION_KEY_REGISTER_RAND_CODE);
		if (_randCode == null)
		{
			return this.toJSONResult(false, "402");
		}

		if (!((String) _randCode).equals(randCode))
		{
			return this.toJSONResult(false, MsgDescription.getMsgByKey("401"));
		}

		try
		{
			userService.register(user);
			//发送注册成功的邮件
			if(CommonUtils.isNotEmpty(user.getEmail())){
				sendRegisterMail(user);
			}
			return this.toJSONResult(true);
		} catch (CoreException e)
		{
			return this.toJSONResult(false, this.getMessage(e));
		}
	}
	
	private void sendRegisterMail(final User user) {
		new Thread(new Runnable() {
			public void run() {
				PropertiesLoader loader = new PropertiesLoader("mail.properties");
				
				Map<String,Object> map = new HashMap<String, Object>();
				Email email = new Email();
				email.setSender(loader.getProperty("mail.from"));
				email.setAddress(user.getEmail());
				/*email.setCc();
				email.setBcc();
				email.setReplyTo();*/
				email.setSubject(loader.getProperty("mail.register.success.subject"));
				//从模板生成
				HashMap<String,Object> map2 = new HashMap<String, Object>();
				map2.put("userName", user.getUserName());
				email.setContent(MailUtils.getMailText(map2, loader.getProperty("mail.register.success.content")));
				
				map.put("email", email);
				MailUtils.sendMailByAsynchronousMode(map, mailSender);
			}
		}).start();
	}
}
