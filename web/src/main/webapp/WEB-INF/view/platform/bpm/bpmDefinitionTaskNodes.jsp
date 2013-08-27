<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<%@include file="/commons/include/form.jsp" %>
	<title>选择流程任务列表</title>
	<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	<script type="text/javascript">
		//选择任务节点
		function selectTaskNode(){
			var nodeIdRd=$("input[name='nodeId']:checked");
			if(nodeIdRd){
				window.returnValue={nodeId:nodeIdRd.val(),nodeName:nodeIdRd.attr('nodeName')};
			}else{
				window.returnValue={nodeId:'',nodeName:''};
			}
			window.close();
		}
	</script>
</head>
<body>
	<h2>选择任务节点</h2>
	<table cellpadding="1" cellspacing="1"  class="table-grid">
		<thead>
			<tr>
				<th width="120">
					序号
				</th>
				<th>
					节点名称
				</th>
			</tr>
		</thead>
		<c:forEach items="${taskNodeMap}" var="map" varStatus="i">
			<tr>
				<td>
					${i.count}
					<input type="radio" name="nodeId" value="${map.key}" nodeName="${map.value}"/>
				</td>
				<td>
					${map.value}
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="bottom" style='margin-top:10px;'>
		<a href='#' class='button' onclick="selectTaskNode()"><span >选择</span></a>
		<a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span >取消</span></a>
	</div>
</body>
</html>