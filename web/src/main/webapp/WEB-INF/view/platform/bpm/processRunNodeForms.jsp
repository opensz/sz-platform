<%@page pageEncoding="UTF-8"%>
<html>
	<head>
		<title>流程实例业务表单明细</title>
		<%@include file="/commons/include/getById.jsp" %>
		<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
	</head>
	<body>
		<table class="table-detail" style="width:100%" >
			<c:forEach items="${bpmNodeSetList }" var="nodeSet">
				<tr>
					<th width="200" nowrap="nowrap">${nodeSet.nodeName}(${nodeSet.nodeId})</th>
					<td><a href="#" class="link detail" onclick="jQuery.openFullWindow('${ctx}/platform/bpm/processRun/formHtml.xht?runId=${processRun.runId}&nodeId=${nodeSet.nodeId}')">查看表单</a></td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>