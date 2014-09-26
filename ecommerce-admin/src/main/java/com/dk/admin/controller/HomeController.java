package com.dk.admin.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dk.core.common.base.controller.BaseController;
import com.dk.core.common.base.sys.SessionContextHolder;
import com.dk.core.common.constant.IConstants;
import com.dk.core.common.constant.IPortalConstants;
import com.dk.core.common.utils.CommonUtils;
import com.dk.core.model.bean.Category;
import com.dk.core.model.bean.Product;
import com.dk.core.service.category.ICategoryService;
import com.dk.core.service.product.IProductService;



@Controller
@RequestMapping("/")
public class HomeController extends BaseController{
	@Resource
	private ICategoryService categoryService;
	
	@Resource
	private IProductService productService;
	
	@RequestMapping("/index.do")
	public String index()
	{
		
/*	List<Category> categories=	categoryService.findAllIndexCategories();
	List<Product> products=productService.findActiveHotProducts();*/
		
		Product product=productService.findOne("test1");
		/*product.setStatus("D");
		productService.save(product);*/
		
		Category c=categoryService.findOne("tttttttt");
		
		/*for(int i=2;i<22;i++){
			Product saveP=new Product();
			saveP.setCategory(c);
			saveP.setName("补水面膜---"+i);
			saveP.setBrandName("欧莱雅");
			saveP.setDesc("test");
			saveP.setSku("张");
			saveP.setStock(90);
			saveP.setUnitPrice((double)(50+i));
			saveP.setSellPrice((double)(50+i));
			
			saveP.setStatus(IConstants.PRODUCT_STATUS.ACTIVE);
			saveP.setCreatedTime(new Date());
			saveP.setApprovedTime(new Date());
			saveP.setApprovedBy("root");
			saveP.setCreatedBy("root");
			System.out.println(productService.save(saveP));
		}*/
		
		return "/index.jsp";
	}

	@RequestMapping("/register.do")
	public String register()
	{
		return "/register.jsp";
	}

	@RequestMapping("/makeRandCode.do")
	public String makeRandCode()
	{
		return "/makeRandCode.jsp";
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
		return "/login.jsp";
	}

	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public String logout(HttpServletResponse response, HttpServletRequest req)
	{

		Object loginUser = SessionContextHolder.getSession().getAttribute(IPortalConstants.SESSION_KEY_LOGIN_USER);
		if(loginUser!=null){
			SessionContextHolder.getSession().removeAttribute(IPortalConstants.SESSION_KEY_LOGIN_USER);
		}
		return "redirect:/index.do";
	}

}
