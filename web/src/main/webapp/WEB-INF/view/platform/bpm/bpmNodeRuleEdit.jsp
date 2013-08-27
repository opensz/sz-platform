<%--
	desc:edit the 流程节点规则
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程节点跳转规则设置</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerLayout.js" ></script>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bpmNodeRule"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/ScriptDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowVarWindow.js" ></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerTab.js" ></script>
<style type="text/css">
a.ruledetail,a.delrule{cursor: pointer;}
</style>
<script type="text/javascript">
	var deployId="${deployId}";
	var actDefId="${actDefId}";
	var nodeId="${nodeId}";
	var flowVarWindow;
	function showRequest(formData, jqForm, options) {
		return true;
	}
	$(function() {
		valid(showRequest,function(){});
		$("#layoutFlowRule").ligerLayout({ rightWidth:200, height: '95%' });
		 //加载列表
		 loadRuleList(); 
		 $("#btnVars").click(selectVar);
		 $("#btnScript").click(selectScript);
		 $("a.save").click(save);
		 handFlowVars();
		 
	});
	
	function handFlowVars(){
		var objConditionCode=$("#conditionCode")[0];
		$("select[name='selFlowVar']").change(function(){
			var val=$(this).val();
			jQuery.insertText(objConditionCode,val);
		});
	}
	
	function save(){

		 var rtn=$("#bpmNodeRuleForm").valid();
		 if(!rtn) return;
		 var url=__ctx+ "/platform/bpm/bpmNodeRule/save.xht";
		 var para=$('#bpmNodeRuleForm').serialize();
		 $.post(url,para,showResult);
	}
	function showResult(responseText)
	{
		var obj=new org.sz.form.ResultMessage(responseText);
		var ruleId=$("#ruleId").val();
		if(!obj.isSuccess()){
			$.ligerMessageBox.error('出错了',obj.getMessage());
			return;
		}
		//添加
		if(ruleId=="0"){
			$.ligerMessageBox.confirm('提示信息','添加规则成功,继续添加吗?',function(rtn){
				if(!rtn){
					window.close();
				}
				else{
					__valid.resetForm();
					loadRuleList();
				}
			});
		}
		//更新
		else{
			$.ligerMessageBox.confirm('提示信息','更新规则成功,继续更新吗?',function(rtn){
				if(!rtn){
					window.close();
				}
				else{
					loadRuleList();
				}
			});
		}
	}
	function selectScript(){
		var objConditionCode=$("#conditionCode")[0];
		ScriptDialog({callback:function(script){
			jQuery.insertText(objConditionCode,script);
		}});
	}
	
	//选择变量
	function selectVar(){
		var objConditionCode=$("#conditionCode")[0];
		FlowVarWindow({deployId:deployId,nodeId:nodeId,callback:function(vars){
			jQuery.insertText(objConditionCode,vars);
		}});
	}
	
	//显示规则明细
	function showDetail(){
		var obj=$(this);
		var ruleId=obj.attr("rule");
		var url=__ctx + "/platform/bpm/bpmNodeRule/getById.xht?ruleId=" + ruleId;
		$.get(url,function(data) {
			var json=jQuery.parseJSON(data);
			jQuery.setFormByJson(json);		
			var tmp=json.targetNode +"," + json.targetNodeName;
			$("select[name='targetNode']").val(tmp);
		});
	}
	
	function getRow(ruleId,ruleName,idx)
	{
		var className=(idx % 2==0)?"odd":"even";
		var str="<tr class='"+className+"'>";
		str+="<td>";
		str+="	<a class='ruledetail' rule='"+ruleId+"'>"+ruleName+"</a>";
		str+="</td>";
		str+="<td>";
		str+="<a alt='上移' href='#' class='link moveup' onclick='sortUp(this)'>&nbsp;&nbsp;&nbsp;</a>";
		str+="<a alt='下移' href='#' class='link movedown' onclick='sortDown(this)'>&nbsp;&nbsp;&nbsp;</a>";
		str+="<a alt='删除'  class='delrule link del' rule='"+ruleId+"'>&nbsp;&nbsp;&nbsp;</a>";
		str+="</td>";
		str+="</tr>";
		return str;
	}
	//加载规则列表
	function loadRuleList(){
		var url=__ctx + "/platform/bpm/bpmNodeRule/getByDefIdNodeId.xht?actDefId=" + actDefId +"&nodeId=" + nodeId;
		url=url.getNewUrl();
		var tbodyList=$("#ruleList");
		tbodyList.empty();
		$.get(url,function(data) {
			var jsonAry=jQuery.parseJSON(data);
			for(var i=0;i<jsonAry.length;i++){
				var obj=jsonAry[i];
				var row=getRow(obj.ruleId,obj.ruleName,i);
				tbodyList.append($(row));
			}
			$("a.ruledetail").click(showDetail);
			$("a.delrule").click(delByRule);
		});
	}

	//删除规则	
	function delByRule(){
		var obj=$(this);
		var ruleId=obj.attr("rule");
		$.ligerMessageBox.confirm('提示信息','确认删除吗？',function(rtn) {
			if(!rtn) return;
			var url=__ctx + "/platform/bpm/bpmNodeRule/del.xht?ruleId=" + ruleId;
			$.get(url,function(data) {
				var obj=new org.sz.form.ResultMessage(data);
				if(obj.isSuccess()){
					$.ligerMessageBox.success("提示信息","删除成功!",function(){
						loadRuleList();
					});
				}
				else{
					$.ligerMessageBox.warn("提示信息","删除失败!");
				}
			});
		});
	}
	
	//规则上移
	function sortUp(obj) {
		var thisTr = $(obj).parents("tr");
		var prevTr = $(thisTr).prev();
		if(prevTr){
			thisTr.insertBefore(prevTr);
			reSort();
		}
	};
	//重新排序
	function reSort(){
		var ruleids="";
		$("a.ruledetail").each(function(i){
			ruleids+=$(this).attr("rule") +",";
		});
		if(ruleids!="")
			ruleids=ruleids.substring(0, ruleids.length-1);
		
		var url=__ctx + "/platform/bpm/bpmNodeRule/sortRule.xht";
		var params="ruleids=" +ruleids;
		$.post(url,params,function(data){});
	}
	 
	// 规则下移
	function sortDown(obj) {
		var thisTr = $(obj).parents("tr");
		var nextTr = $(thisTr).next();
		if(nextTr){
			thisTr.insertAfter(nextTr);
			reSort();
		}
	}
	
	//更新那个bpm_node_set的IsJumpForDef字段
	function updateIsJumpForDef(ck){
		var url=__ctx+"/platform/bpm/bpmNodeRule/updateIsJumpForDef.xht";
		var params={
			nodeId:nodeId,
			actDefId:actDefId,
			isJumpForDef:ck.checked? 1:0
		};
		$.post(url,params,function(data){});
	}

