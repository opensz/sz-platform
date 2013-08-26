
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
	    var demId="${demension.demId}";
		$(function() {
			
			function showRequest(formData, jqForm, options){ 
				return true;
			} 
	
			valid(showRequest,showResp);
			
			
			
			$("a.save").click(function() {
				document.getElementById('sysOrgForm').submit();
				//$('#sysOrgForm').submit();
			});
			
			function showResp(responseText, statusText){
				
				var obj=new org.sz.form.ResultMessage(responseText);
				
				if(obj.isSuccess()){//成功
				
					var objMsg=eval("(" + obj.getMessage() +")" );
					var orgId=objMsg.orgId;
					
					var action=objMsg.action;
					var msg=(action=="add")?"添加供应商信息成功!":"编辑供应商信息成功!";
					$.ligerMessageBox.success('提示信息',msg,function(){
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
	
		

		
	    
		
		
		
	</script>
</head>
<body>
<div class="panel">
      <div class="panel-top">
			<div class="tbar-title" style="height:17px !important">
				<c:choose>
					<c:when test="${sysOrg.orgId==null}">添加供应商信息</c:when>
					<c:otherwise>编辑供应商信息</c:otherwise>  
				</c:choose>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="supplierList.xht">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="sysOrgForm" method="post" action="supplierSave.xht">
			    <input type="hidden" name="parentId" value="${parentId}"/>
			     <input type="hidden" name="orgSupTempId" value="${orgSupTempId}"/>
			     <input type="hidden" name="demId" value="1"/>
			     <input type="hidden" name="orgType" value="8"/>
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					
					
					<tr>
						<th width="20%">全称: </th>
						<td><input type="text" id="orgName" name="orgName" value="${sysOrg.orgName}" style="width:255px !important" class="inputText"/></td>
					</tr>
					<tr>
						<th width="20%">分类:  <span class="required">*</span></th>
							<td>
							<select name="category" class="select" style="width:250px;">
											<option value="">--选择--</option>
											<option value="1" <c:if test="${sysOrg.category==1}">selected</c:if>>长期</option>
											<option value="2" <c:if test="${sysOrg.category==2}">selected</c:if>>临时</option>
							</select>
							</td>
					</tr>
					<tr>
						<th width="20%">类型:  <span class="required">*</span></th>
							<td>
							<select name="supplierType" class="select" style="width:250px;">
											<option value="">--选择--</option>
											<option value="1" <c:if test="${sysOrg.supplierType==1}">selected</c:if>>服务供应商</option>
											<option value="2" <c:if test="${sysOrg.supplierType==2 }">selected</c:if>>设备供应商</option>
							</select>
							</td>
					</tr>
					<tr>
						<th width="20%">负责人:</th>
						<td>
                        <input type="text" class="inputText" readonly="readonly" style="width:300px" id="ownUserName" value="${sysOrg.contactName}" >
					    <a href="#" onclick="addClick()" class="link get">选择</a>
					    <a href="#" onclick="reSet()" class="link clean">清空</a>
					    <input  type="hidden" name="contactId" id="ownUser" value="${sysOrg.contactId}">
						</td>
					</tr>
					
					
						
					<tr id="webSite_tr">
						<th width="20%">网址: </th>
						<td><input type="text" id="webSite" name="webSite" value="${sysOrg.webSite}" style="width:255px !important" class="inputText"/></td>
					</tr>
					<tr id="telephone_tr">
						<th width="20%">总机: </th>
						<td><input type="text" id="telephone" name="telephone" value="${sysOrg.telephone}" style="width:255px !important" class="inputText"/></td>
					</tr>
					
						
					<tr id="site_tr">
						<th width="20%">办公地点: </th>
						<td><select id="siteId" name="siteId"  style="width:245px !important">
							        <option value="">--请选择--</option>  
							        <c:forEach items="${siteList}" var="model">
										<option value="${model[0]}" <c:if test="${sysOrg.siteId==model[0]}">selected</c:if> >${model[1]}</option>
									</c:forEach>
		                    </select>
		               </td>
					</tr>	
					
					<tr>
						<th width="20%">描述: </th>
						<td><textarea id="orgDesc" name="orgDesc" cols="30" rows="4"  style="width:258px !important">${sysOrg.orgDesc}</textarea></td>
					</tr>		
				</table>				
				<input type="hidden" id="orgId" name="orgId" value="${sysOrg.orgId}" />
				<input type="hidden" id="orgSupId" name="orgSupId" value="${sysOrg.orgSupId}"/>
				<br>
				
		  </form>
	</div>

</div>

</body>
</html>
