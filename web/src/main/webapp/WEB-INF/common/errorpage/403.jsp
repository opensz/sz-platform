<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<jsp:useBean id="tools" class="org.servicezone.framework.common.webapp.bean.ActionTools" scope="page" />
<jsp:setProperty name="tools" property="request" value="${pageContext.request}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>403 - 没有访问权限</title>
	<link rel="stylesheet" type="text/css" href="${tools.ctx }/css/default.css" />
</head>
<body class="index-bg">
	<div style="height: 100px;"></div>
	<div class="errorcode">
		<h1 class="error-digits">403</h1>
		<p><img class="ico" src="${tools.ctx }/images/warning_icon.png"  alt="" /> 您没有权限访问当前页面,请联系管理员!</p>
	</div>
	<div class="error-footer">${tools.copyright }</div>
</body>
</html>