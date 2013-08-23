
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>流程定义导入</title>
<%@include file="/commons/include/form.jsp" %>
<link href="${ctx }/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
	window.name="win";
			
	$(function(){
		$("#btnSave").click(function(){
			$("#importForm").submit();
		});			
	});
	
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程定义导入</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="btnSave">导入</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del" onclick="javasrcipt:window.close()">关闭</a></div>
				
				</div>	
			</div>
	</div>
	<div class="panel-body">
		<div class="panel-search">
			<form id="importForm" name="importForm" method="post" target="win" action="importXml.xht" enctype="multipart/form-data">
				<div class="row">
				 <table id="tableid" class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th width="22%">选择文件：</th>
						<td width="78%"><input type="file" size="40" name="xmlFile" id="xmlFile"/></td>						
					</tr>
				</table>				
				</div>
		    </form>
		</div>    		
	</div><!-- end of panel-body -->				
</div> 
</body>
</html>