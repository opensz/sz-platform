//任务补签窗口  依赖于LigerWindow,UserDialog
function TaskAddSignWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx+'/platform/bpm/task/toAddSign.xht?taskId=' + conf.taskId;
	
	var dialogWidth=500;
	var dialogHeight=200;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1,reload:true},conf);
	

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this);
		}
	}
	
};



