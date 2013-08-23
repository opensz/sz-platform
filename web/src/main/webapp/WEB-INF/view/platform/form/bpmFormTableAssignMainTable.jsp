<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>分配主表</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript">
$(function(){
	$("#dataFormSave").click(assignMainTable);
});

function assignMainTable(){
	var subTableId=${subTableId};
	var checkIndex=$("#mainTableId ").get(0).selectedIndex; 
	if(checkIndex==-1){
		$.ligerMessageBox.error('出错了',"没有选择主表!");
		return;
	}
	
	var mainTableId=$("#mainTableId").val();
	var params={subTableId:subTableId,mainTableId:mainTableId};
	$.post('linkSubtable.xht' ,params, function(data) {
		var obj=new org.sz.form.ResultMessage(data);
		if(obj.isSuccess()){//成功
			$.ligerMessageBox.success('提示信息',"关联主表成功!",function(){
				window.close();
			});
	    }else{//失败
	    	$.ligerMessageBox.error('出错了',obj.getMessage());
	    }
	});
}

</script>
</head>
<body>
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">
				分配主表
		    </span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="#" onclick="window.close()">返回</a></div>
			</div>
		</div>
	</div>
	<form action="linkSubtable.xht" method="post">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">所属主表: </th>
				<td>
					<select id="mainTableId" name="mainTableId">
						<c:forEach items="${mainTableList}" var="mainTable">
							<option value="${mainTable.tableId}">${mainTable.tableDesc}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
