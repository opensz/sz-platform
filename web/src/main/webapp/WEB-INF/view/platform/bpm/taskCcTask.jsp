<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>我的待办任务</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system" %>
<script type="text/javascript">
function executeTask(taskId){
//	 var url="${ctx}/platform/bpm/task/toStart.xht?taskId="+taskId;
	 var url="/platform/bpm/task/toStart.xht?taskId="+taskId;
	 //jQuery.openFullWindow(url);
	 var icon = __ctx +　"/styles/default/images/resicon/setting.png";
	 //top.addToTab(url,"流程启动","taskStartFlowForm",icon);
	 
	 top.addToTab({
		 resId:'taskStartFlowForm',
		 resName:'流程启动',
		 isExt:0,
		 defaultUrl:url
	});
	 
}
//更改任务执行用户
function changeTaskUser(taskId,taskName){
	//显示用户选择器
	UserDialog({
		isSingle:true,
		callback:function(userId,fullname){
			if(userId=='' || userId==null || userId==undefined) return;
			$.ajax({
			     type: "POST",
			     url: '${ctx}/platform/bpm/task/setAssignee.xht',
			     data: {
			    	taskId:taskId,
			    	userId:userId
			     },
			     dataType: "text",
			     success: function (request,error) {
			    	 $.ligerMsg.correct('<p><font color="green">成功进行流程转移!</font></p>'); 
			    	 document.location.reload();
			     },
			     error: function(request,error){
			    	 $.ligerMsg.error('操作失败!'); 
			     }
				}
			);	
		}
	});
}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">任务管理列表</span>
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-search">
				<form id="searchForm" method="post" action="ccTask.xht">
					<div class="row">
						<span class="label">流程定义名称:</span><input type="text" name="Q_processName_S"  class="inputText" style="width:13%;"/>
						<span class="label">事项名称:</span><input type="text" name="Q_subject_S"  class="inputText" style="width:13%;"/>
						<span class="label">任务名称:</span><input type="text" name="Q_name_S"  class="inputText" style="width:13%;"/>
						<a id="btnSearch" class='button' onclick="$('#searchForm').submit();"><span>查询</span></a>
					</div>
				</form>
			</div>
			<div class="panel-data">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="taskList" id="taskItem" requestURI="forMe.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${taskItem.id}">
					</display:column>
					<display:column property="subject" title="事项名称" style="text-align:left"></display:column>
					<display:column property="processName" title="流程定义名称" style="text-align:left"></display:column>
					<display:column property="name" title="任务名称" sortable="true" sortName="name_" style="text-align:left"></display:column>
					<display:column title="所属人" sortable="true" sortName="owner_" style="text-align:left">
						<system:userName userId="${taskItem.owner}"/>
					</display:column>
					<display:column title="执行人" sortable="true" sortName="assignee_" style="text-align:left">
						<system:userName userId="${taskItem.assignee}"/>
					</display:column>
					<display:column title="创建时间" sortable="true" sortName="create_time_">
						<fmt:formatDate value="${taskItem.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column title="到期时间" sortable="true" sortName="due_date_">
						<fmt:formatDate value="${taskItem.dueDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column title="管理" media="html" style="width:240px;">						
						<a href="getForm.xht?taskId=${taskItem.id}" target="_blank" class="link detail" title="明细">明细</a>
					</display:column>
				</display:table>
				<sz:paging tableId="taskItem"/>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


