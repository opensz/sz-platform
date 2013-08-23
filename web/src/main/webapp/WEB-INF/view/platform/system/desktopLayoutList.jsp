<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>桌面布局管理</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">桌面布局管理列表</span>
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
												<span class="label">布局名:</span><input type="text" name="Q_name_S"  class="inputText" />
									</div>
							</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
						<display:table name="desktopLayoutList" id="desktopLayoutItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
						<display:column title="${checkAll}" media="html" style="width:30px;">
							<input type="checkbox" class="pk" name="id" value="${desktopLayoutItem.id}">
						</display:column>
						<display:column property="name" title="布局名" sortable="true" sortName="name"></display:column>
						<display:column property="cols" title="列数" sortable="true" sortName="cols"></display:column>
						<display:column property="width" title="宽度" sortable="true" sortName="width"></display:column>
						<display:column property="memo" title="备注" sortable="true" sortName="memo"></display:column>
						<display:column  title="默认布局" sortable="true" sortName="isDefault">
							<c:if test="${desktopLayoutItem.isDefault==1 }">是</c:if>
							<c:if test="${desktopLayoutItem.isDefault==0 }">否</c:if>
						</display:column>
						<display:column title="管理" media="html" style="width:300px">
							<c:choose>
								<c:when test="${desktopLayoutItem.isDefault==1}">
									<a class="link setting disabled">设置默认</a>
								</c:when>
								<c:otherwise>
								<a class="link setting" href="setDefault.xht?id=${desktopLayoutItem.id}">设置默认</a>
								</c:otherwise>
							</c:choose>
							<a href="del.xht?id=${desktopLayoutItem.id}" class="link del">删除</a>
							<a href="edit.xht?id=${desktopLayoutItem.id}" class="link edit">编辑</a>
							<a href="${ctx}/platform/system/desktopLayoutcol/show.xht?id=${desktopLayoutItem.id}" class="link undo">设置栏目</a>							
						</display:column>
						</display:table>
						<sz:paging tableId="desktopLayoutItem"/>
					</div>
				</div>			
			</div>
</body>
</html>


