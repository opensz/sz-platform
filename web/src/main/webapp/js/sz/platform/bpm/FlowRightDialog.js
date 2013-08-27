/**
 * 流程分配权限。
 * 
 * @param defId		流程定义ID
 * @param typeId	流程分类ID
 * @param type		流程类型(0,流程定义ID,1,分类类型)
 */
function FlowRightDialog(id,type){
	var url=__ctx +"/platform/bpm/bpmDefRight/list.xht?id=" + id +"&type=" +type;
	var winArgs="dialogWidth:650px;dialogHeight:390px;help:0;status:0;scroll:0;center:1;resizable:1";
	url=url.getNewUrl();
 	var rtn=window.showModalDialog(url,"",winArgs);
};
/**
 * 业务数据浏览模板权限分配
 * @param id 模板ID
 * @param type 分类搜索类型(0-->模板ID;1-->表单类型)
 */
function FlowTemplateDialog(id,type){
 	var url=__ctx +"/platform/form/bpmTableTemprights/list.xht?id=" + id+"&type=" +type;
	var winArgs="dialogWidth:650px;dialogHeight:390px;help:0;status:0;scroll:0;center:1;resizable:1";
	url=url.getNewUrl();
 	var rtn=window.showModalDialog(url,"",winArgs);
}