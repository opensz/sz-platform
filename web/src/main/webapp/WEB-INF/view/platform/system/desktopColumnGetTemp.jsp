<%--
	time:2012-03-20 16:39:01
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>桌面栏目模板</title>
<%@include file="/commons/include/form.jsp" %>
<%@include file="/commons/include/get.jsp" %>
<link href="${ctx}/js/desktop/inettuts.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/desktop/inettuts.js.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">查看栏目模板</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" href="list.xht">返回</a></div>
				</div>
			</div>
		</div>
</div>
	   ${html}	
</body>
</html>
