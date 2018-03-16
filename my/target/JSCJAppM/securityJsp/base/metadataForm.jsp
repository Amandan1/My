<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var _id = '';
	var _pageType = '';
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			if (_pageType === '3') {
				url = myhope.contextPath + '/base/metadata!update.myhope';
			} else if (_pageType === '1') {
				url = myhope.contextPath + '/base/metadata!save.myhope';
			}
			var formData = myhope.serializeObject($('form'));
			formData.mdJson = getJson();

			$.post(url, formData, function(result) {
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
		_id = $(':input[name="data.id"]').val();
		_pageType = '${param.pageType}';
		var type;
		if (_pageType === '3' || _pageType === '4') {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(myhope.contextPath + '/base/metadata!getById.myhope', {
				id : _id
			}, function(result) {
				if (result.id != undefined) {
				    type = result.type;
					$('form').form('load', {
						'data.name' : result.name,
						'data.content' : result.content,
						'data.type' : result.type,
						'data.remark' : result.remark
					});
				}
				parent.$.messager.progress('close');
			}, 'json');
			
			$.post(myhope.contextPath + '/base/metadata!findMetadataDetail.myhope', {
				id : _id
			}, function(result) {
				draw_table(result,type);
			}, 'json');
			
		}
		
		if (_pageType === '1') {
			$(':input[name="data.name"]').removeAttr('readonly');
			draw_table([]);
		}
		
	});
	
	function draw_table(json,type){
		str = '';
		str += '<thead>';
		str += '	<th width="25%">键</th>';
		str += '	<th width="25%">值</th>';
		str += '	<th width="25%">排序</th>';
        str += '	<th width="25%">备注</th>';
        str += (_pageType !== '4'?'	<th width="20%"><a href="javascript:void(0);" onclick="table_add_tr();">+</a></th>':'');
		str += '</thead>';
		str += '<tbody id="table_tbody">';
		for (var i = 0; i < json.length; i++) {
			var metadata_d = json[i];
			str += '<tr>';
            if(type == '3') {
                str += '<td><input style="width: 100%;" value="' + metadata_d.key + '" readonly="readonly"/></td>';
            } else {
                str += '<td><input style="width: 100%;" value="' + metadata_d.key + '"/></td>';
			}
			if(metadata_d.remark == undefined) {
                metadata_d.remark = "";
			}
            if(metadata_d.seq == undefined) {
                metadata_d.seq = "";
            }
			str += '<td><input style="width: 100%;" value="' + metadata_d.val + '"/></td>';
			str += '<td><input style="width: 100%;" value="' + metadata_d.seq + '"/></td>';
            str += '<td><input style="width: 100%;" value="' + metadata_d.remark + '"/></td>';
            str += (_pageType !== '4'?'	<td><a href="javascript:void(0);" onclick="($(this).parent().parent()).remove()">-</a></td>':'');
			str += '</tr>';
		}
		str += '</tbody>';
		$('#table_m_d').html(str);
	}
	
	function table_add_tr(){
		str = '';
		str += '<tr>';
		str += '<td><input style="width: 100%;" value=""/></td>';
		str += '<td><input style="width: 100%;"/></td>';
		str += '<td><input style="width: 100%;"/></td>';
        str += '<td><input style="width: 100%;"/></td>';
		str += (_pageType !== '4'?'	<td><a href="javascript:void(0);" onclick="($(this).parent().parent()).remove()">-</a></td>':'');
		str += '</tr>';
		$('#table_tbody').append(str);
		getJson();
	}
	
	function getJson(){
		var array = [];
		var o = {};
		var $table_tbody = $('#table_tbody');
		$('#table_tbody').children().each(function(i,item){
			o = {};
			$(this).find('input').each(function(i,item){
				var thisVal = $(this).val();
				switch (i) {
				case 0:
					o.key = thisVal;
					break;
				case 1:
					o.val = thisVal;
					break;
				case 2:
					o.seq = thisVal;
					break;
                case 3:
                    o.remark = thisVal;
                    break;
				}
			});
			if(o.key && o.val){
				array.push(o);
			}
		});
		
		return JSON.stringify(array);
		
	}
</script>
</head>
<body>
	<form method="post" class="form">
		<input name="data.id" value="${param.id}" type="hidden" />
		<fieldset>
			<legend>元数据基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>编号</th>
					<td><input name="data.name" readonly="readonly" class="easyui-validatebox"
						data-options="required:true"/></td>
					<th>元数据名</th>
					<td><input name="data.content" class="easyui-validatebox"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<th>类型</th>
					<td><input name="data.type" class="easyui-combobox" data-options="valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=metadata_type',panelHeight:'auto',editable:false,required:true" /></td>
					<th>备注</th>
					<td><textarea name="data.remark"></textarea></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<fieldset>
		<legend>元数据值</legend>
		<table id="table_m_d" class="table" style="width: 100%;">
		</table>
	</fieldset>
</body>
</html>