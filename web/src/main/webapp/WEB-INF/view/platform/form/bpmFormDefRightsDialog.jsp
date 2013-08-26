

<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>表单授权</title>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript" src="${ctx}/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/Permission.js"></script>

<script type="text/javascript">
	var nodeId="${nodeId}";
	var actDefId="${actDefId}";
	var formKey="${formKey}";

	var __Permission__;
	
	$(function() {
		//权限处理
		__Permission__=new Permission();
		__Permission__.loadByNode(actDefId,nodeId,formKey);
		$("#dataFormSave").click(savePermission);
		$("#nodeId").change(changeNode);
	});
	
	//重新加载任务节点的权限
	function changeNode(){
		var obj=$("#nodeId");
		nodeId=obj.val();
		__Permission__.loadByNode(actDefId,nodeId,formKey);
	};
	
	//保存权限数据。
	function savePermission(){
		var json=__Permission__.getPermissionJson();
		var params={actDefId:actDefId,nodeId:nodeId,formKey:formKey,permission:json};
		$.post("savePermission.xht",params,showResponse);
	}
		
	function showResponse(data){
		var obj=new org.sz.form.ResultMessage(data);
		if(obj.isSuccess()){//成功
			$.ligerMessageBox.confirm('提示信息','操作成功,继续操作吗?',function(rtn){
				if(!rtn){
					window.close();
				}
			});
	    }else{//失败
	    	$.ligerMessageBox.error('出错了',obj.getMessage());
	    }
	};
</script>
</head>
<body >
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">表单授权</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group">
					<a class="link save" id="dataFormSave" href="#">保存</a>
				</div>
				<div class="l-bar-separator"></div>
				<div class="group">
					<a class="link back" href="javascript:window.close();">返回</a>
				</div>
			</div>
		</div>
	</div>
	<div>
		<div  class="panel-body">
			<div >
				<form id="bpmFormDefForm">
				
					<table cellpadding="0" cellspacing="0" border="0" style=" margin: 4px 0px;"  class="table-detail">
						<tr style="height:25px;">
							<td>流程节点:
								<select id="nodeId" >
									<c:forEach items="${nodeMap }" var="node">
										<option value="${node.key}" <c:if test="${node.key== nodeId}"> selected="selected" </c:if> >${node.value}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div title="权限设置" style="overflow:auto;">
				<div class="panel-body">
					<div class="panel-data">
						<table cellpadding="1" cellspacing="1" class="table-grid" >
							<tr>
								<th width="20%">字段</th>
								<th width="40%">只读权限</th>
								<th width="40%">编辑权限</th>
							</tr>
							<tbody id="fieldPermission"></tbody>
						</table>
					
						<table  cellpadding="1" cellspacing="1" class="table-grid" style="margin-top: 5px;">
							<tr>
								<th width="20%">子表</th>
								<th width="40%">只读权限</th>
								<th width="40%">编辑权限</th>
							</tr>
							<tbody id="tablePermission"></tbody>
						</table>
					
						<table  cellpadding="1" cellspacing="1" class="table-grid" style="margin-top: 5px;">
							<tr>
								<th width="20%">意见</th>
								<th width="40%">只读权限</th>
								<th width="40%">编辑权限</th>
							</tr>
							<tbody id="opinionPermission"></tbody>
						</table>
					</div>
				</div>
			</div>
	</div>
	
</body>
</html>

