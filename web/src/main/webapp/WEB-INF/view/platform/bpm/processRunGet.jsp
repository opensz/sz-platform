<%--
	time:2011-12-04 18:56:52
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system" %>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>流程实例明细</title>
	<%@include file="/commons/include/getById.jsp" %>
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerTab.js" ></script>

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
                $("#taskOpinionDiv").load('${ctx}/platform/bpm/taskOpinion/list.xht?actInstId=${processRun.actInstId}&randId='+ Math.random());
                $("#iframetabitem2").height($(window).height() - 100);
                if($("#iframetabitem4").length > 0){
	                $("#iframetabitem4").height($(window).height() - 100);
                }
            });
     </script> 
	<style type="text/css"> 
	    body{ padding:0px; margin:0;overflow:auto;}  
	</style>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程实例扩展详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back " href="myStart.xht">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<div id="tabMyInfo">
					<div title="流程运行明细">
						<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th width="20%">
									运行实例ID(runId)
								</th>
								<td>${processRun.runId}</td>
							</tr>
							<tr>
								<th width="20%">流程定义ID:</th>
								<td>${processRun.defId}</td>
							</tr>
							<tr>
								<th width="20%">流程实例标题:</th>
								<td>${processRun.subject}</td>
							</tr>
							<tr>
								<th width="20%">创建人ID:</th>
								<td>${processRun.creatorId}</td>
							</tr>
							<tr>
								<th width="20%">创建人:</th>
								<td>${processRun.creator}</td>
							</tr>
							<tr>
								<th width="20%">创建时间:</th>
								<td>${f:shortDate(processRun.createtime)}</td>
							</tr>
							<tr>
								<th width="20%">业务表单简述:</th>
								<td>${processRun.busDescp}</td>
							</tr>
							<tr>
								<th width="20%">状态:</th>
								<td>
									<c:choose>
									<c:when test="${processRun.status==1}">
										<font color='green'>正在运行</font>
									</c:when>
									<c:when test="${processRun.status==2}">
										结束
									</c:when>
								</c:choose>
								
								</td>
							</tr>
							<tr>
								<th width="20%">ACT流程实例ID:</th>
								<td>${processRun.actInstId}</td>
							</tr>
							<tr>
								<th width="20%">ACT流程定义ID:</th>
								<td>${processRun.actDefId}</td>
							</tr>
							<tr>
								<th width="20%">businessKey:</th>
								<td>${processRun.businessKey}</td>
							</tr>
						</table>
					</div>
					<div title="流程示意图">						
								<iframe id="iframetabitem2" presrc="${ctx}/platform/bpm/processRun/userImage.xht?runId=${processRun.runId}" marginheight="0"  marginwidth="0" frameborder="0" scrolling="auto"></iframe>						
					</div>
					<div title="流程审批历史" id="taskOpinionDiv">
					</div>
					<div title="业务表单">
						<c:choose>
							<c:when test="${bpmDefinition.businessType != 'ITSM3' }">
								<%-- <iframe id="iframetabitem4" presrc="${ctx}/platform/bpm/processRun/nodeForms.xht?runId=${processRun.runId}" marginheight="0"  marginwidth="0" frameborder="0" scrolling="auto"></iframe>--%>
								<iframe id="iframetabitem4" presrc="${ctx}/platform/form/bpmFormHandler/bizForm.xht?runId=${processRun.runId}" marginheight="0"  marginwidth="0" frameborder="0" scrolling="auto"></iframe>
							</c:when>
							<c:otherwise>
								<iframe id="iframetabitem4" presrc="${ctx}/platform/form/bpmFormHandler/extForm.xht?runId=${processRun.runId}" marginheight="0"  marginwidth="0" frameborder="0" scrolling="auto"></iframe>
							</c:otherwise>
						</c:choose>
					</div>
			</div><!-- end of tab panel -->
		</div>
</div>

</body>
</html>
