<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>添加表</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/ColumnDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/EditTable.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/FieldsManage.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
	input.error {
	    border: 1px solid red;
	}
</style>
<script type="text/javascript">
	var tableId=${tableId};
	var validator;
	var fieldObj;
	var canEditTbColName=${canEditTbColName};
	
	var table=null;
	
	$(function(){
		fieldObj=new FieldsManage();
		
		TableRow.setFieldManage(fieldObj);
		
		TableRow.setAllowEditColName(canEditTbColName);
		
		if(tableId>0){
			$.get("getByTableId.xht",{tableId:tableId},function(data){
			
				bindTable(data,canEditTbColName);
				table=data.bpmFormTable;
			});
		}
		
		//行高亮
		$("#tableColumnItem").delegate("tbody>tr", "click", function() {
			$("#tableColumnItem>tbody>tr").removeClass("over");
			$(this).addClass("over");
		});
		//编辑列的详细信息
		$("#tableColumnItem").delegate("tbody>tr a[name='editColumn']", "click", function() {
			var trObj=$(this).parent().parent();
			var idx=$("#tableColumnItem>tbody>tr").index(trObj);
			var field=fieldObj.getFieldByIndex(idx);
			TableRow.editField(field.fieldName);
		});
		//处理选项选择。
		$("#tableColumnItem").delegate("tbody>tr input:checkbox", "click", function() {
			var chkObj=$(this);
			TableRow.editFieldOption(chkObj);
		});
		//处理点击列名和列注释。
		$("#tableColumnItem").delegate("tbody>tr>td[class='editField']", "click", function() {
			var tdObj=$(this);
			TableRow.editNameComment(tdObj);
		});
		
		//字段上移动
		$("#moveupField").click(function(){
			TableRow.move(true);
		});
		//字段下移
		$("#movedownField").click(function(){
			TableRow.move(false);
		});
		
		$("#delField").click(function(){
			TableRow.del();
		});
		
		validator=validTable();
		//处理字段保存
		handSave();
		//处理点击主表子表的radio事件。
		handIsMain();
		
		
	});
	
	/**
	 * 处理保存事件。
	 */
	function handSave(){
		$("#dataFormSave").click(function(){
			var rtn=validator.form();
			if(!rtn) return;
			
			var isMain=$("input[name='isMain']:checked").val();
		
			var name=$("#name").val();
			var comment=$("#comment").val();
			//处理主表字段。
			var mainTableId=$("#mainTableId").val();
			var objMainTable=$("#mainTable");
			//如果修改了主表的值，则重新选取主表。
			if(objMainTable.get(0).selectedIndex>0){
				mainTableId=objMainTable.val();
			}
			var tableJson={tableName:name,tableDesc:comment,isMain:isMain,mainTableId:mainTableId};
			table= $.extend({}, table, tableJson);
			var tableJson=JSON.stringify(table);
			var fieldJson=JSON.stringify(fieldObj.Fields);
			$("#txtConent").val(fieldJson);
			$.post("saveTable.xht",{table:tableJson,fields:fieldJson},showResponse);
		});
	}
	
	/**
	 * 显示返回结果。
	 */
	function showResponse(data){
		var obj=new org.sz.form.ResultMessage(data);
		if(obj.isSuccess()){//成功
			$.ligerMessageBox.confirm('提示信息','操作成功,继续操作吗?',function(rtn){
				if(!rtn){
					var returnUrl=$("a.back").attr("href");
					location.href=returnUrl;
				}
				else{
					location.reload();
				}
			});
	    }else{//失败
	    	$.ligerMessageBox.error('出错了',obj.getMessage());
	    }			
	}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
				<c:choose>
					<c:when test="${tableId==0}">
						添加自定义表
					</c:when>
					<c:otherwise>
						编辑自定义表
					</c:otherwise>
				</c:choose> </span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="copy.xht">复制</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.xht">返回</a></div>
				</div>
			</div>
		</div>
		<br>
		<form id="bpmTableForm" method="post" action="add2.xht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th width="5%">表名: </th>
					<td width="15%">
						<input  type="text" id="name" name="name" maxlength='25' class="inputText"/>
						
					</td>
					<th width="5%">注释: </th>
					<td width="15%">
						<input  type="text" id="comment" name="comment"  class="inputText"/>
					</td>
					<th width="10%">是否主表: </th>
					<td>
						<input  type="radio"  name="isMain"  value="1" checked="checked"/>主表
						<input  type="radio"  name="isMain"  value="0" />从表
						
						<span id="spanMainTable" style="display:none;">
							主表:
							<select id="mainTable" name="mainTable">
								<option value="0">请选择</option>
								<c:forEach items="${mainTableList}" var="table">
									<option value="${table.tableId }">${table.tableDesc }</option>
								</c:forEach>
							</select> 
							<input type="hidden" value="" name="mainTableId" id="mainTableId">
						</span>
					</td>
				</tr>
			</table>
			<br>
			<div class="panel-top">
				<div class="panel-toolbar">
					<div class="toolBar">
						<div class="group"><a onclick="TableRow.addColumn();" class="link add">添加列</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a id="moveupField" class="link moveup">上移</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a id="movedownField" class="link movedown">下移</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a id="delField" class="link del ">删除</a></div>
					</div>	
				</div>
			</div>
			<div class="panel-body">
					<table id="tableColumnItem" class="table-grid table-list" id="0" cellpadding="1" cellspacing="1">
			   		<thead>
			   			<th width="15%">列名</th>
			   			<th width="15%">注释</th>
			    		<th width="10%">类型</th>
			    		<th width="10%">必填</th>
			    		<th width="10%">显示到列表</th>
			    		<th width="10%">作为查询条件</th>
			    		<th width="10%">是否流程变量</th>
			    		<th width="10%">是否删除</th>
			    		<th width="10%">管理</th>
			    	</thead>
			    	<tbody>
			    		
			    	</tbody>
			   	 </table>

			</div>
		</form>
	
</div>
</body>
</html>
