<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>系统角色表管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<%@ taglib prefix="sz" uri="http://www.servicezon.com/paging" %>

	<script type="text/javascript" src="${ctx}/js/sz/platform/system/CopyRoleDialog.js"></script>
    <script type="text/javascript">
	    function copyRole(roleId,roleName){
	    	CopyRoleDialog({roleId:roleId});
	    }
	    
		function editRoleRes(roleId){
	        var url=__ctx+"/platform/system/roleResources/edit.xht?roleId="+roleId;
	    	var winArgs="dialogWidth=350px;dialogHeight=460px;status=0;help=0;";
	    	url=url.getNewUrl();
	    	window.showModalDialog(url,"",winArgs);
	    }
    </script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">系统角色管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><f:a alias="searchRole" css="link search" id="btnSearch" >查询</f:a></div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<f:a alias="addRole" css="link add" href="edit.xht?systemId=${systemId}">添加</f:a>
							</div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<f:a alias="delRole" css="link del" action="del.xht">删除</f:a>
							</div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
										<span class="label">角色名:</span><input type="text" name="Q_roleName_S"  class="inputText" />
										<span class="label">子系统名称:</span><input type="text" name="Q_sysName_S"  class="inputText" />
										<span class="label">允许删除:</span>
										<select name="Q_allowDel_SN" class="select" style="width:8%;">
											<option value="">--全部--</option>
											<option value="1">允许</option>
											<option value="0">不允许</option>
										</select>
										<span class="label">允许编辑:</span>
										<select name="Q_allowEdit_SN" class="select" style="width:8%;">
											<option value="">--全部--</option>
											<option value="1">允许</option>
											<option value="0">不允许</option>
										</select>
										<span class="label">是否启用:</span>
										<select name="Q_enabled_SN" class="select" style="width:8%;">
											<option value="">--全部--</option>
											<option value="1">是</option>
											<option value="0">否</option>
										</select>
									</div>
							</form>
					</div>
				
					<div class="panel-data">
					    <display:table name="sysRoleList" id="sysRoleItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
							<display:column title="<input type='checkbox' id='chkall'/>" media="html" style="width:30px;">
							<c:if test="${sysRoleItem.allowDel==0}"><input type="checkbox" class="disabled" name="roleId" id="roleId" value="${sysRoleItem.roleId}" disabled="disabled"></c:if>
							<c:if test="${sysRoleItem.allowDel==1}"><input type="checkbox" class="pk" name="roleId" id="roleId" value="${sysRoleItem.roleId}"></c:if>
							</display:column>
							<display:column property="roleName" title="角色名" style="text-align:left" sortable="true" sortName="a.roleName"></display:column>
							<display:column property="subSystem.sysName" title="子系统名称" style="text-align:left" sortable="true" sortName="b.sysName">
							</display:column>
							<display:column property="memo" title="备注" style="text-align:left" sortable="true" sortName="a.memo"></display:column>
 			                <display:column title="允许删除" sortable="true" sortName="a.allowDel">
							<c:choose>
								<c:when test="${sysRoleItem.allowDel eq 0}"><span class="red">不允许</span></c:when>
								<c:when test="${sysRoleItem.allowDel eq 1}"><span class="green">允许</font></c:when>
								<c:otherwise>未设定</c:otherwise>
							</c:choose>
							</display:column>
							<display:column  title="允许编辑" sortable="true" sortName="a.allowEdit">
							<c:choose>
								<c:when test="${sysRoleItem.allowEdit eq 0}"><span class="red">不允许</span></c:when>
								<c:when test="${sysRoleItem.allowEdit eq 1}"><span class="green">允许</font></c:when>
								<c:otherwise>未设定</c:otherwise>
							</c:choose>
							</display:column>
							<display:column title="状态" sortable="true" sortName="a.enabled">
							<c:choose>
							    <c:when test="${sysRoleItem.enabled eq 0}"><span class="red">禁用</span></c:when>
								<c:when test="${sysRoleItem.enabled eq 1}"><span class="green">启用</span></c:when>
								<c:otherwise><span class="red">未设定</span></c:otherwise>
							</c:choose>
							</display:column>
							<display:column title="管理" media="html" style="width:450px">
								<c:choose>
									<c:when test="${sysRoleItem.allowDel==0}">
										<a href="#" class="link del disabled" >删除</a>
									</c:when>
									<c:otherwise>
										<f:a alias="delRole" css="link del" href="del.xht?roleId=${sysRoleItem.roleId}">删除</f:a>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${sysRoleItem.allowEdit==0}">
										<a href="#" class="link edit disabled" >编辑</a>
									</c:when>
									<c:otherwise>
										<f:a alias="updRole" css="link edit" href="edit.xht?roleId=${sysRoleItem.roleId}">编辑</f:a>
									</c:otherwise>
								</c:choose>
								<f:a alias="roleDetail" css="link detail" href="get.xht?roleId=${sysRoleItem.roleId}">明细</f:a>
								<f:a alias="copyRole" css="link copy" onclick="copyRole('${sysRoleItem.roleId}','${sysRoleItem.roleName}')" >复制角色</f:a>
								<f:a alias="sourceRole" css="link flowDesign" href="javascript:editRoleRes(${sysRoleItem.roleId });">资源分配</f:a>
								<f:a alias="userRole" css="link auth" href="${ctx}/platform/system/userRole/edit.xht?roleId=${sysRoleItem.roleId}" >人员分配</f:a>
								<c:choose>
							    <c:when test="${sysRoleItem.enabled eq 1}"><a href="runEnable.xht?roleId=${sysRoleItem.roleId }&enabled=${sysRoleItem.enabled}" class="link lock" >禁用</a></c:when>
								<c:when test="${sysRoleItem.enabled eq 0}"><a href="runEnable.xht?roleId=${sysRoleItem.roleId }&enabled=${sysRoleItem.enabled}" class="link unlock">启用</a></c:when>
							</c:choose>
							</display:column>
							
						</display:table>
						<sz:paging tableId="sysRoleItem"/>
					</div>
				</div> 			
			</div> 
</body>
</html>


