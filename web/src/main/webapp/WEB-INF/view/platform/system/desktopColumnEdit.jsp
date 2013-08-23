<%--
	time:2012-03-20 16:39:01
	desc:edit the 桌面栏目
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 桌面栏目</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=desktopColumn"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#desktopColumnForm').submit(); 
			});
		});
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
			    <c:choose>
			        <c:when test="${desktopColumn.id !=null }">
			            <span class="tbar-label">编辑桌面栏目</span>
			        </c:when>
			        <c:otherwise>
			            <span class="tbar-label">添加桌面栏目</span>
			        </c:otherwise>
			    </c:choose>
				
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.xht">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<form id="desktopColumnForm" method="post" action="save.xht">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">栏目名称: </th>
							<td>
							<input type="text" id="name" name="name" style="width: 20%;" value="${desktopColumn.name}"  class="inputText" style="width: 80%;"/>
							</td>
						</tr>
						<tr>
							<th width="20%">方法路径: </th>
							<td><input type="text" id="serviceMethod" name="serviceMethod" style="width: 40%;" value="${desktopColumn.serviceMethod}"  class="inputText" style="width: 80%;"/></td>
						</tr>
						<tr>
							<th width="20%">更多路径: </th>
							<td><input type="text" id="columnUrl" name="columnUrl" style="width: 40%;" value="${desktopColumn.columnUrl}"  class="inputText" style="width: 80%;"/>(注：为空则不在工作台页面显示"more"图标)</td>
						</tr>
						<tr>
							<th width="20%">模板html: </th>
							<td><textarea id="html" name="html" cols=100 rows=20>${fn:escapeXml(desktopColumn.html)}</textarea></td>
						</tr>
					</table>
					<input type="hidden" name="id" value="${desktopColumn.id}" />
				</form>
		</div>
</div>
</body>
</html>
