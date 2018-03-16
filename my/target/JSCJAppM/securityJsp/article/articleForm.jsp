<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>

<script type="text/javascript">
	var i = 0;
	var uploader; //上传对象
	var submitNow = function($dialog, $grid, $pjq) {
		var url;
		if ($(':input[name="data.id"]').val().length > 0) {
			url = myhope.contextPath + '/article/article!update.myhope';
		} else {
			url = myhope.contextPath + '/article/article!save.myhope';
		}
		$.post(url, myhope.serializeObject($('form')), function(result) {
			parent.myhope.progressBar('close'); //关闭上传进度条

			if (result.success) {
				$pjq.messager.alert('提示', result.msg, 'info');
				$grid.datagrid('load');
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	};
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			if (uploader.files.length > 0 && i == 0) {
				uploader.start();
				uploader.bind('StateChanged', function(uploader) { // 在所有的文件上传完毕时，提交表单
					if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
						// 避免调用action返回错误信息后，重复上传
						i = 1;
						submitNow($dialog, $grid, $pjq);
					}
				});
			} else {
				submitNow($dialog, $grid, $pjq);
			}
		}
	};
	$(function() {

		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(myhope.contextPath + '/article/article!getById.myhope', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.publisher' : result.publisher,
						'data.createDateTime' : result.createDateTime,
						'data.classify.id' : result.classify ? result.classify.id
									 : '',
						'data.title' : result.title,
						'data.delflag' : result.delflag,
						'data.shelves' : result.shelves,
						'data.article' : result.article,
						'data.label' : result.label,
						'data.img' : result.img,
						
						
					});
					if (result.img) {
						// $('#pic').attr('src', myhope.contextPath + result.pic);
						var pics = result.img 
							var img = document.createElement("img");
							img.setAttribute("id", "pic" + i);
							img.src = pics;
							img.style.width = 100 + "%";
							img.style.height = 100 + "%";
							document.getElementById('pics').appendChild(img);
					}
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
		uploader = new plupload.Uploader({ //上传插件定义
			browse_button : 'pickfiles', //选择文件的按钮
			container1 : 'container1', //文件上传容器
			runtimes : 'html5,flash', //设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
			//flash_swf_url : myhope.contextPath + '/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
			url : myhope.contextPath + '/article/article!uploadImg_js.myhope', //上传文件路径
			max_file_size : '5mb', //100b, 10kb, 10mb, 1gb
			chunk_size : '10mb', //分块大小，小于这个大小的不分块
			unique_names : true, //生成唯一文件名
			// 如果可能的话，压缩图片大小
			/*resize : {
				width : 320,
				height : 240,
				quality : 90
			},*/
			// 指定要浏览的文件类型
			filters : [ {
				title : '图片文件',
				extensions : 'jpg,jpeg,gif,png'
			} ]
		});
		uploader.bind('Init', function(up, params) { //初始化时
			//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
			$('#filelist').html("");
		});
		uploader.bind('BeforeUpload', function(uploader, file) { //上传之前
						if (uploader.files.length > 1) {
							parent.$.messager.alert('提示', '只允许选择一张照片！', 'error');
							uploader.stop();
							return;
						}
			$('.ext-icon-cross').hide();
		});
		
		uploader.bind('FilesAdded', function(up, files) {//选择文件后
			$.each(files, function(i, file) {
				previewImage(file, callback);
			});
			up.refresh();
		});
		/*图片预览*/
		function callback(src, file) {
			$('#pics')
					.append(
							'<span  id="' + file.id + '"><img     style="height:90px;" src="'+ src +'"/>'
									+ '<span id="removepic" onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></span>');
		}
		
		uploader.bind('UploadProgress', function(up, file) { //上传进度改变
			var msg;
			if (file.percent == 100) {
				msg = '99'; //因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
			} else {
				msg = file.percent;
			}
			$('#' + file.id + '>strong').html(msg + '%');

			parent.myhope.progressBar({ //显示文件上传滚动条
				title : '文件上传中...',
				value : msg
			});
		});
		
		/*图片预览*/
		function previewImage(file, callback) {//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
			if (!file || !/image\//.test(file.type))
				return; //确保文件是图片
			if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
				var fr = new mOxie.FileReader();
				fr.onload = function() {
					callback(fr.result);
					fr.destroy();
					fr = null;
				}
				fr.readAsDataURL(file.getSource());
			} else {
				var preloader = new mOxie.Image();
				preloader.onload = function() {
					//preloader.downsize(550, 400);//先压缩一下要预览的图片,宽300，高300
					var imgsrc = preloader.type == 'image/jpeg' ? preloader
							.getAsDataURL('image/jpeg', 80) : preloader
							.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
					callback && callback(imgsrc, file); //callback传入的参数为预览图片的url
					preloader.destroy();
					preloader = null;
				};
				preloader.load(file.getSource());
				if($("#pic0").length > 0){
					$("#pic0").hide();
				} 
			}
		}

            
		uploader.bind('Error', function(up, err) { //出现错误
			$('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
			up.refresh();
		});
		
			
            
            
            
		uploader.bind('FileUploaded', function(up, file, info) { //上传完毕
			var response = $.parseJSON(info.response);
			if (response.status) {
				$('#' + file.id + '>strong').html("100%");
				console.info(response.fileUrl);
				console.info(file.name);
				$('#pics').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
				$('#pics').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
				$(':input[name="pic_bak"]').val(document.getElementById("pic_bak").value + response.fileUrl);
				$(':input[name="data.img"]').val(document.getElementById("pic_bak").value);
			}
		});
		uploader.init();
		
	});
</script>
</head>
<body>
	<form method="post" class="form"  >
		<fieldset>
			<legend>文章信息</legend>
			<table class="table" style="width: 100%;">
				<input name="data.id" value="${param.id}" readonly="readonly"
					type="hidden" />
				<input name="data.createDateTime"   readonly="readonly"
					type="hidden" />
				<input name="data.shelves"   readonly="readonly"
					type="hidden" />
				<input name="data.delflag"   readonly="readonly"
					type="hidden" />
				<input name="data.article"   readonly="readonly"
					type="hidden" />
				<tr>
					<th>文章标题</th>
					<td><input name="data.title" class="easyui-validatebox"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<th>发布人</th>
					<td><input name="data.publisher" class="easyui-validatebox"
						data-options="required:false" /></td>
				</tr>
				<tr>
					<th>自定义标签</th>
					<td><input name="data.label" class="easyui-validatebox"
						data-options="required:false" /></td>
				</tr>
				<tr>
					<th>栏目</th>
					<td>
					 <select id="title" name="data.classify.id"
						class="easyui-combotree"
						data-options="required:true,editable:false,idField:'id',textField:'text',parentField:'pid',url:'${pageContext.request.contextPath}/classify/classify!nsc_getClassifytitle.myhope'"
						style="width: 100px;"></select><img class="iconImg ext-icon-cross"
						onclick="$('#title').combotree('clear');" title="清空" />		</td>
				</tr>
				<tr>
					<th>图片</th>
					<td colspan="3">
						<div id="container1">
							<a id="pickfiles" href="javascript:void(0);"
								class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'">选择文件</a>
							<div id="filelist">您的浏览器没有安装Flash插件，或不支持HTML5！</div>
						</div>
					</td>
				</tr>
				<tr>
					<th>图片预览</th>
					<td colspan="4"><input id="data.img" name="data.img"
						readonly="readonly" style="display: none;" /> <input id="pic_bak"
						name="pic_bak" readonly="readonly" style="display: none;" />
						<div id="pics" style="width:450px;"></div>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>