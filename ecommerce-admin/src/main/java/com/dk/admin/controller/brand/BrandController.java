package com.dk.admin.controller.brand;

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
import com.dk.core.model.bean.Brand;
import com.dk.core.model.bean.User;
import com.dk.core.service.brand.IBrandService;



@Controller
@RequestMapping("/brand")
public class BrandController extends BaseController{
	@Resource
	private IBrandService brandService;
	
	@RequestMapping("/list.do")
	public String list(@RequestParam(value = "searchKey", required = false) String searchKey,
			@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize, Model model)
	{
		
		int page=offset/pageSize;


		List<SearchBean> searchBeans = new ArrayList<SearchBean>();
		searchBeans.add(new SearchBean("status", IConstants.BRAND_STATUS.DELETED, "!="));
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

		Page<Brand> brandPage = brandService.findByPage(new PageRequest(page, pageSize), searchBeans.toArray(new SearchBean[] {}));

		List<Brand> results = brandPage.getContent();
		long total = brandPage.getTotalElements();
		model.addAttribute("counts", total);
		model.addAttribute("brands", results);
		return "/brand/list.jsp";
	}
	
	@RequestMapping("/listAll.do")
	public String listAll(Model model)
	{
		List<Brand> results =brandService.findAll(new Sort(Sort.Direction.ASC, "name"),new SearchBean("status", IConstants.BRAND_STATUS.DELETED, "!="));
		if(CommonUtils.isEmpty(results)){
			model.addAttribute("brands", new ArrayList<Brand>());
			return "/brand/list.jsp";
		}
		model.addAttribute("counts", results.size());
		model.addAttribute("brands", results);
		return "/brand/list.jsp";
	}

	
	@RequestMapping("/manage.do")
	public @ResponseBody
	JSONObject manage(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "status", required = true) String status)
	{
		
		Brand updateBrand=brandService.findOne(id);
		if(CommonUtils.isEmpty(updateBrand)){
			return this.toJSONResult(false, "未找到该品牌");
		}
		
		User user=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(user==null){
			return this.toJSONResult(false, "登陆失效，请重新登陆");
		}
		if(status.equals(IConstants.BRAND_STATUS.ACTIVE)){
			//上架操作
			if(updateBrand.getStatus().equals(status)){
				return this.toJSONResult(false, "已上架，无需再重复操作");
			}
			updateBrand.setStatus(status);
			updateBrand.setUpdatedBy(user.getName());
			updateBrand.setUpdatedTime(new Date());
			brandService.update(updateBrand);
			
		}else if(status.equals(IConstants.BRAND_STATUS.TOKENOFF)){
			//下架操作
			if(updateBrand.getStatus().equals(status)){
				return this.toJSONResult(false, "已下架架，无需再重复操作");
			}
			updateBrand.setStatus(status);
			updateBrand.setUpdatedBy(user.getName());
			updateBrand.setUpdatedTime(new Date());
			brandService.update(updateBrand);
		}else if(status.equals(IConstants.BRAND_STATUS.DELETED)){
			//删除操作
			if(updateBrand.getStatus().equals(status)){
				return toJSONResult(true);
			}
			updateBrand.setStatus(status);
			updateBrand.setUpdatedBy(user.getName());
			updateBrand.setUpdatedTime(new Date());
			brandService.update(updateBrand);
		}
		
		return toJSONResult(true);
	}
	
	
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String setupbrand(@RequestParam(value = "id", required = false) String id ,Model model)
	{
		Brand editbrand=null;
		if(CommonUtils.isNotEmpty(id)){
			editbrand=brandService.findOne(id);
		}else{
			editbrand=new Brand();
		}
		
		model.addAttribute("brand", editbrand);
		
		return "/brand/form.jsp";
	}

	@RequestMapping(value = "/edit.do", method = RequestMethod.POST)
	public @ResponseBody JSONObject setupbrand(HttpServletRequest request, Brand brand)
	{
		
		User user=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(CommonUtils.isNotEmpty(brand.getId())){
			//执行修改 TODO 关闭sessioninview  加入dto

			brand.setStatus(IConstants.BRAND_STATUS.ACTIVE);
			brand.setUpdatedBy(user.getName());
			brand.setUpdatedTime(new Date());
			
			brandService.update(brand);
		}else{
			//新增
			brand.setCreatedBy(user.getName());
			brand.setCreatedTime(new Date());
			brand.setStatus(IConstants.BRAND_STATUS.ACTIVE);
			brandService.save(brand);
		}
		
		return toJSONResult(true);
	}


}
