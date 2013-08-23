<%--
	time:2011-11-09 11:20:13
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title></title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=project"></script>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/htButtons.js" ></script>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx }/js/sz/platform/system/SysDialog.js"></script>
	
	<script type="text/javascript">
	   
		$(function() {
			
			function showRequest(formData, jqForm, options){ 
				return true;
			} 
	
			valid(showRequest,showResp);
			
			
			
			$("a.save").click(function() {
				$('#projectForm').submit();
			});
			
			function showResp(responseText, statusText){
				
				var obj=new org.sz.form.ResultMessage(responseText);
				
				if(obj.isSuccess()){//成功
				
					var objMsg=eval("(" + obj.getMessage() +")" );
					var orgId=objMsg.orgId;
					
					var action=objMsg.action;
					var msg=(action=="add")?"添加项目成功!":"编辑项目成功!";
					$.ligerMessageBox.success('提示信息',msg,function(){
						document.location.href="list.xht";
						//parent.$("#viewFrame").attr("src","get.xht?orgId=" +orgId);
					});
					
			    }else{//失败
			    	$.ligerMessageBox.error('提示信息',obj.getMessage());
			    }
			}
			
		});
		
		
	
		function addClick()//在sysOrgEdit.jsp调用，为了弹出页面的拖动范围大些，所以写在父页面了
		{
			UserDialog({callback:function(userIds,fullnames){
				$("#ownUser").val(userIds);
				$("#ownUserName").val(fullnames);	
			},isSingle:false});
		};
		
		//清空
		function reSet(){
			$("#ownUser").val("");
			$("#ownUserName").val("");	
		}
	
		
		function addContactClick()//在sysOrgEdit.jsp调用，为了弹出页面的拖动范围大些，所以写在父页面了
		{
			UserDialog({callback:function(userIds,fullnames){
				$("#contactId").val(userIds);
				$("#contactName").val(fullnames);	
			},isSingle:true});
		};
		
		//清空
		function retContactSet(){
			$("#contactId").val("");
			$("#contactName").val("");	
		}
		
	   

		
		function addCustomerRight(){
			var chooseArr=new Array();
			$("#selectedCustomerIds option").each(function(){
			    chooseArr.push($(this).val());
						
			});
			var str="";
			if(chooseArr.length>0){
				str=chooseArr.join(",");
			}
			$("#contractSel option:selected").each(function(){
			//console.log($(this).val());
				if(!isExist($(this).val(),"selectedCustomerIds")){	
					if(str!=""){
						str+=","+$(this).val();
					}
					else{
						str=$(this).val();
						
					}
					$("#selectedCustomerIds").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				}
				
			});
			//if(str)
			var arr=str.split(",");
			var contractArr=new Array();
			var customerArr=new Array();
			for(var i=0;i<arr.length;i++){
				var arrTemp=arr[i].split("|");
				contractArr.push(arrTemp[0]);
				customerArr.push(arrTemp[1]);
			} 
			
		    $("#customerIds").val(customerArr.join(","));
		    $("#contractIds").val(contractArr.join(","));
		    
		}
		
		
		function removeCustomerRight(){
			var arrAll=new Array();
			var chooseArr=new Array();
			$("#selectedCustomerIds option").each(function(){
			    arrAll.push($(this).val());
						
			});
		
		    $("#selectedCustomerIds option:selected").each(function(){
			    chooseArr.push($(this).val());
				$(this).remove();		
			});
			var str="";
			
		   var bl=false;
		   for(var i=0;i<arrAll.length;i++){
		   		var value=arrAll[i];
		   		for(var k=0;k<chooseArr.length;k++){
		   			if(value==chooseArr[k]){
		   				bl=true;
		   				break;
		   			}
		   		}
		   		
		   		if(!bl){
		   			if(str!=""){
							str+=","+value;
						}
						else{
							str=value;
						}
		   		}
		   		
		   }
		   
			var arr=str.split(",");
			var contractArr=new Array();
			var customerArr=new Array();
			for(var i=0;i<arr.lenght;i++){
				var arrTemp=arr[i].split("|");
				contractArr.push(arrTemp[0]);
				customerArr.push(arrTemp[1]);
			} 
		    $("#customerIds").val(customerArr.join(","));
		    $("#contractIds").val(contractArr.join(","));
			
		}
		
		function getCustomerByContractId(value){
			$.ajax({ url: "${ctx}/platform/system/sysOrg/getCustomerByContractId.xht?contractId="+value,success: function(data){
				for(var i=0;i<data.length;i++){
					$("#dxcustomerIds").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
				}
		        
		    }});	
		
		}
		
		function isExist(value,objId){
			var flag=false;
			$("#"+objId+" option").each(function(){
			    var obj=$(this);
			    if(obj.val()==value){
			    	flag=true;
			    	return flag;
			    }
				
			});
			return flag;
		}
	</script>
