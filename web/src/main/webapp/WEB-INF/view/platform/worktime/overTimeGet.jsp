
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>加班情况明细</title>
	<%@include file="/commons/include/getById.jsp" %>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">加班情况详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" href="list.xht">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th width="20%">标题:</th>
						<td>${overTime.subject}</td>
					</tr>
					<tr>
						<th width="20%">用户名称:</th>
						<td>${overTime.userName}</td>
					</tr>
					<tr>
						<th width="20%">工作日类型:</th>
						<td><c:if test="${overTime.workType==1}">加班</c:if>
						<c:if test="${overTime.workType==2}">请假</c:if></td>
					</tr>
					<tr>
						<th width="20%">开始时间:</th>
						<td><fmt:formatDate value="${overTime.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<th width="20%">结束时间:</th>
						<td><fmt:formatDate value="${overTime.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
		</div>
</div>

</body>
</html>
