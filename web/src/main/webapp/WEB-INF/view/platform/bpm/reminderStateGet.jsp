
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>任务催办执行情况明细</title>
	<%@include file="/commons/include/getById.jsp" %>
	<script type="text/javascript">
		//放置脚本
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">任务催办执行情况详细信息</span>
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
						<th width="20%">流程定义ID:</th>
						<td>${reminderState.actDefId}</td>
					</tr>
					<tr>
						<th width="20%">任务ID:</th>
						<td>${reminderState.taskId}</td>
					</tr>
					<tr>
						<th width="20%">催办时间:</th>
						<td>${reminderState.reminderTime}</td>
					</tr>
				</table>
		</div>
</div>

</body>
</html>
