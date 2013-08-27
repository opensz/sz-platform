<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/commons/include/form.jsp" %>
<title><c:choose> <c:when test="${isAdd==1}">添加自定义列</c:when><c:otherwise>修改自定义列</c:otherwise> </c:choose> </title>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx}/js/util/loadjscss.js"></script>
<script type="text/javascript" src="${ctx}/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/ScriptDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/EditTable.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/FieldsManage.js"></script>
<script type="text/javascript">
$(function(){
	//值来源
	handValueFrom();
	//字段类型修改
	handFieldType();
	//处理控件类型
	handControlType();
	//
	init();
});

var __isFieldAdd__=true;

function init(){
	var conf = window.dialogArguments;
	
	//设置字段管理,主要是为了验证字段是否存在。
	TableRow.setFieldManage(conf.fieldManage);
	//在EditTable.js 的validateField 方法中有用到。
	__isFieldAdd__=conf.isAdd;
	
	var valid=validateField();
	//添加字段
	if(__isFieldAdd__){
		$("#dataFormSave").text("添加");
		initAdd();
	}
	//更新字段
	else{
		var allowEditColName=conf.allowEditColName;
		$("#dataFormSave").text("保存");
		initControlByField(conf.field,allowEditColName);
	}
	$("#dataFormSave").click(function(){
		//获取字段。
		var field=getField();
		if(!valid.form()) return false;
		if(__isFieldAdd__){
			var rtn=conf.callBack.call(this,field);
			if(!rtn) return;
			//重置字段
			resetField();
		}
		else{
			conf.callBack.call(this,field);
			$.ligerMessageBox.success('提示信息','修改字段属性成功',function(){
				window.close();
			});
		}
	});
}

function selectScript(){
	var objConditionCode=$("#script")[0];
	ScriptDialog({callback:function(script){jQuery.insertText(objConditionCode,script);}});
}


</script>
</head>
<body>
	<div class="panel-top">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="#">添加</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del" href="#" onclick="window.close();">关闭</a></div>
				</div>
				
			</div>
	</div>
<form id="frmFields" action="">
	<input type="hidden" id="fieldId" name="fieldId"/>
	<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
		<tr>
			<th width=100>字段名称:</td>
			<td><input type="text" id="fieldName" maxlength="20" name="fieldName" value="" class="inputText"/></td>
			<th width=100>字段描述:</td>
			<td><input type="text" id="fieldDesc" name="fieldDesc" value="" class="inputText"/></td>
		</tr>
		<tr>
			<th width=100>字段类型:</td>
			<td>
				<select id="fieldType" name="fieldType">
					<option value="varchar">文字</option>
					<option value="number">数字</option>
					<option value="date">日期</option>
					<option value="clob">大文本</option>
				</select>
				<span id="spanDateFormat" style="display:none">
					<br>
					取当前时间：<input type="checkbox" name="isCurrentDate" value="1">
					<br>
					<select id="selDateFormat" >
						<option value="yyyy-MM-dd">yyyy-MM-dd</option>
						<option value="yyyy-MM-dd HH:mm:ss">yyyy-MM-dd HH:mm:ss</option>
						<option value="yyyy-MM-dd HH:mm:00">yyyy-MM-dd HH:mm:00</option>
					</select>
				</span>
			</td>
			
			<th width=100>长度:</td>
			<td>
				<span id="spanCharLen">
					<input type="text" id="charLen" name="charLen" value="50" class="inputText" style="width:40px" />
				</span>
				<span id="spanIntLen" style="display: none">
					<label>整数位:</label>
					<input type="text" id="intLen" name="intLen" value="13" class="inputText" size="4" style="width:40px"/>
				</span>
				<span>
				<span id="spanDecimalLen" style="display: none">
					<label>小数位:</label>
					<input type="text" id="decimalLen" name="decimalLen" value="0" class="inputText" style="width:20px"/>
				</span>
			</td>
		</tr>
	
		<tr>
			<th width=100>选项:</td>
			<td colspan="3">
				<span><input id="isRequired" name="isRequired" type="checkbox" value="1"/>&nbsp;必填&nbsp;</span>
				<span><input id="isList" name="isList" type="checkbox" checked="checked" value="1"/>&nbsp;显示到列表&nbsp;</span>
				<span><input id="isQuery" name="isQuery" type="checkbox" value="1"/>&nbsp;作为查询条件&nbsp;</span>
				<span><input id="isFlowVar" name="isFlowVar" type="checkbox" value="1"/>&nbsp;是否流程变量&nbsp;</span>
			</td>
		</tr>
	
		<tr>
			<th width=100 >值来源:</td>
			<td colspan="3">
				<select id="valueFrom" name="valueFrom">
					<option value="0">表单输入</option>
					<option value="1">脚本运算(显示)</option>
					<option value="2">脚本运算(不显示)</option>
					<option value="3">流水号</option>
				</select>
			</td>
		</tr>
		<tr id="trControlType" >
			<th width=100>控件类型:</td>
			<td colspan="3"><select id="controlType" name="controlType"></select></td>
		</tr>
		<tr id="trDict" >
			<th width=100>数据字典类型:</td>
			<td colspan="3">
				<input id="dictTypeName" class="catComBo" catKey="DIC" valueField="dictType" isNodeKey="true" name="dictTypeName" height="150" width="200"/>
			</td>
		</tr>
		<tr id="trRule" >
			<th width=100>验证规则:</td>
			<td colspan="3">
				<select id="validRule" name="validRule">
					<option value="">无</option>
					<c:forEach items="${validRuleList}" var="rule">
						<option value="${rule.name}">${rule.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		
		<tr id="trScript" >
			<th width=100>脚本:</td>
			<td colspan="3">
				<a href="#" onclick="selectScript()" id="btnScript" class="link var" title="常用脚本">常用脚本</a>
				<br/>脚本中要使用到其他字段参与运算， 请使用“[字段名]”方式引用。<br />
				<textarea id="script" name="script" rows="6" cols="70"></textarea>
			</td>
		</tr>
		<tr id="trIdentity" >
			<th width=100>流水号:</td>
			<td colspan="3">
				<select id="identity" name="identity">
					<c:forEach items="${identityList}" var="identity">
						<option value="${identity.alias}">${identity.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr id="trOption" >
			<th width=100>下拉选项:</td>
			<td colspan="3">
				每行一个项目:<br>
				<textarea id="options" name="options" rows="6" cols="70"></textarea>
			</td>
		</tr>
		<tr id="trEvent" style="display:none;">
			<th width=100>事件方法:</td>
			<td colspan="3">
				<select id="eventClick" name="eventClick">
					<option value="onclick">onclick</option>
					<option value="dbclick">dbclick</option>
					<option value="onchange">onchange</option>
					<!--<option value="onselected">onselected</option>
				--></select>
			</td>
		</tr>
		<tr id="trFunction" style="display:none;" >
			<th width=100>调用函数:</td>
			<td colspan="3">
				
				<textarea id="callFunction" name="callFunction" rows="6" cols="70"></textarea>
			</td>
		</tr>
	</table>
			
</form>
</body>
</html>