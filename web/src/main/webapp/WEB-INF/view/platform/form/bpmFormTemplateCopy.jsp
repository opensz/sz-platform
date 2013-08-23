<%--
	time:2011-11-28 11:31:14
	desc:edit the 系统表单模板
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>复制模板</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript">
		var obj=window.dialogArguments;
		$(function(){
			$("#templateName").val(obj.templateName);
			$("#alias").val(obj.alias);
		});
		function save(){
			var templateId=obj.templateId;
			var newTemplateName=$("#newTemplateName").val();
			var oldTemplateName=$("#templateName").val();
			var alias=$("#alias").val();
			var newAlias=$("#newAlias").val();
			
			var url="copyTemplate.xht";
			var para="templateId="+templateId+"&newTemplateName="+newTemplateName+"&newAlias="+newAlias;
			
			if(newTemplateName==""||newAlias==""){
				window.close();
			}else{
				if(newTemplateName==oldTemplateName){
					$.ligerMessageBox.error('提示信息','模板名不能同名！');
			    }else{
				    $.post(url,para,function(data){
				    	var obj=new org.sz.form.ResultMessage(data);
			    		if(obj.isSuccess()){
					    $.ligerMessageBox.success("提示信息",obj.getMessage(),function(){
						    window.close();
			    		});
			    		}else{
			    		$.ligerMessageBox.error("提示信息",obj.getMessage(),function(){
			    		});
			    		}
				    });
			}
		  }
		}
	</script>
	<style>
		html { overflow-x: hidden; }
	</style>
</head>
<body>
<div class="panel">
  <div class="panel-top">
    <div class="tbar-title">
	    <span class="tbar-label">复制模板</span>
	</div>
	<div class="panel-toolbar">
			<div class="toolBar">
			<div class="group"><a class="link save" id="btnSearch" onclick="save()">保存</a></div>
			<div class="l-bar-separator"></div>
			<div class="group"><a class="link del" onclick="javasrcipt:window.close()">关闭</a></div>
	</div>	
	</div>
</div>
	<div class="panel-body">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="30%">原模板名称: </th>
							<td><input type="text" id="templateName" name="templateName" class="inputText" disabled="disabled"/></td>
						</tr>
						<tr>
							<th width="30%">新模板名称: </th>
							<td><input type="text" id="newTemplateName" name="newTemplateName"class="inputText"/></td>
						</tr>
						<tr>
							<th width="30%">原模板别名: </th>
							<td><input type="text" id="alias" name="alias" class="inputText" disabled="disabled"/></td>
						</tr>
						<tr>
							<th width="30%">新模板别名: </th>
							<td><input type="text" id="newAlias" name="newAlias"class="inputText"/></td>
						</tr>
					</table>
	</div>
</div>
</body>
</html>
