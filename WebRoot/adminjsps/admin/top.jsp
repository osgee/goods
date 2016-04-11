<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>top</title>
<base target="body">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="css/style.css" media="all" />
<link rel="stylesheet" href="/jsps/css/style.css" media="all" />
<link rel="stylesheet" href="/adminjsps/admin/css/style.css" media="all" />
<link rel="stylesheet" href="css/style.css" media="all" />
<link rel="stylesheet" href="css/dropzone.css" media="all" />
<link rel="stylesheet" href="css/ie.css" media="all" />
<style type="text/css">
body {
	font-size: 10pt;
}

a {
	color: #aaa;
}
</style>
</head>

<body>
	<header class="main">
	<h1>
		<strong>二手书交易系统后台管理</strong>
	</h1>
	</header>
	<section class="user">
	<div class="buttons">
		<c:choose>
			<c:when test="${sessionScope.sessionAdmin.authority >= '1'}">
				<span class="button blue"> <a>管理员：${sessionScope.sessionAdmin.adminname}</a>
				</span>
				<span class="button blue"> <a
					href="<c:url value='/admin/AdminCategory_findAll.action'/>">分类管理</a>
				</span>
				<span class="button blue"> <a
					href="<c:url value='/adminjsps/admin/book/main.jsp'/>">图书管理</a>
				</span>
				<span class="button blue"> <a
					href="<c:url value='/adminjsps/AdminOrder_findAll.action'/>">订单管理</a>
				</span>
				<span class="button blue"> <a target="_top"
					href="<c:url value='/admin/Admin_quit.action'/>">退出</a>
				</span>
			</c:when>
			<c:otherwise>
				<span class="button blue"> <a>卖家：${sessionScope.sessionAdmin.adminname }</a>
				</span>
				<span class="button blue"><a
					href="<c:url value='/adminjsps/admin/book/main.jsp'/>">图书管理</a> </span>
				<span class="button blue"><a
					href="<c:url value='/adminjsps/AdminOrder_findAll.action'/>">订单管理</a>
				</span>
				<span class="button blue"> <a target="_top"
					href="<c:url value='/admin/Admin_quit.action'/>">退出</a>
				</span>
			</c:otherwise>
		</c:choose>
	</div>
	</section>
</body>
</html>
