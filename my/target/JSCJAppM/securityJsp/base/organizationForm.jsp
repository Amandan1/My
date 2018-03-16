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
				url = myhope.contextPath + '/base/organization!update.myhope';
			} else {
				url = myhope.contextPath + '/base/organization!save.myhope';
			}
			$.post(url, myhope.serializeObject($('form')), function(result) {
				if (result.success) {
                    $pjq.messager.alert('提示', result.msg, 'info');
					$grid.treegrid('reload');
					$dialog.dialog('destroy');
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
			$.post(myhope.contextPath + '/base/organization!getById.myhope', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.name' : result.name,
						'data.address' : result.address,
						'data.organization.id' : result.organization ? result.organization.id : '',
						'data.iconCls' : result.iconCls,
                        'data.referee' : result.referee,
                        'data.orgmanager' : result.orgmanager,
                        'data.organizationtype' : result.organizationtype,
						'data.seq' : result.seq,
						'data.code' : result.code
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
			<legend>机构基本信息</legend>
			<table class="table" style="width: 100%;">
				<input name="data.id" value="${param.id}" readonly="readonly" type="hidden"/>
				<tr>
					<th>机构名称</th>
					<td><input name="data.name" class="easyui-validatebox" data-options="required:true" /></td>
					<th>机构编码</th>
					<td><input name="data.code" /></td>
				</tr>
				<tr>
					<th>机构类型</th>
					<td>
						<input name="data.organizationtype" class="easyui-combobox" data-options="required:true,valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=organization_type',width:80,panelHeight:'auto',editable:false" />
					</td>
					<th>上级机构</th>
					<td><select id="organization_id" name="data.organization.id" class="easyui-combotree" data-options="required:true,editable:false,idField:'id',textField:'name',parentField:'pid',url:'${pageContext.request.contextPath}/base/organization!nsc_comboTree.myhope'" style="width: 155px;"></select><img class="iconImg ext-icon-cross" onclick="$('#organization_id').combotree('clear');" title="清空" /></td>
				</tr>
				<tr>
					<th>推荐人</th>
					<td><input name="data.referee" class="easyui-validatebox" /></td>
					<th>管理员</th>
					<td><input name="data.orgmanager" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>顺序</th>
					<td><input name="data.seq" class="easyui-numberspinner" data-options="required:true,min:0,max:100000,editable:false" style="width: 155px;" value="100" /></td>
					<th>机构图标</th>
					<td><input id="iconCls" name="data.iconCls" readonly="readonly" style="padding-left: 18px; width: 134px;" /><img class="iconImg ext-icon-zoom" onclick="showIcons();" title="浏览图标" />&nbsp;<img class="iconImg ext-icon-cross" onclick="$('#iconCls').val('');$('#iconCls').attr('class','');" title="清空" /></td>
				</tr>
				<tr>
					<th>机构地址</th>
					<td><input name="data.address" /></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>