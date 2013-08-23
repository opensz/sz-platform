<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>桌面个人栏目管理</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">桌面个人栏目管理列表</span>
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
										<span class="label">用户ID:</span><input type="text" name="Q_userId_S"  class="inputText" />
									
										<span class="label">布局ID:</span><input type="text" name="Q_layoutId_S"  class="inputText" />
									
										<span class="label">栏目ID:</span><input type="text" name="Q_columnId_S"  class="inputText" />
									
										<span class="label">列:</span><input type="text" name="Q_col_SN"  class="inputText" />
									
										<span class="label">序号:</span><input type="text" name="Q_sn_S"  class="inputText" />
							</div>
					</form>
			</div>
			<br/>
			<div class="panel-data">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="desktopMycolumnList" id="desktopMycolumnItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
					  	<input type="checkbox" class="pk" name="id" value="${desktopMycolumnItem.id}">
					</display:column>
					<display:column property="userId" title="用户ID" sortable="true" sortName="userId"></display:column>
					<display:column property="layoutId" title="布局ID" sortable="true" sortName="layoutId"></display:column>
					<display:column property="columnId" title="栏目ID" sortable="true" sortName="columnId"></display:column>
					<display:column property="col" title="列" sortable="true" sortName="col"></display:column>
					<display:column property="sn" title="序号" sortable="true" sortName="sn"></display:column>
					<display:column title="管理" media="html" style="width:180px">
						<a href="del.xht?id=${desktopMycolumnItem.id}" class="link del">删除</a>
						<a href="edit.xht?id=${desktopMycolumnItem.id}" class="link edit">编辑</a>
						<a href="get.xht?id=${desktopMycolumnItem.id}" class="link detail">明细</a>
					</display:column>
				</display:table>
				<sz:paging tableId="desktopMycolumnItem"/>
			</div>
		</div>			
	</div>
</body>
</html>