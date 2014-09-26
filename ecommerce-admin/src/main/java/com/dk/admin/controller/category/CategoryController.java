package com.dk.admin.controller.category;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dk.core.common.base.controller.BaseController;
import com.dk.core.common.constant.IAdminConstants;
import com.dk.core.common.constant.IConstants;
import com.dk.core.common.utils.CommonUtils;
import com.dk.core.common.utils.search.SearchBean;
import com.dk.core.model.bean.Category;
import com.dk.core.model.bean.User;
import com.dk.core.service.category.ICategoryService;
import com.dk.core.service.product.IProductService;



@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController{
	@Resource
	private ICategoryService categoryService;
	
	@Resource
	private IProductService productService;
	
	@RequestMapping("/list.do")
	public String list(@RequestParam(value = "searchKey", required = false) String searchKey,
			@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize, Model model)
	{
		
		int page=offset/pageSize;


		List<SearchBean> searchBeans = new ArrayList<SearchBean>();
		searchBeans.add(new SearchBean("status", IConstants.CATEGORY_STATUS.DELETED, "!="));
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

		Page<Category> categoryPage = categoryService.findByPage(new PageRequest(page, pageSize), searchBeans.toArray(new SearchBean[] {}));

		List<Category> results = categoryPage.getContent();
		long total = categoryPage.getTotalElements();
		model.addAttribute("counts", total);
		model.addAttribute("categories", results);
		return "/category/list.jsp";
	}
	
	@RequestMapping("/listAll.do")
	public String listAll(Model model)
	{
		List<Category> results =categoryService.findAll(new Sort(Sort.Direction.ASC, "order"),new SearchBean("status", IConstants.CATEGORY_STATUS.DELETED, "!="));
		if(CommonUtils.isEmpty(results)){
			model.addAttribute("categories", new ArrayList<Category>());
			return "/category/list.jsp";
		}
		model.addAttribute("counts", results.size());
		model.addAttribute("categories", results);
		return "/category/list.jsp";
	}

	
	@RequestMapping("/manage.do")
	public @ResponseBody
	JSONObject manage(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "status", required = true) String status)
	{
		
		Category updateCate=categoryService.findOne(id);
		if(CommonUtils.isEmpty(updateCate)){
			return this.toJSONResult(false, "未找到该分类");
		}
		
		User user=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(user==null){
			return this.toJSONResult(false, "登陆失效，请重新登陆");
		}
		if(status.equals(IConstants.CATEGORY_STATUS.ACTIVE)){
			//上架操作
			if(updateCate.getStatus().equals(status)){
				return this.toJSONResult(false, "已上架，无需再重复操作");
			}
			updateCate.setStatus(status);
			updateCate.setUpdatedBy(user.getName());
			updateCate.setUpdatedTime(new Date());
			categoryService.update(updateCate);
			
		}else if(status.equals(IConstants.CATEGORY_STATUS.DELETED)){
			//删除操作
			if(updateCate.getStatus().equals(status)){
				return toJSONResult(true);
			}
			updateCate.setStatus(status);
			updateCate.setUpdatedBy(user.getName());
			updateCate.setUpdatedTime(new Date());
			categoryService.update(updateCate);
		}
		
		return toJSONResult(true);
	}
	
	
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String setupCategory(@RequestParam(value = "id", required = false) String id ,Model model)
	{
		Category editCategory=null;
		if(CommonUtils.isNotEmpty(id)){
			editCategory=categoryService.findOne(id);
		}else{
			editCategory=new Category();
		}
		
		model.addAttribute("category", editCategory);
		
		return "/category/form.jsp";
	}

	@RequestMapping(value = "/edit.do", method = RequestMethod.POST)
	public @ResponseBody JSONObject setupCategory(HttpServletRequest request, Category category)
	{
		
		User user=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(CommonUtils.isNotEmpty(category.getId())){
			//执行修改 TODO 关闭sessioninview  加入dto

			category.setStatus(IConstants.CATEGORY_STATUS.ACTIVE);
			category.setUpdatedBy(user.getName());
			category.setUpdatedTime(new Date());
			
			categoryService.update(category);
		}else{
			//新增
			category.setCreatedBy(user.getName());
			category.setCreatedTime(new Date());
			category.setStatus(IConstants.CATEGORY_STATUS.ACTIVE);
			categoryService.save(category);
		}
		
		return toJSONResult(true);
	}


}
