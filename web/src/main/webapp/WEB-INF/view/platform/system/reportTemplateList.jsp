
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.sz.platform.system.model.GlobalType"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>报表模板管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>

	<div class="panel">
		<div class="panel-top">
			
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
						<span class="label">标题:</span><input type="text" name="Q_title_S"  class="inputText" />
					</div>
				</form>
			</div>
			<br/>
			<div class="panel-data">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="reportTemplateList" id="reportTemplateItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
					<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="reportId" value="${reportTemplateItem.reportId}">
					</display:column>
					<display:column property="title" title="标题" sortable="true" sortName="title"></display:column>
					<display:column property="reportLocation" title="报表模板文件路径" sortable="true" sortName="reportLocation"></display:column>
					<display:column  title="创建时间" sortable="true" sortName="createTime">
						<fmt:formatDate value="${reportTemplateItem.createTime}" pattern="yyyy-MM-dd"/>
					</display:column>
					<display:column  title="修改时间" sortable="true" sortName="updateTime">
						<fmt:formatDate value="${reportTemplateItem.updateTime}" pattern="yyyy-MM-dd"/>
					</display:column>
					<display:column title="是否缺省" sortable="true" sortName="isDefaultIn">
						<c:if test="${reportTemplateItem.isDefaultIn==1}">是</c:if>
						<c:if test="${reportTemplateItem.isDefaultIn==0}">否</c:if>
					</display:column>
					<display:column title="管理" media="html" style="width:180px">
						<a href="<%=request.getContextPath()%>${reportTemplateItem.reportSeverlet}${reportTemplateItem.fileName}" 
							class="link detail">预览</a>
						<!-- <a href="get.xht?reportId=${reportTemplateItem.reportId}" class="link-detail"><span class="link-btn">参数设置</span></a> -->
						<a href="edit.xht?reportId=${reportTemplateItem.reportId}" class="link edit">编辑</a>
						<a href="del.xht?reportId=${reportTemplateItem.reportId}" class="link del">删除</a>
					</display:column>
				</display:table>
				<sz:paging tableId="reportTemplateItem"/>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->

</body>
</html>


