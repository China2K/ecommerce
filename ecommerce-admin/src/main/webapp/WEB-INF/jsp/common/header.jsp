<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="header">
	<h2>
		<a href="<c:url value ='/index.do'/>" style="color: white;">管理平台</a>
	</h2>
</div>


<div id="user-nav" class="navbar navbar-inverse">
	<ul class="nav btn-group">
		<li class="btn btn-inverse"><a title="" href="#"><i
				class="icon icon-user"></i> <span class="text">用户中心</span></a></li>
		<li class="btn btn-inverse"><a title="" href="#"><i
				class="icon icon-cog"></i> <span class="text">设置</span></a></li>
		<li class="btn btn-inverse"><a title=""
			href="<c:url value ='/user/logout.do'/>"><i
				class="icon icon-share-alt"></i> <span class="text">退出</span></a></li>
	</ul>
</div>