<%--
	time:2011-12-19 15:29:52
	desc:edit the 任务会签数据
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 任务会签数据</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=taskSignData"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#taskSignDataForm').submit(); 
			});
		});
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
				     <c:choose>
						<c:when test="${taskSignData.dataId==null}">
							添加任务会签数据
						</c:when>
						<c:otherwise>
							编辑任务会签数据
						</c:otherwise>
					 </c:choose> 	
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.xht">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<form id="taskSignDataForm" method="post" action="save.xht">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">流程实例ID: </th>
							<td><input type="text" id="actInstId" name="actInstId" value="${taskSignData.actInstId}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">流程节点名称:  <span class="required">*</span></th>
							<td><input type="text" id="nodeName" name="nodeName" value="${taskSignData.nodeName}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">nodeId: </th>
							<td><input type="text" id="nodeId" name="nodeId" value="${taskSignData.nodeId}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">会签任务Id: </th>
							<td><input type="text" id="taskId" name="taskId" value="${taskSignData.taskId}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">投票人ID:  <span class="required">*</span></th>
							<td><input type="text" id="voteUserId" name="voteUserId" value="${taskSignData.voteUserId}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">投票人名: </th>
							<td><input type="text" id="voteUserName" name="voteUserName" value="${taskSignData.voteUserName}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">投票时间:  <span class="required">*</span></th>
							<td><input type="text" id="voteTime" name="voteTime" value="<fmt:formatDate value='${taskSignData.voteTime}' pattern='yyyy-MM-dd'/>" class="inputText date"/></td>
						</tr>
						<tr>
							<th width="20%">是否同意:  <span class="required">*</span></th>
							<td><input type="text" id="isAgree" name="isAgree" value="${taskSignData.isAgree}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">投票意见内容: </th>
							<td><input type="text" id="content" name="content" value="${taskSignData.content}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">signNums: </th>
							<td><input type="text" id="signNums" name="signNums" value="${taskSignData.signNums}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">是否完成: </th>
							<td><input type="text" id="isCompleted" name="isCompleted" value="${taskSignData.isCompleted}"  class="inputText"/></td>
						</tr>
					</table>
					<input type="hidden" name="dataId" value="${taskSignData.dataId}" />
				</form>
		</div>
</div>
</body>
</html>
