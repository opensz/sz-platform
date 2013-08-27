<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>404 - 页面未找到</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/css/default.css" />
</head>
<body class="index-bg">
	<div style="height: 100px;"></div>
	<div class="errorcode">
		<h1 class="error-digits">404</h1>
		<p><img class="ico" src="${ctx }/commons/image/error.gif"  alt="" /> 
		您访问的页面不存在!</p>
	</div>
	<div class="error-footer">${tools.copyright }</div>
</body>
</html>