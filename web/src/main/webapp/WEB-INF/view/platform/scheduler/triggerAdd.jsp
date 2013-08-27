<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
	<head>
		<title>添加计划</title>
		<%@include file="/commons/include/form.jsp" %>
		<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
		<script type="text/javascript" src="${ctx }/js/sz/platform/scheduler/JobDialog.js"></script>
		<script type="text/javascript" src="${ctx }/js/sz/platform/scheduler/Trigger.js"></script>
 		<script  type="text/javascript">
 		$(function() {
 			
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				var str=getPlan();
				$("#planJson").val(str);
				$('#dataForm').submit(); 
			});
		});
 		function showResponse(responseText, statusText)  { 
			var obj=new org.sz.form.ResultMessage(responseText);
			if(obj.isSuccess()){//成功
				$.ligerMessageBox.confirm('提示信息',obj.getMessage()+',是否继续操作?',function(rtn){
					if(!rtn){
						var returnUrl=$("a.back").attr("href");
						location.href=returnUrl;
					}
					else{
						valid.resetForm();
					}
				});
				
		    }else{//失败
		    	$.ligerMessageBox.error('出错了',obj.getMessage());
		    }
		} 
		var valid;
		function valid(showRequest,showResponse){
		var options={};
		if(showRequest )
			options.beforeSubmit=showRequest;
		if(showResponse )
		   options.success=showResponse;
		valid=$("#dataForm").validate({
			rules: {
				name:{required:true,maxlength:128}
			},
			messages: {
				name:{required:"计划名称必填.",maxlength:"任务 最多 128 个字符."
				}
			},
			submitHandler:function(form){
				$(form).ajaxSubmit(options);
		    },
		    success: function(label) {
				label.html("&nbsp;").addClass("checked");
			}
			});
		}
		
 		</script>
	</head>
	<body>
		<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">添加定时计划:${jobName}</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="getTriggersByJob.xht?jobName=${jobName}">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<form id="dataForm" method="post" action="addTrigger2.xht">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
						    
							<th width="20%">计划名称: </th>
							<td>
							<input type="text" id="name" name="name" class="inputText"/>
							<input id="planJson" name="planJson" type="hidden" />	
						    <input id="jobName" name="jobName" type="hidden" value="${jobName}" />
							</td>
						</tr>
						<tr>
							<th colspan="2"  >
							<div class="tbar-title">
								<span class="tbar-label">执行计划的方式</span>
							</div>               
							</th>
						</tr>
						<tr>
							<th width="20%"><input type="radio" value="1"  name="rdoTimeType" />一次: </th>
							<td>
							开始:<input type="text" id="txtOnceDate" class="Wdate" size="10" onclick="WdatePicker({minDate:'%y-%M-{%d}'})" />
						<select id="txtOnceHour">
							<option value="0">0时</option>
							<option value="1">1时</option>
							<option value="2">2时</option>
							<option value="3">3时</option>
							<option value="4">4时</option>
							<option value="5">5时</option>
							<option value="6">6时</option>
							<option value="7">7时</option>
							<option value="8">8时</option>
							<option value="9">9时</option>
							<option value="10">10时</option>
							<option value="11">11时</option>
							<option value="12">12时</option>
							<option value="13">13时</option>
							<option value="14">14时</option>
							<option value="15">15时</option>
							<option value="16">16时</option>
							<option value="17">17时</option>
							<option value="18">18时</option>
							<option value="19">19时</option>
							<option value="20">20时</option>
							<option value="21">21时</option>
							<option value="22">22时</option>
							<option value="23">23时</option>
						</select>
						<select id="txtOnceMinute">
							<option value="00">0分</option>
							<option value="05">5分</option>
							<option value="10">10分</option>
							<option value="15">15分</option>
							<option value="20">20分</option>
							<option value="25">25分</option>
							<option value="30">30分</option>
							<option value="35">35分</option>
							<option value="40">40分</option>
							<option value="45">45分</option>
							<option value="50">50分</option>
							<option value="55">55分</option>
							<option value="59">59分</option>
						</select>
						<select id="txtOnceSecond">
							<option value="00">0秒</option>
							<option value="05">5秒</option>
							<option value="10">10秒</option>
							<option value="15">15秒</option>
							<option value="20">20秒</option>
							<option value="25">25秒</option>
							<option value="30">30秒</option>
							<option value="35">35秒</option>
							<option value="40">40秒</option>
							<option value="45">45秒</option>
							<option value="50">50秒</option>
							<option value="55">55秒</option>
							<option value="59">59秒</option>
						</select>
						</td>
						</tr>
						<tr>
							<th width="20%"><input type="radio" checked="checked" value="2" name="rdoTimeType" />每天  </th>
							<td>
							<select id="selEveryDay">
							<option value="1">1分钟</option>
	               			<option value="5">5分钟</option>
	               			<option value="10">10分钟</option>
	               			<option value="15">15分钟</option>
	               			<option value="30">30分钟</option>
	               			<option value="60">1小时</option>
	               		</select>
							</td>
						</tr>
						<tr>
							<th width="20%"><input type="radio"  value="3" name="rdoTimeType" />每天  </th>
							<td>
								
						<select id="txtDayHour">
							<option value="0">0时</option>
							<option value="1">1时</option>
							<option value="2">2时</option>
							<option value="3">3时</option>
							<option value="4">4时</option>
							<option value="5">5时</option>
							<option value="6">6时</option>
							<option value="7">7时</option>
							<option value="8">8时</option>
							<option value="9">9时</option>
							<option value="10">10时</option>
							<option value="11">11时</option>
							<option value="12">12时</option>
							<option value="13">13时</option>
							<option value="14">14时</option>
							<option value="15">15时</option>
							<option value="16">16时</option>
							<option value="17">17时</option>
							<option value="18">18时</option>
							<option value="19">19时</option>
							<option value="20">20时</option>
							<option value="21">21时</option>
							<option value="22">22时</option>
							<option value="23">23时</option>
						</select>
						
						<select id="txtDayMinute">
							<option value="00">0分</option>
							<option value="05">5分</option>
							<option value="10">10分</option>
							<option value="15">15分</option>
							<option value="20">20分</option>
							<option value="25">25分</option>
							<option value="30">30分</option>
							<option value="35">35分</option>
							<option value="40">40分</option>
							<option value="45">45分</option>
							<option value="50">50分</option>
							<option value="55">55分</option>
							<option value="59">59分</option>
						</select>
							</td>
						</tr>
						<tr>
							<th width="20%"><input type="radio" value="4" name="rdoTimeType" />每周  </th>
							<td>
							     <input type="checkbox" name="chkWeek" value="MON"/>星期一
	               		 <input type="checkbox" name="chkWeek" value="TUE"/>星期二
	               		 <input type="checkbox" name="chkWeek" value="WED"/>星期三
	               		 <input type="checkbox" name="chkWeek" value="THU"/>星期四
	               		 <input type="checkbox" name="chkWeek" value="FRI"/>星期五
	               		 <input type="checkbox" name="chkWeek" value="SAT"/>星期六
	               		 <input type="checkbox" name="chkWeek" value="SUN"/>星期日	<br/>
	               		 
	               		 <select id="txtWeekHour">
							<option value="0">0时</option>
							<option value="1">1时</option>
							<option value="2">2时</option>
							<option value="3">3时</option>
							<option value="4">4时</option>
							<option value="5">5时</option>
							<option value="6">6时</option>
							<option value="7">7时</option>
							<option value="8">8时</option>
							<option value="9">9时</option>
							<option value="10">10时</option>
							<option value="11">11时</option>
							<option value="12">12时</option>
							<option value="13">13时</option>
							<option value="14">14时</option>
							<option value="15">15时</option>
							<option value="16">16时</option>
							<option value="17">17时</option>
							<option value="18">18时</option>
							<option value="19">19时</option>
							<option value="20">20时</option>
							<option value="21">21时</option>
							<option value="22">22时</option>
							<option value="23">23时</option>
						</select>
						
						<select id="txtWeekMinute">
							<option value="00">0分</option>
							<option value="05">5分</option>
							<option value="10">10分</option>
							<option value="15">15分</option>
							<option value="20">20分</option>
							<option value="25">25分</option>
							<option value="30">30分</option>
							<option value="35">35分</option>
							<option value="40">40分</option>
							<option value="45">45分</option>
							<option value="50">50分</option>
							<option value="55">55分</option>
							<option value="59">59分</option>
						</select>
	               		 
							</td>
						</tr>
						<tr>
							<th width="20%"><input type="radio" value="5" name="rdoTimeType" />每月</th>
							<td>
							     <input type="checkbox" name="chkMon" value="1"/>1
	               		 <input type="checkbox" name="chkMon" value="2"/>2
	               		 <input type="checkbox" name="chkMon" value="3"/>3
	               		 <input type="checkbox" name="chkMon" value="4"/>4
	               		 <input type="checkbox" name="chkMon" value="5"/>5
	               		 <input type="checkbox" name="chkMon" value="6"/>6
	               		 <input type="checkbox" name="chkMon" value="7"/>7
	               		 <input type="checkbox" name="chkMon" value="8"/>8
	               		 <input type="checkbox" name="chkMon" value="9"/>9
	               		 <input type="checkbox" name="chkMon" value="10"/>10
	               		 <input type="checkbox" name="chkMon" value="11"/>11
	               		 <input type="checkbox" name="chkMon" value="12"/>12
	               		 <input type="checkbox" name="chkMon" value="13"/>13
	               		 <input type="checkbox" name="chkMon" value="14"/>14
	               		 <input type="checkbox" name="chkMon" value="15"/>15
	               		 <input type="checkbox" name="chkMon" value="16"/>16
	               		 <input type="checkbox" name="chkMon" value="17"/>17
	               		 <input type="checkbox" name="chkMon" value="18"/>18
	               		 <input type="checkbox" name="chkMon" value="19"/>19
	               		 <input type="checkbox" name="chkMon" value="20"/>20
	               		 <input type="checkbox" name="chkMon" value="21"/>21
	               		 <input type="checkbox" name="chkMon" value="22"/>22
	               		 <input type="checkbox" name="chkMon" value="23"/>23
	               		 <input type="checkbox" name="chkMon" value="24"/>24
	               		 <input type="checkbox" name="chkMon" value="25"/>25
	               		 <input type="checkbox" name="chkMon" value="26"/>26
	               		 <input type="checkbox" name="chkMon" value="27"/>27
	               		 <input type="checkbox" name="chkMon" value="28"/>28
	               		 <input type="checkbox" name="chkMon" value="29"/>29
	               		 <input type="checkbox" name="chkMon" value="30"/>30
	               		 <input type="checkbox" name="chkMon" value="31"/>31
	               		 <input type="checkbox" name="chkMon" value="L"/>最后一天<br/>
	               		 
	               		 <select id="txtMonHour">
							<option value="0">0时</option>
							<option value="1">1时</option>
							<option value="2">2时</option>
							<option value="3">3时</option>
							<option value="4">4时</option>
							<option value="5">5时</option>
							<option value="6">6时</option>
							<option value="7">7时</option>
							<option value="8">8时</option>
							<option value="9">9时</option>
							<option value="10">10时</option>
							<option value="11">11时</option>
							<option value="12">12时</option>
							<option value="13">13时</option>
							<option value="14">14时</option>
							<option value="15">15时</option>
							<option value="16">16时</option>
							<option value="17">17时</option>
							<option value="18">18时</option>
							<option value="19">19时</option>
							<option value="20">20时</option>
							<option value="21">21时</option>
							<option value="22">22时</option>
							<option value="23">23时</option>
						</select>
						<select id="txtMonMinute">
							<option value="00">0分</option>
							<option value="05">5分</option>
							<option value="10">10分</option>
							<option value="15">15分</option>
							<option value="20">20分</option>
							<option value="25">25分</option>
							<option value="30">30分</option>
							<option value="35">35分</option>
							<option value="40">40分</option>
							<option value="45">45分</option>
							<option value="50">50分</option>
							<option value="55">55分</option>
							<option value="59">59分</option>
						</select>
							</td>
						</tr>
						<tr>
						<th width="20%"><input type="radio" value="6" name="rdoTimeType" />Cron表达式:</th>
						<td><input type="text" id="txtCronExpression" name="txtCronExpression" /></td>
						</tr>
					</table>
				</form>
		</div>
</div>
	</body>
</html>