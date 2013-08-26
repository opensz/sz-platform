
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<%@include file="/commons/include/form.jsp"%>
<title>设置分支条件</title>
<script type="text/javascript"
	src="${ctx}/js/sz/platform/bpm/FlowVarWindow.js"></script>
<script type="text/javascript"
	src="${ctx}/js/sz/platform/system/ScriptDialog.js"></script>
<script type="text/javascript">
	var nodeId = "${nodeId}";
	var deployId = "${deployId}";
	var defId = "${defId}";
	var slectTextarea;
	$(function() {
		$("a[name='btnVars']").click(selectVar);
		$("#btnScript").click(selectScript);
		$("a[name='signResult']").click(function() {
			addToTextarea($(this).attr("result"));
		});
		$("a.save").click(saveCondition);
	});
	var flowVarWindow;
	//选择变量
	function selectVar() {
		FlowVarWindow({
			deployId : deployId,
			nodeId : nodeId,
			callback : function(vars) {
				addToTextarea(vars);
			}
		});
	};
	//将条件表达式追加到脚本输入框内
	function addToTextarea(str){
		if(slectTextarea==null){
			$.ligerMessageBox.warn('提示信息','请先选中脚本输入框');
			return;
		}
		$.insertText(slectTextarea,str);
	};
	//textarea切换获取焦点
	function toggleFocus(e){
		slectTextarea=e;
	};
	function selectScript() {
		ScriptDialog({
			callback : function(script) {
				addToTextarea(script);
			}
		});
	};
	function handFlowVars(obj) {
		addToTextarea($(obj).val());
	};
	function saveCondition() {
		var tasks = "";
		var conditions = "";
		$("tr.taskTr").each(function() {
			var obj = $(this);
			var condition = $("[name='condition']", obj).val();
			var task = $("input[name='task']", obj).val();
			if (condition != null && condition.trim() != "") {
				tasks += task + ",";
				conditions += condition + ",";
			}
		});
		if (conditions != "") {
			tasks = tasks.substring(0, tasks.length - 1);
			conditions = conditions.substring(0, conditions.length - 1);
		}
		var url = __ctx + "/platform/bpm/bpmDefinition/saveCondition.xht";
		//var paras="defId=" + defId + "&nodeId="+nodeId+"&tasks=" + tasks +"&conditions=" +conditions;

		var paras = {
			"defId" : defId,
			"nodeId" : nodeId,
			"tasks" : tasks,
			"conditions" : conditions
		};

		$.post(url, paras, function(data) {
			var resultObj = new org.sz.form.ResultMessage(data);
			if (resultObj.isSuccess()) {
				$.ligerMessageBox.success("提示信息", "编辑规则成功!", function() {
					window.close();
				});
			} else {
				$.ligerMessageBox.warn("提示信息", "编辑规则失败!");
			}
		});
	};
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">条件分支设置</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="btnSearch">保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link del" onclick="javasrcipt:window.close()">关闭</a>
					</div>

				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="bpmNodeRuleForm" method="post" action="save.xht">
				<table class="table-detail" cellpadding="0" cellspacing="0"
					border="0">
					<tr>
						<th>条件表达式</th>
						<td>
							<div style="margin: 8px 0; ">
								<a href="#" name="btnVars" class="link var" title="选择流程变量">流程变量</a>
								<a href="#" id="btnScript" class="link var" title="常用脚本">常用脚本</a>
								&nbsp;&nbsp;表单变量:
								<f:flowVar defId="${defId}" change="handFlowVars(this)"></f:flowVar>
							</div> <c:forEach items="${incomeNodes}" var="inNode">
								<div style="padding: 4px;">
									<c:choose>
										<c:when test="${inNode.isMultiple==true}">
											<a href="#" name="signResult"
												result='signResult_${inNode.nodeId}=="pass"'>[${inNode.nodeName}]投票通过</a>
														&nbsp;
														<a href="#" name="signResult"
												result='signResult_${inNode.nodeId}=="refuse"'>[${inNode.nodeName}]投票不通过</a>
										</c:when>
										<c:otherwise>
											<a href="#" name="signResult"
												result="approvalStatus_${inNode.nodeId}==1">[${inNode.nodeName}]-通过</a>
														&nbsp;
														<a href="#" name="signResult"
												result="approvalStatus_${inNode.nodeId}==2">[${inNode.nodeName}]-反对</a>
										</c:otherwise>
									</c:choose>
									&nbsp;&nbsp;&nbsp;&nbsp;(先选中下方的脚本输入框，然后再插入条件表达式。)								
								</div>
							</c:forEach>							
						</td>
					</tr>
					<c:forEach items="${outcomeNodes}" var="outNode">
						<tr class="taskTr">
							<th width="20%">
								<input type="hidden" name="task" value="${outNode.nodeId}" /> ${outNode.nodeName }
							</th>
							<td>
								<textarea name="condition" onfocus="toggleFocus(this)" rows="3" cols="50" class="inputText">${outNode.condition}</textarea>
							</td>
						</tr>
					</c:forEach>
				</table>

			</form>
		</div>
	</div>
</body>
</html>
