
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>系统日历管理</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">系统日历管理列表</span>
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
						<span class="label">日历名称:</span><input type="text" name="Q_name_S"  class="inputText" />
					</div>
				</form>
			</div>
		
			<div class="panel-data">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="sysCalendarList" id="sysCalendarItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="id" value="${sysCalendarItem.id}">
					</display:column>
					<display:column property="name" title="日历名称" sortable="true" sortName="name"></display:column>
					<display:column title="默认" sortable="true" sortName="name" style="text-align:center;">
						<c:choose>
							<c:when test="${sysCalendarItem.isDefault==0 }">
								<font color="red"><b>非默认</b></font>
							</c:when>
							<c:otherwise>
								<font color="green"><b>默认</b></font>
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column property="memo" title="描述" sortable="true" sortName="memo" maxLength="80"></display:column>
					<display:column title="管理" media="html" style="width:220px">
						<a href="edit.xht?id=${sysCalendarItem.id}" class="link edit">编辑</a>
						<c:if test="${sysCalendarItem.isDefault==0}">
							<a href="setDefault.xht?id=${sysCalendarItem.id}" class="link detail">设置默认</a>
							<a href="del.xht?id=${sysCalendarItem.id}" class="link del">删除</a>
						</c:if>
						<!--<a href="get.xht?id=${sysCalendarItem.id}" class="link detail">明细</a>-->
					</display:column>
				</display:table>
				<sz:paging tableId="sysCalendarItem"/>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


