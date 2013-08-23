/**
 * 功能:流程运行对话框
 * 
 * 参数：
 * conf:配置为一个JSON
 * 
 * dialogWidth:对话框的宽度。
 * dialogHeight：对话框高度。
 * 
 * actDefId:流程定义ID。
 * 
 */
function TaskImageUserDialog(conf)
{
	if(!conf) conf={};	
	var url=__ctx + '/platform/bpm/processRun/userImage.xht?taskId=' + conf.taskId;
	
	var winArgs="dialogWidth=800px;dialogHeight=600px;help=1;status=1;scroll=1;center=1;resizable:1";
	url=url.getNewUrl();
	window.showModalDialog(url,"",winArgs);
	
}