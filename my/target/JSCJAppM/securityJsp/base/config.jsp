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
	var addFun = function() {
		var dialog = parent.myhope.modalDialog({
			title : '添加系统参数',
			url : myhope.contextPath + '/securityJsp/base/configForm.jsp',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
    var addPicFun = function() {
        var dialog = parent.myhope.modalDialog({
            title : '上传图片',
            url : myhope.contextPath + '/securityJsp/base/configPic.jsp',
            buttons : [ {
                text : '上传',
                handler : function() {
                    dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                }
            } ]
        });
    };
	var showFun = function(id) {
		var dialog = parent.myhope.modalDialog({
			title : '查看系统参数',
			url : myhope.contextPath + '/securityJsp/base/configForm.jsp?id=' + id
		});
	};
	var editFun = function(id) {
		var dialog = parent.myhope.modalDialog({
			title : '编辑系统参数',
			url : myhope.contextPath + '/securityJsp/base/configForm.jsp?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(myhope.contextPath + '/base/config!delete.myhope', {
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
			url : myhope.contextPath + '/base/config!grid.myhope',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'name',
			sortOrder : 'asc',
            pageSize : 50,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [ {
				width : '100',
				title : '参数编号',
				field : 'key',
				sortable : true
			},{
				width : '100',
				title : '参数名称',
				field : 'name',
				sortable : true
			}, {
				width : '200',
				title : '参数值',
				field : 'val',
				sortable : true
			}, {
				width : '200',
				title : '参数分类',
				field : 'type',
				sortable : true,
				formatter : function(value, row, index) {
					return myhope.translateMetadata('config_type',value);
				}
			} ] ],
			columns : [ [ {
				width : '200',
				title : '参数描述',
				field : 'remark'
			}, {
				width : '140',
				title : '创建时间',
				field : 'createdatetime',
				sortable : true
			}, {
				width : '100',
				title : '创建人',
				field : 'createid',
				sortable : true
			}, {
				width : '140',
				title : '修改时间',
				field : 'updatedatetime',
				sortable : true
			}, {
				width : '100',
				title : '修改人',
				field : 'updateid',
				sortable : true
			}, {
				title : '操作',
				field : 'action',
				width : '80',
				formatter : function(value, row) {
					var str = '';
					<%if (securityUtil.havePermission("/base/config!getById")) {%>
						str += myhope.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/config!update")) {%>
						str += myhope.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/config!delete")) {%>
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
								<td>参数分类</td>
								<td>
								<input name="QUERY_t#type_S_EQ" class="easyui-combobox" 
								data-options="valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=config_type',width:100,panelHeight:'auto',editable:false" />
								<td>参数编号</td>
								<td><input name="QUERY_t#key_S_LK" style="width: 80px;" /></td>
								<td>参数名</td>
								<td><input name="QUERY_t#name_S_LK" style="width: 80px;" /></td>
								<td>参数值</td>
								<td><input name="QUERY_t#val_S_LK" style="width: 80px;" /></td>
								<td>备注</td>
								<td><input name="QUERY_t#remark_S_LK" style="width: 80px;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',myhope.serializeObject($('#searchForm')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<%if (securityUtil.havePermission("/base/config!save")) {%>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加参数</a></td>
							<%}%>
							<%if (securityUtil.havePermission("/base/config!uploadImg")) {%>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addPicFun();">添加图片</a></td>
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
</body>
</html>