</head>
<body>
<div class="panel">
      
      <div class="panel-top">
			<div class="tbar-title" style="height:17px !important">
				<c:if test="${itsmBpmProject.id==null}">添加项目信息</c:if>
				<c:if test="${itsmBpmProject.id!=null}">编辑项目信息</c:if>
				
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.xht">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="projectForm" method="post" action="save.xht">
			   
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					
					
					<tr>
						<th width="20%">项目名称:<span class="required">*</span> </th>
						<td><input type="text" id="name" name="name" value="${itsmBpmProject.name}" style="width:255px !important" class="inputText"/></td>
					</tr>
					<tr>
						<th width="20%">项目代码:</th>
						<td><input type="text" id="code" name="code" value="${itsmBpmProject.code}" style="width:255px !important" class="inputText"/></td>
					</tr>
					<tr>
						<th width="20%">项目组:</th>
						<td>
							<select id="groupId" name="groupId"  style="width:245px !important">
							        <option value="0">--请选择--</option>  
							        <c:forEach items="${groups}" var="model">
										<option value="${model.id}" <c:if test="${itsmBpmProject.groupId==model.id}">selected</c:if> >${model.name}</option>
									</c:forEach>
		                    </select>
						</td>
					</tr>
					<tr>
						<th width="20%">负责人:<span class="required">*</span></th>
						<td>
                        <input type="text" class="inputText" name="managerName" readonly="readonly" style="width:300px" id="ownUserName" value="${itsmBpmProject.managerName}" >
					    <a href="#" onclick="addClick()" class="link get">选择</a>
					    <a href="#" onclick="reSet()" class="link clean">清空</a>
					    <input  type="hidden" name="managerId" id="ownUser" value="${itsmBpmProject.managerId}">
						</td>
					</tr>
					
					<tr>
						<th width="20%">计划开始时间: </th>
						<td><input type="text" id="planStartTime" name="planStartTime" value="<fmt:formatDate value='${itsmBpmProject.planStartTime}' pattern='yyyy-MM-dd HH:mm:ss' type='both' />" style="width:255px !important" class="inputText datetime" datetype="datetime"/></td>
					</tr>
					
					<tr>
						<th width="20%">计划结束时间: </th>
						<td><input type="text" id="planEndTime" name="planEndTime" value="<fmt:formatDate value='${itsmBpmProject.planEndTime}' pattern='yyyy-MM-dd HH:mm:ss' type='both' />" style="width:255px !important" class="inputText datetime" datetype="datetime"/></td>
					</tr>
					
					<tr>
						<th width="20%">描述: </th>
						<td><textarea id="orgDesc" name="des" cols="30" rows="4"  style="width:258px !important">${itsmBpmProject.des}</textarea></td>
					</tr>		
				</table>		
				<input type="hidden" id="contractIds" name="contractIds" value="${contractIds}" />	
				<input type="hidden" id="customerIds" name="customerIds" value="${customerIds}" />		
				<input type="hidden" name="id" value="${itsmBpmProject.id}" />		
				
				<br>
				<div
						
				>
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
				    <tr>
					    <td style="text-align:center;width:25%">待选合同</td>
					   
					    <td style="text-align:center;width:10%"></td>
					    <td style="text-align:center;width:40%">选择合同</td>
						
					</tr>
					<tr>
					   
						<td style="text-align:center;width:25%">
							<select size="5" multiple="multiple" id="contractSel" name="multipleselect" style="width:300px;height:200px;overflow-y:auto;">
								<c:forEach items="${contractList}" var="model">
										<option value="${model.id}|${model.orgA}">${model.contractName}-${model.orgAName}</option>
								</c:forEach>
		                      
		                    </select>
						</td>
						
						<td style="text-align:center;width:10%">
							<input type="button" value="添加>>" onclick="addCustomerRight();"/><br><br><br>
							<input type="button" value="<<删除" onclick="removeCustomerRight();"/>
						</td>
						<td style="text-align:center;width:40%">
							<select size="5" id="selectedCustomerIds" multiple="multiple" style="width:300px;height:200px;overflow-y:auto;">
		                        <c:forEach items="${selectContractList}" var="model">
										<option value="${model.id}|${model.orgA}">${model.contractName}-${model.orgAName}</option>
								</c:forEach>
		                    </select>
							
						</td>
					</tr>
				</table>
				</div>
				
		  </form>
	</div>

</div>

</body>
</html>
