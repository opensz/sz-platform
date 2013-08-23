<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务催办设置</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<link href="${ctx}/jslib/lg/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<f:link href="web.css" ></f:link>
<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/additional-methods.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery.validate.ext.js"></script>
<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=taskReminder"></script>
<script type="text/javascript" src="${ctx }/js/sz/platform/system/TemplateDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/ScriptDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowVarWindow.js" ></script>
<script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor_remind.js"></script>
<script type="text/javascript">        
		function showRequest(formData, jqForm, options) { 
			return true;
		} 
		$(function() {
			$("#tabContent").ligerTab({height:300});
			change();
			$("a.save").click(save);
			editorMail=ckeditor('mailContent');
			editorMsg=ckeditor('msgContent');	
		});
		function change(){
			var s= $("#action").val();
			if(s==3){
				$(".sub").show();
			}else{
				$(".sub").hide();
			}
		}
		function slectTemplate(txtId,isText){
			var objConditionCode=document.getElementById(txtId);
		    TemplateDialog({isText:isText,callback:function(content){
		    	if(isText)
					jQuery.insertText(objConditionCode,content);
		    	else{		    		
		    		CKEDITOR.instances[txtId].setData(content);
		    	}				
			}});
		};		
		function save(){			
			var ctime=getTotalMinute($("#completeTr"));
			var stime=getTotalMinute($("#startTr"));
			//每次时间间隔*催办次数。
			var etime=getTotalMinute($("#endTr")) * ( parseInt($("#times").val())-1);
			if(ctime<stime+etime){				
				$.ligerMessageBox.warn('提示信息','办结时间不能比催办时间短');
				return;
			}
			$(".ckeditor").each(function(){
				$(this).val(CKEDITOR.instances[$(this).attr('name')].getData());
			});
			 var rtn=$("#taskReminderForm").valid();
	   		 if(!rtn) return;
			 var url=__ctx+ "/platform/bpm/taskReminder/save.xht";
	   		 var para=$('#taskReminderForm').serialize();
	   		 $.post(url,para,showResult);
		}
		function showResult(responseText){
			var obj=new org.sz.form.ResultMessage(responseText);
			if(!obj.isSuccess()){
				$.ligerMessageBox.error('出错了',obj.getMessage());
				return;
			}else{
				$.ligerMessageBox.confirm('提示信息',obj.getMessage()+',是否继续操作?',function(rtn){
					if(!rtn){
						window.close();
					}
				});
			}
		}
		function slectVars(txtId){
			var objConditionCode=document.getElementById(txtId);
			FlowVarWindow({callback:function(varKey,varName){
				jQuery.insertText(objConditionCode,varKey);
			}});
			
		}		
		function slectScript(txtId){
			var objConditionCode=document.getElementById(txtId);
			ScriptDialog({callback:function(script){
				jQuery.insertText(objConditionCode,script);
			}});
		}
		function getTotalMinute(e){
			var t=0;
			$(e).find(".dayInput").each(function(){
				t+= parseInt(3600* this.value);
			});
			$(e).find(".hourInput").each(function(){
				t+=parseInt(60* this.value);
			});			
			$(e).find(".minuteInput").each(function(){
				t+=parseInt(this.value);
			});
			return t;
		}
	</script>
	<style>
   .sub{ display: none;}
   </style>
   </head>
