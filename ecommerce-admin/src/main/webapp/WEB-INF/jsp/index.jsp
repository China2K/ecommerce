<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
	<!-- container-fluid -->
	<head>
		<title>Admin</title>
		<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        
		<link rel="stylesheet" href="<c:url value ='/resource/css/bootstrap.min.css'/>" />
		<link rel="stylesheet" href="<c:url value ='/resource/css/bootstrap-responsive.min.css'/>" />
		<link rel="stylesheet" href="<c:url value ='/resource/css/fullcalendar.css'/>" />	
		<link rel="stylesheet" href="<c:url value ='/resource/css/unicorn.main.css'/>" />
		<link rel="stylesheet" href="<c:url value ='/resource/css/unicorn.grey.css'/>" class="skin-color" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
	<body>
		<jsp:include page="common/header.jsp"></jsp:include>
		<jsp:include page="common/sidebar.jsp"></jsp:include>
		
		<div id="content">
			<div id="content-header">
				<h1>概览</h1>
				<div class="btn-group">
					<a class="btn btn-large tip-bottom" title="客户管理"><i class="icon-user"></i></a>
					<a class="btn btn-large tip-bottom" title="订单管理"><i class="icon-shopping-cart"></i></a>
				</div>
			</div>
			<div id="breadcrumb">
				<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> 主页</a>
				<a href="#" class="current">概览</a>
			</div>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12 center" style="text-align: center;">					
						<ul class="stat-boxes">
							<li>
								<div class="left peity_bar_good"><span>2,4,9,7,12,10,12</span>+20%</div>
								<div class="right">
									<strong>36094</strong>
									访问量
								</div>
							</li>
							<li>
								<div class="left peity_bar_neutral"><span>20,15,18,14,10,9,9,9</span>0%</div>
								<div class="right">
									<strong>1433</strong>
									用户
								</div>
							</li>
						</ul>
					</div>	
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>网站信息统计</h5><div class="buttons"><a href="#" class="btn btn-mini"><i class="icon-refresh"></i> Update stats</a></div></div>
							<div class="widget-content">
								<div class="row-fluid">
								<div class="span4">
									<ul class="site-stats">
										<li><i class="icon-user"></i> <strong>1433</strong> <small>用户量</small></li>
										<li><i class="icon-arrow-right"></i> <strong>16</strong> <small>新加入用户 (上周)</small></li>
										<li class="divider"></li>
										<li><i class="icon-shopping-cart"></i> <strong>259</strong> <small>热销产品</small></li>
										<li><i class="icon-tag"></i> <strong>8650</strong> <small>订单总量</small></li>
										<li><i class="icon-repeat"></i> <strong>29</strong> <small>待发货订单</small></li>
									</ul>
								</div>
								<div class="span8">
								<!-- TODO -->
									<div class="chart"></div>
								</div>	
								</div>							
							</div>
						</div>					
					</div>
				</div>
				<jsp:include page="common/footer.jsp"></jsp:include>
			</div>
		</div>
		

            <script src="<c:url value ='/resource/js/excanvas.min.js'/>"></script>
            <script src="<c:url value ='/resource/js/jquery.min.js'/>"></script>
            <script src="<c:url value ='/resource/js/jquery.ui.custom.js'/>"></script>
            <script src="<c:url value ='/resource/js/bootstrap.min.js'/>"></script>
            <script src="<c:url value ='/resource/js/jquery.flot.min.js'/>"></script>
            <script src="<c:url value ='/resource/js/jquery.flot.resize.min.js'/>"></script>
            <script src="<c:url value ='/resource/js/jquery.peity.min.js'/>"></script>
            <script src="<c:url value ='/resource/js/fullcalendar.min.js'/>"></script>
            <script src="<c:url value ='/resource/js/unicorn.js'/>"></script>
            <script src="<c:url value ='/resource/js/unicorn.dashboard.js'/>"></script>
            
	</body>
</html>
