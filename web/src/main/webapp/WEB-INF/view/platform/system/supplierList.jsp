<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="org.sz.platform.system.model.SysUser"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>供应商管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group">
								<f:a alias="searchUser" css="link search" id="btnSearch">查询</f:a>
							</div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<f:a alias="addUser" css="link add" href="supplierEdit.xht?action=add">添加</f:a>
							</div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<f:a alias="delUser" css="link del" action="supplierDel.xht">删除</f:a>
							</div>
							
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="supplierList.xht">
									<div class="row">
										<span class="label">名称:</span><input type="text" name="Q_orgName_S" value="${params['orgName']}"   class="inputText" style="width:9%"/>					
										
									</div>
							</form>
					</div>
					 
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="supplierList" id="model" requestURI="supplierList.xht" sort="external" cellpadding="1" cellspacing="1"  export="true"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;text-align:center;">
								  	<input type="checkbox" class="pk" name="orgId" value="${model.orgId}">
							</display:column>
							<display:column property="orgName" title="名称" sortable="true" sortName="orgName" style="text-align:left"></display:column>
							
							<display:column  title="分类" sortable="true" sortName="supplierType">
								<c:if test="${model.supplierType==1}">服务供应商</c:if>
								<c:if test="${model.supplierType==2}">设备供应商</c:if>
							</display:column>
							
							<display:column  title="分类" sortable="true" sortName="category">
								<c:if test="${model.category==1}">长期</c:if>
								<c:if test="${model.category==2}">临时</c:if>
							</display:column>
							
							<display:column  title="网址" sortable="true" sortName="webSite">
								${model.webSite}
							</display:column>
								
							<display:column title="管理" media="html" style="width:420px;text-align:center;">
								<f:a alias="delUser" css="link del" href="supplierDel.xht?orgId=${model.orgId}">删除</f:a>
								<f:a alias="updateUserInfo" css="link edit" href="supplierEdit.xht?orgId=${model.orgId}">编辑</f:a>	
							</display:column>
						</display:table>
						<sz:paging tableId="supplier"/>
					</div>
				</div>
			</div>
</body>
</html>


