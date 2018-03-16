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
	var addFun = function() {
		var dialog = parent.myhope.modalDialog({
			title : '添加文章信息',
			width : 800,
			height : 800,
			url : myhope.contextPath + '/securityJsp/article/articleForm.jsp',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ]
		});
	};
	var editclassify = function(id) {
        if(id == null){
            id = menu_id;
        }
        var dialog = parent.myhope.modalDialog({
            title : '编辑文章',
			width:1000,
			height:800,
            url : myhope.contextPath + '/securityJsp/article/article_article.jsp?id=' + id,
            buttons : [ {
                text : '编辑文章',
                handler : function() {
                    dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                }
            } ]
        });
    };
	var showFun = function(id) {
		if (id == null) {
			id = menu_id;
		}
		var dialog = parent.myhope.modalDialog({
			title : '查看文章信息',
			width : 800,
			height : 800, 
			url : myhope.contextPath + '/securityJsp/article/articleForm.jsp?id='
					+ id+'&type=' + 2
		});
	};
	var editFun = function(id) {
		if (id == null) {
			id = menu_id;
		}
		var dialog = parent.myhope.modalDialog({
			title : '编辑文章信息',
			width : 800,
			height : 800,
			url : myhope.contextPath + '/securityJsp/article/articleForm.jsp?id='
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
	var removeFun = function(id) {
		if (id == null) {
			id = menu_id;
		}
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(myhope.contextPath + '/article/article!delete.myhope', {
					id : id
				}, function() {
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	var auditFun = function(id,str) {
        if(id == null){
            id = menu_id;
        }
      
        parent.$.messager.confirm('询问',str=="上架"?'您确定要上架此模板？':'您确定要下架此模板？', function(r) {
            if (r) {
                $.post(myhope.contextPath + '/article/article!audit.myhope', {
                    id : id
                }, function() {
                    grid.datagrid('reload');
                }, 'json');
            }
        });
    };
	$(function() {
		grid = $('#grid')
				.datagrid({
			title : '文章信息',
			url : myhope.contextPath
					+ '/article/article!grid.myhope',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createDateTime',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
					400, 500 ],
			frozenColumns : [ [
					{
						width : '100',
						title : '标题',
						field : 'title',
						sortable : true
					},
					{
						width : '100',
						title : '栏目',
						field : 'classify.title',
						sortable : true,
						 formatter: function (value, row, index) {
	                            if (row.classify) {
	                                return row.classify.title;
	                            }
	                     }
					},
					{
						width : '100',
						title : '自定义标签',
						field : 'label',
						sortable : true
					},
					{
						width : '100',
						title : '发布人',
						field : 'publisher',
						sortable : true
					}
				 ] ],
			columns : [ [
					{
						title : '操作',
						field : 'action',
						width : '90',
						formatter : function(value, row) {
							var str = '';
								<%if (securityUtil.havePermission("/article/article!getById")) {%>
									str += myhope.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>',row.id);
						     	<%}%>
						     	<%if (securityUtil.havePermission("/article/article!update")) {%>
								str += myhope.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>',row.id);
						    	<%}%>
						     	<%if (securityUtil.havePermission("/article/article!uploadImg")) {%>
						     	str += myhope.formatString('<img class="iconImg ext-icon-application_view_gallery" title="编辑商品描述" onclick="editclassify(\'{0}\');"/>', row.id);
						    	<%}%>
						     	<%if (securityUtil.havePermission("/article/article!delete")) {%>
								str += myhope.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>',row.id);
						    	<%}%>
					        	if(row.shelves==0){
								 	 <%if (securityUtil.havePermission("/article/article!audit")) {%>
				                    str += myhope.formatString('<img class="iconImg ext-icon-tick" title="文章上架" onclick="auditFun(\'{0}\',\'{1}\');"/>', row.id,"上架");
				                    <%}%>
								
								} else if(row.shelves==1){
									 <%if (securityUtil.havePermission("/article/article!audit")) {%>
				                    str += myhope.formatString('<img class="iconImg ext-icon-cross" title="文章下架" onclick="auditFun(\'{0}\',\'{1}\');"/>', row.id,"下架");
				                    <%}%>
								}
									
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
								<td>标题</td>
								<td><input name="QUERY_t#title_S_LK" style="width: 80px;" />
								</td>
								<td>栏目</td>
								<td> 
								 <select    name="QUERY_t#classify#id_S_LK"
					          	class="easyui-combotree"
						        data-options="editable:false,idField:'id',textField:'text',parentField:'pid',url:'${pageContext.request.contextPath}/classify/classify!nsc_getClassifytitle.myhope'"
					        	style="width: 100px;"></select>
								</td>
								<td>发布人</td>
								<td><input name="QUERY_t#publisher_S_LK"
									style="width: 80px;" /></td>
							 	<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="grid.datagrid('load',myhope.serializeObject($('#searchForm')));">过滤</a>
									<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom_out',plain:true"
									onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a>
								</td>
							</tr>
						</table>
					</form>
				</td>
				<%if (securityUtil.havePermission("/article/article!save")) {%>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<%}%>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>


</body>
</html>