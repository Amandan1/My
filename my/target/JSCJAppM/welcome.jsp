<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>欢迎页面</title>
<jsp:include page="inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var portal;
	var panels;
/* 	$(function() {
		
		panels = [{
			id : 'p1',
			title : '商品总销量前5统计',
			height : 350,
			collapsible : true,
			href : '${pageContext.request.contextPath}/securityJsp/portal/p1.jsp'
		},{
			id : 'p2',
			title : '会员最近3月注册统计',
			height : 350,
			collapsible : true,
			href:'${pageContext.request.contextPath}/securityJsp/portal/p2.jsp'
		},{
            id : 'p3',
            title : '最近3月内销售额统计',
            height : 350,
            collapsible : true,
            href:'${pageContext.request.contextPath}/securityJsp/portal/p3.jsp'
        },{
            id : 'p4',
            title : '库存前5统计',
            height : 350,
            collapsible : true,
            href:'${pageContext.request.contextPath}/securityJsp/portal/p4.jsp'
        }];

		portal = $('#portal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-state');
		if (!state) {
			state = 'p1,p2:p3,p4';/*冒号代表列，逗号代表行 
		}
		addPanels(state);
		portal.portal('resize');

	}); */

	function getPanelOptions(id) {
		for ( var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
	function getPortalState() {
		var aa=[];
		for(var columnIndex=0;columnIndex<2;columnIndex++) {
			var cc=[];
			var panels=portal.portal('getPanels',columnIndex);
			for(var i=0;i<panels.length;i++) {
				cc.push(panels[i].attr('id'));
			}
			aa.push(cc.join(','));
		}
		return aa.join(':');
	}
	function addPanels(portalState) {
		var columns = portalState.split(':');
		for (var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
			var cc = columns[columnIndex].split(',');
			for (var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]);
				if (options) {
					var p = $('<div/>').attr('id', options.id).appendTo('body');
					p.panel(options);
					portal.portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				}
			}
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div id="portal" style="position:relative">
		<div></div>
		<div></div>
	</div>
</body>
</html>