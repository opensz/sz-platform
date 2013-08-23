<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
String frmId = request.getParameter("frmId");
%>

<style type="text/css" media="print">
		.noprint{display:none;} 
		.printForm{display:"block";} 
</style>
<!-- 流程操作按钮区域 -->
	<script type="text/javascript">
		var frmId="<%=frmId%>";
		var taskId='${task.id}';
		var isExtForm=eval('${isExtForm}');
		var isEmptyForm=${isEmptyForm};
		var isSignTask=${isSignTask};
		
		function showSignWindow(){
			var win=new TaskSignWindow({
				title:'任务-${task.name}会签情况列表',
				postUrl:'${ctx}/platform/bpm/task/saveSign.xht?taskId=${task.id}',
				url:'${ctx}/platform/bpm/task/toSign.xht?taskId=${task.id}',
				height:480
			});
			win.show();
		}
		//补签
		function showAddSignWindow(){
			TaskAddSignWindow({taskId:taskId,callback:function(sign){
				loadTaskSign();
			}});
		}
		
		function showTaskUserDlg(){
			TaskImageUserDialog({taskId:taskId});
		}

		//完成当前任务。
		function completeTask(){
			if(isEmptyForm){
				$.ligerMessageBox.error('提示信息',"还没有设置表单!");
				return;
			}
			if(isExtForm){
				$('#'+frmId).submit();
			}else{
				var rtn=CustomForm.validate();
				if(rtn){
					//Office控件提交。
					OfficePlugin.submit();
					var data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					$('#'+frmId).submit();
				}else{
					CustomForm.showError();
				}
			}
		}
		
		function showResponse(responseText){
			var obj=new org.sz.form.ResultMessage(responseText);
			if(obj.isSuccess()){
				alert("执行任务成功!");
				if(window.opener){
					window.opener.location.reload();
					window.close();
				}
				
			}else{
				alert("执行任务失败!");
			}
		}
		
		//弹出回退窗口 TODO 去除
		function showBackWindow(){
			new TaskBackWindow({taskId:taskId}).show();
		}
		
		$(function(){
			initForm();
			if(isSignTask){
				loadTaskSign();
			}
			//显示路径
			chooseJumpType(1);
		});
		
		// 加载会签人员列表
		function loadTaskSign(){
			$("#taskSign").load('${ctx}/platform/bpm/task/toSign.xht?taskId=${task.id}');
		}
		
		function initForm(){
			$("a.run").click(completeTask);
			if(isEmptyForm){
				return;
			}
			if(isExtForm){
				var formUrl=$('#divExternalForm').attr("formUrl");
				$('#divExternalForm').load(formUrl, function() {
					hasLoadComplete=true;
					initSubForm();
				});
			}else{
				initSubForm();
			}
		}
		
		function initSubForm(opitons){
			opitons=$.extend({},{success:showResponse },opitons);
			$('#'+frmId).ajaxForm(opitons); 
		}
		
		function showRoleDlg(){
			RoleDialog({callback:function(roleId,roleName){$('#forkUserUids').val(roleId);}}); 
		}
		
		function chooseJumpType(val){
			var obj=$('#jumpDiv');
			if(val==1){
				var url="${ctx}/platform/bpm/task/tranTaskUserMap.xht?taskId=${task.id}&selectPath=0".getNewUrl();
				obj.html(obj.attr("tipInfo")).show().load(url);
			}else if(val==2){//选择路径跳转
				var url="${ctx}/platform/bpm/task/tranTaskUserMap.xht?taskId=${task.id}".getNewUrl();
				obj.html(obj.attr("tipInfo")).show().load(url);
			}else if(val==3){//自由跳转
				var url="${ctx}/platform/bpm/task/freeJump.xht?taskId=${task.id}".getNewUrl();
				obj.html(obj.attr("tipInfo")).show().load(url);
			}
		}
		
		//为目标节点选择执行的人员列表
		function selectExeUsers(link){
			var destTaskId=$("#destTask").val();
			$("#lastDestTaskId").val(destTaskId)
			$('#jumpUserLink').prev('span').remove();
			UserDialog({callback:function(uIds,uNames){
				if(uIds==null) return;
				var ids=uIds.split(',');
				var names=uNames.split(',');
				for(var i=0;i<ids.length;i++){
					var span="<span><input type='checkbox' name='" + destTaskId + "_userId' checked='checked' value='"+ids[i]+"'/>&nbsp;"+names[i]+"</span>";
					$(link).before(span);
				}
			}});
		}
		
		function selExeUsers(nodeId,link){
			$(link).prev('span').remove();
			UserDialog({callback:function(uIds,uNames){
				if(uIds==null) return;
				var ids=uIds.split(',');
				var names=uNames.split(',');
				for(var i=0;i<ids.length;i++){
					var span="<span><input type='checkbox' name='" + nodeId + "_userId' checked='checked' value='"+ids[i]+"'/>&nbsp;"+names[i]+"</span>";
					$(link).before(span);
				}
			}});
		}
		//显示审批历史
		function showTaskOpinions(){
			var winArgs="dialogWidth=800px;dialogHeight=600px;help=1;status=1;scroll=1;center=1;resizable:1";				
			var url='${ctx}/platform/bpm/taskOpinion/list.xht?actInstId=${task.processInstanceId}';
			url=url.getNewUrl();
			window.showModalDialog(url,"",winArgs);
		}
		//更改
		function changeDestTask(sel){
			$('#lastDestTaskId').val(sel.value);
			if(sel.value!=""){
				var nodeId=sel.value;
				var url="${ctx}/platform/bpm/task/getTaskUsers.xht?taskId=${task.id}&nodeId="+nodeId;
				$.getJSON(url, function(dataJson){
					$('#jumpUserLink').siblings('span').remove();	
					var data=eval(dataJson);
					for(var i=0;i<data.length;i++){
						  var span="<span><input type='checkbox' name='" + nodeId + "_userId' checked='checked' value='"+data[i].userId+"'/>&nbsp;"+data[i].fullname+"</span>";
						  $('#jumpUserLink').before(span);
					}
				});
			}else{
				$('#jumpUserLink').siblings('span').remove();	
			}
		}
		//转交代办
		function changeAssignee(){
			UserDialog({
				isSingle:true,
				callback:function(userId,fullname){
					if(userId=='' || userId==null || userId==undefined) return;
					var url="${ctx}/platform/bpm/task/setAssignee.xht";
					var params= {taskIds:taskId,userId:userId };
					$.post(url,params,function(responseText){
						 $.ligerMessageBox.success('提示信息',('成功把任务[${task.name}]转交给'+fullname));
				    	 closeTabItem("task${task.id}");
					});
				}
			});
		}
		
	</script>
    							<a id="btnComplete" class="link run">完成任务</a>
								<div class="group"><a class="link goForward " onclick="changeAssignee()">转交代办</a></div>
								<c:if test="${isSignTask==true}">
									<a class="link flowDesign" onclick="showAddSignWindow()">补签</a>
								</c:if>
								<a class="link setting" onclick="showTaskUserDlg()">流程执行示意图</a>
								<a class="link search" onclick="showTaskOpinions()">审批历史</a>
								
								<a class="link print" onclick="window.print();">打印</a>
								
								通知方式：<input type="checkbox" value="1" name="informType">手机短信 &nbsp;<input type="checkbox" value="2" name="informType">邮件&nbsp;<input type="checkbox" value="11" name="informType">RTX
								<c:if test="${fn:indexOf(bpmNodeSet.jumpType,'1')!=-1}">
									<span style="padding-top:4px"><input type="radio" checked="checked" name="jumpType" onclick="chooseJumpType(1)" />&nbsp;正常跳转</span>
								</c:if>
								<c:if test="${fn:indexOf(bpmNodeSet.jumpType,'2')!=-1}">
									<span style="padding-top:4px"><input type="radio" name="jumpType" onclick="chooseJumpType(2)" />&nbsp;选择路径跳转</span>
								</c:if>
								<c:if test="${fn:indexOf(bpmNodeSet.jumpType,'3')!=-1}">
									<span style="padding-top:4px"><input type="radio" name="jumpType" onclick="chooseJumpType(3)" />&nbsp;自由跳转</span>
								</c:if>

