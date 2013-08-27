<%--
	desc:edit the 流程节点消息
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 流程节点消息</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerTab.js" ></script>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
    <script type="text/javascript"  src="${ctx }/js/sz/platform/system/SysDialog.js"></script>
	<script type="text/javascript">
		 var obj;
		 function showRequest(formData, jqForm, options) {
				return true;
			}
			$(function() {
				 $("a.save").click(save);
				 
			});
	     function save(){
    		 var valRes=validata();
    		 if(!valRes) return;
    		 var rtn=$("#bpmNodeMessageForm").valid();
    		 if(!rtn) return;
    		 var url=__ctx+ "/platform/bpm/bpmNodeMessage/save.xht";
    		 var para=$('#bpmNodeMessageForm').serialize();
    		 $.post(url,para,showResult);
	     }
	     function validata(){
	    	 if($("#receiver_mail").val()==""&&$("#receiver_mobile").val()==""&&$("#receiver_inner").val()==""){
	    		 $.ligerMessageBox.warn('提示信息','至少需要填写一种消息发送方式的参数');
	    		 return false;
	    	 }
	    	 else{
	    		 if($("#receiver_mail").val()!=""){
	    			 if($("#subject_mail").val()==""){
	    				 $.ligerMessageBox.warn('提示信息','请输入邮件的主题');
	    				 return false;}
	    			 else return true;
	    		 }
	    		 if($("#receiver_inner").val()!=""){
	    			 if($("#subject_inner").val()==""){
	    				 $.ligerMessageBox.warn('提示信息','请输入站内信息的主题');
	    				 return false;}
	    			 else return true;
	    		 }
	    		 else return true;
	    	 }	    		 
	     }
	     function showResult(responseText)
			{			
				var obj=new org.sz.form.ResultMessage(responseText);
				
				if(!obj.isSuccess()){
					$.ligerMessageBox.error('出错了',obj.getMessage());
					return;
				}else{
					$.ligerMessageBox.success('提示信息',obj.getMessage(),function(rtn){
						if(rtn) window.close();						
					});
				}
			}
	    function dlgCallBack(userIds,fullnames,emails,mobiles)
		{
			var userIdArr;
			var userNameArr;
			var emailArr;
			var mobileArr;
			var userIdStr="";
			var userNameStr="";
			var emailStr="";
			var mobileStr="";
			var resultStr="";
			if(userIds!="")
			{
				userIdArr=userIds.split(",");
				userNameArr=fullnames.split(",");
				emailArr=emails.split(",");
				mobileArr=mobiles.split(",");
				for(var i=0;i<userIdArr.length;i++)
				{ 					
					userIdStr=userIdArr[i];					
					userNameStr=userNameArr[i];
					emailStr=emailArr[i];
					mobileStr=mobileArr[i];
					if(resultStr=="")
					{
						if(obj.indexOf("mail")!=-1)
						{
							resultStr=userNameStr+"("+emailStr+")";
							//resultStr=emailStr;
						}
						else if(obj.indexOf("mobile")!=-1)
						{
							resultStr=userNameStr+"("+mobileStr+")";
						}
						else if(obj.indexOf("inner")!=-1)
						{
							resultStr=userNameStr+"("+userIdStr+")";
						}
						
					}
					else
					{
						if(obj.indexOf("mail")!=-1)
						{
							resultStr=resultStr+","+userNameStr+"("+emailStr+")";
							//resultStr=resultStr+emailStr;
						}
						else if(obj.indexOf("mobile")!=-1)
						{
							resultStr=resultStr+","+userNameStr+"("+mobileStr+")";
						}
						else if(obj.indexOf("inner")!=-1)
						{
							resultStr=resultStr+","+userNameStr+"("+userIdStr+")";
						}						
					}
					
				} 
				
			}
			
			$("#"+obj).val(resultStr);			
		
		};
		function addClick(oName)
		{
			obj=oName;
			 UserDialog({sSingle:false,callback:dlgCallBack});
		};
		//清空
		function reSet(obj)
		{
			$("#"+obj).val("");			
		}
	</script>
