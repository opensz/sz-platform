/**
 * 通用对话框。
 * @param alias		对话框别名。
 * @param callBack	回调函数。
 * 调用示例：
 * CommonDialog("role",function(data){
 * 
 * });
 * data:为json数组或者为json对象。
 */
function CommonDialog(alias,callBack){
	if(alias==null || alias==undefined){
		$.ligerMessageBox.warn('提示信息',"别名为空！");
		return;
	}
	var url=__ctx + "/platform/form/bpmFormDialog/dialogObj.xht?alias=" +alias;
	url=url.getNewUrl();
	$.post(url,{"alias":alias},function(data){
		if(data.success==0){
			$.ligerMessageBox.warn('提示信息',"输入别名不正确！");
			return;
		}
		var obj=data.bpmFormDialog;
		var width=obj.width;
		var name=obj.name;
		var height=obj.height;
		var displayList=obj.displayfield.trim();
		var resultfield=obj.resultfield.trim();
		
		if( displayList==""){
			$.ligerMessageBox.warn('提示信息',"没有设置显示字段！");
			return;
		}
		if( resultfield==""){
			$.ligerMessageBox.warn('提示信息',"没有设置结果字段！");
			return;
		}
		
		var urlShow=__ctx + "/platform/form/bpmFormDialog/show.xht?dialog_alias_=" +alias;
		urlShow=urlShow.getNewUrl();
		$.ligerDialog.open({ url:urlShow, height: height,width: width, title :name,name:"frameDialog_",
			buttons: [ { text: '确定', onclick: function (item, dialog) { 
					var json=document.getElementById('frameDialog_').contentWindow.getResult();
					if(json==-1){
						alert("还没有选择数据项！");
						return;
					}
					if(callBack){
						callBack(json);
					}
					dialog.close();
			} }, 
			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	});
}

