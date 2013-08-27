
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>可访问地址管理</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">可访问地址管理列表</span>
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
												<span class="label">标题:</span><input type="text" name="Q_title_S"  class="inputText" />
											
												
												<span class="label">类型:</span>
												
												
												<select  name="Q_ipType_SN" >
													<option value="">--请选择---</option>
													<option value="0">白名单</option>
													<option value="1">黑名单</option>
												</select>
											
												
									</div>
							</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="sysAcceptIpList" id="sysAcceptIpItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="acceptId" value="${sysAcceptIpItem.acceptId}">
							</display:column>
							<display:column property="title" title="标题" sortable="true" sortName="title"></display:column>
							<display:column property="startIp" title="开始地址" sortable="true" sortName="startIp"></display:column>
							<display:column property="endIp" title="结束地址" sortable="true" sortName="endIp"></display:column>
							<display:column media="html" title="类型" sortable="true" sortName="ipType">
								<c:if test="${sysAcceptIpItem.ipType==0}">白名单</c:if>
								<c:if test="${sysAcceptIpItem.ipType==1}">黑名单</c:if>
							</display:column>
							<display:column title="管理" media="html" style="width:180px">
								<a href="del.xht?acceptId=${sysAcceptIpItem.acceptId}" class="link del">删除</a>
								<a href="edit.xht?acceptId=${sysAcceptIpItem.acceptId}" class="link edit">编辑</a>
								<a href="get.xht?acceptId=${sysAcceptIpItem.acceptId}" class="link detail">明细</a>
							</display:column>
						</display:table>
						<sz:paging tableId="sysAcceptIpItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


