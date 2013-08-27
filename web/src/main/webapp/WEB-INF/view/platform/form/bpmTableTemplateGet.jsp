<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>查看表格业务数据的模板详细信息</title>
	<%@include file="/commons/include/getById.jsp" %>
	<%@include file="/commons/include/get.jsp" %>	
	<script type="text/javascript">		
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">${titleStr}</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>				
						<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="myList.xht">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				${f:unescapeXml(html)}
				${f:unescapeXml(pageHtml)}	
				<br />		
		</div>
</div>
</body>
</html>