</script>
<style>
html { overflow-x: hidden; }
</style>
<body>
	<div class="panel">
		<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">流程节点跳转规则设置</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link save" id="btnSearch">保存</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del" onclick="javasrcipt:window.close()">关闭</a></div>
						</div>	
					</div>
				</div>
		<div class="panel-body">
			<div id="layoutFlowRule" style="width: 100%">
				<div  style="width:100%;" position="right" title="规则列表">
					<table  cellpadding="1" class="table-grid table-list" cellspacing="1">
						<tr>
							<th>规则名称</th><th>管理</th>
						</tr>
						<tbody id="ruleList">
						</tbody>
					</table>
				</div>
				<div  id="framecenter" position="center">
					<form id="bpmNodeRuleForm" method="post" action="save.xht">
						<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th width="20%">当前节点名称:</th>
								<td>${nodeName}</td>
							</tr>
							<tr>
								<th>当规则符合时是否正常跳转</th>
								<td>
									<input type="checkbox" name="isJumpForDef" value="1" onclick="updateIsJumpForDef(this);" <c:if test="${bpmNodeSet.isJumpForDef==1}">checked="checked"</c:if> >
								</td>
							</tr>
							<tr>
								<th width="20%">规则名称: <span class="required">*</span></th>
								<td><input type="text" id="ruleName" name="ruleName" size="40" value="${bpmNodeRule.ruleName}" class="inputText" /></td>
							</tr>
							<tr>
								<th width="20%">规则表达式:<span class="required">*</span></th>
								<td>
									<div style="margin:8px 0;">
										<a href="#" id="btnVars" class="link var" title="选择流程变量">流程变量</a>
										<a href="#" id="btnScript" class="link var" title="常用脚本">常用脚本</a>
										&nbsp;&nbsp;表单变量:<f:flowVar defId="${defId}" controlName="selFlowVar"></f:flowVar>
									</div>
									<textarea id="conditionCode" rows="12" cols="55" name="conditionCode" >return true;</textarea>
									<br/> 
									<div style="margin:8px 0;">这个脚本需要使用返回语句(return)返回布尔值，返回true流程将跳转到指定的节点。</div>
								</td>
							</tr>
							<tr>
								<th width="20%">跳转节点名称:</th>
								<td>
									<select name="targetNode">
										<c:forEach items="${activityList}" var="item">
											<optgroup label="${item.key}">
												<c:forEach items="${item.value}" var="node">
													<option value="${node.key},${node.value}">${node.value}</option>
												</c:forEach>
											</optgroup>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<th width="20%">备注:</th>
								<td>
									<textarea id="memo" rows="4" cols="40" name="memo" >${bpmNodeRule.memo}</textarea>
								</td>
							</tr>
						</table>
						<input type="hidden" id="ruleId" name="ruleId" value="${bpmNodeRule.ruleId}" />						
						<input type="hidden" name="priority" value="${bpmNodeRule.priority}" />
						<input type="hidden" name="actDefId" value="${bpmNodeRule.actDefId}" />
						<input type="hidden" name="nodeId" value="${bpmNodeRule.nodeId}" />
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

