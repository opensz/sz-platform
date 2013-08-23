<%--
	time:2011-11-09 11:20:13
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>添加组织架构</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysOrg"></script>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/htButtons.js" ></script>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx }/js/sz/platform/system/SysDialog.js"></script>
	<script type="text/javascript">
	    var orgTypeStr="${orgTypeStr}";
		$(function() {
			
			function showRequest(formData, jqForm, options){ 
				return true;
			} 
	
			valid(showRequest,showResp);
			
			//createoption();
			
			$("a.save").click(function() {
				$('#sysOrgForm').submit();
			});
			
			function showResp(responseText, statusText){
				
				var obj=new org.sz.form.ResultMessage(responseText);
				
				if(obj.isSuccess()){//成功
				
					var objMsg=eval("(" + obj.getMessage() +")" );
					var orgId=objMsg.orgId;
					
					var action=objMsg.action;
					var msg=(action=="add")?"添加客户信息成功!":"编辑客户信息成功!";
					$.ligerMessageBox.success('提示信息',msg,function(){
						parent.$("#viewFrame").attr("src","customerSelector.xht?demId=1");
						parent.loadTree(1);
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
		
	     function createoption(){
	     	var orgType="${sysOrg.orgType}";
	     	var orgId="${sysOrg.orgId}";
	     	
	     	var obj=document.getElementById('orgType');
	     	
	    	obj.options.add(new Option("集团","1"));    
	       	obj.options.add(new Option("公司/单位","2")); 
	       	obj.options.add(new Option("部门","3"));
	       	obj.options.add(new Option("小组","4"));
	       	obj.options.add(new Option("其他组织","5"));
	       	obj.options.add(new Option("客户","6"));
	       	var typeNum=Number(orgType);
			if(orgTypeStr=="3"){//在部门节点上操作时
				    obj.options.length=0;
					obj.options.add(new Option("部门","3"));
					document.getElementById("orgTypeId_tr").style.display="";
					document.getElementById("site_tr").style.display="";
					
					document.getElementById("level_tr").style.display="none";
					document.getElementById("webSite_tr").style.display="none";
				    document.getElementById("telephone_tr").style.display="none";
				    
			}
			if(orgTypeStr=="6"){//在客户节点上操作时
				    obj.options.length=0;
				    obj.options.add(new Option("客户","6"));
					obj.options.add(new Option("部门","3"));
					document.getElementById("level_tr").style.display="";
				
					document.getElementById("site_tr").style.display="";
					document.getElementById("webSite_tr").style.display="";
				    document.getElementById("telephone_tr").style.display="";
				    document.getElementById("orgTypeId_tr").style.display="none";
					
			}
			if(orgId!=""){
				//obj.options.length = 0; 
				 
		        obj.value=orgType;
		        obj.disabled='disabled';
				
			}
			else{
			
			}
		}
		
		function getOrgType(obj){
			var bl=true;
			if(orgTypeStr=="3"){
				if(obj.value!=3){
					alert("部门节点下只能添加类型为部门的机构");
					bl=false;
				}
			}
			if(bl){
				if(obj.value==6){
					document.getElementById("level_tr").style.display="";
				    document.getElementById("webSite_tr").style.display="";
				    document.getElementById("telephone_tr").style.display="";
					document.getElementById("site_tr").style.display="";
				}
				else if(obj.value==3){
					document.getElementById("orgTypeId_tr").style.display="";
					document.getElementById("site_tr").style.display="";
					document.getElementById("level_tr").style.display="none";
					document.getElementById("webSite_tr").style.display="none";
				    document.getElementById("telephone_tr").style.display="none";
				}
				else {
					document.getElementById("level_tr").style.display="none";
					document.getElementById("orgTypeId_tr").style.display="none";
					document.getElementById("site_tr").style.display="none";
					document.getElementById("webSite_tr").style.display="none";
				    document.getElementById("telephone_tr").style.display="none";
				}
			}
			
		}
		
		function addRight(){
			var chooseArr=new Array();
			$("#selectedContractIds option").each(function(){
			    chooseArr.push($(this).val());
						
			});
			var str="";
			if(chooseArr.length>0){
				str=chooseArr.join(",");
			}
			$("#multipleselect option:selected").each(function(){
			//console.log($(this).val());
				if(!isExist($(this).val(),"selectedContractIds")){	
					if(str!=""){
						str+=","+$(this).val();
					}
					else{
						str=$(this).val();
					}
					$("#selectedContractIds").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				}
				
			});
			//if(str)
		    $("#contractId").val(str);
		    
		}
		
		function removeRight(){
			var arrAll=new Array();
			var chooseArr=new Array();
			$("#selectedContractIds option").each(function(){
			    arrAll.push($(this).val());
						
			});
		
		    $("#selectedContractIds option:selected").each(function(){
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
		   
			$("#contractId").val(str);
			
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
				<c:choose>
					<c:when test="${sysOrg.orgId==null}">添加客户信息</c:when>
					<c:otherwise>编辑客户信息</c:otherwise>  
				</c:choose>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">保存</a>
					</div>
					<div class="l-bar-separator"></div>
						<div class="group"><a class="link back" href="customerSelector.xht?demId=1">返回</a></div>
					</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="sysOrgForm" method="post" action="save.xht">
			    <input type="hidden" name="parentId" value="${parentId}"/>
			     <input type="hidden" name="orgSupTempId" value="${orgSupTempId}"/>
			     <input type="hidden" name="demId" value="1"/>
			     <input type="hidden" name="orgType" value="6"/>
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<!--<tr>
						<th width="20%">维度名称: </th>
						<td>
		         			<input type="hidden" name="demId" value="${demension.demId}"/>
	         				${demension.demName}
		         		</td>
					</tr>
						-->
					<!--<tr>
						<th width="20%">上级组织: </th>
						<td>${sysOrg.orgSupName}<input type="hidden" id="orgSupName" value="${sysOrg.orgSupName}" readonly="readonly" style="width:255px !important" class="inputText"/></td>
					</tr>
					--><tr>
						<th width="20%">名称: </th>
						<td><input type="text" id="orgName" name="orgName" value="${sysOrg.orgName}" style="width:255px !important" class="inputText"/></td>
					</tr>
					
					<tr>
						<th width="20%">负责人:</th>
						<td>
                        <input type="text" class="inputText" readonly="readonly" style="width:300px" id="ownUserName" value="${sysOrg.ownUserName}" >
					    <a href="#" onclick="addClick()" class="link get">选择</a>
					    <a href="#" onclick="reSet()" class="link clean">清空</a>
					    <input  type="hidden" name="ownUser" id="ownUser" value="${sysOrg.ownUser}">
						</td>
					</tr>
					
					<tr>
						<th width="20%">联系人:</th>
						<td>
                        <input type="text" class="inputText" style="width:300px" id="linkName" name="linkName" value="${sysOrg.linkName}" >
					    
						</td>
					</tr>
						
					<tr id="webSite_tr"
						
					>
						<th width="20%">网址: </th>
						<td><input type="text" id="webSite" name="webSite" value="${sysOrg.webSite}" style="width:255px !important" class="inputText"/></td>
					</tr>
					<tr
						id="telephone_tr"
						
					>
						<th width="20%">联系方式: </th>
						<td><input type="text" id="telephone" name="telephone" value="${sysOrg.telephone}" style="width:255px !important" class="inputText"/></td>
					</tr>
					
					<tr id="level_tr" 
						
					>
						<th width="20%">客户级别: </th>
						<td><select id="level" name="level"  style="width:245px !important">
							        <option value="">--请选择--</option>  
							        <c:forEach items="${customerLevelList}" var="model">
										<option value="${model[0]}" <c:if test="${sysOrg.level==model[0]}">selected</c:if> >${model[1]}</option>
									</c:forEach>
		                    </select>
		               </td>
					</tr>	
					<tr id="site_tr" 
						
					>
						<th width="20%">办公地点: </th>
						<td><select id="siteId" name="siteId"  style="width:245px !important">
							        <option value="">--请选择--</option>  
							        <c:forEach items="${siteList}" var="model">
										<option value="${model[0]}" <c:if test="${sysOrg.siteId==model[0]}">selected</c:if> >${model[1]}</option>
									</c:forEach>
		                    </select>
		               </td>
					</tr>	
					<tr id="orgTypeId_tr" 
						
					>
						<th width="20%">机构类型: </th>
						<td><select id="orgTypeId" name="orgTypeId"  style="width:245px !important">
							        <option value="">--请选择--</option>  
							        <c:forEach items="${orgTypeList}" var="model">
										<option value="${model[0]}" <c:if test="${sysOrg.orgTypeId==model[0]}">selected</c:if> >${model[1]}</option>
									</c:forEach>
		                    </select>
		               </td>
					</tr>
					<tr>
						<th width="20%">组织描述: </th>
						<td><textarea id="orgDesc" name="orgDesc" cols="30" rows="4"  style="width:258px !important">${sysOrg.orgDesc}</textarea></td>
					</tr>		
				</table>		
				<input type="hidden" id="contractId" name="contractIds" value="${contractIds}" />		
				<input type="hidden" id="orgId" name="orgId" value="${sysOrg.orgId}" />
				<input type="hidden" id="orgSupId" name="orgSupId" value="${sysOrg.orgSupId}"/>
				<br>
				<div
						<c:if test="${action=='add'}">style="display:none"</c:if>
						
				>
				<!--<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="45%">
							<select size="5" multiple="multiple" id="multipleselect" name="multipleselect" style="width:300px;height:200px;overflow-y:auto;">
								<c:forEach items="${contractList}" var="model">
										<option value="${model.id}">${model.contractName}</option>
								</c:forEach>
		                      
		                    </select>
						</td>
						<td style="text-align:center;width:10%">
						<input type="button" value="添加>>" onclick="addRight();"/><br><br><br>
						<input type="button" value="<<删除" onclick="removeRight();"/>
						</td>
						<td width="45%">
							<select size="5" id="selectedContractIds" multiple="multiple" style="width:300px;height:200px;overflow-y:auto;">
		                        <c:forEach items="${myContractList}" var="model">
										<option value="${model.id}">${model.contractName}</option>
								</c:forEach>
		                    </select>
						</td>
					</tr>
				</table>
				--></div>
		  </form>
	</div>

</div>

</body>
</html>
