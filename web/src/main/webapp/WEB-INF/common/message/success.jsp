<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<jsp:useBean id="tools" class="orcom.hanweis.framework.common.webapp.bean.ActionToolsscope="page" />
<jsp:setProperty name="tools" property="request" value="${pageContext.request}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<s:if test="#parameters.url!=null"><meta http-equiv="Refresh" content="2; url=${tools.ctx}<s:text name="%{#parameters.url}" />.${tools.suffix}" /></s:if>
	<title>操作成功</title>
	<link rel="stylesheet" type="text/css" href="${tools.ctx }/css/default.css" />
	<!--[if IE 6]>
		<script src="${tools.ctx }/script/DD_belatedPNG.min.js"></script>
	<![endif]-->
</head>
<body class="index-bg">
	<div style="height: 100px;"></div>
	<div class="messagebox successbox">
		<div class="message">
			<div class="content">
				<div class="text">
					<p><s:if test="#parameters.message!=null"><s:text name="%{#parameters.message}" /></s:if><s:else><s:text name="message_success" /></s:else></p>
					<s:if test="#parameters.url!=null"><p>2秒后自动跳转 <a href="${tools.ctx}<s:text name="%{#parameters.url}" />.${tools.suffix}"><s:text name='%{#parameters.url}_name' /></a></p></s:if>
				</div>
			</div>
		</div>
	</div>
	<div class="error-footer">${tools.copyright }</div>
</body>
</html>