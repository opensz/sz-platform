<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>任务管理列表</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js" ></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMenu.js" ></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMenuBar.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/TaskCommentWindow.js" ></script>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system" %>
<script type="text/javascript">
	//为某个任务分配人员
	function assignTask(){
		var taskIds=new Array();
		$("input[name='id']:checked").each(function(){
			taskIds.push($(this).val());
		});
		if(taskIds.length==0){
			$.ligerMessageBox.warn('提示信息',"没有选择任务!");
			return;
		}
		UserDialog({
			isSingle:true,
			callback:function(userId,fullname){
				if(!userId) return;
				$.ajax({
				     type: "POST",
				     url: "${ctx}/platform/bpm/task/assign.xht",
				     data: {
				    	taskIds:taskIds.join(','),
				    	userId:userId
				     },
				     dataType: "text",
				     success: function (request) {
				    	 document.location.reload();
				     },
				     error: function(request,error){}
					});
			}
		});
	}
	
	function setTaskAssignee(link,taskId){
		var url="${ctx}/platform/bpm/task/setAssignee.xht";
		var callback=function(userId,fullname){
			$(link).siblings("span").html('<img src="${ctx}/themes/img/commons/user-16.png">' + fullname);	
		};
		setTaskExecutor(taskId,url,callback);
	}
	
	function setTaskOwner(link,taskId){
		var url="${ctx}/platform/bpm/task/setOwner.xht";
		var callback=function(userId,fullname){
			$(link).siblings("span").html('<img src="${ctx}/themes/img/commons/user-16.png">' + fullname);	
		};
		setTaskExecutor(taskId,url,callback);
	}
	
	//设置任务的执行人
	function setTaskExecutor(taskId,url,callback){
		var taskIds=new Array();
		if(!taskId){
			$("input[name='id']:checked").each(function(){
				taskIds.push($(this).val());
			});	
		}else{
			taskIds.push(taskId);
		}
		if(taskIds.length==0){
			$.ligerMessageBox.warn('提示信息',"没有选择任务!");
			return;
		}
		//显示用户选择器
		UserDialog({
			isSingle:true,
			callback:function(userId,fullname){
				if(userId=='' || userId==null || userId==undefined) return;
				$.ajax({
				     type: "POST",
				     url: url,
				     data: {
				    	taskIds:taskIds.join(','),
				    	userId:userId
				     },
				     dataType: "text",
				     success: function (request) {
				    	 $.ligerMsg.correct('<p><font color="green">操作成功!</font></p>'); 
				    	 if(!callback){
				    	 	document.location.reload();
				    	 }else{
				    		 callback.call(this,userId,fullname);
				    	 }
				     },
				     error: function(request,error){
				    	 $.ligerMsg.error('操作失败!'); 
				     }  
					}
				);	
			}
		});
	}
	//执行任务
	function executeTask(taskId){
		 var url="${ctx}/platform/bpm/task/toStart.xht?taskId="+taskId;
		 jQuery.openFullWindow(url);
	}
	
	$(function(){
		var layout=$("#taskLayout").ligerLayout({rightWidth:300,height: '100%',isRightCollapse:true});
		$("tr.odd,tr.even").each(function(){
			$(this).bind("mousedown",function(event){
				if(event.target.tagName!="TD")  return;
				var strFilter='input[type="checkbox"][class="pk"]';
				var obj=$(this).find(strFilter);
				if(obj.length==1){
					var taskId=obj.val();
					layout.setRightCollapse(false);
					$("#taskDetailPanel").html("<div>正在加载...</div>");
					//在任务表单明细面版中加载任务详细
					$("#taskDetailPanel").load('${ctx}/platform/bpm/task/miniDetail.xht?manage=true&taskId='+taskId,null);
				}
			});    
		});
	});
	
</script>
</head>
<body style="overflow: hidden;">
	<div class="panel">
		<div id="taskLayout" >
           	<div position="center" style="overflow: auto;">
           		<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">任务管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link grant" onclick="assignTask();">分配任务</a></div>
							<div class="group"><a class="link del" action="delete.xht">删除任务</a></div> 
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
						<form id="searchForm" method="post" action="list.xht">
							<div class="row">
								<span class="label">流程定义名称:</span><input type="text" name="Q_processName_S"  class="inputText" style="width:13%;" value="${param['Q_processName_S']}" />
								<span class="label">事项名称:</span><input type="text" name="Q_subject_S"  class="inputText" style="width:13%;" value="${param['Q_subject_S']}"/>
								<span class="label">任务名称:</span><input type="text" name="Q_name_S"  class="inputText" style="width:13%;" value="${param['Q_name_S']}"/>
								<a id="btnSearch" class='button' onclick="$('#searchForm').submit();"><span>查询</span></a>
							</div>
						</form>
					</div>					
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="taskList" id="taskItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"   class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${taskItem.id}">
							</display:column>
							<display:column property="subject" title="事项名称" sortable="true" sortName="subject" style="text-align:left;" ></display:column>
							<display:column property="name" title="任务名称" sortable="true" sortName="name_" style="text-align:left;"></display:column>
							<display:column title="所属人" sortable="true" sortName="owner_">
								<system:userName userId="${taskItem.owner}"/>
							</display:column>
							<display:column title="执行人" sortable="true" sortName="assignee_">
								<system:userName userId="${taskItem.assignee}"/>
							</display:column>
							<display:column title="状态" sortable="true" sortName="delegation_">
								<c:choose>
									<c:when test="${empty taskItem.delegationState}">待执行</c:when>
									<c:otherwise>${taskItem.delegationState}</c:otherwise>
								</c:choose>
							</display:column>
							<display:column title="创建时间" sortable="true" sortName="create_time_">
								<fmt:formatDate value="${taskItem.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</display:column>
							<display:column title="管理" media="html" style="width:160px;">
								<a href="detail.xht?taskId=${taskItem.id}" class="link detail" title="明细">明细</a>
								<a href="delete.xht?taskId=${taskItem.id}" class="link del" title="删除">删除</a>
								<a href="javascript:executeTask(${taskItem.id},'${taskItem.name}')" class="link run" title="主办">办理</a>
							</display:column>
						</display:table>
						<sz:paging tableId="taskItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> 
			<div id="taskDetailPanel" position="right" title="任务明细"></div>
		</div>
		
	</div><!--  end of panel -->
</body>
</html>


