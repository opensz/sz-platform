<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
	<head>
		<title>编辑 系统角色表</title>
		<%@include file="/commons/include/get.jsp" %>
		<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerTab.js" ></script>
	   <script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMenu.js" ></script>
	   <script type="text/javascript">
	           var tab = null;
	           $(function (){
	           	var h = $(window).height() - 70;            	
	               $("#tabMyInfo").ligerTab({height:h,onBeforeSelectTabItem:function(tabid){
	               	var iframe=$("#iframe"+tabid);
	             		if(iframe.length>0){
	             			iframe.attr("src",iframe.attr("presrc"));
	             		}
	               }});
	               for(var i=2;i<7;i++){
	               	$("#iframetabitem"+i).height(h - 30);
	               }
	             	$("#taskDetailDiv").load('${ctx}/platform/bpm/task/miniDetail.xht?taskId=${task.id}');              
	           });
	           
	    </script> 
	</head>
	<body>
		<div class="panel">
			<div class="panel-top">
				<div class="tbar-title">
					<span class="tbar-label">任务明细--${processRun.subject}--${task.name}</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" onclick="history.back(-1);">返回</a></div>
				</div>
			</div>
		</div>
		<div id="tabMyInfo">
			<div id="taskDetailDiv" title="任务明细" style="overflow: auto">
			</div>
			<div title="任务流程执行人员明细">
				<iframe id="iframetabitem2" presrc="${ctx}/platform/bpm/processRun/userImage.xht?taskId=${task.id}" height="100%" width="100%" marginheight="0"  marginwidth="0" frameborder="0" scrolling="auto"></iframe>
			</div>
			<div title="任务审批明细">
				<iframe id="iframetabitem3" presrc="${ctx}/platform/bpm/taskOpinion/list.xht?actInstId=${task.processInstanceId}" height="100%" width="100%"  marginheight="0"  marginwidth="0" frameborder="0" scrolling="auto"></iframe>
			</div>
		    <div title="任务变量管理">
		    	<iframe id="iframetabitem4" height="100%" width="100%" frameborder="0" presrc="${ctx}/platform/bpm/taskVars/list.xht?taskId=${task.id}"></iframe>
			</div>
			<div title="业务表单">
				<c:choose>
					<c:when test="${bpmDefinition.businessType != 'ITSM3' }">
						<iframe id="iframetabitem5" height="100%" width="100%" frameborder="0" presrc="${ctx}/platform/bpm/task/getForm.xht?taskId=${task.id}"></iframe>
					</c:when>
					<c:otherwise>
						<iframe id="iframetabitem5" presrc="${ctx}/platform/form/bpmFormHandler/extForm.xht?runId=${processRun.runId}" marginheight="0"  marginwidth="0" frameborder="0" scrolling="auto"></iframe>
					</c:otherwise>
				</c:choose>
			</div>
			<c:if test="${not empty businessUrl}">
				<div id="businessDetail" title="业务明细">
					<iframe id="iframetabitem6" height="100%" width="100%" frameborder="0" presrc="${ctx}${businessUrl}"></iframe>	
				</div>
			</c:if>
			</div>
		</div>
	</body>
</html>
