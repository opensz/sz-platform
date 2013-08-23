<%--
	time:2012-05-22 14:58:08
	desc:edit the 查看表格业务数据的模板
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>生成数据模板</title>
	<%@include file="/commons/include/form.jsp" %>
	<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css"/>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerComboBox.js"></script>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bpmTableTemplate"></script>
	<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/htCatCombo.js"></script>	
	<script type="text/javascript">	    
		$(function() {
			function closeWindow(){
				
			};
			function showRequest(formData, jqForm, options) {				
				return true;
			};			
			function showResponse(responseText, statusText)  { 
				var obj=new org.sz.form.ResultMessage(responseText);
				if(obj.isSuccess()){//成功
					$.ligerMessageBox.success('提示信息',obj.getMessage());					
			    }else{//失败
			    	$.ligerMessageBox.error('出错了',obj.getMessage());
			    }
			};
			valid(showRequest,showResponse);
			$("a.save").click(function(){				
				$('#bpmTableTemplateForm').submit();				
			});
		});
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">生成数据模板</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:;">保存</a></div>					
				</div>
			</div>
		</div>
		<div class="panel-body">
				<form id="bpmTableTemplateForm" method="post" action="save.xht">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">数据模板名称: </th>
							<td><input type="text" id="templateName" name="templateName" value="${bpmTableTemplate.templateName}"  class="inputText"/></td>
						</tr>						
						<tr>
							<th width="20%">表单分类 : </th>
							<td>
								<input class="catComBo" catKey="FORM_TYPE" valueField="categoryId" catValue="${bpmTableTemplate.categoryId}" name="typeName" height="200" width="200"/>
							</td>
						</tr>
						<tr>
							<th width="20%">业务数据授权类型: </th>
							<td>							
								<select name="authorType" >
									<option value="1" <c:if test="${bpmTableTemplate.authorType==1 }">selected</c:if>>查看所有的业务数据</option>
									<option value="2" <c:if test="${bpmTableTemplate.authorType==2 }">selected</c:if>>查看本人的业务数据</option>
									<option value="4" <c:if test="${bpmTableTemplate.authorType==4 }">selected</c:if>>查看本组织的业务数据</option>
								</select>
							</td>
						</tr>
						<c:choose>
							<c:when test="${ifEdit==1}">
							<tr>
								<th width="20%">列表模板：</th>
									<td>
										<textarea id="htmlList" name="htmlList" cols=100 rows=20>${fn:escapeXml(bpmTableTemplate.htmlList)}</textarea>
									</td>
							</tr>
							<tr>
								<th width="20%">明细模板：</th>
									<td>
										<textarea id="htmlDetail" name="htmlDetail" cols=100 rows=20>${fn:escapeXml(bpmTableTemplate.htmlDetail)}</textarea>
									</td>
							</tr>
							</c:when>								
							<c:otherwise>
							<tr>
								<th width="20%">明细模板: </th>
								<td>
									<select name="htmlDetail">
										<c:forEach items="${detailTemplate}" var="bpmFormTemplate">
											<option value="${bpmFormTemplate.templateId}">${bpmFormTemplate.templateName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th width="20%">列表模板: </th>
								<td>
									<select name="htmlList">
										<c:forEach items="${listTemplate}" var="bpmFormTemplate">
											<option value="${bpmFormTemplate.templateId}">${bpmFormTemplate.templateName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<input type="hidden" name="tableId" value="${bpmTableTemplate.tableId}" />
					<input type="hidden" name="id" value="${bpmTableTemplate.id}" />
				</form>
		</div>
</div>
</body>
</html>
