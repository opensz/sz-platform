<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="java.util.Map"%>
<%@page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%
	Exception e;
		try {
			ValueStack vs = (ValueStack) request.getAttribute("struts.valueStack");
			e = (Exception) vs.findValue("exception");
		} catch (Exception ex) {
			e = pageContext.getException();
		}
	if (e != null) {
		e.printStackTrace();
		pageContext.setAttribute("exception", e);
		pageContext.setAttribute("exceptionMessage", e.getMessage());
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>500 - 服务器异常</title>
	<link rel="stylesheet" type="text/css" href="${tools.ctx }/css/default.css" />
	<script type="text/javascript">
		try {
			if (parent.messageCallback) {
				try {
					parent.$.ligerDialog.closeWaitting();
				} catch (e) {
				}
				parent.$.ligerDialog.error("<li>操作失败</li><li>${pageScope.exception.message }</li>");
				parent.messageCallback = null;
			}
		} catch (e) {
		}
	</script>
</head>
<body class="index-bg">
	<div style="height: 100px;"></div>
	<div class="errorcode">
		<h1 class="error-digits">500</h1>
		<p><img class="ico" src="${tools.ctx }/images/warning_icon.png"  alt="" /> 您访问的页面出现异常!</p>
		<c:if test="${tools.debug }">
			<p style="text-align: left;"><c:out value="${pageScope.exception }" /></p>
			<p style="text-align: left;"><c:out value="${pageScope.exception.cause }" /></p>
			<p style="text-align: left;"><c:out value="${pageScope.exception.message }" /></p>
		</c:if>
	</div>
	<div class="error-footer">${tools.copyright }</div>
</body>
</html>