<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myhope.util.base.SecurityUtil"%>
<%
	SecurityUtil securityUtil = new SecurityUtil(session);
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var grid;
	var menu_id;
	var addFun = function() {
		var dialog = parent.myhope.modalDialog({
			title : '添加元数据',
            width:1000,
            height:500,
			url : myhope.contextPath + '/securityJsp/base/metadataForm.jsp?pageType=1',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var showFun = function(id) {
		if(id == null){
			id = menu_id;
		}
		var dialog = parent.myhope.modalDialog({
			title : '查看元数据信息',
            width:1000,
            height:500,
			url : myhope.contextPath + '/securityJsp/base/metadataForm.jsp?pageType=4&id=' + id
		});
	};
	var editFun = function(id) {
		if(id == null){
			id = menu_id;
		}
		var dialog = parent.myhope.modalDialog({
			title : '编辑元数据信息',
            width:1000,
            height:500,
			url : myhope.contextPath + '/securityJsp/base/metadataForm.jsp?pageType=3&id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		if(id == null){
			id = menu_id;
		}
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(myhope.contextPath + '/base/metadata!delete.myhope', {
					id : id
				}, function() {
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : myhope.contextPath + '/base/metadata!grid.myhope',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [ {
				width : '100',
				title : '编号',
				field : 'name',
				sortable : true
			}, {
				width : '80',
				title : '元数据名',
				field : 'content',
				sortable : true
			}, {
				width : '80',
				title : '备注',
				field : 'remark',
				sortable : true
			} ] ],
			columns : [ [ {
				width : '150',
				title : '类型',
				field : 'type',
				sortable : true,
				formatter : function(value, row, index) {
					return myhope.translateMetadata('metadata_type',value);
				}
			}, {
				title : '操作',
				field : 'action',
				width : '90',
				formatter : function(value, row) {
					var str = '';
					<%if (securityUtil.havePermission("/base/metadata!getById")) {%>
						str += myhope.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/metadata!update")) {%>
						str += myhope.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/metadata!delete")) {%>
						str += myhope.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>', row.id);
					<%}%>
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', myhope.pixel_0);
				parent.$.messager.progress('close');
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				menu_id = rowData.id;
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<td>元数据编号</td>
								<td><input name="QUERY_t#name_S_LK" style="width: 80px;" /></td>
								<td>元数据名</td>
								<td><input name="QUERY_t#content_S_LK" style="width: 80px;" /></td>
								<td>类型</td>
								<td>
								<input name="QUERY_t#type_S_EQ" class="easyui-combobox" data-options="valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=metadata_type',width:80,panelHeight:'auto',editable:false" />
								</td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',myhope.serializeObject($('#searchForm')));">过滤</a>  <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<%if (securityUtil.havePermission("/base/metadata!save")) {%>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<%}%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
	
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<%if (securityUtil.havePermission("/base/metadata!getById")) {%>
		<div onclick="showFun();" data-options="iconCls:'iconImg ext-icon-note'">查看</div>
		<%}%>
		<%if (securityUtil.havePermission("/base/metadata!update")) {%>
		<div onclick="editFun();" data-options="iconCls:'iconImg ext-icon-note_edit'">编辑</div>
		<%}%>
		<%if (securityUtil.havePermission("/base/metadata!delete")) {%>
		<div onclick="removeFun();" data-options="iconCls:'iconImg ext-icon-note_delete'">删除</div>
		<%}%>
	</div>
</body>
</html>