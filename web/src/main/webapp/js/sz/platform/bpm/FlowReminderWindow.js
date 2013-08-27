/**
 * 流程节点催办时间设置窗口。
 * conf:参数如下：
 * actDefId：流程定义ID
 * nodeId：流程节点Id
 * dialogWidth：窗口宽度，默认值650
 * dialogWidth：窗口宽度，默认值400
 * @param conf
 */

function FlowReminderWindow(conf)
{
	var url=__ctx + "/platform/bpm/taskReminder/edit.xht?actDefId="+conf.actDefId+"&nodeId=" + conf.nodeId;
	var winArgs="width=650,height=400,status=no,menubar=no,location=no";
	var rtn=window.open(url,"",winArgs);
}