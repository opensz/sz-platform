
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务催办执行情况管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">任务催办执行情况管理列表</span>
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
												<span class="label">流程定义ID:</span><input type="text" name="Q_actDefId_S"  class="inputText" />
											
												<span class="label">任务ID:</span><input type="text" name="Q_taskId_S"  class="inputText" />
											
												<span class="label">催办时间 从:</span> <input  name="Q_beginreminderTime_S"  class="inputText date" />
												<span class="label">至: </span><input  name="Q_endreminderTime_S" class="inputText date" />
											
									</div>
							</form>
					</div>
					<br/>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="reminderStateList" id="reminderStateItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${reminderStateItem.id}">
							</display:column>
							<display:column property="actDefId" title="流程定义ID" sortable="true" sortName="actDefId"></display:column>
							<display:column property="taskId" title="任务ID" sortable="true" sortName="taskId"></display:column>
							<display:column  title="催办时间" sortable="true" sortName="reminderTime">
								<fmt:formatDate value="${reminderStateItem.reminderTime}" pattern="yyyy-MM-dd"/>
							</display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?id=${reminderStateItem.id}" class="link del">删除</a>
								<a href="edit.xht?id=${reminderStateItem.id}" class="link edit">编辑</a>
								<a href="get.xht?id=${reminderStateItem.id}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="reminderStateItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


