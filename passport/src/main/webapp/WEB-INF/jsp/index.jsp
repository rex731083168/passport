<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Question</title>
</head>
<body>
	
	
	<form id="mainForm" action="/login" name="coupon"
		method="post" class="form-horizontal form-row-seperated" enctype="multipart/form-data"
		onsubmit="return check()">

		
		<div class="control-group">
			<label class="control-label">88888:</label>
			<div class="controls">
				<label class="radio"> <input type="radio" name="ankle"
					value="true" checked/>是
				</label> <label class="radio"> <input type="radio" name="ankle"
					value="false"  />否
				</label> 
			</div>
		</div>
		


		<div class="form-actions">
			<button type="submit" class="btn blue">
				<i class="icon-ok"></i> 提交
			</button>
			<button type="button" onclick="cancel()" class="btn" >取消</button>
		</div>
	</form>
</body>
</html>