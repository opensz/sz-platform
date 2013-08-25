<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" import="org.sz.platform.bpm.model.flow.BpmDefRights"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>流程定义权限管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bpmDefRights"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/htButtons.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>

<script type="text/javascript">
	$(function() {
		$("span.htbtn").htButtons();

		function showRequest(formData, jqForm, options) { 
			return true;
		} 
		valid(showRequest,showResponse);
		
		$("a.save").click(function() {
			$('#bpmDefRightsForm').submit(); 
		});
		
		function showResponse(responseText, statusText)  { 
			var obj=new org.sz.form.ResultMessage(responseText);
			if(obj.isSuccess()){//成功
				$.ligerMessageBox.success('提示信息',obj.getMessage(),function(rtn){
					if(rtn){
						window.close();
					}
				});
				
		    }else{//失败
		    	$.ligerMessageBox.error('提示信息',obj.getMessage());
		    }
		} 
		
	});

	function reset(obj) {
		var tr=$(obj).parents("tr");
		$(tr).find(":input[name='ownerId']").val('');
		$(tr).find(":input[name='ownerName']").val('');
	}

	function setVal(obj,userIds, fullnames){
		var tr=$(obj).parents("tr");
		$(tr).find(":input[name='ownerId']").val(userIds);
		$(tr).find(":input[name='ownerName']").val(fullnames);
	}
	
	function chooseUser(obj) {
		UserDialog({
			callback:function(userIds, fullnames){
				setVal(obj,userIds, fullnames);
			}
		});
	};

	function chooseOrg(obj){
		OrgDialog({
			callback:function(orgIds, orgnames){
				setVal(obj,orgIds, orgnames);
			}
		});
	};

	function chooseRole(obj){
		RoleDialog({
			callback:function(roleIds, rolenames){
			setVal(obj,roleIds, rolenames);
			}
		});
	};

</script>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
				<c:choose>
					<c:when test="${type==0}">${bpmDefinition.subject}</c:when>
					<c:otherwise>${globalType.typeName}</c:otherwise>
				</c:choose>-权限设置
				</span>
			</div>
			
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save">保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del" onclick="window.close()" href="#">关闭</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-data">
			<form id="bpmDefRightsForm" action="${ctx}/platform/bpm/bpmDefRight/save.xht" method="post">
				<input type="hidden"  name="id" value="${id}">
				<input type="hidden"  name="type" value="${type}">
				<table id="bpmDefRightTable"  class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th>权限分类</th>
						<th style="text-align: left;">授权给</th>
						<th style="text-align: left;">选择</th>
					</tr> 
						<tr>
							<th>用户授权</th>
							<td>
								<textarea rows="3" cols="60" name="ownerName" readonly="readonly">${rightsMap.user.ownerName}</textarea>
								<input type="hidden" name="ownerId" value="${rightsMap.user.ownerId }">
								<input type="hidden" name="rightType" value="1">
							</td>
							<td>
								<a class="button" onclick="chooseUser(this);" ><span>选择...</span></a>
								&nbsp;				
								<a class="button" onclick="reset(this);" ><span>重置</span></a>
							</td>
						</tr>
						<tr>
							<th>角色授权</th>
							<td>		
								<textarea rows="3" cols="60" name="ownerName" readonly="readonly">${rightsMap.role.ownerName}</textarea>
								<input type="hidden" name="ownerId" value="${rightsMap.role.ownerId }">
								<input type="hidden" name="rightType" value="2">				
							</td>
							<td>
								<a class="button" onclick="chooseRole(this);" ><span>选择...</span></a>
								&nbsp;
								<a class="button" onclick="reset(this);" ><span>重置</span></a>
							</td>
						</tr>
						<tr>
							<th>组织授权</th>
							<td>
								<textarea rows="3" cols="60" name="ownerName" readonly="readonly">${rightsMap.org.ownerName}</textarea>
											<input type="hidden" name="ownerId" value="${rightsMap.org.ownerId }">
											<input type="hidden" name="rightType" value="3">
							</td>
							<td>
								<a class="button" onclick="chooseOrg(this);" ><span>选择...</span></a>
								&nbsp;
								<a class="button" onclick="reset(this);" ><span>重置</span></a>
							</td>
						</tr>
				</table>
				</form>
			</div>
		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


