<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx }/js/sz/platform/system/SysDialog.js"></script>
<script type="text/javascript">
	function dlgCallBack(userIds){
		var orgId="${orgId}";
		var path="${path}";
		var url="addOrgUser.xht";
		para="userIds="+userIds+"&orgId="+orgId;
		$.post(url,para,function(rtn){
		   $.ligerMessageBox.success("提示信息","人员加入成功!！",function(rtn){
				  location.href="userList.xht?orgId="+orgId+"&path="+path;
			  });
			obj.close();
		});
	};
	function addClick(){
		UserDialog({callback:dlgCallBack,isSingle:false});
	};

</script>
</head>
<body>
<div class="panel">
       <f:tab curTab="3" tabName="sysOrg"/>
       <c:choose>
       		<c:when test="${empty sysOrg}">
					<div style="text-align: center;margin-top: 10%;">尚未指定具体组织!</div>
				</c:when>
       		<c:otherwise>
       		<div class="panel-toolbar">
					<div class="toolBar">
					    <div class="l-bar-separator"></div>
						<div class="group"><a class="link search" id="btnSearch">查询</a></div>
						<!--<div class="l-bar-separator"></div>
						<div class="group"><a class="link add"  href="${ctx}/platform/system/sysUser/edit.xht?orgId=${orgId}">新增人员</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link add"  href="#" onclick="addClick()">加入人员</a></div>
						<div class="l-bar-separator"></div>-->
						<div class="group"><a class="link del" action="${ctx}/platform/system/sysUserOrg/del.xht">移除</a></div>
					</div>	
			
		 	</div>
			<div class="panel-body">
		 		<div class="panel-search">
					<form id="searchForm" method="post" action="userList.xht?orgId=${orgId}">
						<div class="row">
							<span class="label">姓名:</span><input type="text" name="Q_fullname_S"  class="inputText" style="width:110px !important" />
							<span class="label">帐号:</span><input type="text" name="Q_account_S"  class="inputText" style="width:110px !important" />
						</div>
					</form>
		 		</div>
       			<div class="panel-data">
			        <c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
					</c:set>
				    <display:table name="sysOrgUserList" id="sysOrgItem" requestURI="userList.xht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
						<display:column title="${checkAll}" media="html" style="width:30px;text-align:center;">
							  	<input type="checkbox" class="pk" name="userOrgId" value="${sysOrgItem.userOrgId}">
						</display:column>
						<display:column property="orgName" title="所属组织" sortable="true" sortName="orgName"></display:column>
					
						<display:column property="userName" title="姓名" sortable="true" sortName="userName"></display:column>
						<display:column property="account" title="帐号" sortable="true" sortName="account"></display:column>
						<display:column   title="是否主组织" >
							<c:choose>
								<c:when test="${sysOrgItem.isPrimary==1}"><span class="green">是</span></c:when>
								<c:otherwise><span class="red">否</span></c:otherwise>
							</c:choose>
						</display:column>
						<display:column   title="是否负责人" >
							<c:choose>
								<c:when test="${sysOrgItem.isCharge==1}"><span class="green">是</span></c:when>
								<c:otherwise><span class="red">否</span></c:otherwise>
							</c:choose>
						</display:column>
						
						<display:column title="管理" media="html" >
							<a href="${ctx}/platform/system/sysUserOrg/del.xht?userOrgId=${sysOrgItem.userOrgId}" class="link del">删除</a>
							
							<a href="${ctx}/platform/system/sysUser/get.xht?userId=${sysOrgItem.userId}" class="link detail">明细</a>
							<c:choose>
								<c:when test="${sysOrgItem.isPrimary==0}">
									<a class="link primary" title="设置为主岗位" href="setIsPrimary.xht?userPosId=${sysOrgItem.userOrgId}">设置为主组织</a>
								</c:when>
								<c:otherwise>
									<a class="link notPrimary" title="设置为非主岗位" href="setIsPrimary.xht?userPosId=${sysOrgItem.userOrgId}">设置为非主组织</a>
								</c:otherwise>
							</c:choose>
							<c:choose>
									<c:when test="${sysOrgItem.isCharge==0}">
										<a class="link charge" title="设置为负责人" href="setIsCharge.xht?userPosId=${sysOrgItem.userOrgId}">设置为负责人</a>
									</c:when>
									<c:otherwise>
										<a class="link noCharge" title="设置为非负责人" href="setIsCharge.xht?userPosId=${sysOrgItem.userOrgId}">设置为非负责人</a>
									</c:otherwise>
							</c:choose>
						</display:column>
					</display:table>
					<sz:paging tableId="sysOrgItem"/>
	  		 </div>	
	   		</div>
       		</c:otherwise>
       </c:choose>
       
	  </div> 					
</body>
</html>