<body>
<div class="panel">
		<div class="panel-top">
				<div class="tbar-title">
					<span class="tbar-label">编辑任务节点催办时间设置</span>
				</div>
				<div class="panel-toolbar">
					<div class="toolBar">
						<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link del" onclick="javascript:window.close();">关闭</a></div>
					</div>
				</div>
		</div>
		<div class="panel-body">
				<form id="taskReminderForm" method="post" action="save.xht">
				<div id="tabContent" >
				<div tabid="baseMessage" title="催办基本信息设置">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr id="completeTr">
							<th width="30%">办结时间: </th>
							<td>
							<select id="completeTimeDay" class="dayInput" name="completeTimeDay">
								<c:forEach var="i" begin="0" end="30" step="1">
									<option value="${i}" <c:if test="${completeTimeDay==i}">selected="selected"</c:if>>${i}天</option>
								</c:forEach>
							</select>
							<select id="completeTimeHour" class="hourInput" name="completeTimeHour">
								<c:forEach var="i" begin="0" end="23" step="1">
									<option value="${i}" <c:if test="${completeTimeHour==i}">selected="selected"</c:if>>${i}小时</option>
								</c:forEach>
							</select>
							<select id="completeTimeMinute" class="minuteInput" name="completeTimeMinute">
								<c:forEach var="i" begin="5" end="59" step="5">
									<option value="${i}" <c:if test="${completeTimeMinute==i}">selected="selected"</c:if>>${i}分钟</option>
								</c:forEach>
							</select>					
					     </td>					    
						</tr>
						<tr>
							<th width="30%">任务到期处理动作: </th>
							<td>
							<select id="action" onchange="change()" name="action">
							<option value="0" <c:if test="${taskReminder.action==0}">selected="selected"</c:if>>无动作</option>
							<option value="1" <c:if test="${taskReminder.action==1}">selected="selected"</c:if>>流程自动往下跳转</option>
							<option value="2" <c:if test="${taskReminder.action==2}">selected="selected"</c:if>>结束该流程</option>
							<option value="3" <c:if test="${taskReminder.action==3}">selected="selected"</c:if>>调用指定方法</option>
							</select>
							</td>
						</tr>
						<tr id="startTr">
							<th width="30%">催办开始时间:</th>
							<td>
							<select id="reminderStartDay" class="dayInput" name="reminderStartDay">
							<c:forEach var="i" begin="0" end="30" step="1">
								<option value="${i}" <c:if test="${reminderStartDay==i}">selected="selected"</c:if>>${i}天</option>
							</c:forEach>
							</select>
							<select id="reminderStartHour" class="hourInput" name="reminderStartHour">
							<c:forEach var="i" begin="0" end="23" step="1">
								<option value="${i}" <c:if test="${reminderStartHour==i}">selected="selected"</c:if>>${i}小时</option>
							</c:forEach>
							</select>
							<select id="reminderStartMinute" class="minuteInput" name="reminderStartMinute">
								<c:forEach var="i" begin="5" end="59" step="5">
									<option value="${i}" <c:if test="${reminderStartMinute==i}">selected="selected"</c:if>>${i}分钟</option>
								</c:forEach>
							</select>							
							</td>
						</tr>
						<tr id="endTr">
							<th width="30%">催办间隔时长: </th>
							<td>
								<select id="reminderEndDay" class="dayInput" name="reminderEndDay">
									<c:forEach var="i" begin="0" end="30" step="1">
										<option value="${i}" <c:if test="${reminderEndDay==i}">selected="selected"</c:if>>${i}天</option>
									</c:forEach>
								</select>
								<select id="reminderEndHour" class="hourInput" name="reminderEndHour">
									<c:forEach var="i" begin="0" end="23" step="1">
										<option value="${i}" <c:if test="${reminderEndHour==i}">selected="selected"</c:if>>${i}小时</option>
									</c:forEach>
								</select>
								<select id="reminderEndMinute" class="minuteInput" name="reminderEndMinute">
									<c:forEach var="i" begin="5" end="59" step="5">
										<option value="${i}" <c:if test="${reminderEndMinute==i}">selected="selected"</c:if>>${i}分钟</option>
									</c:forEach>
								</select>
								
								<a href="#" class="tipinfo"><span>每次催办的时间间隔。</span></a>
							</td>
						</tr>
						<tr>
							<th width="30%">催办次数: </th>
							<td>
								<select name="times" >
									<c:forEach var="i" begin="1" end="10" step="1">
										<option value="${i}" <c:if test="${taskReminder.times==i}">selected="selected"</c:if>>${i}</option>
									</c:forEach>
								</select>
								
							</td>
						</tr>
						
						<tr class="sub">
							<th width="30%" >执行脚本: </th>
							<td>
							<div>
								<a href="#"  class="link var" title="选择流程变量" onclick="slectVars('script')">流程变量</a>
								<a href="#"  class="link var" title="常用脚本" onclick="slectScript('script')">常用脚本</a>
							</div>
							<textarea rows="6" cols="60" id="script" name="script">${taskReminder.script}</textarea> </td>
						</tr>
						
						</table>
						</div>
					    <div tabid="mailContent" title="邮件内容">
							    <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
							    	<tr>
							     		<th width="30%" >邮件内容: </th>
										<td>
											<div>
												<a href="#"  class="link var" title="选择模板内容" onclick="slectTemplate('mailContent',false)">选择模板内容</a>
											</div>
											<textarea id="mailContent" class="ckeditor" name="mailContent" rows="12" cols="50">${taskReminder.mailContent}</textarea>
										</td>
									</tr>
								</table>
					     </div>
					     <div tabid="msgContent" title="站内消息内容">
					    <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					    <tr>
					     <th width="30%" >站内消息内容: </th>
							<td>
							<div>
								<a href="#"  class="link var" title="选择模板内容" onclick="slectTemplate('msgContent',false)">选择模板内容</a>
							</div>
							<textarea id="msgContent" class="ckeditor" name="msgContent" rows="12" cols="50">${taskReminder.msgContent}</textarea>
							</td>
							</tr>
						</table>
					     </div>
					     <div tabid="smsContent" title="手机短信内容">
					    <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					    <tr>
					     <th width="30%" >手机短信内容: </th>
							<td>
							<div>
								<a href="#"  class="link var" title="选择模板内容" onclick="slectTemplate('smsContent',true)">选择模板内容</a>
							</div>
							<textarea id="smsContent" name="smsContent" rows="12" cols="50">${taskReminder.smsContent}</textarea>
							</td>
							</tr>
						</table>
					     </div>
					</div>
					
					<input type="hidden" name="taskDueId" value="${taskReminder.taskDueId}" />
					<input type="hidden" name="actDefId" value="${actDefId}" />
					<input type="hidden" name="nodeId" value="${nodeId}" />
				</form>
		</div>
</div>
</body>
</html>
