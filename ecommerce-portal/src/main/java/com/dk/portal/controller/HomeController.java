package com.dk.portal.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dk.core.common.base.controller.BaseController;
import com.dk.core.common.base.sys.SessionContextHolder;
import com.dk.core.common.constant.IPortalConstants;
import com.dk.core.common.utils.CommonUtils;
import com.dk.core.common.utils.DateUtils;
import com.dk.core.model.bean.User;



@Controller
@RequestMapping("/")
public class HomeController extends BaseController{
	
	@RequestMapping("/index.do")
	public String index()
	{
		return "/common/default.jsp";
	}

	@RequestMapping("/register.do")
	public String register()
	{
		return "/common/register.jsp";
	}

	@RequestMapping("/makeRandCode.do")
	public String makeRandCode()
	{
		return "/common/makeRandCode.jsp";
	}

	@RequestMapping("/changePwd.do")
	public String changePwd()
	{
		return "/settings/changePwd.jsp";
	}

	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public String login()
	{

		Object loginUser = SessionContextHolder.getSession().getAttribute(IPortalConstants.SESSION_KEY_LOGIN_USER);
		if (CommonUtils.isNotEmpty(loginUser))
		{
			return "redirect:/index.do";
		}
		return "/common/login.jsp";
	}

	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public String logout(HttpServletResponse response, HttpServletRequest req)
	{


		User loginUser = (User) SessionContextHolder.getSession().getAttribute(IPortalConstants.SESSION_KEY_LOGIN_USER);

		SessionContextHolder.getSession().removeAttribute(IPortalConstants.SESSION_KEY_LOGIN_USER);
		return "redirect:/index.do";
	}

	// 生成验证码图片
	public void mkRandCodePic(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			// 在内存中创建图象
			int width = 60, height = 20;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			// 获取图形上下文
			Graphics g = image.getGraphics();

			// 生成随机类
			Random random = new Random();

			// 设定背景色
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);

			// 设定字体
			g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++)
			{
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}

			// 取随机产生的认证码(4位数字)
			String sRand = "";
			for (int i = 0; i < 4; i++)
			{
				String rand = String.valueOf(random.nextInt(10));
				sRand += rand;
				// 将认证码显示到图象中
				g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(rand, 13 * i + 6, 16);
			}

			// 将认证码存入返回对象中
			request.getSession().setAttribute(IPortalConstants.SESSION_KEY_REGISTER_RAND_CODE, sRand);

			// 图象生效
			g.dispose();
			response.reset();
			
			//add by gavin 2014-08-26 :添加cookie和cache
			long expire = DateUtils.getCurrentTimestamp().getTime();
			String expireStr = String.valueOf(expire);

			//将旧的验证码删除
			String oldRandValue = this.getFromCookie(IPortalConstants.COOKIE_KEY_RANDOM, request);

			
			OutputStream output = response.getOutputStream();
			// 输出图象到页面
			ImageIO.write(image, "JPEG", output);
			output.flush();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// 给定范围获得随机颜色
	private Color getRandColor(int fc, int bc)
	{
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
