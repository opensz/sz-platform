<%
	//显示流程示意图及显示每个任务节点上的执行人员
%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>流程执行示意图</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/plugins/jquery-powerFloat.js"></script>
<link href="${ctx}/jslib/jquery/plugins/powerFloat.css" rel="stylesheet" type="text/css" />
<style type="text/css">
div.taskInfo{position:absolute;width:400px;height:275px;display:none;background-color: #ffffff;border:1px solid #B0CBED;}
div.header{
	background-image: url(${ctx}/themes/css/default/img/displaytag/tbar-title.png);height:20px;
	font-weight: bold;font-size: 14px;padding-top: 5px;padding-left: 5px;margin: 0 0 0 0;
}
div.divBody{overflow: auto;height:250px;margin: 0 0 0 0;}
</style>

<script type="text/javascript">
	var processInstanceId="${processInstanceId}";
	var isStatusLoaded=false;
	
	$(function(){
		$.each($("div.flowNode"),function(){
			if($(this).attr('type')=='userTask' || $(this).attr('type')=='multiUserTask'){
				$(this).css('cursor','pointer');
				var nodeId=$(this).attr('id');
				var url="${ctx}/platform/bpm/processRun/taskUser.xht?processInstanceId="+processInstanceId+"&nodeId=" + nodeId;
				$(this).powerFloat({ eventType: "click", target:url, targetMode: "ajax"});
			}
		});
		
		loadStatus();
		
		eventHandler();
	});
	
	//事件处理
	function eventHandler(){
//		$("div.flowNode").mouseover(mouseEnter);
		
		$(document).click(function(){
			$("#taskInfo").hide();
		});
		$("#taskInfo").click(function(e){
			e.stopImmediatePropagation();
		});
	}
	//鼠标进入事件处理
	function mouseEnter(){
		if(isStatusLoaded){
			var obj=$(this);
			var taskId=obj.attr("id");
			var rtn=getTableHtml(taskId);
			if(rtn){
				showDiv(obj);
			}
		}
		else{
			loadStatus();
		}
	}
	//显示div。
	function showDiv(obj){
		var top= ( parseInt( obj.css("top").replace("px",""))) + "px";
		var left= (parseInt(obj.css("left").replace("px","")) + obj.width()+5) + "px";
		var divObj=$("#taskInfo");
		divObj.css({"top":top,"left":left}).show();
		var offsetHeight=document.body.offsetHeight;
		var offsetWidth=document.body.offsetWidth;
		var scrollHeight=  document.body.scrollHeight;
		var scrollWidth=  document.body.scrollWidth;
		var height=scrollHeight-offsetHeight;
		var width=scrollWidth-offsetWidth;
		$(document.body).scrollLeft(width);
		$(document.body).scrollTop(height);
	}

	//状态数据
	var aryResult=null;
	//加载流程状态数据。
	function loadStatus(){
		var url="${ctx}/platform/bpm/processRun/getFlowStatusByInstanceId.xht";
		var params={instanceId:processInstanceId};
		$.post(url,params,function(result){
			aryResult=result;
			isStatusLoaded=true;
		});
	}

	//构建显示的html
	function getTableHtml(taskId){
		
		var node=getNode(taskId);
		if(node==null) return false;
		var aryOptions=node.taskOpinionList;
		var divBody=$("#taskInfo > .divBody");
		divBody.empty();
		
		for(var i=0;i<aryOptions.length;i++){
			var p=aryOptions[i];
			var sb=new StringBuffer();
			
			var exeFullname=p.exeFullname;
			var taskName=p.taskName;
			var startTime=p.startTimeStr;
			var endTime=p.endTimeStr;
			var status=p.status;
			var opinion=p.opinion==null ?"无":p.opinion;
			var durTime=p.durTimeStr;

			sb.append('<table class="table-detail" cellpadding="0" cellspacing="0" border="0">');
			
			sb.append('<tr><th width="20%">任务名: </th>');
			sb.append('<td>'+taskName+'</td></tr>');
			
			sb.append('<tr><th width="20%">执行人: </th>');
			sb.append('<td>'+exeFullname+'</td></tr>');
			
			sb.append('<tr><th width="20%">开始时间: </th>');
			sb.append('<td>'+startTime+'</td></tr>');
			
			sb.append('<tr><th width="20%">结束时间: </th>');
			sb.append('<td>'+endTime+'</td></tr>');
			
			sb.append('<tr><th width="20%">时长: </th>');
			sb.append('<td>'+durTime+'</td></tr>');
			
			sb.append('<tr><th width="20%">状态: </th>');
			sb.append('<td>'+status+'</td></tr>');
			
			sb.append('<tr><th width="20%">意见: </th>');
			sb.append('<td>'+opinion+'</td></tr>');
			
			sb.append("</table><br>");
		
			divBody.append(sb.toString());
			
			 
		}
		var rtn=aryOptions.length>0;
		return rtn;
	}
	
	//从返回的结果中返回状态数据。
	function getNode(taskId){
		if(aryResult==null) return null;
		for(var i=0;i<aryResult.length;i++){
			var node=aryResult[i];
			var taskKey=node.taskKey;
			if(taskId==taskKey){
				return node;
			}
		}
		return null;
	}
	

</script>
<style type="text/css">
	ul.legendContainer{
		position: relative;
		top:10px;
		left:10px;
	}
	
	ul.legendContainer li{
		list-style: none;
		font-size: 14px;
		display: inline-block;
		font-weight: bold;
	}
	
	ul.legendContainer li .legend{
		width:14px;
		height: 14px;
		border: 1px solid black;
		margin-right:5px;
		margin-top:2px;
		float: left;
	}
	

	 
</style>
</head>
<body>
	<ul class="legendContainer">
		<li><div style="background-color:gray; " class="legend"></div>未执行</li>
		<li><div style="background-color:#00FF00;" class="legend"></div>同意</li>
		<li><div style="background-color:orange;" class="legend"></div>弃权</li>
		<li><div style="background-color:red;" class="legend"></div>当前节点</li>
		<li><div style="background-color:blue;" class="legend"></div>反对</li>
		<li><div style="background-color:#8A0902;" class="legend"></div>驳回</li>
		<li><div style="background-color:#023B62;" class="legend"></div>追回</li>
	</ul>
	<div style="padding-top:40px;background-color: white;">
		<div><b>说明：</b>点击任务节点可以查看节点的执行人员</div>
		<div id="divTaskContainer" style="margin:0 auto;  position: relative;background:url('${ctx}/bpmImage?processInstanceId=${processInstanceId}&randId=<%=Math.random()%>') no-repeat;width:${shapeMeta.width}px;height:${shapeMeta.height}px;">
			${shapeMeta.xml} 
			
			
			<div id="taskInfo" class="taskInfo">
				<div class="header">
					任务执行情况
				</div>
				<div class="divBody">
				</div>
			</div>
		</div>
	</div>
</body>
</html>