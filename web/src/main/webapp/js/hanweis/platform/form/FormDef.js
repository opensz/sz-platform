if (typeof FormDef == 'undefined') {
	FormDef = {};
}
var editor;
FormDef.getEditor = function() {
	var h = $(document.body).height();
	editor = new baidu.editor.ui.Editor({minFrameHeight:h-225});
};

FormDef.openWin = function(title, width, height, url, buttons, frameId) {
	var left = ($(window).width() - width) / 2;
	var top = ($(window).height() - height) / 2;
	var p = {
		url : url,
		width : width,
		height : height,
		left : left,
		top : top,
		title : title,
		buttons : buttons,
		name : frameId
	};
	$.ligerDialog.open(p);
};

/**
 * 根据表获取字段和子表，构建树。
 * 
 * @param tableId
 */
FormDef.getFieldsByTableId = function(tableId) {
//	var iconFolder = __ctx + '/styles/tree/';
	var iconFolder = __ctx + '/themes/img/tree/';
	$.post('getAllFieldsByTableId.xht?tableId=' + tableId, function(data) {
		var mainTable = data.mainTable;
		var mainTableFields = data.mainTableFields;

		for ( var i = 0; i < mainTableFields.length; i++) {
			mainTableFields['tableId'] = mainTable.tableId;
		}
		$('#tableName').val(mainTable.tableName);

		var subTables = data.subTables;

		var treeData = [];
		for ( var i = 0; i < mainTableFields.length; i++) {
			if (mainTableFields[i].isHidden == 0) {
				var field = mainTableFields[i];
				treeData.push(field);
				field.name = field.fieldDesc;
				field.icon = iconFolder + field.fieldType + '.png';
			}
		}

		for ( var i = 0; i < subTables.length; i++) {
			subTables[i].name = subTables[i].tableDesc;
			subTables[i].icon = iconFolder + 'table.png';
			treeData.push(subTables[i]);
		}

		glTypeTree = $.fn.zTree.init($("#colstree"), {
			callback : {
				beforeClick : function(treeId, treeNode, clickFlag) {
					FormDef.insertHtml(editor, treeNode);
					return false;
				}
			}
		}, treeData);
	});
};

/**
 * 重新生成html模版。
 * 
 * @param tableId
 * @param tableTemplateId
 */
FormDef.genByTemplate = function(tableId, templatesId) {
	$.post('genByTemplate.xht', {
		templateTableId : tableId,
		templatesId : templatesId
	}, function(data) {
		editor.setContent(data);
	});
};

/*
 * 编辑器页面
 */
var controls = {};

FormDef.insertHtml = function(editor, node) {
	if (node.fieldType) {
		// 如果是字段
		if (!controls[node.fieldName]) {
//			FormDef.showSelectTemplate('selectTemplate.xht?tableId=' + node.tableId
//									+ '&isSimple=1',
//							function(item, dialog) {
//								var form = $(document.getElementById('selectTemplate').contentDocument);
//								var templateId = form.find('select[templateId=templateId]').val();

//								dialog.close();
								var templateId = FormDef.getTemplateIdHidden();
								if(!templateId){
									alert("没有设置模板ID");
								}
								$.post('getControls.xht', {
									templateId : templateId,
									tableId : node.tableId
								}, function(data) {
									controls = data;
									editor.execCommand('inserthtml',controls[node.fieldName],1);
								});
//							});
		} else {
			editor.execCommand('inserthtml', controls[node.fieldName],1);
		}
	} else {
		// 如果是子表
		FormDef.showSelectTemplate(
			'selectTemplate.xht?tableId=' + node.tableId
					+ '&isSimple=1',
			function(item, dialog) {
				var form = $(document.getElementById('selectTemplate').contentDocument);
				var templateId = $('select[templateId="templateId"]', form).val();
				dialog.close();
				$.post('genByTemplate.xht', {
					templateTableId : node.tableId,
					templatesId : templateId
				}, function(data) {
					editor.execCommand('inserthtml', data,1);
				});
			});
	}
};

// 显示选择模板窗口
FormDef.showSelectTemplate = function(url, callback) {
	if (!callback)
		callback = FormDef.onOk;
	var buttons = [ {
		text : "确定",
		onclick : callback
	} ];
	FormDef.openWin('选择模板', 550, 350, url, buttons, "selectTemplate");
};

FormDef.onOk = function(item, dialog) {
	var form = $(document.getElementById('selectTemplate').contentDocument);

	var aryTableId = [];
	var aryTemplateId = [];

	$("select[templateId='templateId']", form).each(function(i) {
		aryTableId.push($(this).attr("tableid"));
		aryTemplateId.push($(this).val());
	});

	if(aryTemplateId.length > 0){
		//设置TemplateId到hidden
		FormDef.setTemplateIdHidden(aryTemplateId[0]);
	}
	
	FormDef.genByTemplate(aryTableId.join(","), aryTemplateId.join(","));

	dialog.close();
};

FormDef.showResponse = function(data) {
	var obj = new org.sz.form.ResultMessage(data);
	if (obj.isSuccess()) {// 成功
		$.ligerMessageBox.success('提示信息', '操作表单成功！', function() {
			window.close();
		});
	} else {// 失败
		$.ligerMessageBox.error('出错了', obj.getMessage());
	}
};


//设置TemplateId 到hidden
FormDef.setTemplateIdHidden = function(templateId){
	if(templateId){		
		var $templateId = $("#templateId");
		if($templateId.length > 0){
			$templateId.val(templateId);
		}
	}
}

FormDef.getTemplateIdHidden = function(){
	var $templateId = $("#templateId");
	if($templateId.length > 0){
		return $templateId.val();
	}
	return null;
}