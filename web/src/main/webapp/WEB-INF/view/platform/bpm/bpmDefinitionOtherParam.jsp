<%--
	time:2012-01-05 12:01:21
	desc:edit the 脚本管理
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>其他参数设置</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor_rule.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowVarWindow.js"></script>
	
	<script type="text/javascript">
	var defId=${bpmDefinition.defId };
	
	$(function(){
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

	
	function saveParam(){
		var taskNameRule=CKEDITOR.instances["taskNameRule"].getData();
		var toFirstNode=$("#toFirstNode").attr("checked");
		var needStartForm=$("#needStartForm").attr("checked");
		var toFirstNodeVal=1;
		var needStartFormVal=1;
		if(toFirstNode==undefined){
			toFirstNodeVal=0;
		}
		if(needStartForm==undefined){
			needStartFormVal=0;
		}
		var params={defId:defId,taskNameRule:taskNameRule,toFirstNode:toFirstNodeVal,needStartForm:needStartFormVal};
		
		$.post("saveParam.xht",params,function(msg){
			var obj=new org.sz.form.ResultMessage(msg);
			if(obj.isSuccess()){
				$.ligerMessageBox.success('操作成功',obj.getMessage());
			}else{
				$.ligerMessageBox.error('操作失败',obj.getMessage());
			}
		});
	}
	
	
	</script>
</head>
<body>
            <%@include file="incDefinitionHead.jsp" %>
            <f:tab curTab="9" tabName="flow"/>
            <div class="panel-top">
				<div class="panel-toolbar">
					<div class="toolBar">
						<div class="group"><a class="link save" onclick="saveParam()">保存</a></div>
					</div>	
				</div>
			</div>
            
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th width="15%">流程标题规则定义</th>
					<td>
						表单变量:<f:flowVar defId="${defId}" controlName="selFlowVar"></f:flowVar>
						<textarea id="taskNameRule" row="6" name="taskNameRule" >${bpmDefinition.taskNameRule }</textarea>
					</td>	
				</tr>
				<tr>
					<th width="15%">跳过第一个任务:</th>
					<td>
						<input id="toFirstNode" type="checkbox" name="toFirstNode" value="1"  <c:if test="${bpmDefinition.toFirstNode==1 }">checked="checked"</c:if> />
						<a href="#" class="tipinfo"><span>流程启动后直接完成第一个节点的任务。</span></a>
					</td>	
				</tr>
				<tr>
					<th width="15%">开始节点需要表单:</th>
					<td>
						<input id="needStartForm" type="checkbox" name="needStartForm" value="1"  <c:if test="${bpmDefinition.needStartForm==1 }">checked="checked"</c:if> />
						<a href="#" class="tipinfo"><span>如果勾选，那么流程起始节点需要定义表单，默认为需要。</span></a>
					</td>	
				</tr>
				
			</table>
			<input type="hidden" id="defId" name="defId" value="${bpmDefinition.defId }">
</body>
</html>
