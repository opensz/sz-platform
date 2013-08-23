<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>维度信息管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">维度信息管理列表
						</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link add"  href="edit.xht?demId=0">添加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.xht">删除</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
												<span class="label">维度名称:</span><input type="text" name="Q_demName_S"  class="inputText" />
											
												<span class="label">维度描述:</span><input type="text" name="Q_demDesc_S"  class="inputText" />
									</div>
							</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="demensionList" id="demensionItem" requestURI="list.xht" 
					    	sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;text-align:center;">
								  	<input type="checkbox" class="pk" name="demId" value="${demensionItem.demId}" ${demensionItem.demName=="行政维度"?"disabled='disabled'":""}>
							</display:column>
							<display:column property="demName" title="维度名称" sortable="true" sortName="demName" style="text-align:left"></display:column>
							<display:column property="demDesc" title="维度描述" sortable="true" sortName="demDesc" style="text-align:left"></display:column>
							<display:column title="管理" media="html" style="width:120px;">
							
							<c:choose>
							<c:when test='${demensionItem.demName eq "行政维度"}'>
							</c:when>
							<c:otherwise>
							<a href='del.xht?demId=${demensionItem.demId}'  class='link del'>删除</a>
							<a href='edit.xht?demId=${demensionItem.demId}' class="link edit">编辑</a>
							</c:otherwise>
							</c:choose>
								
							</display:column>
						</display:table>
						<sz:paging tableId="demensionItem"/>
					</div>
				</div>			
			</div>
</body>
</html>


