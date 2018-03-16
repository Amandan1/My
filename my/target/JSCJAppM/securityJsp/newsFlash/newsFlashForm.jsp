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
			url = myhope.contextPath + '/newsflash/newsflash!update.myhope';
		} else {
			url = myhope.contextPath + '/newsflash/newsflash!save.myhope';
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
			$.post(myhope.contextPath + '/newsflash/newsflash!getById.myhope', {
				id : ids
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.createDateTime' : result.createDateTime,
						'data.title':result.title,
						'data.degree':result.degree,
						'data.sendStatus':result.sendStatus,
						'data.thumbsNum':result.thumbsNum,
						'data.step':result.step
					});
				}
				if(type && type == 1){
					var  tds  = $('form').find('td');
					for(var i=0;i<tds.length;i++){
						$(tds[i]).html($(tds[i]).children().val());
						$(tds[i]).children().hide();
					}
					$("#sendStatus").html(myhope.translateMetadata('sendStatus',result.sendStatus));
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
			<legend>快讯信息</legend>
			<table class="table" style="width: 100%;">
				<input id="id" name="data.id" value="${param.id}" type="hidden"/>
				<tr>
					<th>标题</th>
					<td style="width:80%;line-height: 25px;">
					<textarea id= 'title' name="data.title" class="easyui-validatebox"
					   style="height: 90px; width: 300px" data-options="required:true" ></textarea>
					</td>
				</tr>
				<tr>
				  	<th>重要程度</th>
					<td style="width:80%;line-height: 25px;"><input id="degree" name="data.degree"  max="5"  min="0"    class="easyui-numberbox"   value=""/></td>
				</tr>
				<tr>
				  	<th>发布状态</th>
					<td id="sendStatus"><input   name="data.sendStatus" class="easyui-combobox"
						data-options="required:true,valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=sendStatus',width:80,panelHeight:'auto',editable:false" />
					</td>
				</tr>
				<tr>
				  	<th>点赞数</th>
					<td style="width:80%;line-height: 25px;"><input id="thumbsNum" name="data.thumbsNum"   min="0"   class="easyui-numberbox"   value=""/></td>
				</tr>
				<tr>
				  	<th>踩数</th>
					<td style="width:80%;line-height: 25px;"><input id="step" name="data.step"  min="0"    class="easyui-numberbox"   value=""/></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>