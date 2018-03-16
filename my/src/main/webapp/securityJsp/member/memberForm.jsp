<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">

$.extend($.fn.validatebox.defaults.rules, {
    mobile: { //验证手机号
        validator: function (value, param) {
                return /^1[34578]\d{9}$/.test(value);
            },
            message: '请填写正确的手机号。'
    }
});
var i = 0;
var submitNow = function ($dialog, $grid, $pjq) {
    var url;
    if ($(':input[name="data.id"]').val().length > 0) {
        url = myhope.contextPath + '/member/member!update.myhope';
    } else {
        url = myhope.contextPath + '/member/member!save.myhope';
    }
    $.post(url, myhope.serializeObject($('form')), function (result) {
        parent.myhope.progressBar('close'); //关闭上传进度条

        if (result.success) {
            $pjq.messager.alert('提示', result.msg, 'info');
            $grid.datagrid('load');
            $dialog.dialog('destroy');
        } else {
            $pjq.messager.alert('提示', result.msg, 'error');
        }
    }, 'json');
};
var submitForm = function ($dialog, $grid, $pjq) {
    submitNow($dialog, $grid, $pjq);
};
$(function () {
    if ($(':input[name="data.id"]').val().length > 0) {
        parent.$.messager.progress({
            text: '数据加载中....'
        });
        var type = ${param.type};
        if (type == "1") {
            $("#pwd").removeAttr("readonly");

        } else {
            $("#status").combobox('readonly', true);
            $("#pwd").attr("readonly", "readonly");
        }
        var ids = $("#id").val();
        $.post(myhope.contextPath + '/member/member!getById.myhope', {

            id: ids
        }, function (result) {
            if (result.id != undefined) {
                $('form').form('load', {
                    'data.id': result.id,
                    'data.createdatetime': result.createdatetime,
                    'data.updatedatetime': result.updatedatetime,
                    'data.code': result.code,
                    'data.sex': result.sex,
                    'data.pwd': result.pwd,
                    'data.mobile': result.mobile,
                    'data.pic': result.pic,
                    'data.status': result.status,
                    'data.name': result.name,
                    'data.introduce': result.introduce,
                });
                var idcardpics = result.pic;
                if (idcardpics != null) {
                    idcardpics = idcardpics.trim();
                    if (idcardpics != "") {
                        $("#idcardpics").after('<td> <img src="' + idcardpics + '"  width="100px" height="100px"   ></img></td>');
                    } else {
                        $("#idcardpics").after('<td> <span>还没有上传头像</span>');
                    }
                } else {
                    $("#idcardpics").after('<td> <span>还没有上传头像</span>');
                }
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
    <legend>会员信息</legend>
    <table class="table" style="width: 100%;">
      <input id="id" name="data.id" value="${param.id}" type="hidden" />
      <tr>
        <th>手机号</th>
        <td>
          <input name="data.mobile" class="easyui-validatebox" readonly="readonly" data-options="required:true,prompt:'请填写正确的手机号。',validType:'mobile'" readonly="readonly" /></td>
        <th>密码</th>
        <td>
          <input id="pwd" name="data.pwd" type="password" class="easyui-validatebox" data-options="required:true" value="" /></td>
      </tr>
      <tr>
        <th>昵称</th>
        <td>
          <input id="name" name="data.name" type="easyui-combobox" readonly="readonly" class="easyui-validatebox" data-options="required:true" value="" /></td>
        <th>会员编号</th>
        <td>
          <input id="code" name="data.code" type="easyui-combobox" readonly="readonly" class="easyui-validatebox" data-options="required:true" value="" />
          <input name="data.createdatetime" type="hidden" readonly="readonly" class="easyui-validatebox" data-options="required:true" value="" />
          <input name="data.updatedatetime" type="hidden" readonly="readonly" class="easyui-validatebox" data-options="required:true" value="" /></td>
      </tr>
      <tr>
        <th>性别</th>
        <td>
          <input id="sex" name="data.sex" class="easyui-combobox" data-options="required:true,valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=sex',width:80,panelHeight:'auto',editable:false,readonly:'readonly'" /></td>
        <th>会员状态</th>
        <td>
          <input id="status" name="data.status" class="easyui-combobox" data-options="required:true,valueField:'key',textField:'val',url:'${pageContext.request.contextPath}/base/metadata!nssnsc_findByName.myhope?data.name=member_status',width:80,panelHeight:'auto',editable:false" /></td>
      </tr>
      <tr>
        <th>会员介绍</th>
        <td>
          <textarea name="data.introduce" readonly="readonly"></textarea>
        </td>
        <th id="idcardpics">会员头像
          <input name="data.pic" type="hidden" /></th></tr>
    </table>
  </fieldset>
</form>
</body>
</html>