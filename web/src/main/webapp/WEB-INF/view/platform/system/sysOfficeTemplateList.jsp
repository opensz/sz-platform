
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>office模版管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">系统模版管理列表</span>
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
								<span class="label">主题:</span><input type="text" name="Q_subject_S"  class="inputText" />
								<span class="label">模版类型:</span>
								<select name="Q_templatetype_S">
									<option value="">请选择</option>
									<option value="1">普通模版</option>
									<option value="2">套红模版</option>
								</select>
							</div>
						</form>
					</div>
				
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="sysOfficeTemplateList" id="sysOfficeTemplateItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"   class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="id" value="${sysOfficeTemplateItem.id}">
								  	<input type="hidden" class="rtn" name="rtn" value="${sysOfficeTemplateItem.id},${ sysOfficeTemplateItem.subject},${sysOfficeTemplateItem.path}">
							</display:column>
							<display:column property="subject" title="主题" sortable="true" sortName="subject"></display:column>
							<display:column title="模版类型" sortable="true" sortName="templatetype">
								<c:choose>
									<c:when test="${sysOfficeTemplateItem.templatetype==1 }">普通模版</c:when>
									<c:otherwise>
										套红模版
									</c:otherwise>
								</c:choose>
							</display:column>
							<display:column property="creator" title="创建人" sortable="true" sortName="creator"></display:column>
							<display:column  title="创建时间" sortable="true" sortName="createtime">
								<fmt:formatDate value="${sysOfficeTemplateItem.createtime}" pattern="yyyy-MM-dd"/>
							</display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?id=${sysOfficeTemplateItem.id}" class="link del">删除</a>
								<a href="edit.xht?id=${sysOfficeTemplateItem.id}" class="link edit">编辑</a>
								<a href="get.xht?id=${sysOfficeTemplateItem.id}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="sysOfficeTemplateItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


