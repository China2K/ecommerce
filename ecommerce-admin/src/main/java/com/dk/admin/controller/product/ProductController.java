package com.dk.admin.controller.product;

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
import com.dk.core.model.bean.Category;
import com.dk.core.model.bean.Product;
import com.dk.core.model.bean.User;
import com.dk.core.service.brand.IBrandService;
import com.dk.core.service.category.ICategoryService;
import com.dk.core.service.product.IProductService;



@Controller
@RequestMapping("/product")
public class ProductController extends BaseController{
	@Resource
	private ICategoryService categoryService;
	
	@Resource
	private IProductService productService;
	
	@Resource
	private IBrandService brandService;
	
	@RequestMapping("/list.do")
	public String list(@RequestParam(value = "searchKey", required = false) String searchKey,
			@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize, Model model)
	{
		
		int page=offset/pageSize;


		List<SearchBean> searchBeans = new ArrayList<SearchBean>();
		searchBeans.add(new SearchBean("status", IConstants.PRODUCT_STATUS.DELETED, "!="));
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

		Page<Product> productPage = productService.findByPage(new PageRequest(page, pageSize), searchBeans.toArray(new SearchBean[] {}));

		List<Product> results = productPage.getContent();
		long total = productPage.getTotalElements();
		model.addAttribute("counts", total);
		model.addAttribute("products", results);
		return "/product/list.jsp";
	}
	
	@RequestMapping("/listAll.do")
	public String listAll(Model model)
	{
		List<Product> results =productService.findAll(new SearchBean("status", IConstants.PRODUCT_STATUS.DELETED, "!="));
		if(CommonUtils.isEmpty(results)){
			model.addAttribute("products", new ArrayList<Product>());
			return "/product/list.jsp";
		}
		model.addAttribute("counts", results.size());
		model.addAttribute("products", results);
		return "/product/list.jsp";
	}

	
	@RequestMapping("/manage.do")
	public @ResponseBody
	JSONObject manage(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "status", required = true) String status)
	{
		
		Product updateProd=productService.findOne(id);
		if(CommonUtils.isEmpty(updateProd)){
			return this.toJSONResult(false, "未找到该产品");
		}
		
		User user=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(user==null){
			return this.toJSONResult(false, "登陆失效，请重新登陆");
		}
		if(status.equals(IConstants.PRODUCT_STATUS.ACTIVE)){
			//上架操作
			if(updateProd.getStatus().equals(status)){
				return this.toJSONResult(false, "已上架，无需再重复操作");
			}
			updateProd.setStatus(status);
			updateProd.setApprovedBy(user.getName());
			updateProd.setApprovedTime(new Date());
			updateProd.setUpdatedBy(user.getName());
			updateProd.setUpdatedTime(new Date());
			productService.update(updateProd);
			
		}else if(status.equals(IConstants.PRODUCT_STATUS.TOKENOFF)){
			//下架操作
			if(updateProd.getStatus().equals(status)){
				return this.toJSONResult(false, "已下架架，无需再重复操作");
			}
			updateProd.setStatus(status);
			updateProd.setTokenOffBy(user.getName());
			updateProd.setTokenOffTime(new Date());
			updateProd.setUpdatedBy(user.getName());
			updateProd.setUpdatedTime(new Date());
			productService.update(updateProd);
		}else if(status.equals(IConstants.PRODUCT_STATUS.DELETED)){
			//删除操作
			if(updateProd.getStatus().equals(status)){
				return toJSONResult(true);
			}
			updateProd.setStatus(status);
			updateProd.setUpdatedBy(user.getName());
			updateProd.setUpdatedTime(new Date());
			productService.update(updateProd);
		}
		
		return toJSONResult(true);
	}
	
	
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String setupCategory(@RequestParam(value = "id", required = false) String id ,Model model)
	{
		Product editProduct=null;
		if(CommonUtils.isNotEmpty(id)){
			editProduct=productService.findOne(id);
		}else{
			editProduct=new Product();
		}
		List<Category> categories=categoryService.findAll(new Sort(Sort.Direction.ASC, "name"),new SearchBean("status", IConstants.CATEGORY_STATUS.ACTIVE, "="));
		List<Brand> brands=brandService.findAll(new Sort(Sort.Direction.ASC, "name"),new SearchBean("status", IConstants.BRAND_STATUS.ACTIVE, "="));
		if(CommonUtils.isEmpty(categories)){
			categories=new ArrayList<Category>();
		}
		
		if(CommonUtils.isEmpty(brands)){
			brands=new ArrayList<Brand>();
		}
		model.addAttribute("categories", categories);
		model.addAttribute("brands", brands);
		model.addAttribute("product", editProduct);
		
		return "/product/form.jsp";
	}

	@RequestMapping(value = "/edit.do", method = RequestMethod.POST)
	public @ResponseBody JSONObject setupCategory(HttpServletRequest request, Product product)
	{
		
		if(CommonUtils.isNotEmpty(product.getCategory().getId())){
			Category category=categoryService.findOne(product.getCategory().getId());
			product.setCategory(category);
		}else{
			return this.toJSONResult(false, "请选择一个产品类别");
		}
		
		if(CommonUtils.isNotEmpty(product.getBrand().getId())){
			Brand brand=brandService.findOne(product.getBrand().getId());
			if(brand!=null){
				product.setBrand(brand);
				product.setBrandName(brand.getName());
			}
			
		}else{
			product.setBrand(null);
		}
		User user=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(CommonUtils.isNotEmpty(product.getId())){
			//执行修改 TODO 关闭sessioninview  加入dto
			/*Product updProduct=productService.findOne(product.getId());
			product.setApprovedBy(updProduct.getApprovedBy());
			product.setApprovedTime(updProduct.getApprovedTime());
			product.setTokenOffBy(updProduct.getTokenOffBy());
			product.setTokenOffTime(updProduct.getTokenOffTime());
			product.setStatus(updProduct.getStatus());*/
			product.setStatus("A");
			product.setUpdatedBy(user.getName()	);
			product.setUpdatedTime(new Date());
			
			productService.update(product);
		}else{
			//新增
			product.setCreatedBy(user.getName());
			product.setCreatedTime(new Date());
			product.setStatus(IConstants.PRODUCT_STATUS.ACTIVE);
			productService.save(product);
		}
		
		return toJSONResult(true);
	}

	
	/*@RequestMapping("/delete.do")
	public @ResponseBody
	JSONObject delete(@RequestParam(value = "ids", required = false) String ids)
	{
		
		if(CommonUtils.isNotEmpty(ids)){
			String[] idsArray=ids.split(",");
			for(int i=0;i<idsArray.length;i++){
				String id=idsArray[i];
				try
				{
					fodderService.deleteByIdAndPNid(id,getPublicNumId());
				} catch (CoreException e)
				{
					return this.toJSONResult(false, this.getMessage(e, e.getArgs()));
				}
				}
		}
		return toJSONResult(true);
	}
*/
	
	
	




}
