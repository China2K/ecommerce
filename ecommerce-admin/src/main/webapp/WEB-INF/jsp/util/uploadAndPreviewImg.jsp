<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script>
	//三个参数必须都是jquery objec, $("")
	//_uploadObj 上传的按钮
	//_picUrlInputObj 存储图片url input
	//_imgPreviewObj 示例图片的 img
	function initUploadAndPreview(_uploadObj, _picUrlInputObj, _imgPreviewObj){
		//商家图片
		var _picUrl = _picUrlInputObj.val();
		if(_picUrl != undefined && _picUrl != ""){
			_imgPreviewObj.attr("src",'${imageUrlPrefix}'+_picUrl);
		}
		
		//上传
		  var url = "/upload/uploadImg.do";
		  _uploadObj.fileupload({
		        url: url,
		        paramName:"imgFile",
		        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
		        maxFileSize: 500000, // 500kb
		        dataType: 'text',
		        done: function (e, data) {
					_imgPreviewObj.attr('src','${imageUrlPrefix}'+data.result);
					_picUrlInputObj.val(data.result);

		        },messages: {
	                acceptFileTypes: '图片类型不合法 ',
	                maxFileSize: '图片大小超过限制'
	            }/*,
		        progressall: function (e, data) {
		            var progress = parseInt(data.loaded / data.total * 100, 10);
		            $('#progress .progress-bar').css(
		                'width',
		                progress + '%'
		            );
		        }*/
		    }).on('fileuploadprocessalways', function (e, data) {
		        var index = data.index,
	            file = data.files[index];
	            if (file.error) {
			           alert(file.error);
			        }
	      
	   			 });
	}
</script>
