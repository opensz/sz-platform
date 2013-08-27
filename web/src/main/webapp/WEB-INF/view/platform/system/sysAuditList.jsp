
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>系统日志管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
	
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">系统日志管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.xht">删除</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
												<span class="label">操作名称:</span><input type="text" name="Q_opName_S"  class="inputText" />
												<span class="label">执行时间 从:</span> <input  name="Q_beginexeTime_DL"  class="inputText date" />
												<span class="label">至: </span><input  name="Q_endexeTime_DG" class="inputText date" />
												<span class="label">执行人:</span><input type="text" name="Q_executor_S"  class="inputText" />
									</div>
							</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="sysAuditList" id="sysAuditItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="auditId" value="${sysAuditItem.auditId}">
							</display:column>
							<display:column property="opName" title="操作名称" sortable="true" sortName="opName"></display:column>
							<display:column title="执行时间" sortable="true" sortName="exeTime">
								<fmt:formatDate value="${sysAuditItem.exeTime}" pattern="yyyy-MM-dd HH:mm"/>
							</display:column>
						
							<display:column property="executor" title="执行人" sortable="true" sortName="executor"></display:column>
							<display:column property="fromIp" title="IP" sortable="true" sortName="fromIp"></display:column>
							<display:column property="exeMethod" title="执行方法" sortable="true" sortName="exeMethod"></display:column>
							<display:column property="requestURI" title="请求URL" sortable="true" sortName="requestURI"></display:column>
							<display:column title="管理" media="html" style="width:100px">
								<a href="del.xht?auditId=${sysAuditItem.auditId}" class="link del">删除</a>
								<a href="get.xht?auditId=${sysAuditItem.auditId}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="sysAuditItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


