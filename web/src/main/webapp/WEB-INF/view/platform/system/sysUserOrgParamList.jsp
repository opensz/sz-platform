
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>组织参数属性</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>
<div class="panel">
	<f:tab curTab="4" tabName="sysOrg"/>	
	<c:choose>
		<c:when test="${empty sysOrg}">
			<div style="text-align: center;margin-top: 10%;">尚未指定具体组织!</div>
		</c:when>
		<c:otherwise>
	    	<display:table name="userParamList" id="paramItem"  cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column property="paramName" title="参数名" style="text-align:left;" ></display:column>
				<display:column property="paramValue"  title="参数值" media="html"></display:column>
			</display:table>
		</c:otherwise>
	</c:choose>	
 </div>
</body>
</html>