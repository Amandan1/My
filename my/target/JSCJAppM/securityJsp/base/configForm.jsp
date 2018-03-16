<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="data.id"]').val().length > 0) {
				url = myhope.contextPath + '/base/config!update.myhope';
			} else {
				url = myhope.contextPath + '/base/config!save.myhope';
			}
			$.post(url, myhope.serializeObject($('form')), function(result) {
				if (result.success) {
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	$(function() {
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(myhope.contextPath + '/base/config!getById.myhope', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.key' : result.key,
						'data.name' : result.name,
						'data.val' : result.val,
						'data.type' : result.type,
                        'data.createid' : result.createid,
						'data.remark' : result.remark
					});
				}
				parent.$.messager.progress('close');
			}, 'json');
		} else {
			$(':input[name="data.key"]').removeAttr('readonly');
		}
	});
</script>
</head>
<body>
	<form method="post" class="form">
	<input name="data.id" value="${param.id}" type="hidden"/>
	<input name="data.createid" value="${param.createid}" type="hidden"/>
		<fieldset>
			<legend>角色基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>参数编号</th>
					<td><input name="data.key" class="easyui-validatebox" data-options="required:true" readonly="readonly"/></td>
					<th>参数名称</th>
					<td><input name="data.name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>参数值</th>
					<td><input name="data.val" class="easyui-validatebox" data-options="required:true" /></td>
					<th>参数类型</th>
					<td><input name="data.type" class="easyui-combobox" 
								data-options="valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=config_type',panelHeight:'auto',editable:false" /></td>
				</tr>
				<tr>
					<th>参数描述</th>
					<td colspan="3"><textarea name="data.remark" style="width: 500px;height: 100px;max-width: 500px;max-height: 100px;"></textarea></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>