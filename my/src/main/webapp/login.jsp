<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
    <head>
        <title>正淘玩具管理后台</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/loginCss.css"/>
    </head>
    <body>
		<div class="wrapper">
			<h1>正淘玩具</h1>
			<h2><div id="subtitle_str">正淘玩具后台管理系统</div></h2>
			<div class="content">
				<div id="form_wrapper" class="form_wrapper">
					<form id="reg_form" class="register">
						<h3>注册</h3>
						<div class="column">
							<div>
								<label>登录名：</label>
								<input id="reg_loginname" name="data.loginname" type="text"/>
								<span class="error">This is an error</span>
							</div>
							<div>
								<label>密码：</label>
								<input id="reg_pwd" name="data.pwd" type="password" />
								<span class="error">This is an error</span>
							</div>
							<div>
								<label>确认密码：</label>
								<input id="reg_repwd" type="password" />
								<span class="error">This is an error</span>
							</div>
						</div>
						<div class="column">
							<div>
								<label>姓名：</label>
								<input id="reg_name" name="data.name"  type="text" />
								<span class="error">This is an error</span>
							</div>
							<div>
								<label>QQ：</label>
								<input id="reg_qq" name="data.qq"  type="text" />
								<span class="error">This is an error</span>
							</div>
							<div>
								<label>邮箱：</label>
								<input id="reg_mail" name="data.mail"  type="text" />
								<span class="error">This is an error</span>
							</div>
						</div>
						<div class="bottom">
							<span id="reg_error" class="error">This is an error</span>
							<input id="regBtn" onclick="regFun();" type="submit" value="注册" />
							<a href="#" rel="login" class="linkform">已有账号？点此登录</a>
							<div class="clear"></div>
						</div>
					</form>
					<form id="login_form" class="login active">
						<h3>登录</h3>
						<div>
							<label>登录名：</label>
							<input type="text" name="data.loginname" id="login_loginname"/>
							<span class="error">登录名不能为空</span>
						</div>
						<div>
							<label>密码：</label>
							<input type="password" name="data.pwd" id="login_pwd"/>
							<span class="error">This is an error</span>
						</div>
						<div class="bottom">
							<div class="remember"><input type="checkbox" id="login_remember" checked="checked"/><span>记住我</span></div>
							<input id="loginBtn" onclick="loginFun();" type="submit" value="登录"></input>
							<%--<a href="#" rel="register" class="linkform">还没账号？点此注册</a>--%>
							<div class="clear"></div>
						</div>
					</form>
				</div>
				<div class="clear"></div>
			</div>
			<a href="#" class="back" title="正淘玩具">版权所有：@正淘玩具</a>
		</div>
		<script type="text/javascript" src="<%=contextPath%>/jslib/jquery-1.9.1.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=contextPath%>/jslib/jquery.cookie.js"></script>
		<script type="text/javascript">
			$(function() {
				$(document).bind('contextmenu',function() {
					return false;
				});
	
				var $form_wrapper = $('#form_wrapper'),
				$currentForm = $form_wrapper.children('form.active'),
				$linkform = $form_wrapper.find('.linkform');
				$form_wrapper.children('form').each(function(i) {
					var $theForm = $(this);
					if (!$theForm.hasClass('active')) $theForm.hide();
					$theForm.data({
						width: $theForm.width(),
						height: $theForm.height()
					});
				});
				setWrapperWidth();
				$linkform.bind('click',
				function(e) {
					var $link = $(this);
					var target = $link.attr('rel');
					$currentForm.fadeOut(400,
					function() {
						$currentForm.removeClass('active');
						$currentForm = $form_wrapper.children('form.' + target);
						$form_wrapper.stop().animate({
							width: $currentForm.data('width') + 'px',
							height: $currentForm.data('height') + 'px'
						},
						500,
						function() {
							$currentForm.addClass('active');
							$currentForm.fadeIn(400);
						});
					});
					e.preventDefault();
				});
				function setWrapperWidth() {
					$form_wrapper.css({
						width: $currentForm.data('width') + 'px',
						height: $currentForm.data('height') + 'px'
					});
				}
				$form_wrapper.find('input[type="submit"]').click(function(e) {
					e.preventDefault();
				});
				var loginData = $.parseJSON($.cookie('login-data'));
				if (loginData) {
					$('#login_loginname').val(loginData.login_loginname);
					$('#login_pwd').val(loginData.login_pwd);
				}
			});
	
			function loginFun() {
				cleanError();
				var $login_loginname = $('#login_loginname');
				var $login_pwd = $('#login_pwd');
	
				if (inputNotNull($login_loginname, '登录名') | inputNotNull($login_pwd, '密码')) return;
				var $loginForm = $('#login_form');
				$('#loginBtn').attr('disabled', true);
				$.post('<%=contextPath%>/base/user!nssnsc_login.myhope', $loginForm.serialize(),
				function(result) {
					if (result.success) {
						if ($('#login_remember').is(':checked')) {
							$.cookie('login-data', '{"login_loginname":"' + $login_loginname.val() + '","login_pwd":"' + $login_pwd.val() + '"}', {
								expires: 7
							});
						} else {
							$.cookie('login-data', null);
						}
						location.replace('<%=contextPath%>/index.jsp');
					} else {
						$login_pwd.next().text(result.msg);
						$login_pwd.next().css('visibility', 'visible');
						$('#loginBtn').attr('disabled', false);
					}
				},
				'json');
			};
	
			function regFun() {
				cleanError();
				var $reg_loginname = $('#reg_loginname');
				var $reg_pwd = $('#reg_pwd');
				var $reg_repwd = $('#reg_repwd');
				var $reg_name = $('#reg_name');
				var $reg_qq = $('#reg_qq');
				var $reg_mail = $('#reg_mail');
	
				if (inputNotNull($reg_loginname, '登录名') | inputNotNull($reg_pwd, '密码') | inputNotNull($reg_repwd, '重复密码') | inputNotNull($reg_name, '姓名')) return;
	
				if ($reg_pwd.val() != $reg_repwd.val()) {
					$reg_pwd.next().text('密码输入不一致');
					$reg_pwd.next().css('visibility', 'visible');
					$reg_repwd.next().text('密码输入不一致');
					$reg_repwd.next().css('visibility', 'visible');
					return;
				}
				var $regForm = $('#reg_form');
				$('#regBtn').attr('disabled', true);
	
				$.post('<%=contextPath%>/base/user!nssnsc_reg.myhope', $regForm.serialize(),
				function(result) {
					if (result.success) {
						location.replace('<%=contextPath%>/index.jsp');
					} else {
						$('#reg_error').text(result.msg);
						$('#reg_error').css('visibility', 'visible');
						$('#regBtn').attr('disabled', false);
					}
				},
				'json');
			};
	
			function inputNotNull($pom, text) {
				if ($.trim($pom.val()) == '') {
					var $nextDiv = $pom.next();
					$nextDiv.text(text + '不能为空');
					$nextDiv.css('visibility', 'visible');
					return true;
				}
			};
	
			function cleanError() {
				$('.error').text('haha');
				$('.error').css('visibility', 'hidden');
			};
        </script>
    </body>
</html>