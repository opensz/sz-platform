<%--
	time:2011-12-23 16:15:45
	desc:edit the SYS_USER_AGENT
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑用户代理</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysUserAgent"></script>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/BpmDefinitionDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#sysUserAgentForm').submit(); 
			});
			bpmAgent();
			
			initCalendar();
		});
		function add(){
			UserDialog({
						isSingle:true,
						callback:function(userIds, fullnames){
									$("#touserid").val(userIds);
									$("#tofullname").val(fullnames);
						}
					}
			);
		};
		
		function initCalendar(){
			$("body").delegate("input.Wdate", "focus", function(){
				WdatePicker({el:this,dateFmt:"yyyy-MM-dd"});
			});
		}
		
		function dlgCallBack(defIds,subjects){
			//删除空的记录
			$('#firstRow').remove();
			var newDefIds=defIds.split(",");
			var newSubjects=subjects.split(",");
			//已经存在的流程定义IDs
			var existDefIds=$("input[name='defId']");
			//判断新增定义，是否已存在，若存在即不再保存
			var postDefIds=[];
			var postSubjects=[];
			for(var i=0;i<newDefIds.length;i++){
				//新增加的定义
				var isNew=true;
				for(var j=0;j<existDefIds.length;j++){
					if(newDefIds[i]==$(existDefIds[j]).val()){
						isNew=false;
						break;
					}
				}
				if(isNew){
					postDefIds.push(newDefIds[i]);
					postSubjects.push(newSubjects[i]);
				}
			}
			if(postDefIds.length>0){
				for(var i=0;i<postDefIds.length;i++){
					var tr='';
					tr+='<tr>';
					tr+='<td>';
					tr+='<input type="hidden" name="defId" value="'+postDefIds[i]+'">';
					tr+='<a href="${ctx}/platform/bpm/bpmDefinition/get.xht?defId='+postDefIds[i]+'" target="_blank">';
					tr+=postSubjects[i] + '</a>';
					tr+='</td>';
					tr+='<td>';
					tr+='	<a href="#" class="link-del" onclick="singleDell(this);"><span class="link-btn">删除</span></a>';
					tr+='</td>';
					tr+='</tr>';
					$("#bpmAgentItem").append(tr);
				}
			}
		
		};

		function addFlow(){
			 BpmDefinitionDialog({isSingle:true,callback:dlgCallBack});
		};
		function singleDell(obj){
			var tr=$(obj).parents('tr');
			$(tr).remove();
		};
		
		function bpmAgent(){
			var isall=$(':input:radio:checked').val();
			if(isall==0){
				$("#bpmAgent").show();
			}else{
				$("#bpmAgent").hide();
			}
		}
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
				<c:if test="${sysUserAgent.agentid eq null}">添加代理授权</c:if>
				<c:if test="${not empty sysUserAgent.agentid }">编辑代理授权</c:if>   
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="save bar-button" id="dataFormSave" href="#">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="back bar-button" href="list.xht">返回</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body" >
				<form id="sysUserAgentForm" method="post" action="save.xht">
					<input type="hidden" id="touserid" name="touserid" value="${sysUserAgent.touserid}"  class="inputText"/>
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					   <tr>
							<th width="20%">执行代理人: </th>
							<td>
							<input type="text" id="tofullname" name="tofullname" value="${sysUserAgent.tofullname}"  class="inputText" readonly="readonly" size="40"/>
							<a href="#" class="button" onclick="add();"><span>选 择...</span></a>
							</td>
						</tr>
						<tr>
						</tr>
						<tr>
							<th width="20%">开始时间: </th>
							<td><input type="text" id="starttime" name="starttime" value="<fmt:formatDate value='${sysUserAgent.starttime}' pattern='yyyy-MM-dd'/>" class="inputText Wdate"/></td>
						</tr>
						<tr>
							<th width="20%">结束时间: </th>
							<td><input type="text" id="endtime" name="endtime" value="<fmt:formatDate value='${sysUserAgent.endtime}' pattern='yyyy-MM-dd'/>" class="inputText Wdate"/></td>
						</tr>
						<tr>
							<th width="20%">是否全权代理: </th>
							<td>
								是<input type="radio" id="isall" name="isall" value="1" checked="checked" onchange="bpmAgent()" <c:if test="${sysUserAgent.isall==1}"> checked="checked" </c:if> />
								否<input type="radio" id="isall" name="isall" value="0" onchange="bpmAgent()" <c:if test="${sysUserAgent.isall==0}"> checked="checked" </c:if> >
							</td>
							
						</tr>
						<tr>
							<th width="20%">是否有效: </th>
							<td>
								是<input type="radio" id="isvalid" name="isvalid" value="1" checked="checked" <c:if test="${sysUserAgent.isvalid==1}"> checked="checked" </c:if> />
								否<input type="radio" id="isvalid" name="isvalid" value="0"  <c:if test="${sysUserAgent.isvalid==0}"> checked="checked" </c:if> >	
							</td>
						</tr>
					</table>
					<input type="hidden" name="agentid" value="${sysUserAgent.agentid}" />
				<div>
				<div style="display:none;" id="bpmAgent">
				<div class="panel-data" >
					<div class="panel-top">
						<div class="tbar-title">
							<span class="tbar-label">授权流程</span>
						</div>
						<div class="panel-toolbar">
							<div class="toolBar">
								<div class="group">
									<a class="add bar-button" href="#" onclick="addFlow();">添加代理流程</a>
								</div>
							</div>
						</div>
					</div>
				</div>		
			   	<table id="bpmAgentItem" class="table-grid table-list" id="0" cellpadding="1" cellspacing="1" style="width:100%">
			   		<thead>
			    		<th>流程名称</th>
			    		<th width="80px">管理</th>
			    	</thead>
			    	<tbody>
			    	<c:choose>
			    		<c:when test="${fn:length(bpmAgentList)>0}">
			    			<c:forEach items="${bpmAgentList}" var="bpmAgentItem">
					    		<tr>
					    			<td>
					    				<input type="hidden" name="defId" value="${bpmAgentItem.defid}" />
					    				<a href="${ctx}/platform/bpm/bpmDefinition/get.xht?${bpmAgentItem.defid}" target="_blank">${bpmAgentItem.subject}</a>
					    			</td>
					    			<td>
					    				<a href="#" class="link-del" onclick="singleDell(this);"><span class="link-btn">删除</span></a>
									</td>
					    		</tr>
					    	</c:forEach>
			    		</c:when>
			    		<c:otherwise>
			    			<tr id="firstRow">
			    				<td colspan="2" align="center">
			    					<font color='red'>没有选择代理的流程定义！</font>
			    				</td>
			    			</tr>
			    		</c:otherwise>
			    	</c:choose>
			    	</tbody>
			    </table>
				</form>
		</div>
</div>
</body>
</html>
