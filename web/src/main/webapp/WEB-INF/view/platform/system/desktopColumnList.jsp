<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>桌面栏目管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
function previewTemplate(url){
	var winArgs="dialogWidth=450px;dialogHeight=250px;help=0;status=0;scroll=0;center=1" ;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,null,winArgs);
	
}
</script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">桌面栏目管理列表</span>
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
							<div class="l-bar-separator"></div>
							<div class="group"><a  class="link init" id="bntInit" action="init.xht">初始化模板</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
												<span class="label">栏目名称:</span><input type="text" name="Q_name_S"  class="inputText" />
											    <span class="label">模板名称:</span><input type="text" name="Q_templateName_S"  class="inputText" />

									</div>
							</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="desktopColumnList" id="desktopColumnItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
							<input type="checkbox" class="pk" name="id" value="${desktopColumnItem.id}">
							</display:column>
							<display:column property="name" title="栏目名称" sortable="true" sortName="name"></display:column>
							<display:column property="serviceMethod" title="数据方法名" sortable="true" sortName="serviceMethod"></display:column>
							<display:column property="columnUrl" title="更多路径" sortable="true" sortName="columnUrl"></display:column>
							<display:column title="管理" media="html" style="width:300px">
								<a href="del.xht?id=${desktopColumnItem.id}" class="link del">删除</a>
								<a href="edit.xht?id=${desktopColumnItem.id}" class="link edit">编辑</a>
								<a href="get.xht?id=${desktopColumnItem.id}" class="link detail">明细</a>
								<a href="#" onclick="previewTemplate('getTemp.xht?id=${desktopColumnItem.id}');" class="link preview">浏览模板</a>
							</display:column>
						</display:table>
						<sz:paging tableId="desktopColumnItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


