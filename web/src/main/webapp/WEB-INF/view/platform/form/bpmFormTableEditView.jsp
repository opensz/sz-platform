<%--
	desc:edit the 脚本管理
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>选择视图</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor_view.js"></script>
	<script type="text/javascript">
	function showResponse(data){
		var obj=new org.sz.form.ResultMessage(data);
		$.ligerMessageBox.success('提示信息',obj.getMessage());
	}
	
	$(function(){
		editor=ckeditor('txtViewHtml');
		$('#frmView').ajaxForm({success:showResponse}); 
		$("#btnViewSave").click(function(){
			$("#frmView").submit();
		});
	});
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">生成视图文件</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<div class="group"><a class="link run" id="btnViewSave" href="#">生成</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link back" onclick="history.back()">返回</a></div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form action="saveView.xht" id="frmView">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					
						<tr>
							<th width="10%">模版: </th>
							<td >
								<textarea id="txtViewHtml" row="20" name="txtViewHtml" >${fn:escapeXml(html) }</textarea>	
								<input type="hidden" name="viewName" value="${viewName}"/>
									
							</td>
						</tr>
				</table>
			</form>
		</div>
</div>
</body>
</html>
