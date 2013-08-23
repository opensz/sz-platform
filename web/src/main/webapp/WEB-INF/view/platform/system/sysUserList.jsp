<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="org.sz.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>用户表管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">用户表管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group">
								<f:a alias="searchUser" css="link search" id="btnSearch">查询</f:a>
							</div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<f:a alias="addUser" css="link add" href="edit.xht">添加</f:a>
							</div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<f:a alias="delUser" css="link del" action="del.xht">删除</f:a>
							</div>
							<div class="l-bar-separator"></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
										<span class="label">姓名:</span><input type="text" name="Q_fullname_S" value="${params['fullname']}"   class="inputText" style="width:9%"/>	
										<span class="label">部门:</span>		
										<select name="Q_deptId_S" class="select" style="width:8%;">
											<option value="">--选择--</option>
											<c:forEach items="${listDept}" var="m">
												<option value="${m.orgId}" <c:if test="${m.orgId==params['deptId']}">selected</c:if>>${m.orgName}</option>
											</c:forEach>
											
										</select>	
										<span class="label">创建时间从:</span><input type="text" name="Q_begincreatetime_DL" value="" class="inputText date" style="width:9%"/>
										<span class="label">至</span><input type="text" name="Q_endcreatetime_DG" value="" class="inputText date" style="width:9%"/>
										<span class="label">是否过期:</span>	
										<select name="Q_isExpired_S" class="select" style="width:8%;">
											<option value="">--选择--</option>
											<option value="<%=SysUser.EXPIRED%>">是</option>
											<option value="<%=SysUser.UN_EXPIRED%>">否</option>
										</select>
										<span class="label">是否锁定:</span>
										<select name="Q_isLock_S" class="select" style="width:8%;">
											<option value="">--选择--</option>
											<option value="<%=SysUser.LOCKED%>">是</option>
											<option value="<%=SysUser.UN_LOCKED%>">否</option>
										</select>
										<span class="label">状态:</span>
										<select name="Q_status_S" class="select" style="width:8%;">
											<option value="">--选择--</option>
											<option value="<%=SysUser.STATUS_OK%>">激活</option>
											<option value="<%=SysUser.STATUS_NO%>">禁用</option>
											<option value="<%=SysUser.STATUS_Del%>">删除</option>
										</select>
									</div>
							</form>
					</div>
					 
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="sysUserList" id="sysUserItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  export="true"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;text-align:center;">
								  	<input type="checkbox" class="pk" name="userId" value="${sysUserItem.userId}">
							</display:column>
							<display:column property="fullname" title="姓名" sortable="true" sortName="fullname" style="text-align:left"></display:column>
							<display:column property="account" title="帐号" sortable="true" sortName="account" style="text-align:left"></display:column>
							<display:column  title="创建时间" sortable="true" sortName="createtime">
								<fmt:formatDate value="${sysUserItem.createtime}" pattern="yyyy-MM-dd"/>
							</display:column>
							<display:column title="是否过期" sortable="true" sortName="isExpired">
								<c:choose>
									<c:when test="${sysUserItem.isExpired==1}">
										<span class="red">已过期</span>
								   	</c:when>
							       	<c:otherwise>
								    	<span class="green">未过期</span>
								   	</c:otherwise>
								</c:choose>
							</display:column>
			                <display:column title="是否可用" sortable="true" sortName="isLock">
								<c:choose>
									<c:when test="${sysUserItem.isLock==1}">
										<span class="red">已锁定</span>
								   	</c:when>
							       	<c:otherwise>
							       		<span class="green">未锁定</span>
								   	</c:otherwise>
								</c:choose>
							</display:column>
		                	<display:column title="状态" sortable="true" sortName="status">
								<c:choose>
									<c:when test="${sysUserItem.status==1}">
										<span class="green">激活</span>
										
								   	</c:when>
								   	<c:when test="${sysUserItem.status==0}">
								   		<span class="red">禁用</span>
										
								   	</c:when>
							       	<c:otherwise>
							       		<span class="red">删除</span>
								        
								   	</c:otherwise>
								</c:choose>
							</display:column>	
							<display:column title="管理" media="html" style="width:420px;text-align:center;">
								<f:a alias="delUser" css="link del" href="del.xht?userId=${sysUserItem.userId}">删除</f:a>
								<f:a alias="updateUserInfo" css="link edit" href="edit.xht?userId=${sysUserItem.userId}">编辑</f:a>
								<f:a alias="userInfo" css="link detail" href="get.xht?userId=${sysUserItem.userId}">明细</f:a>
								<f:a alias="setParams" css="link parameter" href="${ctx}/platform/system/sysUserParam/editByUserId.xht?userId=${sysUserItem.userId}" >参数属性</f:a>
								<f:a alias="resetPwd" css="link resetPwd" href="resetPwdView.xht?userId=${sysUserItem.userId}">重置密码</f:a>
								<f:a alias="setStatus" css="link setting" href="editStatusView.xht?userId=${sysUserItem.userId}">设置状态</f:a>
							</display:column>
						</display:table>
						<sz:paging tableId="sysUserItem"/>
					</div>
				</div>
			</div>
</body>
</html>


