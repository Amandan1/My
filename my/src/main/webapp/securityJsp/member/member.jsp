<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	var showFun = function(id) {
		if (id == null) {
			id = menu_id;
		}
		var dialog = parent.myhope.modalDialog({
			title : '查看会员信息',
			width : 800,
			height : 800,
			url : myhope.contextPath + '/securityJsp/member/memberForm.jsp?id='
					+ id+'&type=' + 2
		});
	};
	var editFun = function(id) {
		if (id == null) {
			id = menu_id;
		}
		var dialog = parent.myhope.modalDialog({
			title : '编辑会员信息',
			width : 800,
			height : 800,
			url : myhope.contextPath + '/securityJsp/member/memberForm.jsp?id='
					+ id +'&type=' + 1,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ]
		});
	};
	$(function() {
		grid = $('#grid')
				.datagrid({
			title : '会员信息',
			url : myhope.contextPath
					+ '/member/member!grid.myhope',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdatetime',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
					400, 500 ],
			frozenColumns : [ [
					{
						width : '100',
						title : '会员编号',
						field : 'code',
						sortable : true
					},
					{
						width : '100',
						title : '姓名',
						field : 'name',
						sortable : true
					},
					{
						width : '100',
						title : '手机号',
						field : 'mobile',
						sortable : true
					},
					{
						width : '100',
						title : '性别',
						field : 'sex',
					     formatter : function(value, row, index) {
			                    return myhope.translateMetadata('sex',value);
			                }
					}
				 ] ],
			columns : [ [
					{
						title : '操作',
						field : 'action',
						width : '90',
						formatter : function(value, row) {
							var str = '';
								<%if (securityUtil.havePermission("/member/member!getById")) {%>
									str += myhope.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>',row.id);
						     	<%}%>
						     	<%if (securityUtil.havePermission("/member/member!update")) {%>
								str += myhope.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>',row.id);
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
    <tbody>
     <tr> 
      <td> 
       <form id="searchForm"> 
        <table> 
         <tbody>
          <tr> 
           <td>会员编号</td> 
           <td><input name="QUERY_t#code_S_LK" style="width: 80px;" /></td> 
           <td>手机号</td> 
           <td><input name="QUERY_t#mobile_S_LK" style="width: 80px;" /></td> 
           <td>性别</td> 
           <td><input name="QUERY_t#sex_S_LK" style="width: 80px;" class="easyui-combobox" data-options=" valueField:'id',textField:'remark',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=sex',width:80,panelHeight:'auto',editable:false" /></td> 
           <td>创建时间</td> 
           <td><input name="QUERY_t#createdatetime_D_GE" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 120px;" /> - <input name="QUERY_t#createdatetime_D_LE" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 120px;" /></td> 
           <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',myhope.serializeObject($('#searchForm')));">过滤</a> <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a> </td> 
          </tr> 
         </tbody>
        </table> 
       </form> </td> 
     </tr> 
    </tbody>
   </table> 
  </div> 
  <div data-options="region:'center',fit:true,border:false"> 
   <table id="grid" data-options="fit:true,border:false"></table> 
  </div>


</body>
</html>