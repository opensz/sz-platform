
/**
 * 设置网关跳转规则。
 * conf参数属性：
 * deployId:activiti流程定义ID
 * defId:流程定义ID
 * nodeId:流程节点ID
 * @param conf
 */
function ForkConditionWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/bpmDefinition/setCondition.xht?deployId="+conf.deployId+"&defId=" + conf.defId +"&nodeId=" + conf.nodeId;
	
	var dialogWidth=650;
	var dialogHeight=350;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:1,scroll:1,center:1},conf);
	

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
}