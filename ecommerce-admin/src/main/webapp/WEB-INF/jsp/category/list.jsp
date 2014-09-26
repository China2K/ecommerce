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
			location.href="<c:url value ='/category/edit.do'/>"+"?id="+id;
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
			url : "<c:url value ='/category/manage.do'/>",
			async : false,
			data : params,
			success : function(data) {
				if (!data.success) {
					alert(data.message);
				} else {
					alert("成功");
					location.href = "<c:url value ='/category/listAll.do'/>";
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
				class="icon-home"></i>主页</a> <a href="#" class="current">分类</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<button class="btn btn-success"
					onclick="javascript:window.location.href='<c:url value ='/category/edit.do'/>'">
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
							<h5>产品列表</h5>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered data-table ">
								<thead>
									<tr>
										<th style="width: 10px;"><i class="icon-resize-vertical"></i></th>
										<th>类别名称</th>
										<th>排序</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${categories}" var="category">
										<tr class="gradeX">
											<td><span><input type="checkbox" /></span></td>
											<td>${category.name}</td>
											<td>${category.order}</td>
											<td><c:choose>
													<c:when test="${category.status=='A'}">激活的</c:when>
													<c:when test="${category.status=='B'}">已下架</c:when>
												</c:choose>
											</td>
											<td>
												<p>
													<button class="btn btn-mini">
														<i class="icon-eye-open"></i> 查看
													</button>
													<button class="btn btn-primary btn-mini" onclick="editProd('${category.id}');">
														<i class="icon-pencil icon-white"></i> 编辑
													</button>
													<button class="btn btn-danger btn-mini"
														onclick="setStatus('${category.id}','${category.status}','D');">
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
