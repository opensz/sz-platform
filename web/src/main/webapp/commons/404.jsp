<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<html>
<head>
	<title>访问的页面不存在</title>
	<%@include file="/commons/include/get.jsp" %>
	
</head> 
<body>

<table border="0" cellspacing="0" cellpadding="0" class="listHeader" style="width:600px;margin-top: 50px"  align="center">
	<tr>
		<td class="title">访问的页面不存在</td>
		<td>
		
		</td>
	</tr>
</table>

<table border="0" cellspacing="0" cellpadding="0" class="listTable" style="width:600px;height: 150px" align="center">
	<tr>
		<td>
			<table width="100%" height="100%">
				<tr>
					<td width="100px"><img alt="" src="${ctx }/commons/image/error.gif"></td>
					<td>
					对不起，你访问该页面不存在!</td>
				</tr>
			</table>

		</td>
	</tr>
</table>
</body>
</html>

