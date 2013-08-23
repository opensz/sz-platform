<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="system" tagdir="/WEB-INF/tags/system" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div style="padding:4px;margin: 4px;font-size:12px;line-height: 20px;">
<c:choose>
	<c:when test="${fn:length(candidateUsers)==0 && fn:length(assignees)==0}">
		<div style="width:80px"><font color='red'>暂无指定人员</font></div>
	</c:when>
	<c:when test="${fn:length(candidateUsers)>0}">
		<c:forEach items="${candidateUsers}" var="userId">
			<div><system:userName userId="${userId}"/></div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:forEach items="${assignees}" var="userId">
			<div><system:userName userId="${userId}"/></div>
		</c:forEach>
	</c:otherwise>
</c:choose>
</div>