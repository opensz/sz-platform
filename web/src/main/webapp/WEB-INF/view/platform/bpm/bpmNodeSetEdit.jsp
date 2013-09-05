<%@page language="java" pageEncoding="UTF-8" import="java.util.*;"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 节点设置</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bpmNodeSet"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/form/FormDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/ScriptDialog.js" ></script>
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMsg.js"></script>
	<script type="text/javascript">
	var actDefId="${actDefId}";
	
	$(function() {
		
		handFormType();
		validHandler();
		
	});
	

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
	
	//清除表单
	function clearForm(obj){
		var btn=$(obj);
		var tdObj=btn.parent();
		$("input.formKey",tdObj).val('');
		$("input.formDefName",tdObj).val('');
		$("span[name='spanForm']",tdObj).text('');
	}
	
	//表单授权
	function authorizeDialog(nodeId,formKey){
		var url=__ctx + '/platform/form/bpmFormDef/rightsDialog.xht?actDefId=' +actDefId+"&nodeId=" + nodeId +"&formKey=" + formKey;
		var winArgs="dialogWidth=800px;dialogHeight=600px;help=0;status=no;center=yes;resizable=no;";
		url=url.getNewUrl();
		window.showModalDialog(url,"",winArgs);
	}
	
	//表单授权
	function authorize(obj,nodeId){
		var btn=$(obj);
		var tdObj=btn.parent();
		var objDefId=$("input.formKey",tdObj);
		if(objDefId.val()==""){
			$.ligerMessageBox.warn("提示信息","请先选择表单!");
			return;
		}
		var formKey=objDefId.val();
		authorizeDialog(nodeId,formKey);
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
	
	function selectCmp(obj){
		var cmpIds=$(obj).siblings("input[name='cmpIds']");
		var cmpNames=$(obj).siblings("textarea[name='cmpNames']");
		var assignType=$(obj).parent().prev('td').children('[name="assignType"]').val();

		var nodeUserId=$(obj).parent().prev('td').prev('td').children('[name="nodeUserId"]').val();
		
		var nodeId=$(obj).parent().prev('td').prev('td').children('[name="nodeId"]').val();
		
		var callback=function(ids,names){				
			cmpIds.val(ids);
			cmpNames.val(names);
		};
		
		if(assignType==1){
			 UserDialog({callback:callback});
		}
		else if(assignType==2){
			RoleDialog({callback:callback});
		}
		else if(assignType==3 || assignType==4){
			OrgDialog({callback:callback});	
		}
		else if(assignType==5){
			PosDialog({callback:callback});
		}
		else if(assignType==6){
			UplowDialog({callback:callback});
		}
		else if(assignType==7){
			UserParamDialog({callback:callback,nodeUserId:nodeUserId,cmpIds:cmpIds.val(),cmpNames:cmpNames.val()});
		}
		else if(assignType==8){
			OrgParamDialog({callback:callback,nodeUserId:nodeUserId,cmpIds:cmpIds.val(),cmpNames:cmpNames.val()});
		}else if(assignType==10){
			showOtherNodeDlg({callback:callback,nodeId:nodeId});
		}
		else if(assignType==12){
			showScript(cmpNames);
		}
		else if(assignType==100){
			//showScript(cmpNames);
			alert('多组选择未实现');
		}
	}
	
	function addRow(rowId){
		
		var tbody=$("#table_"+rowId + " tbody.data");
		var rowLen=tbody.children('tr').length;
		var idx = rowLen+1;
		var str="<tr id='"+rowId+"_"+idx+"'>";
			str+="<td nowrap='nowrap' height='28'>";
				str+="<input type='checkbox' name='nodeUserCk'/>&nbsp;";
				str+=idx;
				str+="<input type='hidden' name='nodeUserId'/>";
				str+="<input type='hidden' name='nodeId' value='" + rowId + "'/>";
			str+="</td>";
		
			str+="<td>";
				str+="<select name='assignType' class='select' onchange='assignTypeChange(this);'>";
				str+="<option value='0'>发起人</option>";
				str+="<option value='1' selected='selected'>用户</option>";
				str+="<option value='100'>多组选择</option>";
				str+="<option value='2'>角色</option>";
				str+="<option value='3'>组织</option>";
				str+="<option value='4'>组织负责人</option>";
				str+="<option value='5'>岗位</option>";
				str+="<option value='6'>上下级</option>";
				str+="<option value='7'>用户属性</option>";
				str+="<option value='8'>组织属性</option>";
				str+="<option value='9'>与发起人相同部门</option>";
				str+="<option value='10'>与其他节点相同执行人</option>";
				str+="<option value='11'>发起人的直属领导(组织)</option>";
				str+="<option value='13'>上个任务执行人的直属领导(组织)</option>";
				str+="<option value='14'>发起人的领导</option>";
				str+="<option value='15'>上个任务执行人的领导</option>";
				str+="<option value='12'>脚本</option>";
				str+="</select>";
			str+="</td>";
			
			str+="<td>";
				str+="<input type='hidden' name='cmpIds'/><textarea name='cmpNames' readonly='readonly' style='width:80%' rows='2' class='textarea'></textarea>";
				str+="&nbsp;<a class='button' onclick='selectCmp(this);'><span>选择...</span></a>";
			str+="</td>";
			
			str+="<td>";
			str+="<a id='moveupField' class='link moveup' onclick='move(\"table_"+rowId+"\",\"up\",\""+idx+"\")'></a>";
			str+="<a id='movedownField' class='link movedown' onclick='move(\"table_"+rowId+"\",\"down\",\""+idx+"\")'></a>";
			str+="</td>";
			
			str+="<td>";
			str+="<select name='compType'>";
			str+="<option value='0'>或运算</option>";
			str+="<option value='1'>与运算</option>";
			str+="<option value='2'>排除运算</option>";
			str+"</select>";
			str+="</td>";
			
		str+="</tr>";
		tbody.last().append(str);
	}
	
	function assignTypeChange(obj){
		var cmpIds=$(obj).parent().next('td').children('input[name="cmpIds"]');
		var cmpdNames=$(obj).parent().next('td').children('textarea[name="cmpNames"]');
		var selButtons=$(obj).parent().next('td').children('.button');
		if(obj.value==0||obj.value==11||obj.value==9 || obj.value==13 ||obj.value==14 || obj.value==15){
			selButtons.hide();
			cmpdNames.hide();
		}else{
			selButtons.show();
			cmpdNames.show();
		}
		cmpIds.val('');
		cmpdNames.val('');
	}
	
	 // 移动行
	 function move(tableId, direct, idx){
		var objTr=$("#"+tableId+">tbody.data>tr");
		if(objTr.length==0) return;
		
		var nodeId = tableId.split('_')[1];
		var curObj = $('#'+nodeId+'_'+idx);
		if(direct=='up'){
			var prevObj=curObj.prev();
			if(prevObj!=null){
				curObj.insertBefore(prevObj);	
			}
		}
		else{
			var nextObj=curObj.next();
			if(nextObj!=null){
				curObj.insertAfter(nextObj);
			}
		}
	};
	
	function delRows(rowId){
		var cks=$("#table_"+rowId + " input[name=nodeUserCk]:checked");
		if(cks.length==0){
			$.ligerMsg.info('请选择要删除的行!');
			return ;
		}
		for(var i=cks.length-1;i>=0;i--){
			var nodeUserId=$(cks[i]).siblings("input[name='nodeUserId']").val();
			if(nodeUserId!='' && nodeUserId!=undefined){
				$.post('${ctx}/platform/bpm/bpmDefinition/delBpmNodeUser.xht',{nodeUserId:nodeUserId});
			}
			$(cks[i]).parent().parent().remove();
			$.ligerMsg.correct('<p><font color="green">成功删除所选行的用户设置!</font></p>');
		}
	}
	

	function save(){

		var url = __ctx + "/platform/bpm/bpmNodeSet/save.xht";
		var para = $('#bpmNodeSetForm').serialize();
		$.post(url, para, showResponse);

	}

	function showResponse(data) {
		var obj = new org.sz.form.ResultMessage(data);
		if (obj.isSuccess()) {//成功
			$.ligerMessageBox.confirm('提示信息', '操作成功,继续操作吗?', function(rtn) {
				if (rtn) {
					location.reload();
				} else {
					window.close();
				}
			});
		} else {//失败
			$.ligerMessageBox.error('出错了', obj.getMessage());
		}
	};
</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">节点设置</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:this.save()">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link del" href="javascript:this.close()">关闭</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="bpmNodeSetForm" method="post" action="save.xht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th>节点名称: </th>
					<td width="15%">${activityName}</td>
					<td>
						<input type="checkbox" id="isAudit" name="isAudit" value="1" <c:if test="${item.isAudit==1}">checked="checked"</c:if> />审批
						<input type="checkbox" id="isForkJoin" name="isForkJoin" value="1" <c:if test="${item.isForkJoin==1}">checked="checked"</c:if> />分发
					</td>
				</tr>
				
				<tr>
					<th>表单类型: </th>
					<td>
						<select name="formType" class="selectForm" >
							<option value="-1">请选择..</option>
							<option value="0" <c:if test="${item.formType==0}">selected</c:if>>在线表单</option>
							<option value="1" <c:if test="${item.formType==1}">selected</c:if>>URL表单</option>
						</select>
					</td>
					<td>
						<table class="table-detail"  <c:if test="${item.formType==-1}">style="display:none"</c:if>>
							<tr>
								<td nowrap="nowrap" class="head">表单:</td>
								<td>
									<div  class="form" <c:if test="${item!=null && item.formType!=0 }">style="display: none" </c:if> >
										<input type="hidden" class="formKey" name="formKey" value="${item.formKey}">
										<input type="hidden" class="formDefName" name="formDefName" value="${item.formDefName}">
										<span name="spanForm">${item.formDefName}</span>
										<a href="#" class="link get" onclick="selectForm(this)">选择</a>
										<a href="#" class="link clean" onclick="clearForm(this)">重选</a>
										<a href="#" class="link auth" onclick="authorize(this,'${item.nodeId}')">表单授权</a>
									</div>
									<div <c:if test="${item!=null && item.formType!=1 }">style="display: none" </c:if> class="url">
										表单URL:<input type="text" name="formUrl" value="${item.formUrl }" class="inputText" size="40"/>
									</div>
									
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap" class="head">前置处理器:</td>
								<td><input type="text" name="beforeHandler" value="${item.beforeHandler}" class="inputText handler" size="40"/>
									<br>填写格式:service+方法名(参数【ProcessCmd】)
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap" class="head">后置处理器:</td>
								<td><input type="text" name="afterHandler" value="${item.afterHandler}" class="inputText handler" size="40"/>
									<br>填写格式:service+方法名(参数【ProcessCmd】)
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<th>预分配: </th>
					<td colspan="2">
							<table class="table-grid" style="width:100%">
								<c:forEach items="${nodeUserMapList}" var="nodeUserMap" varStatus="i">
								<tr>
									<td>
										<table style="width:100%" id="table_${nodeUserMap.nodeId}" class="table-grid">
											
											<tr>
												<td colspan="5" style="padding:0;margin: 0;">
													<div class="panel-toolbar">
														<div class="toolBar">
															<div class="group"><a class="link add" id="btnSearch" onclick="addRow('${nodeUserMap.nodeId}');">添加</a></div>
															<div class="l-bar-separator"></div>
															<div class="group"><a class="link del " id="btnSearch" onclick="delRows('${nodeUserMap.nodeId}');">删除</a></div>
														</div>
													</div>
												</td>
											</tr>
											<thead>
											<tr>
												<th width="30" nowrap="nowrap">序号</th>
												<th width="98" nowrap="nowrap">用户类型</th>
												<th width="*" nowrap="nowrap">用户来自</th>
												<th width="15">位置调整</th>
												<th nowrap="nowrap" width="80">运算类型</th>
											</tr>
											</thead>
											<tbody class="data">
											<c:choose>
												<c:when test="${fn:length(nodeUserMap.nodeUserList)==0}">
													<tr>
														<td nowrap="nowrap" height="28">
															<input type='checkbox' name='nodeUserCk'/>&nbsp;1
															<input type="hidden" name="nodeUserId" value=""/>
															<input type="hidden" name="nodeId" value="${nodeUserMap.nodeId}"/>
														</td>
														<td>
															<select name="assignType" class="select"onchange="assignTypeChange(this);">
																<option value="0">发起人</option>
																<option value="1" selected="selected">用户</option>
																<option value="100">多组选择</option>
																<option value="2">角色</option>
																<option value="3">组织</option>
																<option value="4">组织负责人</option>
																<option value="5">岗位</option>
																<option value="6">上下级</option>
																<option value="7">用户属性</option>
																<option value="8">组织属性</option>
																<option value="9">与发起人相同部门</option>
																<option value="10">与其他节点相同执行人</option>
																<option value="11">发起人的直属领导(组织)</option>
																<option value='13'>上个任务执行人的直属领导(组织)</option>
																<option value='14'>发起人的领导</option>
																<option value='15'>上个任务执行人的领导</option>
																<option value="12">脚本</option>
															</select>
														</td>
														<td>
															<input type="hidden" name="cmpIds" value=""/>
															<textarea name="cmpNames" style="width:80%" rows="2" class="textarea" readonly="readonly"></textarea>
															<a class="button" onclick="selectCmp(this);"><span>选择...</span></a>
														</td>
														<td>
															<a id="moveupField" class="link moveup"></a>
															<a id="movedownField" class="link movedown"></a>
														</td>
														<td>
															<select name="compType">
																<option value="0">或运算</option>
																<option value="1">与运算</option>
																<option value="2">排除</option>
															</select>
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${nodeUserMap.nodeUserList}" var="userNode" varStatus="cnt">
														<tr id="${nodeUserMap.nodeId}_${cnt.count}">
															<td  nowrap="nowrap" height="28">
																<input type='checkbox' name='nodeUserCk'/> ${cnt.count}
																<input type="hidden" name="nodeUserId" value="${userNode.nodeUserId}"/>
																<input type="hidden" name="nodeId" value="${userNode.nodeId}"/>
															</td>
															<td>
																<input type="hidden" name="assignType" value="${userNode.assignType}"/>
																<c:choose>
																	<c:when test="${userNode.assignType==0}">发起人</c:when>
																	<c:when test="${userNode.assignType==1}">用户</c:when>
																	<c:when test="${userNode.assignType==2}">角色</c:when>
																	<c:when test="${userNode.assignType==3}">组织</c:when>
																	<c:when test="${userNode.assignType==4}">组织负责人</c:when>
																	<c:when test="${userNode.assignType==5}">岗位</c:when>
																	<c:when test="${userNode.assignType==6}">上下级</c:when>
																	<c:when test="${userNode.assignType==7}">用户属性</c:when>
																	<c:when test="${userNode.assignType==8}">组织属性</c:when>
																	<c:when test="${userNode.assignType==9}">与发起人相同部门</c:when>
																	<c:when test="${userNode.assignType==10}">与其他节点相同执行人</c:when>
																	<c:when test="${userNode.assignType==11}">发起人的直属领导(组织)</c:when>
																	<c:when test="${userNode.assignType==13}">上个任务执行人的直属领导(组织)</c:when>
																	<c:when test="${userNode.assignType==14}">发起人的领导</c:when>
																	<c:when test="${userNode.assignType==15}">上个任务执行人的领导</c:when>
																	<c:when test="${userNode.assignType==12}">脚本</c:when>
																</c:choose>
															</td>
															<td>
																<input type="hidden" name="cmpIds" value='${userNode.cmpIds}'/>
																<c:choose>
																	<c:when test="${userNode.assignType==0}">
																		流程发起人<textarea name="cmpNames" style="width:80%;display:none;" rows="3" class="textarea">${userNode.cmpNames}</textarea>
																		<a class="button" onclick="selectCmp(this);" style="display:none;"><span>选择...</span></a>
																	</c:when>
																	<c:when test="${userNode.assignType==9}">
																		与发起人相同部门<textarea name="cmpNames" style="width:80%;display:none;" rows="3" class="textarea">${userNode.cmpNames}</textarea>
																		<a class="button" onclick="selectCmp(this);" style="display:none;"><span>选择...</span></a>
																	</c:when>
																	<c:when test="${userNode.assignType==11}">
																		发起人的直属领导(组织)<textarea name="cmpNames" style="width:80%;display:none;" rows="3" class="textarea">${userNode.cmpNames}</textarea>
																		<a class="button" onclick="selectCmp(this);" style="display:none;"><span>选择...</span></a>
																	</c:when>
																	<c:when test="${userNode.assignType==13}">
																		上个任务执行人的直属领导(组织)<textarea name="cmpNames" style="width:80%;display:none;" rows="3" class="textarea">${userNode.cmpNames}</textarea>
																		<a class="button" onclick="selectCmp(this);" style="display:none;"><span>选择...</span></a>
																	</c:when>
																	<c:when test="${userNode.assignType==14}">
																		发起人的领导<textarea name="cmpNames" style="width:80%;display:none;" rows="3" class="textarea">${userNode.cmpNames}</textarea>
																		<a class="button" onclick="selectCmp(this);" style="display:none;"><span>选择...</span></a>
																	</c:when>
																	<c:when test="${userNode.assignType==15}">
																		上个任务执行人的领导<textarea name="cmpNames" style="width:80%;display:none;" rows="3" class="textarea">${userNode.cmpNames}</textarea>
																		<a class="button" onclick="selectCmp(this);" style="display:none;"><span>选择...</span></a>
																	</c:when>
																	<c:otherwise>
																		<textarea name="cmpNames" readonly="readonly" style="width:80%;visibility:visible" rows="2" class="textarea">${userNode.cmpNames}</textarea>
																		<a class="button" onclick="selectCmp(this);" style="visibility:visible"><span>选择...</span></a>
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<a class="link moveup" onclick="move('table_${nodeUserMap.nodeId}','up','${cnt.count}')"></a>
																<a class="link movedown" onclick="move('table_${nodeUserMap.nodeId}','down','${cnt.count}')"></a>
															</td>
															<td>
																<select name="compType">
																	<option value="0" <c:if test="${userNode.compType==0}">selected</c:if> >或运算</option>
																	<option value="1" <c:if test="${userNode.compType==1}">selected</c:if> >与运算</option>
																	<option value="2" <c:if test="${userNode.compType==2}">selected</c:if> >排除</option>
																</select>
															</td>
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
											</tbody>
										</table>
									</td>
								</tr>
								</c:forEach>
							</table>
					</td>
				</tr>
				
				<tr>
					<th>分配规则: </th>
					<td colspan="2">
						<c:choose>
							<c:when test="${item.assignMode==null}">
								<input type="radio" id="assignMode" name="assignMode" value="0" checked="checked" />上一步选取
								<input type="radio" id="assignMode" name="assignMode" value="1" />自由选取
								<input type="radio" id="assignMode" name="assignMode" value="2" />自动分配
							</c:when>
							<c:otherwise>
								<input type="radio" id="assignMode" name="assignMode" value="0" <c:if test="${item.assignMode==0}">checked="checked"</c:if> />上一步选取
								<input type="radio" id="assignMode" name="assignMode" value="1" <c:if test="${item.assignMode==1}">checked="checked"</c:if> />自由选取
								<input type="radio" id="assignMode" name="assignMode" value="2" <c:if test="${item.assignMode==2}">checked="checked"</c:if> />自动分配
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				
				<c:if test="${type=='multiUserTask'}">
				<tr>
					<th>会签规则:  <span class="required">*</span></th>
					<td colspan="2">
						决策方式:
						<c:choose>
							<c:when test="${bpmNodeSign.signId==0}">
								<input type="radio" value="1" name="decideType" checked="checked" />通过
								<input type="radio" value="2" name="decideType"  />拒绝
							</c:when>
							<c:otherwise>
								<input type="radio" value="1" name="decideType" <c:if test="${bpmNodeSign.decideType==1}">checked="checked"</c:if>  />通过
								<input type="radio" value="2" name="decideType" <c:if test="${bpmNodeSign.decideType==2}">checked="checked"</c:if> />拒绝
							</c:otherwise>
						</c:choose>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						是否允许补签
						
						<input type="checkbox" id="isAllowAdd" name="isAllowAdd" value="1" <c:if test="${bpmNodeSign.isAllowAdd==1}"> checked="checked" </c:if> />
						<br>
						投票类型:
							<c:choose>
							<c:when test="${bpmNodeSign.signId==0}">
								<input type="radio" value="2" name="voteType" checked="checked" />绝对票数
								<input type="radio" value="1" name="voteType"  />百分比
								
							</c:when>
							<c:otherwise>
								<input type="radio" value="2" name="voteType" <c:if test="${bpmNodeSign.voteType==2}">checked="checked"</c:if> />绝对票数
								<input type="radio" value="1" name="voteType" <c:if test="${bpmNodeSign.voteType==1}">checked="checked"</c:if>  />百分比

							</c:otherwise>
						</c:choose>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						票数: 
						<input type="text" id="voteAmount" name="voteAmount" value="${bpmNodeSign.voteAmount}"  class="inputText"/>
						
					</td>
				</tr>
				</c:if>
				
				<tr>
					<th>流转模式: </th>
					<td colspan="2">
						<c:forEach items="${fn:split(item.jumpType,',')}" var="jp">
							<c:choose>
								<c:when test="${jp==1}">
									<c:set var="jumpType1" value="1"/>
								</c:when>
								<c:when test="${jp==2}">
									<c:set var="jumpType2" value="2"/>
								</c:when>
								<c:when test="${jp==3}">
									<c:set var="jumpType3" value="3"/>
								</c:when>
								<c:when test="${jp==4}">
									<c:set var="jumpType4" value="4"/>
								</c:when>
							</c:choose>
						</c:forEach>
						<input type="checkbox" class="jumpType" name="jumpType" value="1" <c:if test="${jumpType1==1}">checked="checked"</c:if> />正常跳转
						<input type="checkbox" class="jumpType" name="jumpType" value="2" <c:if test="${jumpType2==2}">checked="checked"</c:if> />选择路径跳转 
						<input type="checkbox" class="jumpType" name="jumpType" value="3" <c:if test="${jumpType3==3}">checked="checked"</c:if> />回退
						<input type="checkbox" class="jumpType" name="jumpType" value="4" <c:if test="${jumpType4==4}">checked="checked"</c:if> />自由跳转
						<c:remove var="jumpType1"/>
						<c:remove var="jumpType2"/>
						<c:remove var="jumpType3"/>
						<c:remove var="jumpType4"/>
					</td>
				</tr>
			</table>
			
			
			<input type="hidden" id="defId" name="defId" value="${defId}" />
			<input type="hidden" id="actDefId" name="actDefId" value="${actDefId}" />
			<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}" />
			<input type="hidden" name="nodeName" value="${item.nodeName}" />
			<input type="hidden" id="signId" name="signId" value="${bpmNodeSign.signId}" />
			<input type="hidden" id="type" name="type" value="${type}" />
		</form>
	</div>
</div>
</body>
</html>
