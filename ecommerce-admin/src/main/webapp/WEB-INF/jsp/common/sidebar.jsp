<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="sidebar">
	<a href="<c:url value ='/index.do'/>" class="visible-phone"><i class="icon icon-home"></i> 主页</a>
	<ul>
		<li class="active"><a href="<c:url value ='/index.do'/>"><i
				class="icon icon-home"></i> <span>主页</span></a></li>
		<li class="submenu"><a href="#"><i class="icon icon-th-list"></i>
				<span>商品管理</span> <span class="label">3</span></a>
			<ul>
				<li><a href="<c:url value ='/product/listAll.do'/>">普通商品</a></li>
				<li><a href="#">首页幻灯片产品</a></li>
				<li><a href="#">首页推荐产品</a></li>
			</ul></li>
		<li><a href="<c:url value ='/category/listAll.do'/>"><i class="icon icon-th-list"></i> <span>分类管理</span></a></li>
		<li><a href="<c:url value ='/brand/listAll.do'/>"><i class="icon icon-th-list"></i> <span>品牌管理</span></a></li>
		<li><a href="<c:url value ='/order/listAll.do'/>"><i class="icon icon-th-list"></i> <span>订单管理</span></a></li>
		<li><a href="<c:url value ='/user/listAll.do'/>"><i class="icon icon-th-list"></i> <span>客户管理</span></a></li>
		<li><a href="#"><i class="icon icon-cog"></i> <span>系统设置</span></a></li>
	</ul>
</div>