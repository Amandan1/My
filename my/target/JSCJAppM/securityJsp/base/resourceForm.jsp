<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="data.id"]').val().length > 0) {
				url = myhope.contextPath + '/base/resource!update.myhope';
			} else {
				url = myhope.contextPath + '/base/resource!save.myhope';
			}
			$.post(url, myhope.serializeObject($('form')), function(result) {
				if (result.success) {
					$grid.treegrid('reload');
					$dialog.dialog('destroy');
					$mainMenu.tree('reload');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	var showIcons = function() {
		var dialog = parent.myhope.modalDialog({
			title : '浏览小图标',
			url : myhope.contextPath + '/style/icons.jsp',
			buttons : [ {
				text : '确定',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.selectIcon(dialog, $('#iconCls'));
				}
			} ]
		});
	};
	$(function() {
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(myhope.contextPath + '/base/resource!getById.myhope', {
				id : $(':input[name="data.id"]').val(),
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.name' : result.name,
						'data.url' : result.url,
						'data.resourcetype.id' : result.resourcetype.id,
						'data.description' : result.description,
						'data.resource.id' : result.resource ? result.resource.id : '',
						'data.iconCls' : result.iconCls,
						'data.seq' : result.seq,
						'data.target' : result.target
					});
					$('#iconCls').attr('class', result.iconCls);//设置背景图标
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
			<legend>资源基本信息</legend>
			<input name="data.id" value="${param.id}" readonly="readonly" type="hidden" />
			<table class="table" style="width: 100%;">
				<tr>
					<th>资源名称</th>
					<td><input name="data.name" class="easyui-validatebox" data-options="required:true" /></td>
					<th>资源路径</th>
					<td><input name="data.url" /></td>
				</tr>
				<tr>
					<th>资源类型</th>
					<td><select name="data.resourcetype.id" class="easyui-combobox" data-options="required:true,editable:false,valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/base/resourcetype!nsc_combobox.myhope',panelHeight:'auto'" style="width: 155px;"></select></td>
					<th>上级资源</th>
					<td><select id="resource_id" name="data.resource.id" class="easyui-combotree" data-options="editable:false,idField:'id',textField:'text',parentField:'pid',url:'${pageContext.request.contextPath}/base/resource!nsc_getMainMenu.myhope'" style="width: 155px;"></select><img class="iconImg ext-icon-cross" onclick="$('#resource_id').combotree('clear');" title="清空" /></td>
				</tr>
				<tr>
					<th>资源图标</th>
					<td><input id="iconCls" name="data.iconCls" readonly="readonly" style="padding-left: 18px; width: 134px;" /><img class="iconImg ext-icon-zoom" onclick="showIcons();" title="浏览图标" />&nbsp;<img class="iconImg ext-icon-cross" onclick="$('#iconCls').val('');$('#iconCls').attr('class','');" title="清空" /></td>
					<th>顺序</th>
					<td><input name="data.seq" class="easyui-numberspinner" data-options="required:true,min:0,max:100000" style="width: 155px;" value="100" /></td>
				</tr>
				<tr>
					<th>目标</th>
					<td><input name="data.target" /></td>
					<th>资源描述</th>
					<td><textarea name="data.description"></textarea></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>