<%
	//任务设置执行路径
%>
<%@page pageEncoding="UTF-8"%>
<html>
	<head>
		<title>更改任务执行的路径</title>
		<%@include file="/commons/include/form.jsp" %>
		<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
		<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
		<script type="text/javascript">
			//更改任务执行路径
			function saveTaskChangePath(){
				if($('#destTask').val()==''){
					$.ligerMessageBox.confirm("提示信息","请选择更换的目标节点!");
					return;
				}
				var params=$('#taskForm').serialize();
				$.post('${ctx}/platform/bpm/task/saveChangePath.xht',params,function(){
					parent.location.reload();
					window.close();
				});
			}
			
			//更改
			function changeDestTask(sel){
				$('#lastDestTaskId').val(sel.value);
				if(sel.value!=""){
					var nodeId=sel.value;
					$.getJSON("${ctx}/platform/bpm/task/getTaskUsers.xht?taskId=${taskEntity.id}&nodeId="+nodeId, function(dataJson){
						$('#jumpUserLink').siblings('span').remove();	
						var data=eval(dataJson);
						for(var i=0;i<data.length;i++){
							  var span="<span><input type='checkbox' name='" + nodeId + "_userId' checked='checked' value='"+data[i].userId+"'/>&nbsp;"+data[i].fullname+"</span>";
							  $('#jumpUserLink').before(span);
						}
					});
				}else{
					$('#jumpUserLink').siblings('span').remove();	
				}
			}
			
			//为目标节点选择执行的人员列表
			function selectExeUsers(nodeId,link){
				$('#link').before('span').remove();
				UserDialog({callback:function(uIds,uNames){
					if(uIds!=null){
						var ids=uIds.split(',');
						var names=uNames.split(',');
						for(var i=0;i<ids.length;i++){
							var span="<span><input type='checkbox' name='" + nodeId + "_userId' checked='checked' value='"+ids[i]+"'/>&nbsp;"+names[i]+"</span>";
							$(link).before(span);
						}
					}
				}});
			}
		</script>
	</head>
	<body>
	<div class="panel">
		<div class="panel-top">
		   <div class="tbar-title">
		    	<span class="tbar-label">任务设置执行路径</span>
		   </div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="btnSearch" onclick="saveTaskChangePath()">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del" onclick="javasrcipt:window.close()">关闭</a></div>
			    </div>
			</div>
		</div>
	 <div class="panel-body">
		<form id="taskForm">
			<table class="table-detail">
				<tr>
					<th nowrap="nowrap">当前任务</th>
					<td>
						<input type="hidden" name="taskId" value="${taskEntity.id}"/>
						${taskEntity.name}
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">目标节点</th>
					<td>
						<select name="destTask" id="destTask" onchange="changeDestTask(this)">
							<option value="">请选择目标节点..</option>
							<c:forEach items="${taskNodeMap}" var="map" varStatus="i">
								<option value="${map.key}">${map.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">
						节点对应的执行人
					</th>
					<td>
						<input type="hidden" id="lastDestTaskId" name="lastDestTaskId" value="">
						<div id="jumpUserDiv">
							<a href="#" id="jumpUserLink" class="link get" onclick="selectExeUsers('${nodeUserMap.nodeId}',this)">&nbsp;&nbsp;</a>
						</div>
					</td>
				</tr>
				<tr>
					<th>更改备注</th>
					<td>
						<textarea rows="5" cols="60" id="voteContent" name="voteContent" maxlength="512">${curUser.fullname}进行任务路径更改!</textarea>
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">通知</th>
					<td>
						<input type="checkbox" value="1" checked="checked">手机短信 &nbsp;<input type="checkbox" value="1" checked="checked"> 邮件
					</td>
				</tr>
			</table>
		</form>
	 </div>
	</div>
	</body>
</html>