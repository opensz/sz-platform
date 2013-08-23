<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system"%>
<html>
	<head>
		<title>流程任务-[${task.name}]执行</title>
		<%@include file="/commons/include/customForm.jsp"%>
		<script type="text/javascript"
			src="${ctx}/js/sz/platform/bpm/TaskSignWindow.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/sz/platform/bpm/TaskAddSignWindow.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/sz/platform/bpm/TaskBackWindow.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/sz/platform/bpm/TaskImageUserDialog.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/sz/platform/tabOperator.js"></script>
		<script type="text/javascript">
		var taskId='${task.id}';
		var isExtForm=eval('${isExtForm}');
		var isEmptyForm=${isEmptyForm};
		var isSignTask=${isSignTask};
		var jumpTypeFrist = ${fn:split(bpmNodeSet.jumpType,',')[0]};
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
			//TaskImageUserDialog({taskId:taskId});
			var url = __ctx + '/platform/bpm/processRun/userImage.xht?taskId=' + taskId;
			top.$.ligerDialog.open({ url: url, height: 600,width:800 ,isResize: false,showMax: true });
		}
		
		//暂存任务
		function temporaryTask(){
			if(CustomForm.validate()){
				var formData=CustomForm.getData();
				$.post("${ctx}/platform/bpm/task/temporary.xht",{formData:formData,taskId:'${task.id}'},function(data){
					var obj=new org.sz.form.ResultMessage(data);
					if(obj.isSuccess()){
						$.ligerMessageBox.success("提示信息", obj.getMessage(),function(){
							top.closeCurActPanel();					
						});
					}else{
						$.ligerMessageBox.error("提示信息", obj.getMessage());
					}
				});
			}else{
				CustomForm.showError();
			}
		}
		
		//完成当前任务。
		function completeTask(){	
			if(isEmptyForm){
				$.ligerMessageBox.error('提示信息',"还没有设置表单!");
				return;
			}

			if(isExtForm){
				$('#frmWorkFlow').submit();
			}else{
				var rtn=CustomForm.validate();
				if(rtn){
					try{
						top.showMask("正在处理任务，请稍候...");
					}catch(exc){}
			
					//Office控件提交。
					OfficePlugin.submit();
					var data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					$('#frmWorkFlow').submit();
				}else{
					CustomForm.showError();
				}
			}
		}
	
			
		function showResponse(responseText){
			try
			{
				top.hideMask();
			}catch(exc){}
		
			var obj=new org.sz.form.ResultMessage(responseText);
			if(obj.isSuccess()){
				$.ligerMessageBox.success("提示信息", "执行任务成功！",function(){
					top.closeCurActPanel();					
				});
			}else{
				$.ligerMessageBox.error("提示信息", "执行任务失败！");
			}
		}
		
		//弹出回退窗口 TODO 去除
		function showBackWindow(){
			new TaskBackWindow({taskId:taskId}).show();
		}
		
		$(function(){
			//$("input[class='inputText']").removeClass('inputText');
			//$("textarea").removeClass('l-textarea valid');
			initForm();
			
			if(isSignTask){
				loadTaskSign();
			}
			//显示路径
			chooseJumpType(jumpTypeFrist);
			
			
			
			$("#formDiv").css("height", $(window).height() - 76);
		});
		
		window.onresize = function(){
			$("#formDiv").css("height", $(window).height() - 76);
		}
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
			$('#frmWorkFlow').ajaxForm(opitons); 
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
			}else if(val==3){//回退
				var url="${ctx}/itsm/bpm/task/Back.xht?taskId=${task.id}".getNewUrl();
				obj.html(obj.attr("tipInfo")).show().load(url);
			}else if(val==4){//自由跳转
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
		
		//抄送
		function ccExeUsers(){
			UserDialog({callback:function(uIds,uNames){
				if(uIds==null) return;
				var ids=uIds.split(',');
				var names=uNames.split(',');
				var tmp = "";
				for(var i=0; i < ids.length; i++){
					tmp += "<span style='padding-right:5px;'>\"" + names[i] + "\";<input type='hidden' name='ccUserIds' value='"+ids[i]+"' /></span>";
				}
				$("#ccTask").html(tmp);
			}});
		}
		
		//显示审批历史
		function showTaskOpinions(){
			var winArgs="dialogWidth=800px;dialogHeight=600px;help=1;status=1;scroll=1;center=1;resizable:1";				
			var url='${ctx}/platform/bpm/taskOpinion/list.xht?actInstId=${task.processInstanceId}';
			//url=url.getNewUrl();
			//window.showModalDialog(url,"",winArgs);
			
			top.$.ligerDialog.open({ url: url, height: 600,width:800 ,isResize: false,showMax: true });
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
		
		// 选择常用语
		function addComment(){
			var objContent=document.getElementById("voteContent");
			var selItem = $('#selTaskAppItem').val();
			jQuery.insertText(objContent,selItem);
		}
		
		$(function() {
				
				
				//字段交互
				$("input[name='m:request_case:form_no']").on("change",function(){
					var value=$("input[name='m:request_case:form_no']").val();
					var obj=$("input[name='m:request_case:form_no']");
					$.post(__ctx+"/platform/bpm/task/vadlidField.xht?tableName=request_case&field=form_no&value="+value,function(data){
						var json = eval('(' + data + ')'); 
						if(json.success=="false" || !json.success){
							obj.addClass('validError');
							obj.mouseover(function() {
								obj.ligerTip({
									content : "表单编号不能重复!",
									appendIdTo : obj
								});
							});
							obj.mouseout(function() {
								obj.ligerHideTip();
							});
							
						}
						else{
							obj.removeClass('validError');
							obj.ligerHideTip();
							obj.unbind('mouseover');
						}
					});
				});
			});
</script>
		<style type="text/css" media="print">
.noprint {
	display: none;
}

.printForm {
	display: "block";
}
</style>
<style type="text/css">

body {
	margin: 0px;
	padding: 0px;
	font-size: 12px;
	overflow:hidden;
}
</style>
	</head>
	<body>
		<form id="frmWorkFlow" action="${ctx}/platform/bpm/task/complete.xht" method="post">
			<div id="taskLayout">
				<div class="l-layout-header noprint">
					任务审批处理--
					<b>${task.name}</b>--
					<i>[${bpmDefinition.subject}-V${bpmDefinition.versionNo}]</i>
				</div>
				<div class="panel">
					<div class="panel-top noprint">
						<div class="panel-toolbar">
							<div class="toolBar">
								<div style="float:left;">
									
									<a id="btnComplete" class="link run">完成任务</a>
									<div class="group">
										<a id="btnComplete" onclick="temporaryTask();" class="link temporary">暂存</a>
									</div>
									<div class="group">
										<a class="link goForward " onclick="changeAssignee()">转交代办</a>
									</div>
									<c:if test="${isSignTask==true}">
										<a class="link flowDesign" onclick="showAddSignWindow()">补签</a>
									</c:if>
						
									<span style="display:none;">
									<a class="link print" onclick="window.print();">打印</a> 通知方式：
									<input type="checkbox" value="1" name="informType">
									手机短信 &nbsp;
									<input type="checkbox" value="2" name="informType">
									邮件
									</span>
								</div>
								
								<div style="float:right;">
									<a class="link setting" onclick="showTaskUserDlg()">流程执行示意图</a>
									<a class="link search" onclick="showTaskOpinions()">审批历史</a>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<!-- 加载加载会签人员列表 -->
						<div class="panel-body printForm" id="formDiv" style="overflow: auto;">
						
							<c:choose>
								<c:when test="${isEmptyForm==true}">
									<div class="noForm">
										没有设置流程表单。
									</div>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${isExtForm}">
											<div id="divExternalForm" formUrl="${form}">
											</div>
										</c:when>
										<c:otherwise>
											<div type="custform">
												${form}
											</div>
											<input type="hidden" name="formData" id="formData" />
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						
						<input type="hidden" id="taskId" name="taskId" value="${task.id}" />
						<input type="hidden" id="destCaseId" name="destCaseId" value="${processRun.businessKey}" />
						<input type="hidden" id="destCaseNo" name="destCaseNo" value="${destCaseNo}" />
						<input type="hidden" name="agentTask" value="${param['agentTask']}" />

						<!-- 暂时隐藏会签功能 -->
						<div id="taskSign" style="display:none;" class="noprint"></div>

						<c:if test="${isSignTask!=true}">
							<div class="noprint">
								<table class="table-detail">
									<c:if test="${bpmNodeSet.isAudit==1}">
									<tr>
										<th>
											审批意见
										</th>
										<td>
											<input type="radio" name="voteAgree" value="1"
												checked="checked">
											&nbsp;同意&nbsp;
											<input type="radio" name="voteAgree" value="2">
											&nbsp;反对&nbsp;
											<c:if test="${isAllowBack==true}">
												<input type="radio" name="voteAgree" value="3">&nbsp;驳回&nbsp;
												</c:if>
										</td>
									</tr>
									</c:if>
									<c:if test="${!empty taskAppItems}">
										<tr>
											<th>
												常用语选择:
											</th>
											<td>
												<select style="width: 25%; text-align: center;"
													id="selTaskAppItem" onchange="addComment()">
													<option value="" style="text-align: center;">
														-- 请选择 --
													</option>
													<c:forEach var="taskAppItem" items="${taskAppItems}">
														<option value="${taskAppItem}">
															${taskAppItem}
														</option>
													</c:forEach>
												</select>
											</td>
										</tr>
									</c:if>
									<tr>
										<th>
											意见
										</th>
										<td>
											<textarea rows="2" cols="78" id="voteContent"
												name="voteContent" maxlength="512"></textarea>
										</td>
									</tr>
									
									<tr>
										<th>
											跳转方式
										</th>
										<td>
											<c:forEach items="${fn:split(bpmNodeSet.jumpType,',')}" var="item" varStatus="i">
												<c:if test="${item==1}">
													<span style="padding-top: 4px"><input type="radio" name="jumpType" <c:if test="${i.index==0}">checked="checked"</c:if> onclick="chooseJumpType(1)" />&nbsp;正常跳转</span>
												</c:if>
												<c:if test="${item==2}">
													<span style="padding-top: 4px"><input type="radio" name="jumpType" <c:if test="${i.index==0}">checked="checked"</c:if> onclick="chooseJumpType(2)" />&nbsp;选择路径跳转</span>
												</c:if>
												<c:if test="${item==3}">
													<span style="padding-top: 4px"><input type="radio" name="jumpType" <c:if test="${i.index==0}">checked="checked"</c:if> onclick="chooseJumpType(3)" />&nbsp;回退</span>
												</c:if>
												<c:if test="${item==4}">
													<span style="padding-top: 4px"><input type="radio" name="jumpType" <c:if test="${i.index==0}">checked="checked"</c:if> onclick="chooseJumpType(4)" />&nbsp;自由跳转</span>
												</c:if>
											</c:forEach>

											<div id="jumpDiv" class="noprint"
												style="display: none; padding: 2px 2px 2px 2px; text-align:left;"
												tipInfo="正在加载表单请稍候...">
											</div>
										</td>
									</tr>
									<tr>
										<th>
											<a class="link" href="javascript:void(0);" onclick="ccExeUsers();">抄送</a>
										</th>
										<td>
											<div id="ccTask"></div>
										</td>
									</tr>
								</table>
							</div>
						</c:if>
						<div class="panel-toolbar">
							<div class="toolBar">
								<a id="btnComplete" class="link run">完成任务</a>
								<div class="group">
									<a class="link goForward " onclick="changeAssignee()">转交代办</a>
								</div>
								<c:if test="${isSignTask==true}">
									<a class="link flowDesign" onclick="showAddSignWindow()">补签</a>
								</c:if>
							</div>
						</div>
					
						</div>
					</div>
			      
				</div>
				<!--  end of center div -->

			</div>
		</form>
	</body>
</html>