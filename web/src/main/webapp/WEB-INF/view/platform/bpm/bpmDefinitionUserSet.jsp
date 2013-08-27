<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>流程节点人员设置</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/ScriptDialog.js" ></script>
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerMsg.js"></script>
	<script type="text/javascript">
		
		
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
		
		function selectScript(){
			var objConditionCode=$("#txtScriptData")[0];
			ScriptDialog({callback:function(script){
				jQuery.insertText(objConditionCode,script);
			}});
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
		
		var win;
		function showScript(obj){
			$("#txtScriptData").val(obj.val());
			
			var divObj=$("#divScriptData");
			win= $.ligerDialog.open({ target:divObj , height: 350,width:500, modal :true,
				buttons: [ { text: '确定', onclick: function (item, dialog) { 
						obj.val($("#txtScriptData").val());
						dialog.hide();
					} 
				}, 
				{ text: '取消', onclick: function (item, dialog) { dialog.hide(); } } ] }); 
			
		}
		
		function changeVar(obj){
			var val=$(obj).val();
			var objScript=$("#txtScriptData")[0];
			jQuery.insertText(objScript,val);
		}
		
		//显示其他节点的对话框
		function showOtherNodeDlg(conf){
			var winArgs="dialogWidth=650px;dialogHeight=500px;help=0;status=0;scroll=1;center=1";
			url=__ctx + "/platform/bpm/bpmDefinition/taskNodes.xht?actDefId=${bpmDefinition.actDefId}&nodeId="+conf.nodeId;
			url=url.getNewUrl();
			var rtn=window.showModalDialog(url,"",winArgs);
			if(conf.callback){
				if(rtn!=undefined){
					conf.callback.call(this,rtn.nodeId,rtn.nodeName);
				}	
			}
		}
		$(function(){
			$("a.del").unbind("click");
		});
	</script>
</head>
<body>
    <%@include file="incDefinitionHead.jsp" %>
	<f:tab curTab="4" tabName="flow"/>
			<form action="saveUser.xht" method="post" id="defUserForm">
				<input type="hidden" name="defId" value="${defId}"/>
				<table class="table-grid" style="width:100%">
					<thead>
					<tr>
						<th width="15" nowrap="nowrap">序号</th>
						<th >节点名称</th>
						<th width="*">节点人员</th>
					</tr>
					</thead>
					<c:forEach items="${nodeUserMapList}" var="nodeUserMap" varStatus="i">
					<tr>
						<td>${i.count}</td>
						<td>${nodeUserMap.nodeName}(${nodeUserMap.nodeId})</td>
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
									<th width="80" nowrap="nowrap">序号</th>
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
					<tr>
						<td colspan="4" style="text-align: center">
							<a class="button" onclick="$('#defUserForm').submit();"><span class="icon save"></span><span >保 存</span></a>
						</td>
					</tr>
				</table>
				<div style="height:40px"></div>
			</form>
			<div id="divScriptData" style="display: none;">
				
				<a href="#" id="btnScript" class="link var" title="常用脚本" onclick="selectScript()">常用脚本</a>
				<ul>
					<li>可以使用的流程变量,[startUser],开始用户,<li>[startUser],上个任务的用户[prevUser]。</li>
					<li>表达式必须返回Set&lt;String&gt;集合类型的数据。</li>
				</ul>
				<textarea id="txtScriptData" rows="10" cols="80" style="height: 200px;width:480px"></textarea>
			</div>
</body>
</html>
