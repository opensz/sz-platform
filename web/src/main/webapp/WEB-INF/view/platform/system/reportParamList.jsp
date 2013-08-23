
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>报表参数管理</title>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">报表参数管理列表</span>
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
							<span class="label">所属报表:</span><input type="text" name="Q_REPORTID_S"  class="inputText" />
							<span class="label">参数名称:</span><input type="text" name="Q_PARAMNAME_S"  class="inputText" />
							<span class="label">参数Key:</span><input type="text" name="Q_PARAMKEY_S"  class="inputText" />
							<span class="label">缺省值:</span><input type="text" name="Q_DEFAULTVAL_S"  class="inputText" />
							<span class="label">类型:</span><input type="text" name="Q_PARAMTYPE_S"  class="inputText" />
							<span class="label">系列号:</span><input type="text" name="Q_SN_S"  class="inputText" />
							<span class="label">PARAMTYPESTR:</span><input type="text" name="Q_PARAMTYPESTR_S"  class="inputText" />
						</div>
				</form>
			</div>
			<br/>
			<div class="panel-data">
		    	<c:set var="checkAll"><input type="checkbox" id="chkall"/></c:set>
				<display:table name="reportParamList" id="reportParamItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
	 					<input type="checkbox" class="pk" name="PARAMID" value="${reportParamItem.PARAMID}">
					</display:column>
					<display:column property="REPORTID" title="所属报表" sortable="true" sortName="REPORTID"></display:column>
					<display:column property="PARAMNAME" title="参数名称" sortable="true" sortName="PARAMNAME"></display:column>
					<display:column property="PARAMKEY" title="参数Key" sortable="true" sortName="PARAMKEY"></display:column>
					<display:column property="DEFAULTVAL" title="缺省值" sortable="true" sortName="DEFAULTVAL"></display:column>
					<display:column property="PARAMTYPE" title="类型" sortable="true" sortName="PARAMTYPE"></display:column>
					<display:column property="SN" title="系列号" sortable="true" sortName="SN"></display:column>
					<display:column property="PARAMTYPESTR" title="PARAMTYPESTR" sortable="true" sortName="PARAMTYPESTR" maxLength="80"></display:column>
					<display:column title="管理" media="html" style="width:180px">
						<a href="del.xht?PARAMID=${reportParamItem.PARAMID}" class="link del">删除</a>
						<a href="edit.xht?PARAMID=${reportParamItem.PARAMID}" class="link edit">编辑</a>
						<a href="get.xht?PARAMID=${reportParamItem.PARAMID}" class="linkdetail">明细</a>
					</display:column>
				</display:table>
				<sz:paging tableId="reportParamItem"/>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


