<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.sz.platform.system.model.SysParam"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>系统参数管理</title>
<%@include file="/commons/include/get.jsp"%>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">系统参数管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link search" id="btnSearch">查询</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link add" href="edit.xht">添加</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link update" id="btnUpd" action="edit.xht">修改</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link del" action="del.xht">删除</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.xht">
					<div class="row">

						<span class="label">参数名:</span><input type="text"
							name="Q_paramName_S" class="inputText" /> <span class="label">参数类型:</span>
						<select name="Q_effect_SN">
							<option value="">--请选择--</option>
							<option value="1">用户参数</option>
							<option value="2">组织参数</option>
						</select> <span class="label">数据类型:</span> <select name="Q_dataType_S">
							<option value="">--请选择--</option>
							<c:forEach items="${dataTypeMap}" var="t">
								<option value="${t.key}">${t.value }</option>
							</c:forEach>
						</select>

					</div>
				</form>
			</div>
			<div class="panel-data">
				<display:table name="sysParamList" id="sysParamItem"
					requestURI="list.xht" sort="external" cellpadding="1"
					cellspacing="1" export="false" class="table-grid">
					<display:column title="${checkAll}" media="html"
						style="width:30px;">
						<input type="checkbox" class="pk" name="paramId"
							value="${sysParamItem.paramId}">
					</display:column>
					<display:column property="paramName" title="参数名" sortable="true"
						sortName="paramName"></display:column>
					<display:column property="paramKey" title="参数Key" sortable="true"
						sortName="paramKey"></display:column>


					<display:column media="html" title="参数类型" sortable="true"
						sortName="dataType">
						<c:if test="${sysParamItem.effect==1}">用户参数</c:if>
						<c:if test="${sysParamItem.effect==2}">组织参数</c:if>
					</display:column>

					<display:column media="html" title="数据类型" sortable="true"
						sortName="dataType">
						<c:forEach items="${dataTypeMap}" var="t">
							<c:if test="${t.key==sysParamItem.dataType}">${t.value}</c:if>
						</c:forEach>
					</display:column>
					<display:column title="管理" media="html" style="width:180px">
						<a href="del.xht?paramId=${sysParamItem.paramId}" class="link del">删除</a>
						<a href="edit.xht?paramId=${sysParamItem.paramId}"
							class="link edit">编辑</a>
						<a href="get.xht?paramId=${sysParamItem.paramId}"
							class="link detail">明细</a>
					</display:column>
				</display:table>
				<sz:paging tableId="sysParamItem" />
			</div>
		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


