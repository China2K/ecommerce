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

#ImgListDiv ul {
	padding:0;
	list-style-type:none;
	text-align: center;
	padding-left: 0px;
}
#ImgListDiv ul li {
	float:left;
	position:relative;
	padding-right:17px;
}
#ImgListDiv ul li img{
	width:122px;
	height:110px;
}

#ImgListDiv ul li.last {
	padding:0;
}
#ImgListDiv .uploadimg {
	background:url(${contextPath}/resource/img/uploadtxt-bg.png) no-repeat 0 0;
	bottom:45px;
	display: block;
	height: 21px;
	margin: 0 8px;
	padding: 3px 0;
	text-align:center;
	color:#fff;
	position: absolute;
	width: 110px;
	left: 0;
}

#ImgListDiv .uploadimg .urlImgUpload{
cursor: pointer;
}
#ImgListDiv .uploadimg a{
cursor:pointer;
color: #5bb75b;
}

#ImgListDiv ul li {	
	width: 126px;
	height: 115px;
	text-align: center;
	float:left;
	position:relative;
	padding-right:12px;
}

</style>

<script type="text/javascript">

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
				class="icon-home"></i>主页</a> <a href="#" class="current">产品</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
					<div class="span12">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon">
									<i class="icon-align-justify"></i>									
								</span>
								<h5>产品编辑 </h5>
							</div>
							<div class="widget-content nopadding">
								<s:form  id="productForm" name="productForm" method="post" action="${contextPath}/product/edit.do" modelAttribute="product" class="form-horizontal" >
									<s:hidden path="id" id="productId"/>
									<div class="control-group">
										<label class="control-label">分类 </label>
										<div class="controls">
										<s:select path="category.id" class="w_150" id="categoryId">
											<c:if test="${category.id==null||category.id=='' }">
												 <s:option value="-1" label="请选择一个类别" selected="selected"/>  
											 </c:if>
											 <s:options items="${categories}" itemLabel="name" itemValue="id"/>  
										</s:select>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">品牌</label>
										<div class="controls">
										
											<s:select path="brand.id" class="w_150" id="brandId">
												<c:if test="${brand.id==null||brand.id=='' }">
													 <s:option value="-1" label="请选择一个品牌" selected="selected"/>  
												</c:if>
												 <s:options items="${brands}" itemLabel="name" itemValue="id"/>  
											</s:select>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">名称</label>
										<div class="controls">
											<s:input path="name" id="prod_name"/>
										</div>
									</div>
                                                                         
                                    <div class="control-group">
                                        <label class="control-label">生产日期</label>
                                        <div class="controls w_150">
                                            <s:input path="yearMade" data-date-format="yyyy-mm-dd" class="datepicker"/>
                                        </div>
                                    </div>
									<div class="control-group">
										<label class="control-label">价格信息</label>
										
										<div class="controls">
											单价:&nbsp;&nbsp;<s:input path="unitPrice" cssClass="w_150" id="unitPrice"/>
											&nbsp;&nbsp;<s:checkbox path="isDiscount" id="isDiscount" accesskey="true"/>是否打折
											&nbsp;&nbsp;售价:&nbsp;&nbsp;<s:input path="sellPrice" cssClass="w_150" id="sellPrice"/>
										</div>
										
									</div>
									<div class="control-group">
										<label class="control-label">库存信息</label>
										<div class="controls">
											库存量：<s:input path="stock" cssClass="w_150" id="prod_stock"/>
											库存单位：<s:select path="sku">
											      <s:option value="张"></s:option>
											      <s:option value="件"></s:option>
											      <s:option value="个"></s:option>
											      <s:option value="份"></s:option>
											      </s:select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">编号信息</label>
										<div class="controls">
											型号：<s:input path="modelNo" id="modelNo" cssClass="w_150"/>
											序列号：<s:input path="serialNo" id="serialNo" cssClass="w_150"/>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">图片展示</label>
										<div class="controls">
											<span class="btn btn-success fileinput-button">
										        <i class="glyphicon glyphicon-plus"></i>
										        <span>选择图片</span>
										        <!-- The file input field used as target for the file upload widget -->
										        <input id="fileupload" type="file" class="btn-success"/>
										    </span>
										</div>
										<div class="controls" id="ImgListDiv">
											<ul>
												<li id="img1">
												<c:choose>
														<c:when test="${product.productImg1!=null&& product.productImg1!=''}">
															<img src="${imageUrlPrefix}${product.productImg1}"  />
															<div class="uploadimg">
																<a href="javascript:removeImg(1)">删除</a>
															</div> 
														</c:when>
														<c:otherwise>
															<img src="${contextPath}/resource/img/blank-upload-img.jpg"  />
															<div class="uploadimg">
																<a>待上传</a>
															</div> 
														</c:otherwise>
													</c:choose>
													<s:hidden  path="productImg1" id="productImg1"/>
												</li>
												<li id="img2">
													<c:choose>
														<c:when test="${product.productImg2!=null && product.productImg2!=''}">
															<img src="${imageUrlPrefix}${product.productImg2}"  />
															<div class="uploadimg">
																<a href="javascript:removeImg(2)">删除</a>
															</div> 
														</c:when>
														<c:otherwise>
															<img src="${contextPath}/resource/img/blank-upload-img.jpg"  />
															<div class="uploadimg">
																<a>待上传</a>
															</div> 
														</c:otherwise>
													</c:choose>
													
													<s:hidden  path="productImg2" id="productImg2"/>
												</li>
												<li id="img3">
													<c:choose>
														<c:when test="${product.productImg3!=null and product.productImg3!=''}">
															<img src="${imageUrlPrefix}${product.productImg3}"  />
															<div class="uploadimg">
																<a href="javascript:removeImg(3)">删除</a>
															</div> 
														</c:when>
														<c:otherwise>
															<img src="${contextPath}/resource/img/blank-upload-img.jpg"  />
															<div class="uploadimg">
																<a>待上传</a>
															</div> 
														</c:otherwise>
													</c:choose>
													<s:hidden  path="productImg3" id="productImg3"/>
												</li>
												<li id="img4">
													<c:choose>
														<c:when test="${product.productImg4!=null && product.productImg4!=''}">
															<img src="${imageUrlPrefix}${product.productImg4}"  />
															<div class="uploadimg">
																<a href="javascript:removeImg(4)">删除</a>
															</div> 
														</c:when>
														<c:otherwise>
															<img src="${contextPath}/resource/img/blank-upload-img.jpg"  />
															<div class="uploadimg">
																<a>待上传</a>
															</div> 
														</c:otherwise>
													</c:choose>
													<s:hidden  path="productImg4" id="productImg4"/>
												</li>
							
												<li class="last" id="img5">
													<c:choose>
														<c:when test="${product.productImg5!=null and product.productImg5!=''}">
															<img src="${imageUrlPrefix}${product.productImg5}"  />
															<div class="uploadimg">
																<a href="javascript:removeImg(5)">删除</a>
															</div> 
														</c:when>
														<c:otherwise>
															<img src="${contextPath}/resource/img/blank-upload-img.jpg"  />
															<div class="uploadimg">
																<a>待上传</a>
															</div> 
														</c:otherwise>
													</c:choose>
													<s:hidden  path="productImg5" id="productImg5"/>
												</li>
											</ul>
										</div>
									</div>
									
									<div class="control-group">
										<label class="control-label">详细信息</label>
										<div class="controls">
												<s:textarea path="desc" id="producteditor" style="width:600px;height:300px;"/>
										</div>
									</div>
									
									<div class="form-actions">
										<input type="button" onclick="saveProd();" value="保存" class="btn btn-primary"/>
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
		
	//实例化加载编辑器
	 var ue = new UE.ui.Editor({
			textarea:'desc'
			});
	
	 ue.render('producteditor');

	 function removeImg(imgNum){
			var srcDocuName="#img"+imgNum+" img";
			var valDocuName="#productImg"+imgNum;
			var divDocName="#img"+imgNum+" .uploadimg a";
			$(srcDocuName).attr('src','${contextPath}/resource/img/blank-upload-img.jpg');
			$(valDocuName).val(null);
			$(divDocName).html("待上传");
		 }
	 $(function(){
			//上传
		  var url = "/ecom-admin/upload/uploadImg.do";
		  $("#fileupload").fileupload({
		        url: url,
		        paramName:"imgFile",
		        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
		        maxFileSize: 5000000, // 5m
		        dataType: 'text',
		        done: function (e, data) {
					var imgNum=1;				
					var isUpload=true;
					var idName;
					while(isUpload && imgNum<6)
						{								
							idName ="productImg"+imgNum;													
							if(document.getElementById(idName).value!="") {
								imgNum++;}
							else {isUpload=false;}
						}
					var srcDocuName="#img"+imgNum+" img";
					var valDocuName="#"+idName;
					var divDocName="#img"+imgNum+" .uploadimg a";
					$(srcDocuName).attr('src','${imageUrlPrefix}'+data.result);
					$(valDocuName).val(data.result);
					
					$(divDocName).html("删除");
					$(divDocName).attr("href","javascript:removeImg('"+imgNum+"')");

		        },messages: {
	                acceptFileTypes: '图片类型不合法 ',
	                maxFileSize: '图片大小超过限制'
	            }
		    }).on('fileuploadprocessalways', function (e, data) {
		        var index = data.index,
	            file = data.files[index];
	            if (file.error) {
			           alert(file.error);
			        }
	      
	   			 });

		//upload over
		
		
		
		  $("#unitPrice").blur(function(){
					var unitPrice= $("#unitPrice").val();
					if(!checkDouble.test(unitPrice)){
						alert("请输入正确的单价");
						}
					else{
						if(!($("#isDiscount").attr("checked")=="checked")){
							$("#sellPrice").val($("#unitPrice").val());
							}
						}
					
				});
			 

		 });

	
	function validateLength(){
		//TODO 长度验证
	}
	 function validateProd(){
		 var categ=$("#categoryId").val();
		 if(categoryId==null||categoryId==""){
				alert("请选择类别。");
				return false;
			}
		 var name=$("#prod_name").val();
		 if(name==null||name==""){
				alert("名称为必填项，请填写完整。");
				return false;
			}
		if(name.length>100){
			alert("名称长度最多为100.");
			return false;
			}

		 var stock=$("#prod_stock").val();
		 if(stock==null||stock==""||!checkNum.test(stock)){
				alert("库存为必填项，请填写正确。");
				return false;
			}
		 var unitPrice=$("#unitPrice").val();
		 var sellPrice=$("#sellPrice").val();


		 var modelNo=$("#modelNo").val();
		 var serialNo=$("#serialNo").val();

		 if(modelNo.length>30||serialNo.length>30){
				alert("编号长度最多为30.");
				return false;
				}

		if(unitPrice==''||sellPrice==''||!checkDouble.test(unitPrice)||!checkDouble.test(sellPrice)){
			alert("请填写正确的价格信息。");
			return false;
			}
		

		 var textcon=ue.getContent();
			if(textcon==null||textcon==""){
				alert("请填写产品详细信息。");
				return false;
			}

			return true;
		}
		function saveProd(){
			if(validateProd()){
				//兼容性（IE9）：清空input file下的上传内容，以免IE9保存弹出下载框
				$("#fileupload").replaceWith($("#fileupload").clone(true));
				
				$("#productForm").ajaxSubmit(
					function(data) {
						if (data.success) {
							location.href="<c:url value ='/product/listAll.do'/>";
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
