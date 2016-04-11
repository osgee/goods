<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>top</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="css/style.css" media="all" />
<link rel="stylesheet" href="css/dropzone.css" media="all" />
<link rel="stylesheet" href="css/ie.css" media="all" />
</head>

<script type="text/javascript" charset="utf-8"
	src="/jsps/js/jquery.leanModal.min.js"></script>

</head>
<body>
	<header class="main">
	<h1><strong>二手书交易系统</strong></h1>
</header>
	<section class="user"> 
	<div class="buttons">
	<c:choose>
		<c:when test="${empty sessionScope.sessionUser }">
			<span class="button blue"> <a
				href="<c:url value='/jsps/user/login.jsp'/>" target="body"
				id="modaltrigger">会员登录</a>
			</span>
			<span class="button blue"> <a
				href="<c:url value='/adminjsps/login.jsp'/>" target="body">卖家登录</a>
			</span>
		</c:when>

		<c:otherwise>
		 <span class="button blue">
		 <a href="<c:url value='#'/>">会员：${sessionScope.sessionUser.loginname }</a>
		 </span>
		  <span class="button blue"> <a
				href="<c:url value='/CartItem_myCart.action'/>" target="body">我的购物车</a>
			</span>
			<span class="button blue"> <a
				href="<c:url value='/Order_myOrders.action?Pc=1'/>" target="body">我的订单</a>
			</span>
			<span class="button blue"> <a
				href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a>
			</span>
			<span class="button blue"> <a
				href="<c:url value='/User_quit.action'/>" target="_parent">退出</a>
			</span>
		</c:otherwise>
	</c:choose>
	</div>
	 </section>
	<%-- 根据用户是否登录，显示不同的链接 --%>

	<script type="text/javascript">
		$(function() {
			$('#loginform').submit(function(e) {
				return false;
			});

			$('#modaltrigger').leanModal({
				top : 110,
				overlay : 0.45,
				closeButton : ".hidemodal"
			});
		});
	</script>


	</div>
</body>
</html>
