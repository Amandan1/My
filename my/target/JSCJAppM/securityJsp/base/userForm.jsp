<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">

    $.extend($.fn.validatebox.defaults.rules, {
        mobile: { //验证手机号
            validator: function(value, param){
                return /^1[34578]\d{9}$/.test(value);
            },
            message: '请填写正确的手机号。'
        },
        mail: { //验证邮箱
            validator: function(value, param){
                return /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value);
            },
            message: '请填写正确的邮箱。'
        }
    });
    var i = 0;
	var uploader;//上传对象
	var submitNow = function($dialog, $grid, $pjq) {
		var url;
		if ($(':input[name="data.id"]').val().length > 0) {
			if($(':input[name="data.id"]').val() == '${sessionScope.sessionInfo.user.id}'){
				url = myhope.contextPath + '/base/user!nssnsc_update.myhope';
			}else{
				url = myhope.contextPath + '/base/user!update.myhope';
			}
		} else {
			url = myhope.contextPath + '/base/user!save.myhope';
		}
		$.post(url, myhope.serializeObject($('form')), function(result) {
			parent.myhope.progressBar('close');//关闭上传进度条

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
			if (uploader.files.length > 0 && i==0) {
				uploader.start();
				uploader.bind('StateChanged', function(uploader) {// 在所有的文件上传完毕时，提交表单
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
			$.post(myhope.contextPath + '/base/user!getById.myhope', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.name' : result.name,
						'data.loginname' : result.loginname,
						'data.sex' : result.sex,
						'data.age' : result.age,
						'data.qq' : result.qq,
						'data.mail' : result.mail,
                        'data.mobile' : result.mobile,
						'data.photo' : result.photo,
                        'data.code' : result.code
					});
					if (result.photo) {
						$('#photo').attr('src', result.photo);
					}
				}
				parent.$.messager.progress('close');
			}, 'json');
		}

		uploader = new plupload.Uploader({//上传插件定义
			browse_button : 'pickfiles',//选择文件的按钮
			container : 'container',//文件上传容器
			runtimes : 'html5,flash',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
			//flash_swf_url : myhope.contextPath + '/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
			url : myhope.contextPath + '/base/user!uploadImg.myhope',//上传文件路径
			max_file_size : '5mb',//100b, 10kb, 10mb, 1gb
			chunk_size : '10mb',//分块大小，小于这个大小的不分块
			unique_names : true,//生成唯一文件名
			// 如果可能的话，压缩图片大小
			/*resize : {
				width : 320,
				height : 240,
				quality : 90
			},*/
			// 指定要浏览的文件类型
			filters : [ {
				title : '图片文件',
				extensions : 'jpg,gif,png'
			} ]
		});
		uploader.bind('Init', function(up, params) {//初始化时
			//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
			$('#filelist').html("");
		});
		uploader.bind('FilesAdded', function(up, files) {//选择文件后
			$.each(files, function(i, file) {
				previewImage(file,callback);
			});
			up.refresh();
		});
		/*图片预览*/
		function callback(src,file){
			$('#pics').append('<span  id="' + file.id + '"><img style="height:90px;" src="'+ src +'"/>'+'<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></span>');
		}
		
		uploader.bind('FilesAdded', function(up, files) {//选择文件后
			$.each(files, function(i, file) {
				$('#filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
			});
			up.refresh();
		});
		uploader.bind('UploadProgress', function(up, file) {//上传进度改变
			var msg;
			if (file.percent == 100) {
				msg = '99';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
			} else {
				msg = file.percent;
			}
			$('#' + file.id + '>strong').html(msg + '%');

			parent.myhope.progressBar({//显示文件上传滚动条
				title : '文件上传中...',
				value : msg
			});
		});
		uploader.bind('Error', function(up, err) {//出现错误
			$('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
			up.refresh();
		});
		
			/*图片预览*/
		 function previewImage(file, callback) {//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
                if (!file || !/image\//.test(file.type)) return; //确保文件是图片
                if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
                    var fr = new mOxie.FileReader();
                    fr.onload = function () {
                        callback(fr.result);
                        fr.destroy();
                        fr = null;
                    }
                    fr.readAsDataURL(file.getSource());
                } else {
                    var preloader = new mOxie.Image();
                    preloader.onload = function () {
                        //preloader.downsize(550, 400);//先压缩一下要预览的图片,宽300，高300
                        var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
                        callback && callback(imgsrc,file); //callback传入的参数为预览图片的url
                        preloader.destroy();
                        preloader = null;
                    };
                    preloader.load(file.getSource());
                }
            }
		uploader.bind('FileUploaded', function(up, file, info) {//上传完毕
			var response = $.parseJSON(info.response);
			if (response.status) {
				$('#' + file.id + '>strong').html("100%");
				//console.info(response.fileUrl);
				//console.info(file.name);
				//$('#f1').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
				//$('#f1').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
				$(':input[name="data.photo"]').val(response.fileUrl);
			}
		});
		uploader.init();

	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>员工基本信息</legend>
			<table class="table" style="width:75%;">
				<input name="data.id" value="${param.id}" readonly="readonly" type="hidden"/>
				<input name="data.code" value="${param.code}" readonly="readonly" type="hidden"/>
				<tr>
					<th>登陆名称</th>
					<td><input name="data.loginname" class="easyui-validatebox" data-options="required:true" /></td>
					<th>姓名</th>
					<td><input name="data.name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>年龄</th>
					<td><input name="data.age" class="easyui-validatebox" /></td>
					<th>性别</th>
					<td>
					<input name="data.sex" class="easyui-combobox" data-options="required:true,valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=sex',width:80,panelHeight:'auto',editable:false" />
					</td>
				</tr>
				<tr>
					<th>qq</th>
					<td><input name="data.qq" class="easyui-validatebox" /></td>
					<th>邮箱</th>
					<td><input name="data.mail" class="easyui-validatebox" data-options="prompt:'请填写正确的邮箱。',validType:'mail'"/></td>
				</tr>
				<tr>
					<th>手机</th>
					<td><input name="data.mobile" class="easyui-validatebox" data-options="required:true,prompt:'请填写正确的手机号。',validType:'mobile'"/></td>
				</tr>
			 
			</table>
		</fieldset>
	</form>
</body>
</html>