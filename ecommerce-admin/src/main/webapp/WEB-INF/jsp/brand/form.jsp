<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
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
	href="<c:url value ='/resource/css/datepicker.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/uniform.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/select2.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/unicorn.main.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/unicorn.grey.css'/>"
	class="skin-color" />
	
<link rel="stylesheet"
	href="<c:url value ='/resource/css/extra.css'/>" />
	
 <link rel="stylesheet" href="<c:url value ='/resource/jQuery-File-Upload/css/jquery.fileupload.css'/>">

<style type="text/css">
.w_150{
width:150px !important;
}

</style>

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
					<div class="span12">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon">
									<i class="icon-align-justify"></i>									
								</span>
								<h5>品牌编辑 </h5>
							</div>
							<div class="widget-content nopadding">
								<s:form  id="brandForm" name="brandForm" method="post" action="${contextPath}/brand/edit.do" modelAttribute="brand" class="form-horizontal" >
									<s:hidden path="id" id="brandId"/>
									
									<div class="control-group">
										<label class="control-label">名称</label>
										<div class="controls">
											<s:input path="name" id="brand_name"/>
										</div>
									</div>
                                                                         
									<div class="control-group">
										<label class="control-label">网址</label>
										<div class="controls">
											<s:input path="website" cssClass="w_150" id="brand_website"/>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">电话</label>
										<div class="controls">
											<s:input path="phone" cssClass="w_150" id="brand_phone"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">传真</label>
										<div class="controls">
											<s:input path="fax" cssClass="w_150" id="brand_fax"/>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">邮箱</label>
										<div class="controls">
											<s:input path="email" cssClass="w_150" id="brand_email"/>
										</div>
									</div>
									
									
									<div class="control-group">
										<label class="control-label">简介</label>
										<div class="controls">
												<s:textarea path="desc" id="brand_desc" style="width:300px;height:180px;"/>
										</div>
									</div>
									
									<div class="form-actions">
										<input type="button" onclick="saveBrand();" value="保存" class="btn btn-primary"/>
									</div>
									
									
								</s:form>
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
	<script src="<c:url value ='/resource/js/bootstrap-colorpicker.js'/>"></script>
	<script src="<c:url value ='/resource/js/bootstrap-datepicker.js'/>"></script>
	<script src="<c:url value ='/resource/js/jquery.uniform.js'/>"></script>
	<script src="<c:url value ='/resource/js/select2.min.js'/>"></script>
	 <script src="<c:url value ='/resource/js/jquery.validate.js'/>"></script>
	<script src="<c:url value ='/resource/js/unicorn.js'/>"></script>
	<script src="<c:url value ='/resource/js/unicorn.form_common.js'/>"></script>
            
 	<!-- jquery form 异步提交表单 -->
    <script src="<c:url value ='/resource/js/jquery.form.js'/>"></script>
	<!-- jquery file upload -->

	<script src=" <c:url value ='/resource/jQuery-File-Upload/js/vendor/jquery.ui.widget.js'/>"></script>
	<script src=" <c:url value ='/resource/jQuery-File-Upload/js/jquery.iframe-transport.js'/>"></script>
	<script src=" <c:url value ='/resource/jQuery-File-Upload/js/jquery.fileupload.js'/>"></script>
	<script src=" <c:url value ='/resource/jQuery-File-Upload/js/jquery.fileupload-process.js'/>"></script>
	<script src="<c:url value ='/resource/jQuery-File-Upload/js/jquery.fileupload-validate.js'/>"></script>
	<!-- 配置文件 -->
	<script src="<c:url value ='/resource/ueditor/ueditor.config.js'/>"></script>
	<!-- 编辑器源码文件 -->
	<script src="<c:url value ='/resource/ueditor/ueditor.all.js'/>"></script>
	<script src="<c:url value ='/resource/ueditor/lang/zh-cn/zh-cn.js'/>"></script>         
	
	 <script type="text/javascript">

	//regex start
		var checkchar=/^[a-z,A-Z]+$/;
		var checkNumChar = /^[A-Za-z0-9]+$/;
		var checkNum = /^[0-9]+$/;
		var checkDouble=/^\d{0,8}(\.\d{0,2})?$/;
		

	 function validateBrand(){
		 var name=$("#brand_name").val();
		 if(name==null||name==""){
				alert("名称为必填项，请填写完整。");
				return false;
			}
		if(name.length>100){
			alert("名称长度最多为100.");
			return false;
			}

			return true;
		}
		function saveBrand(){
			if(validateBrand()){
				
				$("#brandForm").ajaxSubmit(
					function(data) {
						if (data.success) {
							location.href="<c:url value ='/brand/listAll.do'/>";
						}else{
							alert(data.message);
							}
					}
				);
			}
		}

	 </script>   	
</body>
</html>
