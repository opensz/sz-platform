<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>流程定义明细</title>
    <%@include file="/commons/include/form.jsp" %>
   
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/SignRuleWindow.js" ></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowRuleWindow.js" ></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowVarWindow.js" ></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/ForkConditionWindow.js" ></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowEventWindow.js" ></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowForkJoinWindow.js" ></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowMessageWindow.js" ></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowReminderWindow.js" ></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowApprovalItemWindow.js" ></script>
    <script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowNodeSetWindow.js" ></script>
    <script type="text/javascript">
    		var defId ="${bpmDefinition.defId}";
    		var actDefId ="${bpmDefinition.actDefId}";
    		var deployId= "${bpmDefinition.actDeployId}";
            var menu;
            //判断参数是否加载
            var isOtherParamFrm=false;
            var currentObj=null;
            $(function (){
                 $("div.flowNode").bind("contextmenu", function (e){
              	   currentObj=$(this);
              	   var type=currentObj.attr("type");
              	   //获取菜单
              	   items=getItems(type);
              	   if(items.length>0){
              		 	menu = $.ligerMenu({ top: 100, left: 100, width: 140, items:items});
              		    menu.show({ top: e.pageY, left: e.pageX  });   
              	   }
                   return false;
                 });

            });
            
            
            function getItems(type)
            {
            	var items=[];
            	if(type=="startEvent" ){
            		items= [{id:'flowForm', text: '设置表单',click:clickHandler },
                            {id:'flowEvent', text: '事件设置',click:clickHandler }];
            	}
            	else if(type=="endEvent"){
            		items= [{id:'flowEvent', text: '事件设置',click:clickHandler }];
            	}
            	else if(type=="parallelGateway"){
            		items= [];
            	}
            	else if(type=="inclusiveGateway" || type=="exclusiveGateway"){
            		items= [{id:'flowCodition', text: '设置分支条件',click:clickHandler }];
            	}
            	else if(type=="multiUserTask"){
            		items= [
            		        {id:'flowRule', text: '跳转规则设置',click:clickHandler },
            		        {id:'flowVote', text: '会签投票规则设置',click:clickHandler },
            		        {id:'flowEvent', text: '事件设置',click:clickHandler },
            		        {id:'flowApproval', text: '常用语设置',click:clickHandler },
            		        {id:'flowNodeSet', text: '节点设置',click:clickHandler }
            		        ];
            	}
            	else if(type=="userTask"){
            		items= [
            		        {id:'flowRule', text: '跳转规则设置',click:clickHandler },
            		        {id:'flowEvent', text: '事件设置',click:clickHandler },
            		        {id:'flowDue', text: '流程催办设置',click:clickHandler },
            		        {id:'flowForkJoin', text: '流程分发汇总',click:clickHandler },
            		        {id:'flowApproval', text: '常用语设置',click:clickHandler },
            		        {id:'flowNodeSet', text: '节点设置',click:clickHandler }
            		        ];
            	}
            	else if(type=="email"){
            		items= [{id:'flowMessage', text: '消息参数',click:clickHandler }];
            	}
            	else if(type=="script"){
            		items= [{id:'flowEvent', text: '设置脚本',click:clickHandler }];
            	}
            	return items;
            }

            var signRule;
            var flowRule;
            var forkCondition;

            function clickHandler(item,i){
            	//节点类型
            	var type=currentObj.attr("type"); 
            	//任务id
            	var activitiId=currentObj.attr("id");
            	
            	var activityName=currentObj.attr("title");
            	
            	var menuId=item.id;
            	if(menuId=="flowVote" && type=="multiUserTask"){
            		SignRuleWindow({actDefId:actDefId,activitiId:activitiId});
            	}
            	else if(menuId=="flowRule"){
            		FlowRuleWindow({deployId:deployId,actDefId:actDefId,nodeId:activitiId,nodeName:activityName});
            	}
            	else if(menuId=="flowCodition"){
            		ForkConditionWindow({defId:defId,deployId:deployId,nodeId:activitiId});
           		}
            	else if(menuId=="flowMessage"){
            		FlowMessageWindow({actDefId:actDefId,nodeId:activitiId});
            	}
            	else if(menuId=="flowEvent"){
            		//开始事件 后置脚本 type startEvent
            		//结束事件 前置脚本 endEvent
            		//用户任务 多实例任务 前后 userTask  multiUserTask
            		//脚本节点 脚本节点 script
            		FlowEventWindow({type:type,actDefId:actDefId,nodeId:activitiId,defId:defId});
            	}
            	else if(menuId=="flowDue"){
            		FlowReminderWindow({actDefId:actDefId,nodeId:activitiId});
            	}else if(menuId=="flowForkJoin"){
            		var win=FlowForkJoinWindow({actDefId:actDefId,nodeId:activitiId,activityName:activityName});
            		win.show();
            	}else if(menuId=="flowApproval"){ // 常用语设置
            		FlowApprovalItemWindow({activitiId:activitiId,defId:defId,actDefId:actDefId});
            	}else if(menuId=="flowNodeSet"){ // 节点设置
					FlowNodeSetWindow({
						activitiId : activitiId,
						defId : defId,
						actDefId : actDefId,
						//activityName : activityName,
						type : type
					});
				}
			}
</script> 
	<style type="text/css"> 
	    body{ padding:0px; margin:0;overflow:auto;}  
	    div.flowNode{cursor:pointer;}
	</style>
</head>
<body>
	<div class="panel">
			<%@include file="incDefinitionHead.jsp" %>
			<f:tab curTab="3" tabName="flow"/>
			<div style="position: relative;background:url('${ctx}/bpmImage?deployId=${bpmDefinition.actDeployId}')  no-repeat;width:${shapeMeta.width}px;height:${shapeMeta.height}px;">
					${f:unescapeHtml(shapeMeta.xml)} 
			</div>
	</div>
</body>
</html>
