<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>全局参数设置</title>
	<%@include file="/commons/include/get.jsp" %>
	<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css"/>
	<script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor_rule.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowVarWindow.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/VarsWindow.js"></script> 	
	<script type="text/javascript" src="${ctx}/js/sz/platform/form/FormDialog.js"></script>
	
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/htCatCombo.js"></script>
	
	<script type="text/javascript">
	var defId=${bpmDefinition.defId };
	
	$(function(){
		handFormType();
		validHandler();
		
		editor=ckeditor('taskNameRule');
		handFlowVars();	
	});
	
	function handFlowVars(){
		var objConditionCode=$("#taskNameRule");
		$("select[name='selFlowVar']").change(function(){		
			var val=$(this).val();
			var text=$(this).find("option:selected").text();
			if(val.length==0) return;
			if(text=="发起人(长整型)")
				text=text.replace("(长整型)","");			
			var inStr="{"+text+":"+val+"}";
			InsertText(inStr);
		});
	}
	
	function InsertText(val){
		// Get the editor instance that we want to interact with.
		var oEditor = CKEDITOR.instances.taskNameRule;
		// Check the active editing mode.
		if ( oEditor.mode == 'wysiwyg' ){
			oEditor.insertText( val );
		}else
			alert( '请把编辑器设置为编辑模式' );
	}

	
	function save(){
		
		$('#dataForm')[0].submit();
		
		/**
		var taskNameRule=CKEDITOR.instances["taskNameRule"].getData();
		var params={defId:defId,taskNameRule:taskNameRule};
		
		$.post("save.xht",params,function(msg){
			var obj=new org.sz.form.ResultMessage(msg);
			if(obj.isSuccess()){
				$.ligerMessageBox.success('操作成功',obj.getMessage());
			}else{
				$.ligerMessageBox.error('操作失败',obj.getMessage());
			}
		});
		*/
	}
	
	function handFormType(){
		$("select.selectForm").change(function(){
			var obj=$(this);
			var value=obj.val();
			var trObj=obj.closest("tr");
			if(value==-1){
				$("table.table-detail",trObj).hide();
			}
			else{
				$("table.table-detail",trObj).show();
				if(value==0){
					$("div.form",trObj).show();
					$("div.url",trObj).hide();
				}
				else{
					$("div.form",trObj).hide();
					$("div.url",trObj).show();
				}
			}
		});
	}
	
	function addVar(falg,varId){
		VarsWindow({falg:falg,varId:varId,defId:defId});
	}
	
	function validHandler(){
		$("input.handler").blur(function(){
			var obj=$(this);
			var val=obj.val();
			if(val.trim()==""){
				return;
			}
			var params={handler:val};
			$.post("${ctx}/platform/bpm/bpmNodeSet/validHandler.xht",params,function(data){
				var json=eval("(" +data +")");
				if(json.result!='0'){
					$.ligerMessageBox.warn("提示信息",json.msg,function(){
						obj.focus();
					});
				}
			});
		});
	}
	
	//清除表单
	function clearForm(obj){
		var btn=$(obj);
		var tdObj=btn.parent();
		$("input.formKey",tdObj).val('');
		$("input.formDefName",tdObj).val('');
		$("span[name='spanForm']",tdObj).text('');
	}
	
	function selectForm(obj) {
		FormDialog({
			callback : function(ids, names) {
				var tdObj=$(obj).parent();
				$("input.formKey",tdObj).val(ids);
				$("input.formDefName",tdObj).val(names);
				$("span[name='spanForm']",tdObj).html(names);
			}
		})
	}
	
	
	</script>
