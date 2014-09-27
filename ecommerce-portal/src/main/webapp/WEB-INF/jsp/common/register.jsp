<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="viewport" content="width=1024,minimum-scale=1.0,maximum-scale=1.0"/>
    <meta charset="UTF-8">
    <title>注册微聚商</title>
     <!-- 最新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="<c:url value ='/resource/themes/bootstrap/css/bootstrap.min.css'/>">

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="<c:url value ='/resource/js/jquery-1.9.1.min.js'/>"></script>
    <script src="<c:url value ='/resource/js/jquery.easing.min.js'/>"></script>
    
    <script src="<c:url value ='/resource/js/jquery.form.js'/>"></script>

 	<!-- jQuery-Validation-Engine -->
    <link rel="stylesheet" href="<c:url value ='/resource/js/jQuery-Validation-Engine/css/validationEngine.jquery.css'/>">
    <script src="<c:url value ='/resource/js/jQuery-Validation-Engine/js/jquery.validationEngine-zh_CN.js'/>"></script>
    <script src="<c:url value ='/resource/js/jQuery-Validation-Engine/js/jquery.validationEngine.min.js'/>"></script>
	
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="<c:url value ='/resource/themes/bootstrap/js/bootstrap.min.js'/>"></script>

    <!-- TODO @lutz 主页面css文件 -->
    <link rel="stylesheet" href="<c:url value ='/resource/themes/default/main.css'/>">
    <!-- ie9以下版本 所使用的样式，注意放到MAIN.CSS后面 -->
	<!--[if lt IE 9]><link rel="stylesheet" href="${contextPath}/resource/themes/default/ie8.css"><![endif]-->
	
	<script type='text/javascript'>
		$(document).ready(function(){
			$("#registerForm").validationEngine();
		}); 
	
		$(function(){
			initRandCodeImg();
		});
		
		function enableRegBtn(){
			$("#regBtn").attr("disabled",false);
			$("#regBtn").val("立即注册");
		}
		
		function disableRegBtn(){
			$("#regBtn").attr("disabled",true);
			$("#regBtn").val("注册中...");
		}
		
		function initRandCodeImg(){
			var now = new Date(); 
			document.getElementById('codeImg').src = "makeRandCode.do";
		}
		
		function register() {
		 	if (!$("#registerForm").validationEngine('validate')) {
                return false;
         	}
		
			var loginName = $("input[name='loginName']").val();
			/*if ($.trim(loginName) == "") {
				alert("用户名不能为空!");
				return false;
			}*/
			
			var pwd = $("input[name='password']").val();
			/*if ($.trim(pwd) == "") {
				alert("密码不能为空!");
				return false;
			}*/
			
			var rptpwd = $("input[name='rptPwd']").val();
			/*if ($.trim(rptpwd) == "") {
				alert("确认密码不能为空!");
				return false;
			}*/
	
			/*if($.trim(pwd)!=$.trim(rptpwd)){
				alert("两次输入的密码不一致!");
				return false;
			}*/
	
			var mobilePhone = $("input[name='mobilePhone']").val();
			/*if ($.trim(mobilePhone) == "") {
				alert("手机号码不能为空!");
				return false;
			}*/
			
			var email = $("input[name='email']").val();
			/*if ($.trim(email) == "") {
				alert("邮箱不能为空!");
				return false;
			}*/
	
			var randCode = $("input[name='randCode']").val();
			/*if ($.trim(randCode) == "") {
				alert("验证码不能为空!");
				return false;
			}*/
			disableRegBtn();
			$("#registerForm").ajaxSubmit(
				function(data) {
					if (data.success) {
						alert("注册成功");
						window.location.href = "<c:url value ='/index.do'/>";
					}else{
						enableRegBtn();
						if(data.message == "402"){
							alert("验证码已过期");
							initRandCodeImg();
						}else{
							alert(data.message);
						}
					}
				}
			);
		}
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
                        <span class="ml20"><a href="<c:url value='/login.do'/>">登录</a></span>
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
                        <div class="panel-title">注册微聚商</div>
                    </div>
                    <div class="panel-body reg">
                        <form class="form-horizontal" action="<c:url value='/merchant/register.do'/>" id="registerForm" method="post">
                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">用户名</label>
                                <div class="col-xs-4">
                                    <input type="text" class="validate[required,minSize[6],maxSize[16],custom[userNameRegx]] form-control" name="loginName" placeholder="用户名">
                                </div>
                                <div class="col-xs-5"><span class="help-inline"><s>*</s> 长度为6~16位字符，可以为“数字/字母/中划线/下划线”组成</span></div>
                            </div>
                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">设置密码</label>
                                <div class="col-xs-4">
                                    <input type="password" class="validate[required,minSize[6],maxSize[16]] form-control" name="password" id="password" placeholder="设置密码">
                                </div>
                                <div class="col-xs-5"><span class="help-inline"><s>*</s> 长度为6~16位字符</span></div>
                            </div>
                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">确认密码</label>
                                <div class="col-xs-4">
                                    <input type="password" class="validate[required,equals[password]] form-control" name="rptPwd" placeholder="确认密码">
                                </div>
                                <div class="col-xs-5"><span class="help-inline"><s>*</s> </span></div>
                            </div>
                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">手机</label>
                                <div class="col-xs-2">
                                    <input type="text" class="validate[required,custom[phone]] form-control" name="mobilePhone" placeholder="手机">
                                </div>
                                <div class="col-xs-5"><span class="help-inline"><s>*</s> 请输入正确的手机号码</span></div>
                            </div>
                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">邮箱</label>
                                <div class="col-xs-3">
                                    <input type="text" class="validate[required,custom[email]] form-control" name="email" placeholder="邮箱">
                                </div>
                                <div class="col-xs-5"><span class="help-inline"><s>*</s> 邮箱将与支付及优惠相关，请填写正确的邮箱</span></div>
                            </div>
                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">QQ</label>
                                <div class="col-xs-2">
                                    <input type="text" class="form-control" name="qq" placeholder="QQ号码">
                                </div>
                                <div class="col-xs-5"></div>
                            </div>
                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">详细地址</label>
                                <div class="col-xs-6 form-inline">
										<select id="province" name="province" class="form-control">
											<option value="-1">
												请选择省级
											</option>
										</select>
										<select id="city" name="city" class="form-control">
											<option value="-1">
												请选择市级
											</option>
										</select>
										<select id="area" name="county" class="form-control">
											<option value="-1">
												请选择县级
											</option>
										</select>
									</div>
                            </div>

                            <div class="form-group">
                                <label for="" class="col-xs-3 control-label">验证码</label>
                                <div class="col-xs-2">
                                    <input type="text" class="validate[required] form-control" name="randCode" placeholder="验证码">
                                </div>
                                <div class="col-xs-2"><img id="codeImg" height="26px" width="69" src="resource/themes/default/images/randCodeLoading2.gif" style="cursor:pointer;vertical-align:middle;" title="点击更换验证码" onclick="initRandCodeImg();" /></div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-4 col-xs-offset-3">
                                    <input id="regBtn" class="btn btn-warning btn-lg btn-block" type="button" onclick="register();" value="立即注册"></input>
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

<script type="text/javascript" src="${contextPath}/resource/js/util/province.js" ></script>
<script>

</script>
</body>
</html>