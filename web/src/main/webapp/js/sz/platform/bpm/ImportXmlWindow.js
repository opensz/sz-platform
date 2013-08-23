function ImportXmlWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/bpmDefinition/import.xht";
	
	
	var dialogWidth=550;
	var dialogHeight=250;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
}