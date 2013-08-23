<%@ page pageEncoding="UTF-8"%>

<html>
	<head>
		<%@include file="/commons/include/customForm.jsp" %>
		<title>流程启动--${bpmDefinition.subject} --版本:${bpmDefinition.versionNo}</title>
		<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/BpmImageDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/sz/platform/tabOperator.js" ></script>
		<script type="text/javascript">
			var isExtForm=${isExtForm};
			var isFormEmpty=${isFormEmpty};
			var hasLoadComplete=false;
			var actDefId="${bpmDefinition.actDefId}";
			
			$(function(){
				//表单不为空的情况。
				if(isFormEmpty==false){
					if(isExtForm){
						var formUrl=$('#divExternalForm').attr("formUrl");
						$('#divExternalForm').load(formUrl, function() {
							hasLoadComplete=true;
							initSubForm();
						});
					}
					else{
						initSubForm();
					}	
				}
				$("a.run").click(startWorkFlow);
			});
			
			function startWorkFlow(){
				if(isFormEmpty){
					alert("流程表单为空，请先设置流程表单!");
					return;
				}
				var bl=true;
				bl=validForm();
				
				if(isExtForm){
					$('#frmWorkFlow').submit();
				}
				else{
					var rtn=CustomForm.validate();
					if(!rtn){
						CustomForm.showError();
						return;
					}
					if(bl){
					//Office控件提交。
					OfficePlugin.submit();
					//获取自定义表单的数据
					var data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					$('#frmWorkFlow').submit();
					
					}
					
				}
			}
		
			function showBpmImageDlg(){
				BpmImageDialog({actDefId:"${bpmDefinition.actDefId}"});
			}
			
			function initSubForm(opitons){
				opitons=$.extend({},{success:showResponse },opitons);
				$('#frmWorkFlow').ajaxForm(opitons); 
			}
			
			function showResponse(responseText){
				var obj=new org.sz.form.ResultMessage(responseText);
				if(obj.isSuccess()){
					$.ligerMessageBox.success("提示信息", "启动流程成功！",function(){
						top.closeCurActPanel();					
					});
				}
				else{
					$.ligerMessageBox.error("提示信息", "出错了:" +obj.getMessage());
				}
			}

			function validForm(){
				return true;
			}
			
			$(function() {
				//字段交互
				$("input[name='m:request_case:form_no']").on("change",function(){
					var value=$("input[name='m:request_case:form_no']").val();
					var obj=$("input[name='m:request_case:form_no']");
					$.post(__ctx+"/platform/bpm/task/vadlidField.xht?tableName=request_case&field=form_no&value="+value,function(data){
						//var json = $.JsonToObject(data);
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
			.noprint{display:none;} 
			.printForm{display:block !important;} 
			.noForm{font-size: 14px;font-weight: bold;text-align: center;}
		</style>
	</head>
	<body>
		<div class="panel">
				<div class="panel-top">
					<div class="tbar-title noprint">
						<span class="tbar-label">流程启动--${bpmDefinition.subject} --V${bpmDefinition.versionNo}</span>
					</div>
					<div class="panel-toolbar noprint" >
						<div class="toolBar">
							<div class="group"><a class="link run">启动流程</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link flowDesign" onclick="showBpmImageDlg()">流程示意图</a></div>
							<div class="l-bar-separator"></div>
							<a class="link print" onclick="window.print();">打印</a>
							通知方式：<input type="checkbox" value="1" name="informType">手机短信 &nbsp;<input type="checkbox" value="2" name="informType">邮件
						</div>
					</div>
				</div>
				<div style="padding:6px 8px 3px 12px;" class="noprint">
					<b>流程简述：</b>${bpmDefinition.descp}
				</div>
				<div class="panel-body printForm" style="overflow: auto;">
					<form id="frmWorkFlow" action="${ctx}/platform/bpm/task/startFlow.xht" method="post" >
						<c:choose>
							<c:when test="${isEmptyForm==true}">
								<div class="noForm">没有设置流程表单。</div>
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
						
						<input type="hidden" name="actDefId" value="${bpmDefinition.actDefId}"/>
					</form>
				</div>
		</div>
	</body>
</html>