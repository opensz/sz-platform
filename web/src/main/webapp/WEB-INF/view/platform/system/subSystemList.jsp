
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>子系统管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">子系统管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.xht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.xht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.xht">删除</a></div>
				</div>	
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.xht">
					<div class="row">
						<span class="label">名称:</span><input type="text" name="Q_sysName_S"  class="inputText" style="width:10%;"/>
						<span class="label">别名:</span><input type="text" name="Q_alias_S"  class="inputText" style="width:10%;"/>
						<span class="label">创建时间:</span><input  name="Q_begincreatetime_DL"  class="inputText date" style="width:10%;"/>
						<span class="label">至</span><input  name="Q_endcreatetime_DG" class="inputText date" style="width:10%;"/>
						<span class="label">允许删除:</span>
						<select name="Q_allowDel_SN" class="select" style="width:8%;">
							<option value="">全部</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
						<span class="label">是否激活:</span>
						<select name="Q_isActive_SN" class="select" style="width:8%;">
							<option value="">全部</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</form>
			</div>
	
			<div class="panel-data">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="subSystemList" id="subSystemItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${subSystemItem.systemId}">
					</display:column>
					<display:column property="sysName" title="名称" sortable="true" sortName="sysName" ></display:column>
					<display:column property="alias" title="别名" sortable="true" sortName="alias" ></display:column>
					
					<display:column title="系统图标" sortName="logo">
						<img alt="" src="${ctx }/${subSystemItem.logo}" >
					</display:column>
					
					
					<display:column property="defaultUrl" title="首页地址" sortable="true" sortName="defaultUrl" ></display:column>
					<display:column  title="创建时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${subSystemItem.createtime}" pattern="yyyy-MM-dd"/>
					</display:column>
					<display:column title="允许删除" sortable="true" sortName="allowDel">
						<c:choose>
							<c:when test="${subSystemItem.allowDel eq 1}">是</c:when>
							<c:when test="${subSystemItem.allowDel eq 0}">否</c:when>
							<c:otherwise>未设定</c:otherwise>
						</c:choose>
					</display:column>
					<display:column title="是否激活" sortable="true" sortName="isActive">
						<c:choose>
							<c:when test="${subSystemItem.isActive eq 1}">是</c:when>
							<c:when test="${subSystemItem.isActive eq 0}">否</c:when>
							<c:otherwise>未设定</c:otherwise>
						</c:choose>
					</display:column>
					<display:column title="本地系统" sortable="true" sortName="isLocal">
						<c:choose>
							<c:when test="${subSystemItem.isLocal eq 1}">是</c:when>
							<c:when test="${subSystemItem.isLocal eq 0}">否</c:when>
							<c:otherwise>是</c:otherwise>
						</c:choose>
					</display:column>
					<display:column title="管理" media="html" style="width:120px;">
						<c:choose>
							<c:when test="${subSystemItem.allowDel==1 }">
								<a href="del.xht?id=${subSystemItem.systemId}" class="link del">删除</a>
							</c:when>
							<c:otherwise>
								<a href="#" class="link del disabled">删除</a>
							</c:otherwise>
						</c:choose>
						
						<a href="edit.xht?id=${subSystemItem.systemId}" class="link edit">编辑</a>
					</display:column>
				</display:table>
				<sz:paging tableId="subSystemItem"/>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


