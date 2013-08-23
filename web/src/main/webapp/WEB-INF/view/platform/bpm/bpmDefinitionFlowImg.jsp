
<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/commons/include/form.jsp" %>
	<title>流程示意图</title>
</head>
<body >
	<div style="padding-top:40px;background-color: white;">
		<div style="position: relative;background:url('${ctx}/bpmImage?definitionId=${actDefId}') no-repeat;width:${shapeMeta.width}px;height:${shapeMeta.height}px;">
			 
		</div>
	</div>
</body>
</html>