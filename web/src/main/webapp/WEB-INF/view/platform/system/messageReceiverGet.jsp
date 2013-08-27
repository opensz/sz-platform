
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>消息接收者明细</title>
	<%@include file="/commons/include/getById.jsp" %>
	<script type="text/javascript">
		//放置脚本
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">消息接收者详细信息</span>
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
						<th width="20%">消息ID:</th>
						<td>${messageReceiver.messageId}</td>
					</tr>
					<tr>
						<th width="20%">接收者类型:</th>
						<td>${messageReceiver.receiveType}</td>
					</tr>
					<tr>
						<th width="20%">接收人ID:</th>
						<td>${messageReceiver.receiverId}</td>
					</tr>
					<tr>
						<th width="20%">接收人:</th>
						<td>${messageReceiver.receiver}</td>
					</tr>
				</table>
		</div>
</div>

</body>
</html>
