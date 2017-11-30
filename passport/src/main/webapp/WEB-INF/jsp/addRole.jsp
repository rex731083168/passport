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
<title>新增角色</title>
</head>
<body>
	
	
	<form id="mainForm" action="addrole" name="coupon"
		method="post" class="form-horizontal form-row-seperated" enctype="multipart/form-data"
		onsubmit="return check()">

		</div>
		<label>角色名称</label>
				<input class="long_input" type="text"  maxlength=11 id="roleName"  name="roleName" >
		
		</div>
			<div class="line fl"></div>
		</div>
        <label>角色说明:</label>	
				<input class="long_input" type="textarea" id="description" name="description" >
        </div>
        	<div class="line fl"></div>
        </div>
        
		<div class="form-actions">
			<button type="submit" class="btn blue">
				<i class="icon-ok"></i> 创建
			</button>
			<button type="button" onclick="cancel()" class="btn" >取消</button>
		</div>
	</form>
</body>
</html>