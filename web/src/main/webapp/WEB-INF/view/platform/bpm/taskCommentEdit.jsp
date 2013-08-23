<%--
	time:2012-04-05 17:02:32
	desc:edit the 流程任务评论
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 流程任务评论</title>
	<%@include file="/commons/include/form.jsp" %>
	<%@ taglib prefix="sz" uri="http://www.servicezon.com/paging" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=taskComment"></script>
	<script type="text/javascript">
		
		function showRequest(formData, jqForm, options) { 
			return true;
		} 
	
		$(function() {
			
			valid(showRequest,function(){});

		});
		
		function save(){

			var rtn=$("#taskCommentForm").valid();
   		 	if(!rtn) return;
   		 	
			var url=__ctx+ "/platform/bpm/taskComment/save.xht";
   		 	var para=$('#taskCommentForm').serialize();

   	    	$.ajax({
   	    		type: 'POST',
   	    		url: url,
   	    		data: para,
   	    		success: function(result){

   	    			var obj = eval('(' + result + ')');
   	    			if(obj.result==1){
   	   	    			$.ligerMessageBox.success('操作成功',obj.message,function(){
   	   	  			
   	   	    				$('#content').val('');
							this.window.location.reload();

   	   	    			});
   	    			}else{
   	   	    			$.ligerMessageBox.error('操作失败',obj.message,function(){});
   	    			}
   	    		}
   	    	});
   		 	
		}
		
		function loadTaskCommentPanel(actDefId,runId,taskId){
			$("#tcPanel").empty();
			
			$("#tcPanel")
				.load('${ctx}/platform/bpm/taskComment/list.xht?taskId='+taskId
				+'&actDefId='+actDefId+'&runId='+runId,
			null,function(){});
		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">
			   <c:choose>
					<c:when test="${taskComment.content==null}">
						添加流程任务评论
					</c:when>
					<c:otherwise>
						编辑流程任务评论
					</c:otherwise>
				</c:choose> 		
			</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:this.save()">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="javascript:this.close()">关闭</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		
		
		<form id="taskCommentForm" method="post" action="save.xht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th width="20%">流程名称:</th>
					<td>${taskComment.subject}</td>
				</tr>
				<tr>
					<th width="20%">任务节点名称:</th>
					<td>${taskComment.nodeName}</td>
				</tr>
				<tr>
					<th width="20%">评论内容: </th>
					<td>
						<textarea rows="5" cols="60" id="content" name="content"
							style="margin-top: 5px;margin-bottom: 5px;">${taskComment.content}</textarea>
						<input type="hidden" id="actDefId" name="actDefId" value="${taskComment.actDefId}" />
						<input type="hidden" id="runId" name="runId" value="${taskComment.runId}" />
						<input type="hidden" id="taskId" name="taskId" value="${taskComment.taskId}" />
					</td>
				</tr>
			</table>
		</form>
		
		<br>
		
		<display:table name="taskCommentList" id="taskCommentItem" requestURI="edit.xht" 
			sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
			<display:column property="author" title="评论人" sortable="true" sortName="author"></display:column>
			<display:column  title="评论时间" sortable="true" sortName="commentTime">
				<fmt:formatDate value="${taskCommentItem.commentTime}" pattern="yyyy-MM-dd"/>
			</display:column>
			<display:column property="content" title="评论内容" sortable="false" 
				sortName="content" maxLength="80"></display:column>
			</display:table>
		<sz:paging tableId="taskCommentItem"/>

	</div>
</div>
</body>
</html>
