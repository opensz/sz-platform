<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看业务数据</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowRightDialog.js"></script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">业务数据目录</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>							
							<div class="group"><a class="link del" action="del.xht">删除</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
						<form id="searchForm" method="post" action="list.xht">
							<div class="row">
								<span class="label">列表模板名:</span><input type="text" name="templateName" class="inputText" />											
							</div>
						</form>
					</div>
					
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="bpmTableTemplateList" id="bpmTableTemplateItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${bpmTableTemplateItem.id}">
							</display:column>
							<display:column property="templateName" title="列表模板名"></display:column>
							<display:column property="tableName" title="表名"></display:column>
							<display:column property="categoryName" title="表单类型 "></display:column>
							<display:column title="管理" media="html" style="width:250px;">								
								<a href="javascript:;" onclick="javascript:jQuery.openFullWindow('edit.xht?id=${bpmTableTemplateItem.id}&ifEdit=1');" class="link edit">编辑</a>
								<a href="del.xht?id=${bpmTableTemplateItem.id}" class="link del">删除</a>
								<a href="javascript:FlowTemplateDialog(${bpmTableTemplateItem.id},0)" class="link grant" title="授权">授权</a>								
							</display:column>
						</display:table>
						<sz:paging tableId="bpmTableTemplateItem"/>
					</div>
				</div>
			</div>
</body>
</html>


