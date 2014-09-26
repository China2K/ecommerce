package com.dk.admin.controller.order;

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
import com.dk.core.model.bean.Order;
import com.dk.core.model.bean.User;
import com.dk.core.service.order.IOrderService;



@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{
	@Resource
	private IOrderService orderService;
	
	@RequestMapping("/list.do")
	public String list(@RequestParam(value = "searchKey", required = false) String searchKey,
			@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize, Model model)
	{
		
		int page=offset/pageSize;


		List<SearchBean> searchBeans = new ArrayList<SearchBean>();
		searchBeans.add(new SearchBean("status", IConstants.ORDER_STATUS.DELETED, "!="));
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

		Page<Order> orderPage = orderService.findByPage(new PageRequest(page, pageSize), searchBeans.toArray(new SearchBean[] {}));

		List<Order> results = orderPage.getContent();
		long total = orderPage.getTotalElements();
		model.addAttribute("counts", total);
		model.addAttribute("orders", results);
		return "/order/list.jsp";
	}
	
	@RequestMapping("/listAll.do")
	public String listAll(Model model)
	{
		List<Order> results =orderService.findAll(new Sort(Sort.Direction.DESC, "createdTime"),new SearchBean("status", IConstants.ORDER_STATUS.DELETED, "!="));
		if(CommonUtils.isEmpty(results)){
			model.addAttribute("orders", new ArrayList<Order>());
			return "/order/list.jsp";
		}
		model.addAttribute("counts", results.size());
		model.addAttribute("orders", results);
		return "/order/list.jsp";
	}

	
	@RequestMapping("/manage.do")
	public @ResponseBody
	JSONObject manage(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "status", required = true) String status)
	{
		
		Order updateorder=orderService.findOne(id);
		if(CommonUtils.isEmpty(updateorder)){
			return this.toJSONResult(false, "未找到该订单");
		}
		
		User user=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(user==null){
			return this.toJSONResult(false, "登陆失效，请重新登陆");
		}
		//A-用户已生成未发货，B-系统已发货，C-用户已付款，D-用户已确认收货,订单完成，E-用户已退货，订单取消
		/*if(status.equals(IConstants.ORDER_STATUS.SENDED)){
			//上架操作
			if(updateorder.getStatus().equals(status)){
				return this.toJSONResult(false, "已发货，无需再重复操作");
			}
			updateorder.setStatus(status);
			orderService.update(updateorder);
			
		}else if(status.equals(IConstants.ORDER_STATUS.TOKENOFF)){
			//下架操作
			if(updateorder.getStatus().equals(status)){
				return this.toJSONResult(false, "已下架架，无需再重复操作");
			}
			updateorder.setStatus(status);
			updateorder.setUpdatedBy(user.getName());
			updateorder.setUpdatedTime(new Date());
			orderService.update(updateorder);
		}else if(status.equals(IConstants.ORDER_STATUS.DELETED)){
			//删除操作
			if(updateorder.getStatus().equals(status)){
				return toJSONResult(true);
			}
			updateorder.setStatus(status);
			updateorder.setUpdatedBy(user.getName());
			updateorder.setUpdatedTime(new Date());
			orderService.update(updateorder);
		}*/
		
		return toJSONResult(true);
	}
	
	
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String setuporder(@RequestParam(value = "id", required = false) String id ,Model model)
	{
		Order editorder=null;
		if(CommonUtils.isNotEmpty(id)){
			editorder=orderService.findOne(id);
		}else{
			editorder=new Order();
		}
		
		model.addAttribute("order", editorder);
		
		return "/order/form.jsp";
	}

	@RequestMapping(value = "/edit.do", method = RequestMethod.POST)
	public @ResponseBody JSONObject setuporder(HttpServletRequest request, Order order)
	{
		
		User user=getLoginUser(IAdminConstants.SESSION_KEY_LOGIN_USER);
		if(CommonUtils.isNotEmpty(order.getId())){
			//执行修改 TODO 关闭sessioninview  加入dto

			orderService.update(order);
		}else{
			//新增
			order.setCreatedTime(new Date());
			orderService.save(order);
		}
		
		return toJSONResult(true);
	}

	
}
