
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>新建表单</title>
<%@include file="/commons/include/form.jsp" %>
<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/htCatCombo.js"></script>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bpmFormDef"></script>
<script type="text/javascript" src="${ctx }/js/sz/platform/form/FormTableDialog.js"></script>

<script type="text/javascript">
	window.name="frmEdit";

	$(function(){
		function showRequest(formData, jqForm, options) { 
			return true;
		} 
		valid(showRequest,showResponse);
		
		$("#dataFormSave").click(function(){
			var rtn=$("#bpmFormDefForm").valid();
			if(rtn){
				$('#bpmFormDefForm')[0].submit(); 
			}
		});
	});
	
	function selectTable(){
		var callBack=function(tableId,tableName){
		
			$("#tableId").val(tableId);
			$("#tableName").val(tableName);
		}
		FormTableDialog(callBack);
	}
	function resetTable(){
		$("#tableId").val('');
		$("#tableName").val('');
	}
</script>
<style type="text/css">
	body{overflow:hidden;}
</style>
</head>
<body >
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">新建表单</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link run" id="dataFormSave" href="#">下一步</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back"  href="javascript:history.back()">返回</a></div>
				</div>
			</div>
		</div>
		
		<div class="panel-body">
			<form  id="bpmFormDefForm" method="post"  action="selectTemplate.xht" target="frmEdit">
				 <table cellpadding="1" cellspacing="1" class="table-detail">
					<tr>
						<th width="150">表单标题:</th>
						<td><input id="subject" type="text" name="subject" value="" class="inputText" size="30" /></td>
					</tr>
					<tr>
						<th width="150">表单类型:</th>
						<td>
							<input class="catComBo" catKey="FORM_TYPE" valueField="categoryId" catValue="${categoryId}" name="typeName" height="200" width="200"/>
						</td>
					</tr>
					<tr>
						<th width="150">表单描述:</th>
						<td>
							<textarea rows="3" cols="35" id="formDesc" name="formDesc" class="textarea"></textarea>
						</td>
					</tr>
					<tr>
						<th width="150">表:</th>
						<td style="padding-top: 5px;">
							
							<input type="text" id="tableName" name="tableName" value="" readonly="readonly">
							<input type="hidden" id="tableId" name="tableId" value="">
							<a href='#' class='button'  onclick="selectTable()" ><span>...</span></a>
							<a href='#' class='button' style='margin-left:10px;' onclick="resetTable()"><span>重选</span></a>
						</td>
					</tr>
				</table>
			</form>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


