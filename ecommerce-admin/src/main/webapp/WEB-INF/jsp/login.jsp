<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
<title>Admin</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet"
	href="<c:url value ='/resource/css/bootstrap.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/bootstrap-responsive.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value ='/resource/css/unicorn.login.css'/>" />

<script src="<c:url value ='/resource/js/jquery.min.js'/>"></script>
<script src="<c:url value ='/resource/js/jquery.form.js'/>"></script>
    
 <script language="javaScript">

 function login() {

	var pwd = $("#password").val();
	var loginName = $("#loginName").val();
	$("#loginform").ajaxSubmit(
		function(data) {
			if (data.success) {
				window.location.href = "<c:url value ='/index.do'/>";
			}else{
				alert(data.message);
			}
		}
	);
}
 </script>
</head>
<body>
	<div id="logo">
		
	</div>
	<div id="loginbox">
		<form id="loginform" class="form-vertical" action="<c:url value ='/user/login.do'/>" />
		<p>请输入用户名密码继续</p>
		<div class="control-group">
			<div class="controls">
				<div class="input-prepend">
					<span class="add-on"><i class="icon-user"></i></span><input
						type="text" placeholder="用户名" id="userName" name="loginName"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<div class="input-prepend">
					<span class="add-on"><i class="icon-lock"></i></span><input
						type="password" placeholder="密码" id="password" name="password"/>
				</div>
			</div>
		</div>
		<div class="form-actions">
			<span class="pull-right"><input id="loginBtn" type="button" onclick="login();"
				class="btn btn-inverse" value="登陆" /></span>
		</div>
		</form>
	</div>
</body>
</html>