</head>
<body>
            <%@include file="/WEB-INF/view/platform/bpm/incDefinitionHead.jsp" %>
            <f:tab curTab="2" tabName="flow"/>
            <div class="panel-top">
				<div class="panel-toolbar">
					<div class="toolBar">
						<div class="group"><a class="link save" onclick="save()">保存</a></div>
					</div>	
				</div>
			</div>
            <form action="save.xht" method="post" id="dataForm">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">            
	            
	            <input type="hidden" id="defId" name="defId" value="${bpmDefinition.defId }">
			
			    <tr>
						<th  width="15%">业务类型:</th>
						<td colspan="2">
							<input class="catComBo" catKey="BUSINESS_TYPE" valueField="businessType" catValue="${bpmDefinition.businessType}" isNodeKey="true" name="typeName" height="100" width="100"/>
						</td>
					</tr>
				<tr>
					<th width="15%">全局表单:</th>
					<td>
						<select name="globalFormType" class="selectForm" >
							<c:choose >
								<c:when test="${globalForm==null }">
									<option value="-1" selected="selected" >请选择..</option>
									<option value="0" >在线表单</option>
									<option value="1" >URL表单</option>
								</c:when>
								<c:otherwise>
									<option value="-1"  >请选择..</option>
									<option value="0"  <c:if test="${globalForm.formType==0 }">selected="selected"</c:if> >在线表单</option>
									<option value="1"  <c:if test="${globalForm.formType==1 }">selected="selected"</c:if>>URL表单</option>
								</c:otherwise>
							</c:choose>
						</select>
					</td>
					<td style="padding: 5px 2px 5px 2px;">
						<table class="table-detail" <c:if test="${globalForm==null}">style="display: none" </c:if>>
							<tr>
								<td nowrap="nowrap" class="head">表单:</td>
								<td>
									<div name="globalForm" class="form" <c:if test="${globalForm!=null && globalForm.formType!=0 }">style="display: none" </c:if>>
										<input id="defaultFormKey" class="formKey"  type="hidden" name="defaultFormKey" value="${globalForm.formKey }" >
										<input id="defaultFormName" class="formDefName"  type="hidden" name="defaultFormName" value="${globalForm.formDefName }">
										<span name="spanForm">${globalForm.formDefName }</span><a href="#" class="link get" onclick="selectForm(this)">选择</a>
										<a href="#" class="link clean" onclick="clearForm(this)">重选</a>
									</div>
									<div name="globalFormUrl" <c:if test="${globalForm!=null && globalForm.formType!=1 }">style="display: none" </c:if> class="url" >
										表单URL:<input type="text" name="formUrlGlobal" value="${globalForm.formUrl }" class="inputText" size="40"/>
									</div>
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap" class="head">前置处理器:</td>
								<td>
									<input type="text" name="beforeHandlerGlobal" value="${globalForm.beforeHandler }" class="inputText handler" size="40"/>
									<a href="#" class="tipinfo"><span>填写格式:service+方法名(参数【ProcessCmd】)</span></a>
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap" class="head">后置处理器:</td>
								<td>
									<input type="text" name="afterHandlerGlobal" value="${globalForm.afterHandler }" class="inputText handler" size="40"/>
									<a href="#" class="tipinfo"><span>填写格式:service+方法名(参数【ProcessCmd】)</span></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			
				<tr>
					<th width="15%">起始节点表单: </th>
					<td>
						<select name="startFormType" class="selectForm" >
							<c:choose >
								<c:when test="${startForm==null }">
									<option value="-1" selected="selected" >请选择..</option>
									<option value="0" >在线表单</option>
									<option value="1" >URL表单</option>
								</c:when>
								<c:otherwise>
									<option value="-1"  >请选择..</option>
									<option value="0"  <c:if test="${startForm.formType==0 }">selected="selected"</c:if> >在线表单</option>
									<option value="1"  <c:if test="${startForm.formType==1 }">selected="selected"</c:if>>URL表单</option>
								</c:otherwise>
							</c:choose>
						</select>
					</td>
					<td style="padding: 5px 2px 5px 2px;">
						<table class="table-detail"  <c:if test="${startForm==null}">style="display: none" </c:if> >
							<tr>
								<td nowrap="nowrap" class="head">表单:</td>
								<td>
									<div name="startForm" class="form" <c:if test="${startForm!=null && startForm.formType!=0 }">style="display: none" </c:if> >
										<input id="startFormKey" class="formKey"  type="hidden" name="startFormKey" value="${startForm.formKey}">
										<input id="startFormName" class="formDefName"  type="hidden" name="startFormName" value="${startForm.formDefName}">
										<span name="spanForm">${startForm.formDefName}</span><a href="#" class="link get" onclick="selectForm(this)">选择</a>
										<a href="#" class="link clean" onclick="clearForm(this)">重选</a>
									</div>
									<div name="startFormUrl" <c:if test="${startForm!=null && startForm.formType!=1 }">style="display: none" </c:if> class="url">
										表单URL:<input type="text" name="formUrlStart" value="${startForm.formUrl }" class="inputText" size="40"/>
									</div>
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap" class="head">前置处理器:</td>
								<td>
									<input type="text" name="beforeHandlerStart" value="${startForm.beforeHandler }" class="inputText handler" size="40" />
									<a href="#" class="tipinfo"><span>填写格式:service+方法名(参数【ProcessCmd】)</span></a>
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap" class="head">后置处理器:</td>
								<td>
									<input type="text" name="afterHandlerStart" value="${startForm.afterHandler }" class="inputText handler" size="40"/>
									<a href="#" class="tipinfo"><span>填写格式:service+方法名(参数【ProcessCmd】)</span></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			
				<tr>
					<th width="15%">流程标题规则定义</th>
					<td colspan="2">
						表单变量:<f:flowVar defId="${defId}" controlName="selFlowVar"></f:flowVar>
						<textarea id="taskNameRule" row="6" name="taskNameRule" >${bpmDefinition.taskNameRule }</textarea>
					</td>	
				</tr>
			</table>	
				
				</form>
				
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">  
				<tr>
					<th width="15%">变量设置</th>
					<td colspan="2">
						
					<div class="panel">
						<div class="panel-top">
							<div class="panel-toolbar">
								<div class="toolBar">
									<div class="group"><a class="link search" id="btnSearch">查询</a></div>
									<div class="l-bar-separator"></div>
									<div class="group"><a class="link add" href="#" onclick="addVar('add','')" >添加</a></div>
									<div class="l-bar-separator"></div>
									<div class="group"><a class="link del"  action="${ctx}/platform/bpm/bpmDefVar/del.xht">删除</a></div>
								</div>	
							</div>
						</div>
						<div class="panel-body">
							<div class="panel-search">
								<form id="searchForm" method="post" action="globalNodeSet.xht?defId=${defId}&actDefId=${actDefId}&actDeployId=${actDeployId } ">
									<div class="row">
										<span class="label">变量名称:</span><input type="text" name="Q_varName_S"  class="inputText" />
									</div>
								</form>
							</div>
						
					
						<div class="panel-data">
					    	<c:set var="checkAll">
								<input type="checkbox" id="chkall"/>
							</c:set>
						    <display:table name="bpmDefVarList" id="bpmDefVarItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"   class="table-grid">
								<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
									  	<input type="checkbox" class="pk" name="varId" value="${bpmDefVarItem.varId}">
								</display:column>
								<display:column property="varName" title="变量名称" ></display:column>
								<display:column property="varKey" title="变量Key" ></display:column>							
								<display:column title="作用域" >
								<c:choose>
								<c:when test="${bpmDefVarItem.varScope eq 'global'}">全局</c:when>
								<c:when test="${bpmDefVarItem.varScope eq 'task'}">局部</c:when>
								</c:choose>
								</display:column>
								<display:column property="nodeName" title="节点名称" ></display:column>
								
								<display:column title="管理" media="html" style="width:180px">
									<a  href="${ctx}/platform/bpm/bpmDefVar/del.xht?varId=${bpmDefVarItem.varId}" class="link del">删除</a>
									<a href="${ctx}/platform/bpm/bpmDefVar/get.xht?varId=${bpmDefVarItem.varId}&actDefId=${actDefId}" class="link detail">明细</a>
									<a onclick="addVar('edit','${bpmDefVarItem.varId}')" class="link flowDesign" href="#">编辑</a>
								</display:column>
							</display:table>
						</div>
					</div>
				</div>		
						
						
					</td>	
				</tr>
				
			</table>
</body>
</html>
