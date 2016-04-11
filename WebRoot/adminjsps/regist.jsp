<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>注册页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/jsps/css/user/regist.css'/>">

<script type="text/javascript"
	src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/adminjsps/admin/js/admin/regist.js'/>"></script>
</head>

<body>
	<form action="<c:url value='/Admin_regist.action'/>" method="post"
		id="registForm">
		<input type="hidden" name="method" value="regist" />
		<table id="tableForm">
			<tr>
				<td class="tdText">用户名：</td>
				<td class="tdInput"><input class="inputClass" type="text"
					name="adminname" id="adminname" value="${form.adminname }" /></td>
				<td class="tdError"><label class="errorClass"
					id="adminnameError">${errors.adminname }</label></td>
			</tr>
			<tr>
				<td class="tdText">登录密码：</td>
				<td><input class="inputClass" type="password" name="adminpwd"
					id="adminpwd" value="${form.adminpwd }" /></td>
				<td><label class="errorClass" id="adminpwdError">${errors.adminpwd }</label>
				</td>
			</tr>
			<tr>
				<td class="tdText">确认密码：</td>
				<td><input class="inputClass" type="password" name="readminpwd"
					id="readminpwd" value="${form.readminpwd }" /></td>
				<td><label class="errorClass" id="readminpwdError">${errors.readminpwd}</label>
				</td>
			</tr>
			<tr>
				<td class="tdText">Email：</td>
				<td><input class="inputClass" type="text" name="email"
					id="email" value="${form.email }" /></td>
				<td><label class="errorClass" id="emailError">${errors.email}</label>
				</td>
			</tr>
			<tr>
				<td class="tdText">验证码：</td>
				<td><input class="inputClass" type="text" name="verifyCode"
					id="verifyCode" value="${form.verifyCode }" /></td>
				<td><label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<div id="divVerifyCode">
						<a href="javascript:_hyz()"><img id="imgVerifyCode"
							src="<c:url value='/VerifyCodeServlet'/>" /></a>
					</div>
				</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="image"
					src="<c:url value='/images/regist1.jpg'/>" id="submitBtn" /></td>
				<td><label></label></td>
			</tr>
		</table>
	</form>
</body>
</html>
