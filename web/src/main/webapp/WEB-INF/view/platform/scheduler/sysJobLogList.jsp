
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>任务执行日志管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">任务执行日志管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="delJobLog.xht">删除</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link back" href="javascript:history.back();">返回</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="getLogList.xht?jobName=${jobName }&trigName=${trigName}">
									<div class="row">
									            <span class="label">任务名称:</span><input type="text" name="Q_jobName_S"  class="inputText" />
									            <span class="label">计划名称:</span><input type="text" name="Q_trigName_S"  class="inputText" />
												<span class="label">时间从:</span> <input type="text" name="Q_startTime_DL"  class="inputText date" />
												<span class="label">至: </span><input type="text" name="Q_endTime_DG" class="inputText date" />
									</div>
							</form>
					</div>
					<br/>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="sysJobLogList" id="sysJobLogItem" requestURI="getLogList.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="logId"  value="${sysJobLogItem.logId}">
							</display:column>
							<display:column property="jobName" title="任务名称" style="text-align:left" ></display:column>
							<display:column property="trigName" title="计划名称" style="text-align:left" ></display:column>
							<display:column  title="开始时间" sortable="true" sortName="startTime">
							<fmt:formatDate value="${sysJobLogItem.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</display:column>							
							<display:column  title="结束时间" sortable="true" sortName="endTime">
							<fmt:formatDate value="${sysJobLogItem.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</display:column>
							<display:column property="content" title="结果"  maxLength="80"></display:column>
							<display:column  title="状态">
							<c:if test="${sysJobLogItem.state eq 1 }"><font color="green">成功</font></c:if>
							<c:if test="${sysJobLogItem.state eq 0 }"><font color="red">失败</font></c:if>
							</display:column>
							<display:column property="runTime" title="执行时间(秒)" sortable="true" sortName="runTime" style="width:100px"></display:column>
							<display:column title="管理" media="html" style="width:80px">
							<a href="delJobLog.xht?logId=${sysJobLogItem.logId }" class="link del">删除</a>
							</display:column>
						</display:table>
						<sz:paging tableId="sysJobLogItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


