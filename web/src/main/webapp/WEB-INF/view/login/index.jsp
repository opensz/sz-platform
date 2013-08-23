<%@page import="org.sz.core.util.SpringContextHolder"%>
<%@page import="java.util.Properties"%>
<%@ page pageEncoding="UTF-8" %>
<%@page import="org.springframework.security.web.WebAttributes"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SZ BPM业务管理平台--登录</title>
<link type="text/css" rel="stylesheet" href="${ctx}/styles/default/css/login-logo.css" />
<script>
function locationUrl(url){
	document.location.href=url;
}

</script>
</head>
<body>

	<ul class="login_logo">
	
		<c:forEach items="${loginList}" var="model">
			<img id="logoImg" src="${ctx}${model.logo}" title="${model.sysName}" onclick="locationUrl('${ctx}/${model.defaultUrl}?systemId=${model.systemId}');"/>
		</c:forEach>
		<!--<li id="logo01" onclick="locationUrl('${ctx}/login/login1.jsp');"></li>
		<li id="logo02" onclick="locationUrl('${ctx}/login/login03.jsp');"></li>
		<li id="logo03" onclick="locationUrl('${ctx}/login/login02.jsp');"></li>	
	--></ul>

</body>
</html>
