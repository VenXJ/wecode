<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<title>Insert title here</title>
<!-- JQuery文件,务必在bootstrap.min.js 之前引入 -->
<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<!-- Bootstrap3的 JavaScript文件 -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<style>
body {
	margin-left: auto;
	margin-right: auto;
	margin-top: 200px;
	width: 20em;
}
</style>
</head>
<body>
	<!--下面是用户名输入框-->
	<form role="form" action="user" method="post">
		<div class="input-group input-group-lg">
			<span class="input-group-addon" id="basic-addon1">@</span> 
			<input id="userName" name="userName" type="text" class="form-control" placeholder="用户名" aria-describedby="basic-addon1">
		</div>
		<br>
		<!--下面是密码输入框-->
		<div class="input-group input-group-lg">
			<span class="input-group-addon" id="basic-addon1">@</span> 
			<input id="passWord" name="passWord" type="password" class="form-control" placeholder="密码" aria-describedby="basic-addon1">
		</div>
		<br>
		<!--下面是登陆按钮,包括颜色控制-->
		<div class="btn-group btn-group-lg">
			<button type="submit" style="width: 280px;" class="btn btn-warning loginbutton">登录</button>
		</div>
	</form>
</body>
</html>