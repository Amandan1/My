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
        }
    });
	var i = 0;
	var submitNow = function($dialog, $grid, $pjq) {
		var url;
		if ($(':input[name="data.id"]').val().length > 0) {
			url = myhope.contextPath + '/classify/classify!update.myhope';
		} else {
			url = myhope.contextPath + '/classify/classify!save.myhope';
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
				submitNow($dialog, $grid, $pjq);
	};
	$(function() {
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			var type = ${param.type};
			
			var ids=$("#id").val();
			$.post(myhope.contextPath + '/classify/classify!getById.myhope', {
				id : ids
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.createdatetime' : result.createdatetime,
						'data.title':result.title,
						'data.index':result.index,
						'data.defaults':result.defaults
					});
				}
				if(type && type == 1){
					$("#title").parent().html($("#title").val());
					$("#title").hide();
					$("#index").parent().html($("#index").val());
					$("#index").hide();
					$("#defaults").parent().html(myhope.translateMetadata('defaults',result.defaults));
					$("#defaults").hide();
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
	
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>栏目信息</legend>
			<table class="table" style="width: 100%;">
				<input id="id" name="data.id" value="${param.id}" type="hidden"/>
				<tr>
					<th>栏目</th>
					<td  style="width:80%;line-height: 25px;"><input id= 'title' name="data.title" class="easyui-validatebox"  data-options="required:true"  /></td>
				</tr>
				<tr>
				  	<th>排序</th>
					<td style="width:80%;line-height: 25px;"><input id="index" name="data.index"     class="easyui-numberbox" max="100"   value=""/></td>
				</tr>
				<tr>
					<th>默认</th>
					<td><input id="defaults"  name="data.defaults" class="easyui-combobox"
						data-options="required:true,valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=defaults',width:80,panelHeight:'auto',editable:false" />
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>