
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择模板</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
<script type="text/javascript">

$(function(){
	$("#dataFormSave").click(function(){
		openEdit();
	});		
});

function openEdit(){
	var templateObj=$("#templatesId");
	var tableObj=$("#templateTableId");
	var aryTemplateId=new Array();
	var aryTableId=new Array();
	$("select[templateId='templateId']").each(function(i){
		aryTableId.push($(this).attr("tableid"));
		aryTemplateId.push($(this).val());
	});
	tableObj.val(aryTableId.join(","));
	templateObj.val(aryTemplateId.join(","));
	
	var params=$("#frmDefInfo").serialize();
	var url='edit.xht?' + params;
	jQuery.openFullWindow(url);
	
	parent.closeWin();
};
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">选择表单模版</span>
			</div>
			<c:if test="${isSimple==0}">
				<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link run" id="dataFormSave" href="#">下一步</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back"  onclick="history.back(-1)">返回</a></div>
				</div>
			</div>
			</c:if>
			
		</div>
		<div class="panel-body">
			<form id="frmDefInfo">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					
					<c:if test="${mainTable != null}">
					<tr>
						<th width="30%">主表模板:</th>
						<td>
							<select id="main" templateId="templateId" tableId="${mainTable.tableId }">
								<c:forEach items="${mainTableTemplates}" var="bpmFormTemplate">
									<option value="${bpmFormTemplate.templateId}">${bpmFormTemplate.templateName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					</c:if>
					<c:forEach items="${subTables}" var="subTable">
					<tr>
						<th width="30%">${subTable.tableDesc }模板:</th>
						<td>
							<select templateId="templateId" tableId="${subTable.tableId }">
								<c:forEach items="${subTableTemplates}" var="bpmFormTemplate">
									<option value="${bpmFormTemplate.templateId}">${bpmFormTemplate.templateName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>					
					</c:forEach>					
				</table>
				<input type="hidden" name="templatesId" id="templatesId" />
				<input type="hidden" name="templateTableId" id="templateTableId" />
				<input type="hidden" name="categoryId" value="${categoryId}"/>
				<input type="hidden" name="tableId" value="${tableId}"/>
				<input type="hidden" name="subject" value="${subject}"/>
				<input type="hidden" name="formDesc" value="${formDesc}"/>
			</form>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


