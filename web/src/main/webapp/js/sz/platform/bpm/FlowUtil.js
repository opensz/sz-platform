if (typeof FlowUtil == 'undefined') {
	FlowUtil = {};
}

/**
 * 启动流程。
 * @param defId 流程定义ID。
 */
FlowUtil.startFlow=function(defId){
	var url= __ctx +"/platform/bpm/bpmDefinition/getCanDirectStart.xht";
	var params={defId:defId};
	$.post(url,params,function(data){
		if(data && data.needStartForm == 0){
			bpmDefinition = data;
//			var callBack=function(rtn){
//				if(!rtn) return;
//				top.showMask("正在创建，请稍候...");
				var flowUrl= __ctx +"/platform/bpm/task/ajaxStartFlow.xht";
				var actDefId=bpmDefinition.actDefId;
				var parameters={actDefId:actDefId};
				$.post(flowUrl,parameters,function(responseText){
//					top.hideMask();
					if(responseText){//成功
//						$.ligerMessageBox.success('提示信息',"启动流程成功!",function(){
							var url="/platform/bpm/task/toStart.xht?taskId="+responseText;
							//打开启动流程tab
//							 top.addToTab({
//								 resId:'taskStartFlowForm',
//								 resName:'流程启动',
//								 isExt:0,
//								 defaultUrl:url
//							});
							var icon = __ctx +　"/styles/default/images/resicon/setting.png";
							top.addToTab(url,"流程启动","taskStartFlowForm",icon);
//						});
					}
					else{
						$.ligerMessageBox.error('出错了',"启动流程失败!");
					}
				});
//			};
//			$.ligerMessageBox.confirm('提示信息',"需要启动流程吗?",callBack);
		}
		else{
			//var url=__ctx +"/platform/bpm/task/startFlowForm.xht?defId="+defId;
			var url = "/platform/bpm/task/startFlowForm.xht?defId="+defId;
			var icon = __ctx +　"/styles/default/images/resicon/setting.png";
//			jQuery.openFullWindow(url);
			top.addToTab(url,"流程启动","taskStartFlowForm",icon);
			
//		 	top.addToTab({
//				 resId:'taskStartFlowForm',
//				 resName:'流程启动',
//				 isExt:0,
//				 defaultUrl:url
//			});
			
		}
	});
};

FlowUtil.startFlow1=function(serviceItemId,flowKey,deskRequestId){
	var url= __ctx +"/platform/bpm/bpmDefinition/getCanDirectStart.xht";
	var params = {
		serviceItemId : serviceItemId,
		actDefKey : flowKey,
		deskRequestId : deskRequestId
	};
	$.post(url,params,function(data){
		if(data && data.needStartForm == 0){
			bpmDefinition = data;
//			var callBack=function(rtn){
//				if(!rtn) return;
				top.showMask("正在创建，请稍候...");
				var flowUrl= __ctx +"/platform/bpm/task/ajaxStartFlow.xht";
				var actDefId=bpmDefinition.actDefId;
				var parameters={actDefId:actDefId,serviceItemId : serviceItemId, deskRequestId : deskRequestId};
				$.post(flowUrl,parameters,function(responseText){
					top.hideMask();
					if(responseText){//成功
//						$.ligerMessageBox.success('提示信息',"启动流程成功!",function(){
							var url="/platform/bpm/task/toStart.xht?taskId="+responseText;
							//打开启动流程tab
							 top.addToTab({
								 resId:'taskStartFlowForm',
								 resName:'流程启动',
								 isExt:0,
								 defaultUrl:url
							});
//						});
					}
					else{
						$.ligerMessageBox.error('出错了',"启动流程失败!");
					}
				});
//			};
//			$.ligerMessageBox.confirm('提示信息',"需要启动流程吗?",callBack);
		}else{
			var url = "/platform/bpm/task/startFlowForm.xht?serviceItemId="+serviceItemId+"&actDefKey="+flowKey+"&deskRequestId="+deskRequestId;
			var icon = __ctx +　"/styles/default/images/resicon/setting.png";
			
		 	top.addToTab({
				 resId:'taskStartFlowForm',
				 resName:'流程启动',
				 isExt:0,
				 defaultUrl:url
			});
		}
	});
};