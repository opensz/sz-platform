
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>流水号生成管理</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>		
	<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">流水号生成管理列表</span>
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
										<span class="label">名称:</span><input type="text" name="Q_name_S"  class="inputText" />
										<span class="label">别名:</span><input type="text" name="Q_alias_S"  class="inputText" />
								</div>
						</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="indetityList" id="indetityItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${indetityItem.id}">
							</display:column>
							<display:column property="name" title="名称" sortable="true" sortName="name"></display:column>
							<display:column property="alias" title="别名" sortable="true" sortName="alias"></display:column>
							<display:column property="rule" title="规则" sortable="true" sortName="rule"></display:column>
							<display:column  title="每天生成" style="text-align:center" >
								<c:choose>
									<c:when test="{indetityItem.genEveryDay==1}">
										是
									</c:when>
									<c:otherwise>
										否
									</c:otherwise>
								</c:choose>
							</display:column>
							<display:column property="noLength" title="流水号长度" sortable="true" sortName="noLength"></display:column>
							<display:column property="initValue" title="初始值" sortable="true" sortName="initValue"></display:column>
						
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?id=${indetityItem.id}" class="link del">删除</a>
								<a href="edit.xht?id=${indetityItem.id}" class="link edit">编辑</a>
								<a href="get.xht?id=${indetityItem.id}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="indetityItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


