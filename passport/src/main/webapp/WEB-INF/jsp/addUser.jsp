<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
</head>
<body>


	<form id="mainForm" action="register" name="coupon" method="post"
		class="form-horizontal form-row-seperated"
		enctype="multipart/form-data" onsubmit="return check()">

		</div>
		<label>姓名</label> <input class="long_input" type="text" maxlength=11
			id="username" name="username">

		</div>
		<div class="line fl"></div>
		</div>
		<label>手机号:</label> <input class="long_input" type="text" id="mobile"
			name="mobile">
		</div>
		<div class="line fl"></div>
		</div>
		<label>登录密码:</label> <input class="long_input" type="text"
			id="password" name="password">
		</div>
		<div class="line fl"></div>

		<div class="line fl"></div>

		<div class="line fl"></div>
		<label>角色:</label>
		<c:forEach var="role" items="${roles}" varStatus="i">
			<input type="checkbox" id="roleId" name="roleId">
							${role.roleName }
					
			</c:forEach>
		<div class="form-actions">
			<button type="submit" class="btn blue">
				<i class="icon-ok"></i> 提交
			</button>
			<button type="button" onclick="cancel()" class="btn">取消</button>
		</div>
	</form>
</body>
</html>