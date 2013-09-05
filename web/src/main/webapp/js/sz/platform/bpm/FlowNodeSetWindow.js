/**
 * 设置节点设置对话框。
 * conf参数属性：
 * activitiId:节点ID
 * defId:流程定义ID
 * @param conf
 */
function FlowNodeSetWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/bpmNodeSet/edit.xht?nodeId=" + conf.activitiId 
	              +"&defId=" + conf.defId
	              +"&actDefId="+conf.actDefId
	             // +"&activityName="+conf.activityName
	              +"&type="+conf.type;

	var dialogWidth=800;
	var dialogHeight=500;
	conf = $.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs = "dialogWidth=" + conf.dialogWidth + "px;dialogHeight=" + conf.dialogHeight + "px;help=" + conf.help + ";status=" + conf.status + ";scroll=" + conf.scroll + ";center=" + conf.center;
	url = url.getNewUrl();
	var rtn = window.showModalDialog(url, "", winArgs);
}