
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>表单验证规则管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">表单验证规则管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link add" href="edit.xht">添加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link update" id="btnUpd" action="edit.xht">编辑</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.xht">删除</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
												<span class="label">规则名:</span><input type="text" name="Q_name_S"  class="inputText" />
									</div>
							</form>
					</div>
					
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="bpmFormRuleList" id="bpmFormRuleItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${bpmFormRuleItem.id}">
							</display:column>
							<display:column property="name" title="规则名" style="text-align:left" ></display:column>
							<display:column property="rule" title="规则" style="text-align:left"></display:column>
							<display:column property="tipInfo" title="错误提示信息" style="text-align:left"></display:column>
							<display:column property="memo" title="描述" style="text-align:left"></display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?id=${bpmFormRuleItem.id}" class="link del">删除</a>
								<a href="edit.xht?id=${bpmFormRuleItem.id}" class="link edit">编辑</a>
								<a href="get.xht?id=${bpmFormRuleItem.id}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="bpmFormRuleItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


