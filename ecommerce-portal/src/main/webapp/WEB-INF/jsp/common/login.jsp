<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<!--fullpage:true-->
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="viewport" content="width=1024,minimum-scale=1.0,maximum-scale=1.0"/>
    <meta charset="UTF-8">
    <title>登录yy网站</title>
    <!-- 最新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="<c:url value ='/resource/themes/bootstrap/css/bootstrap.min.css'/>">

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="<c:url value ='/resource/js/jquery-1.9.1.min.js'/>"></script>
    <script src="<c:url value ='/resource/js/jquery.easing.min.js'/>"></script>
    
    <script src="<c:url value ='/resource/js/jquery.form.js'/>"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="<c:url value ='/resource/themes/bootstrap/js/bootstrap.min.js'/>"></script>
    
    <!-- jQuery-Validation-Engine -->
    <link rel="stylesheet" href="<c:url value ='/resource/js/jQuery-Validation-Engine/css/validationEngine.jquery.css'/>">
    <script src="<c:url value ='/resource/js/jQuery-Validation-Engine/js/jquery.validationEngine-zh_CN.js'/>"></script>
    <script src="<c:url value ='/resource/js/jQuery-Validation-Engine/js/jquery.validationEngine.min.js'/>"></script>

    <!-- TODO @lutz 主页面css文件 -->
    <link rel="stylesheet" href="<c:url value ='/resource/themes/default/main.css'/>">
  
    <script language="JavaScript"><!--
	
	$(document).ready(function(){
		$("#loginForm").validationEngine();
	}); 
	
	
	function enableLoginBtn(){
		$("#loginBtn").attr("disabled",false);
		$("#loginBtn").val("立即登录");
	}
	
	function disableLoginBtn(){
		$("#loginBtn").attr("disabled",true);
		$("#loginBtn").val("登录中...");
	}
	
	function login() {
		
		 if (!$("#loginForm").validationEngine('validate')) {
                return false;
         }
	
		var pwd = $("#password").val();
		var loginName = $("#loginName").val();
		 disableLoginBtn();
		$("#loginForm").ajaxSubmit(
			function(data) {
				if (data.success) {
					window.location.href = "<c:url value ='/index.do'/>";
				}else{
					alert(data.message);
					enableLoginBtn();
				}
			}
		);
	}
	
	
	
	document.onkeydown = function(evt){
   　 		var evt = window.event?window.event:evt;
    　		if (evt.keyCode==13) {
           login();
        }
   	};
</script>

</head>
<body>
    <div class="header">
        <div class="container">
            <div class="row">
                <div class="col-xs-2 sidebar">
                    <a href="${contextPath}/index.do" class="logo"></a>
                </div>
                <div class="col-xs-10">
					<div class="pull-right welcInfo">
                        <span class="ml20"><a href="<c:url value='/register.do'/>">注册</a></span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container wrap">
        <div class="row">
            <div class="col-xs-10 mainCont col-xs-offset-1">
                <div class="conditionBox clearfix mt10 mb10"> </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">登录微聚商</div>
                    </div>
                    <div class="panel-body login">
                       <form class="form-horizontal" action="<c:url value='/user/login.do'/>" id="loginForm" method="post">
                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">用户名</label>
                                <div class="col-xs-4">
                                    <input type="text" class="validate[required] form-control" id="loginName" name="loginName"  placeholder="用户名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">密码</label>
                                <div class="col-xs-4">
                                    <input type="password" class="validate[required] form-control" id="password" name="password" placeholder="密码">
                                </div>
                            </div>
                            <!--<div class="form-group">
                                <label for="" class="col-xs-3 control-label">验证码</label>
                                <div class="col-xs-2">
                                    <input type="text" class="form-control" id="" placeholder="验证码">
                                </div>
                                <div class="col-xs-2">验证码图片</div>
                            </div>-->
                            <div class="form-group">
                                <div class="col-xs-4 col-xs-offset-3">
                                   <input id="loginBtn" type="button" onclick="login();" value="立即登录" class="btn btn-warning btn-lg btn-block"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <p class="text-center">版权所有 ©</p>
    </div>

<script src="<c:url value='/resource/js/common.js'/>"></script>
<script>
$(function(){
    $("#vType").change(function(){
        if($(this).val()==1){$("#textType").hide();$("#tImgType").show();}
        else{$("#textType").show();$("#tImgType").hide();}
    })
})

window.onload = window.onresize = function(){
    var headerH=$(".header").height();
    var footerH = $(".footer").height();
    var allHeight = $("body").height();
    var containerHeight = headerH + $(".wrap").height();
    if(containerHeight < allHeight){
        $(".wrap").height(allHeight-headerH-footerH);
        $(".footer").addClass("fixed");
    }
    else{
        $(".wrap").css("height","auto");
        $(".footer").removeClass("fixed");
    }
}
</script>
</body>
</html>