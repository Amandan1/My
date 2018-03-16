<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		var nodes = $('#tree').tree('getChecked', [ 'checked', 'indeterminate' ]);
		var ids = [];
		for (var i = 0; i < nodes.length; i++) {
			ids.push(nodes[i].id);
		}
		$.post(myhope.contextPath + '/base/user!grantOrganization.myhope', {
			id : $(':input[name="data.id"]').val(),
			ids : ids.join(',')
		}, function(result) {
			if (result.success) {
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
			$pjq.messager.alert('提示', '修改成功！', 'info');
		}, 'json');
	};
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$('#tree').tree({
			url : myhope.contextPath + '/base/organization!nsc_getOrganizationsTree.myhope',
			parentField : 'pid',
			checkbox : true,
			cascadeCheck : false,
			formatter : function(node) {
				return node.name;
			},
            onSelect: function (node) {
                var cknodes = $('#tree').tree("getChecked");
                for (var i = 0; i < cknodes.length; i++) {
                    if (cknodes[i].id != node.id) {
                        $('#tree').tree("uncheck", cknodes[i].target);
                    }
                }
                if (node.checked) {
                    $('#tree').tree('uncheck', node.target);

                } else {
                    $('#tree').tree('check', node.target);

                }

            },
			onLoadSuccess : function(node, data) {
				$.post(myhope.contextPath + '/base/organization!nsc_getOrganizationByUserId.myhope', {
					id : $(':input[name="data.id"]').val()
				}, function(result) {
					if (result) {
						for (var i = 0; i < result.length; i++) {
							var node = $('#tree').tree('find', result[i].id);
							if (node) {
								$('#tree').tree('check', node.target);
							}
						}
					}
					parent.$.messager.progress('close');
				}, 'json');
                //如单点复选框默认取消checkbox的选中状态，必须选择文字。
                $(this).find('span.tree-checkbox').unbind().click(function () {
                    return false;
                })
			}
		});
	});
</script>
</head>
<body>
	<input name="data.id" value="${param.id}" readonly="readonly" type="hidden" />
	<fieldset>
		<legend>所属机构</legend>
		<ul id="tree"></ul>
	</fieldset>
</body>
</html>