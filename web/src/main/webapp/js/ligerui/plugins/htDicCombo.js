$(function() {
	var ctx = __ctx;
	
	
	function readProp(dicCombo) {
		var prop = {
			nodeKey : $(dicCombo).attr("nodeKey"),
			width : $(dicCombo).attr("width"),
			height : $(dicCombo).attr("height"),
			valueFieldID : $(dicCombo).attr("valueFieldID"),
			isMultiSelect : $(dicCombo).attr("isMultiSelect"),
			treeLeafOnly : $(dicCombo).attr("treeLeafOnly"),
			value : $(dicCombo).attr("value"),
			name : $(dicCombo).attr("name")
		};
		if (typeof (prop.nodeKey) == 'undefined' || prop.nodeKey == null
				|| prop.nodeKey == '') {
			$.ligerMsg.warn('数据字典控件，nodeKey属性必须!');
			return;
		}
		if (typeof (prop.width) == 'undefined' || prop.width == null
				|| prop.width == '') {
			prop.width = $(dicCombo).width();
		}
		if (typeof (prop.height) == 'undefined' || prop.height == null
				|| prop.height == '')
			prop.height = 200;
		if (typeof (prop.valueFieldID) == 'undefined'
				|| prop.valueFieldID == null || prop.valueFieldID == '')
			prop.valueFieldID = $(dicCombo).attr("id");
		if (typeof (prop.isMultiSelect) == 'undefined'
				|| prop.isMultiSelect == null || prop.isMultiSelect == '')
			prop.isMultiSelect = false;
		if (prop.isMultiSelect) {
			prop.check = {
				enable : true,
				chkboxType : {
					"Y" : "s",
					"N" : "s"
				}
			};
		}
		if (typeof (prop.treeLeafOnly) == 'undefined'
				|| prop.treeLeafOnly == null || prop.treeLeafOnly == '')
			prop.treeLeafOnly = false;
		return prop;
	}

	function process(dicCombo, prop) {
		$.ajax({
			type : 'POST',
			url : ctx + "/platform/system/dictionary/getDicData.xht?nodeKey="
					+ encodeURI(prop.nodeKey),
			success : function(ret) {
				var data = ret.data;
				if(data){
					$.each(data, function(i, d) {
						d.id = d.itemValue;
						d.name = d.itemName;
						d.pId = d.parentId;
						// 处理修改时，由ＩＤ来显示文字ＮＡＭＥ的情况
						var value = $(dicCombo).val();
						if (typeof (value) != 'undefined' && value != null
								&& value != '' && d.id == value && d.name != value
								&& name != d.name) {
							$("#" + prop.valueFieldID).val(value);
							$(dicCombo).val(d.name);
						}
					});
				}
				if (dicCombo.tagName
						&& dicCombo.tagName.toLowerCase() == "select") {
					var t = $($('<div></div>').append($(dicCombo).clone())
							.html().replace(/<select/gi, '<input').replace(
									/select>/gi, 'input>'));
					$(dicCombo).after(t);
					$(dicCombo).remove();
					dicCombo = t.get(0);
				}

				if ($(dicCombo).is('.dicComboBox')
						|| ($(dicCombo).is('.dicCombo') && ret.type == 0)) {
					// 平铺
					$(dicCombo).ligerComboBox({
						data : data,
						valueFieldID : prop.valueFieldID,
						width : prop.width,
						isMultiSelect : prop.isShowCheckBox,
						isShowCheckBox : prop.isMultiSelect
					});
				} else if ($(dicCombo).is('.dicComboTree')
						|| ($(dicCombo).is('.dicCombo') && ret.type == 1)) {
					// 树形
					$(dicCombo).ligerComboBox({
						valueFieldID : prop.valueFieldID,
						width : prop.width,
						treeLeafOnly : prop.treeLeafOnly,
						tree : {
							treeId : "htDicComboTree",
							data : {
								simpleData : {
									enable: true,
									idKey: 'dicId',
									pIdKey : "parentId",
									rootPId : null
								},
								key : {
									name : "name",
									title : "name"
								},
								data : data
							},
							view : {
								selectedMulti : prop.isMultiSelect
							},
							check : prop.check
						},
						selectBoxWidth : prop.width,
						selectBoxHeight : prop.height
					});
				}
			}
		});
	}
	// htDicCombo
	$.fn.htDicCombo = function(option) {
		$(this).each(function() {
			var prop = readProp(this);
			process(this, prop);
		});
	};

	$('.dicComboBox,.dicComboTree,.dicCombo').each(function() {
		$(this).htDicCombo();
	});
});
