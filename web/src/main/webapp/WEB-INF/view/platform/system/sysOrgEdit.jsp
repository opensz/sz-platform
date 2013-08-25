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
	<script type="text/javascript" src="${ctx }/js/lg/plugins/htButtons.js" ></script>
	<script type="text/javascript" src="${ctx }/js/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx }/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			
			function showRequest(formData, jqForm, options){ 
				return true;
			} 
	
			valid(showRequest,showResp);
			
			createoption();
			
			$("a.save").click(function() {
				$('#sysOrgForm').submit();
			});
			
			function showResp(responseText, statusText){
				
				var obj=new com.hotent.form.ResultMessage(responseText);
				
				if(obj.isSuccess()){//成功
				
					var objMsg=eval("(" + obj.getMessage() +")" );
					var orgId=objMsg.orgId;
					
					var action=objMsg.action;
					var msg=(action=="add")?"添加组织信息成功!":"编辑组织信息成功!";
					$.ligerMessageBox.success('提示信息',msg,function(){
						parent.$("#viewFrame").attr("src","get.xht?orgId=" +orgId);
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
	
		
	     function createoption(){
	     	var orgType="${sysOrg.orgType}";
	     	var obj=document.getElementById('orgType');
	     	
	    	obj.options.add(new Option("集团","1"));    
	       	obj.options.add(new Option("公司/单位","2")); 
	       	obj.options.add(new Option("部门","3"));
	       	obj.options.add(new Option("小组","4"));
	       	obj.options.add(new Option("其他组织","5"));
	       	var typeNum=Number(orgType);
			if (typeNum > 0) {
				for ( var temp = 0; temp < typeNum-1; temp++) {
					obj.options.remove(0);
				}
			}		
			for ( var i = 0; i < obj.length; i++) {
				if (obj[i].value == orgType)
					obj[i].checked = true;
			}
		}
	</script>
</head>
<body>
<div class="panel">
      <div class="panel-top">
			<div class="tbar-title" style="height:17px !important">
				<c:choose>
					<c:when test="${sysOrg.orgId==null}">添加组织信息</c:when>
					<c:otherwise>编辑组织信息</c:otherwise>  
				</c:choose>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">保存</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="sysOrgForm" method="post" action="save.xht">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th width="20%">维度名称: </th>
						<td>
		         			<input type="hidden" name="demId" value="${demension.demId}"/>
	         				${demension.demName}
		         		</td>
					</tr>
						<tr>
						<th width="20%">上级组织: </th>
						<td>${sysOrg.orgSupName}<input type="hidden" id="orgSupName" value="${sysOrg.orgSupName}" readonly="readonly" style="width:255px !important" class="inputText"/></td>
					</tr>
					<tr>
						<th width="20%">组织名称: </th>
						<td><input type="text" id="orgName" name="orgName" value="${sysOrg.orgName}" style="width:255px !important" class="inputText"/></td>
					</tr>
					<tr>
						<th width="20%">组织类型: </th>
						<td><select id="orgType" name="orgType" class="select">  
		                    </select></td>
					</tr>
					<tr>
						<th width="20%">主要负责人:</th>
						<td>
                        <input type="text" class="inputText" readonly="readonly" style="width:300px" id="ownUserName" value="${sysOrg.ownUserName}" >
					    <a href="#" onclick="addClick()" class="link get">选择</a>
					    <a href="#" onclick="reSet()" class="link clean">清空</a>
					    <input  type="hidden" name="ownUser" id="ownUser" value="${sysOrg.ownUser}">
						</td>
					</tr>	
					<tr>
						<th width="20%">组织描述: </th>
						<td><textarea id="orgDesc" name="orgDesc" cols="30" rows="4"  style="width:258px !important">${sysOrg.orgDesc}</textarea></td>
					</tr>
					<tr>
						<th width="20%">网络地址: </th>
						<td><input type="text" id="orgName" name="orgName" value="${sysOrg.webSite}" style="width:255px !important" class="inputText"/></td>
					</tr>
					<tr>
						<th width="20%">电话号码: </th>
						<td><input type="text" id="orgName" name="orgName" value="${sysOrg.telephone}" style="width:255px !important" class="inputText"/></td>
					</tr>		
				</table>					
				<input type="hidden" id="orgId" name="orgId" value="${sysOrg.orgId}" />
				<input type="hidden" id="orgSupId" name="orgSupId" value="${sysOrg.orgSupId}"/>
		  </form>
	</div>

</div>

</body>
</html>