</head>
<body>
<div class="panel">
<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">消息参数设置</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link save" id="btnSearch">保存</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del" onclick="javasrcipt:window.close()">关闭</a></div>
						
						</div>	
					</div>
				</div>
		<div class="panel-body">
		<form id="bpmNodeMessageForm" method="post" action="save.xht">
			<div class="tbar-title">
			 邮件信息
	        </div>				         	         
		             <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">邮件主题: </th>
							<td><input type="text" id="subject_mail" name="subject_mail" value="${subject_mail}"  class="inputText" style="width:325px !important"/></td>
						</tr>
						<tr>
							<th width="20%">邮件接收人: </th>
							<td valign="top">
							<textarea id="receiver_mail" name="receiver_mail"  rows="2" readonly="readonly" style="width:328px !important">${receiver_mail}</textarea>
							<a href="#" onclick="addClick('receiver_mail')" class="link get">选择</a>
					        <a href="#" onclick="reSet('receiver_mail')" class="link clean">清空</a>
							</td>
						</tr>
						<tr>
							<th width="20%">普通抄送: </th>
							<td>
							<textarea id="copyTo_mail" name="copyTo_mail"  rows="3" readonly="readonly" style="width:328px !important">${copyTo_mail}</textarea>
											<a href="#" onclick="addClick('copyTo_mail')" class="link get">选择</a>
					        <a href="#" onclick="reSet('copyTo_mail')" class="link clean">清空</a>														
							</td>
						</tr>
						<tr>
							<th width="20%">秘密抄送: </th>
							<td>
							<textarea id="bcc_mail" name="bcc_mail"  rows="2" readonly="readonly" style="width:328px !important">${bcc_mail}</textarea>
											<a href="#" onclick="addClick('bcc_mail')" class="link get">选择</a>
					        <a href="#" onclick="reSet('bcc_mail')" class="link clean">清空</a>														
							</td>
						</tr>					
						<tr>
							<th width="20%">邮件模版: </th>
							<td>	
							 <select id="templateId_mail" name="templateId_mail" style="width:331px !important" class="select">  
					         <c:forEach var="temp" items="${tempList}">  
					         <option value="${temp.templateId}" <c:if test="${temp.templateId==templateId_mail}">selected</c:if>>${temp.name}</option>  
					         </c:forEach>  
					         </select>	
						    </td>
						</tr>
					</table>
			<div class="tbar-title">
			手机短信
	        </div>	
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">						
						<tr>
							<th width="20%">短信接收人: </th>
							<td valign="top">
							<textarea id="receiver_mobile" name="receiver_mobile" rows="3" readonly="readonly" style="width:328px !important">${receiver_mobile}</textarea>
							<a href="#" onclick="addClick('receiver_mobile')" class="link get">选择</a>
					        <a href="#" onclick="reSet('receiver_mobile')" class="link clean">清空</a>
							</td>
						</tr>								
						<tr>
							<th width="20%">短信模版: </th>
							<td>	
							 <select id="templateId_mobile" name="templateId_mobile" style="width:331px !important" class="select">  
					         <c:forEach var="temp" items="${tempList}">  
					         <option value="${temp.templateId}" <c:if test="${temp.templateId==templateId_mobile}">selected</c:if>>${temp.name}</option>  
					         </c:forEach>  
					         </select>	
						    </td>
						</tr>
					</table>
			<div class="tbar-title">
			内部消息
	        </div>
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">消息主题: </th>
							<td><input type="text" id="subject_inner" name="subject_inner" value="${subject_inner}"  class="inputText" style="width:324px !important"/></td>
						</tr>
						<tr>
							<th width="20%">消息接收人: </th>
							<td valign="top">
							<textarea id="receiver_inner" name="receiver_inner"  rows="3" readonly="readonly" style="width:328px !important">${receiver_inner}</textarea>
							<a href="#" onclick="addClick('receiver_inner')" class="link get">选择</a>
					        <a href="#" onclick="reSet('receiver_inner')" class="link clean">清空</a>
							</td>
						</tr>									
						<tr>
							<th width="20%">消息模版: </th>
							<td>	
							 <select id="templateId_inner" name="templateId_inner" style="width:331px !important" class="select">  
					         <c:forEach var="temp" items="${tempList}">  
					         <option value="${temp.templateId}" <c:if test="${temp.templateId==templateId_inner}">selected</c:if>>${temp.name}</option>  
					         </c:forEach>  
					         </select>	
						    </td>
						</tr>
					</table>	
					<input type="hidden" id="id" name="id" value="${id}"  class="inputText"/>				
					<input type="hidden" id="actDefId" name="actDefId" value="${actDefId}"  class="inputText"/>
					<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}"  class="inputText"/>
	            																		
		 </form>
	    </div>
</div>
</body>
</html>
