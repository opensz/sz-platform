<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>查看表格业务数据的模板详细信息</title>
	<%@include file="/commons/include/getById.jsp" %>
	<script type="text/javascript" src="${ctx}/js/sz/displaytag.js" ></script>
	<script type="text/javascript">
		function goBack(){
			history.back();
		}
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">			
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back" onclick="goBack()" href="javascript:;">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				${html}				
		</div>
</div>
</body>
</html>
