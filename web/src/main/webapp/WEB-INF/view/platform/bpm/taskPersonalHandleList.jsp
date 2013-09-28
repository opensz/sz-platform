<%@page language="java" pageEncoding="UTF-8"%>
<html>
  <head>
  <title>待处理工作列表</title>
  <%@include file="/commons/include/get.jsp" %>
  <%@include file="/commons/include/params.jsp"%>
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

function createCase(){
	
	//$.post(url,params,function(responseText){
		 //console.log(responseText);
	//});
	
    /**
	var win = $.ligerDialog.open({ 
		content : "11111111111111", 
		height: 400, 
		width:800, 
		isResize: false }
	);
	*/
	
	top.createCaseWindow('incident');
	
}
</script>
  </head>
  <body>
  <div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">待处理工作列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link add" onclick="createCase()">创建</a></div>
				</div>	
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-search">
				<form id="searchForm" method="post" action="forMe.xht">
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
					<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${taskItem.id}">
					</display:column>
					<display:column property="caseNo" title="编号"></display:column>
					<display:column title="合规时间">
						<fmt:formatDate value="${taskItem.complianceTime}" pattern="yyyy-MM-dd HH:mm"/>
					</display:column>
					<display:column property="projectName" title="项目"></display:column>
					<display:column property="serviceLevelName" title="服务级别"></display:column>
					<display:column title="期望时间">
						<fmt:formatDate value="${taskItem.expectTime}" pattern="yyyy-MM-dd HH:mm"/>
					</display:column>
					<display:column title="请求人"></display:column>
					<display:column title="请求时间">
						<fmt:formatDate value="${taskItem.requestTime}" pattern="yyyy-MM-dd HH:mm"/>
					</display:column>
					<display:column property="title" title="简要描述"></display:column>
					<display:column title="操作" media="html" style="width:200px;">						
						<c:if test="${empty taskItem.assignee}">
						<a href="claim.xht?taskId=${taskItem.id}" class="link lock" title="锁定">锁定</a>
						</c:if>
						<a href="detail.xht?taskId=${taskItem.id}" class="link detail" title="明细">明细</a>
						<c:if test="${not empty taskItem.assignee}">
							<a href="unlock.xht?taskId=${taskItem.id}" class="link unlock" title="解锁">解锁</a>
							<a href="javascript:executeTask(${taskItem.id})" class="link run" title="主办">主办</a>
							<a href="javascript:changeTaskUser(${taskItem.id},'${taskItem.name}')" class="link goForward" title="交办">交办</a>
						</c:if>
					</display:column>
				</display:table>
				<sz:paging tableId="taskItem"/>
			</div>
		</div>		
	</div>
  </body>
</html>
