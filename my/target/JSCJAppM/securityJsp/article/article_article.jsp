<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<!-- 引入文本编辑器 -->
<%String contextPath = request.getContextPath();%>
<script src="<%=contextPath%>/jslib/wangEditor/release/wangEditor.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">
    var submitForm = function($dialog, $grid, $pjq) {
        var url;
        if ($(':input[name="data.id"]').val().length > 0) {
            url = myhope.contextPath + '/article/article!update.myhope';
        }
        $('#des_htm').val(editor.txt.html() + '');
        $.post(url, myhope.serializeObject($('form')), function(result) {

            if (result.success) {
                $pjq.messager.alert('提示', result.msg, 'info');
                $grid.datagrid('load');
                $dialog.dialog('destroy');
            } else {
                $pjq.messager.alert('提示', result.msg, 'error');
            }
        }, 'json');
    };
    var editor;
    $(function() {
        if ($(':input[name="data.id"]').val().length > 0) {
            parent.$.messager.progress({
                text : '数据加载中....'
            });
            $.post(myhope.contextPath + '/article/article!getById.myhope', {
                id : $(':input[name="data.id"]').val(),
            }, function(result) {
                if (result.id != undefined) {
                    $('form').form('load', {
                        'data.id' : result.id,
                        'data.createDateTime' : result.createDateTime,
                        'data.classify.id' : result.classify ? result.classify.id
								: '',
                        'data.title' : result.title,
                    	'data.delflag' : result.delflag,
						'data.shelves' : result.shelves,
                        'data.img' : result.img,
                        'data.publisher' : result.publisher,
                        'data.article' : result.article,
                        
                        'data.label' : result.label
                    });
                }
                // 文本编辑器
                var E = window.wangEditor;
                editor = new E('#editor');
                editor.customConfig.uploadImgShowBase64 = true;
                editor.customConfig.uploadFileName = "file";
                editor.customConfig.uploadImgServer = myhope.contextPath + "/article/article!uploadImg.myhope?fileFolder=/userPhoto";
                // 将图片大小限制为 3M
                editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
                // 将 timeout 时间改为 60s
                editor.customConfig.uploadImgTimeout = 60000;
				editor.create();
                $('.w-e-text-container').css('height','600px');
                editor.txt.html(result.article);
                parent.$.messager.progress('close');
            }, 'json');
        }
    });
    
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>商品信息</legend>
			<table>
				<input name="data.id" value="${param.id}" readonly="readonly" type="hidden"/>
				<input name="data.createDateTime" value="${param.createDateTime}" readonly="readonly" type="hidden"/>
				<input name="data.shelves"  value="${param.shelves}"  readonly="readonly"
					type="hidden" />
				<input name="data.delflag" value="${param.delflag}"   readonly="readonly"
					type="hidden" />
				<input name="data.classify.id" value="${param.classify}" readonly="readonly" type="hidden"/>
				<input name="data.title" value="${param.title}" readonly="readonly" type="hidden"/>
				<input name="data.img" value="${param.img}" readonly="readonly" type="hidden"/>
				<input name="data.publisher" value="${param.publisher}" readonly="readonly" type="hidden"/>
				<input name="data.label" value="${param.label}" readonly="readonly" type="hidden"/>
				<tr>
					<td>
						<input id="des_htm" type="hidden" name="data.article"/>
						<div id="editor" style="height:640px;width:930px;"></div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>