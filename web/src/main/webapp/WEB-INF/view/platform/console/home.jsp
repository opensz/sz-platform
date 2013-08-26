<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>桌面栏目管理表明细</title>
	<%@include file="/commons/include/get.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
    <link href="${ctx}/js/desktop/inettuts.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/desktop/jquery-ui-personalized.min.js"></script>
    <link href="${ctx}/js/desktop/inettuts.js.css" rel="stylesheet" type="text/css" />    
    <script type="text/javascript" src="${ctx}/js/desktop/inettutshome.js"></script> 
    <script type="text/javascript"> 
    	var more = function(id, name, url){
    		top.addToTab({resId:id,resName:name,defaultUrl:url});
    	}
    </script>       
</head>
<body>
<div id="colshtml" >
	${f:unescapeHtml(html)}
</div>
</body>
</html>
