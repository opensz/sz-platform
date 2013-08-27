<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:if test="${empty styleName}">
	<c:set var="styleName" value="blue" />
</c:if>
<link href="${ctx}/styles/${styleName}/css/Aqua/css/ligerui-all.css" type="text/css" rel="stylesheet" name="styleTag" title="index">
<link href="${ctx}/styles/${styleName}/css/web.css" type="text/css" rel="stylesheet" name="styleTag" title="index">
