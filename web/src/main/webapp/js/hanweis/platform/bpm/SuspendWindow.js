//挂起
function suspendWindow(caseId)
{
	var isValidate = validateCaseForm();
	if(isValidate){
		var url= __ctx + "/itsm/support/case/suspend.xht?caseId="+caseId;

		var dialogWidth=600;
		var dialogHeight=250;
		var conf = $.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1});
		var winArgs = "dialogWidth=" + conf.dialogWidth + "px;dialogHeight=" + conf.dialogHeight + "px;help=" + conf.help + ";status=" + conf.status + ";scroll=" + conf.scroll + ";center=" + conf.center;
		url = url.getNewUrl();
		var rtn = window.showModalDialog(url, window, winArgs);
	}else{
	
		$.ligerMessageBox.warn('提示信息','请将工单信息填写完整!');
	}
	
}

