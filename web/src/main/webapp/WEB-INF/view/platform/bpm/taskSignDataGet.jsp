<%--
	time:2011-12-19 15:29:52
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>任务会签数据明细</title>
	<%@include file="/commons/include/getById.jsp" %>
	<script type="text/javascript">
		//放置脚本
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">任务会签数据详细信息</span>
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
						<th width="20%">流程实例ID:</th>
						<td>${taskSignData.actInstId}</td>
					</tr>
					<tr>
						<th width="20%">流程节点名称:</th>
						<td>${taskSignData.nodeName}</td>
					</tr>
					<tr>
						<th width="20%">nodeId:</th>
						<td>${taskSignData.nodeId}</td>
					</tr>
					<tr>
						<th width="20%">会签任务Id:</th>
						<td>${taskSignData.taskId}</td>
					</tr>
					<tr>
						<th width="20%">投票人ID:</th>
						<td>${taskSignData.voteUserId}</td>
					</tr>
					<tr>
						<th width="20%">投票人名:</th>
						<td>${taskSignData.voteUserName}</td>
					</tr>
					<tr>
						<th width="20%">投票时间:</th>
						<td>${taskSignData.voteTime}</td>
					</tr>
					<tr>
						<th width="20%">是否同意:</th>
						<td>${taskSignData.isAgree}</td>
					</tr>
					<tr>
						<th width="20%">投票意见内容:</th>
						<td>${taskSignData.content}</td>
					</tr>
					<tr>
						<th width="20%">signNums:</th>
						<td>${taskSignData.signNums}</td>
					</tr>
					<tr>
						<th width="20%">是否完成:</th>
						<td>${taskSignData.isCompleted}</td>
					</tr>
				</table>
		</div>
</div>

</body>
</html>
