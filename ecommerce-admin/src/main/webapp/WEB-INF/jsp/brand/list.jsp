<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>管理平台</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet"
	href="<c:url value ='/resource/css/bootstrap.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/bootstrap-responsive.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/uniform.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/select2.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/unicorn.main.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/unicorn.grey.css'/>"
	class="skin-color" />


<script type="text/javascript">
	function editProd(id){
			location.href="<c:url value ='/brand/edit.do'/>"+"?id="+id;
		}
	function setStatus(id, oldStatus, newStatus) {
		if (oldStatus == newStatus) {
			alert("已经是该状态，无执行需操作");
			return;
		}
		var params = {
			"id" : id,
			"status" : newStatus
		};
		$.ajax({
			type : "POST",
			url : "<c:url value ='/brand/manage.do'/>",
			async : false,
			data : params,
			success : function(data) {
				if (!data.success) {
					alert(data.message);
				} else {
					alert("成功");
					location.href = "<c:url value ='/brand/listAll.do'/>";
				}
			}
		});
	}
</script>

</head>


<body>


	<jsp:include page="../common/header.jsp"></jsp:include>
	<jsp:include page="../common/sidebar.jsp"></jsp:include>


	<div id="content">
		<div id="content-header">
			<h1>概览</h1>
			<div class="btn-group">
				<a class="btn btn-large tip-bottom" title="客户管理"><i
					class="icon-user"></i></a> <a class="btn btn-large tip-bottom"
					title="订单管理"><i class="icon-shopping-cart"></i></a>
			</div>
		</div>

		<div id="breadcrumb">
			<a href="#" title="Go to Home" class="tip-bottom"><i
				class="icon-home"></i>主页</a> <a href="#" class="current">品牌</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<button class="btn btn-success"
					onclick="javascript:window.location.href='<c:url value ='/brand/edit.do'/>'">
					<i class="icon-plus"></i> 新增
				</button>
			</div>
			<div class="row-fluid">
				<div class="span12">

					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <input type="checkbox"
								id="title-checkbox" name="title-checkbox" />
							</span>
							<h5>品牌列表</h5>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered data-table ">
								<thead>
									<tr>
										<th style="width: 10px;"><i class="icon-resize-vertical"></i></th>
										<th>名称</th>
										<th>网址</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${brands}" var="brand">
										<tr class="gradeX">
											<td><span><input type="checkbox" /></span></td>
											<td>${brand.name}</td>
											<td>${brand.website}</td>
											<td><c:choose>
													<c:when test="${brand.status=='A'}">激活的</c:when>
													<c:when test="${brand.status=='B'}">已下架</c:when>
												</c:choose>
											</td>
											<td>
												<p>
													<button class="btn btn-mini">
														<i class="icon-eye-open"></i> 查看
													</button>
													<button class="btn btn-success btn-mini"
														onclick="setStatus('${brand.id}','${brand.status}','A');">
														<i class="icon-arrow-up"></i> 上架
													</button>
													<button class="btn btn-warning btn-mini"
														onclick="setStatus('${brand.id}','${brand.status}','B');">
														<i class="icon-arrow-down"></i> 下架
													</button>
													<button class="btn btn-primary btn-mini" onclick="editProd('${brand.id}');">
														<i class="icon-pencil icon-white"></i> 编辑
													</button>
													<button class="btn btn-danger btn-mini"
														onclick="setStatus('${brand.id}','${brand.status}','D');">
														<i class="icon-remove icon-white"></i> 删除
													</button>
												</p>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>

			<jsp:include page="../common/footer.jsp"></jsp:include>
		</div>
	</div>


	<script src="<c:url value ='/resource/js/jquery.min.js'/>"></script>
	<script src="<c:url value ='/resource/js/jquery.ui.custom.js'/>"></script>
	<script src="<c:url value ='/resource/js/bootstrap.min.js'/>"></script>
	<script src="<c:url value ='/resource/js/jquery.uniform.js'/>"></script>
	<script src="<c:url value ='/resource/js/select2.min.js'/>"></script>
	<script src="<c:url value ='/resource/js/jquery.dataTables.min.js'/>"></script>
	<script src="<c:url value ='/resource/js/unicorn.js'/>"></script>
	<script src="<c:url value ='/resource/js/unicorn.tables.js'/>"></script>
	
</body>
</html>
