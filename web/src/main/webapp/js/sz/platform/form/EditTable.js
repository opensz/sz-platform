/**
 * 下拉选项模版。
 */
var optiontemplate = '<option value="#value">#text</option>';

/**
 * 字段类型数据
 */
var varchar_="varchar";
var clob_="clob";
var date_="date";
var number_="number";




/**
 * 控件类型。
 */
var controlList = [ {key : '1',value : '单行文本框'}, {key : '2',value : '多行文本框'},
                    {key : '10',value : '富文本框'}, {key : '3',value : '数据字典'}, 
                    {key : '4',value : '人员选择器(单选)'}, {key : '8',value : '人员选择器(多选)'}, 
                    {key : '5',value : '角色选择器'}, {key : '6',value : '客户选择器'}, 
                    {key : '7',value : '岗位选择器'}, {key : '9',value : '文件上传'},
                    {key : '11',value : '下拉选项'},{key : '13',value : '复选框'},
                    {key : '14',value : '单选按钮'} ,{key : '12',value : 'Office控件'},
                    {key : '15',value : '日期控件'},{key : '16',value : '部门选择器'},
                    {key : '17',value : '项目选择器'},{key : '18',value : '合同选择器'},
                    {key : '20',value : '机房选择器'},{key : '19',value : '交换机选择器'},
                    {key : '21',value : '光电收发选择器'},{key : '22',value : 'vlan选择器'},
                    {key : '23',value : '供应商选择器'},{key : '24',value : '链路选择器'},
                    {key : '25',value : 'IP选择器'}
                  ];
/**
 * 值来源。
 */
var varFromList=[{key:0,value:'表单输入'},{key:1,value:'脚本运算(显示)'},
                 {key:2,value:'脚本运算(不显示)'},{key:3,value:'流水号'}];


/**
 * 判断字段名唯一
 */
jQuery.validator.addMethod("uniqueName", function(value, element) {
	var rtn=TableRow.fieldManage.isFieldExist(value);
	return !rtn;
}, "字段已存在");

jQuery.validator.addMethod("word", function(value, element) {
	return /^[a-zA-Z_]*$/gi.test(value);
}, "只能为字母和下划线");

/**
 * 数据表验证器。
 * @returns
 */
function validTable(){
	var __valid__=$("#bpmTableForm").validate({
		rules: {
			name:{
				required:true,
				maxlength:20,
				word:true
			},
			comment:{
				maxlength:50
			}
		},
		messages: {
			name:{
				required:"表名必填",
				maxlength:"表名最多 20 个字符."
			},
			comment:{
				maxlength:"注释 50字符."
			}
		}
	});
	return __valid__;
}


/**
 * 验证字段填写是否正确。
 * @returns
 */
function validateField(){
	var __valid__ = $('#frmFields').validate({
		rules : {
			fieldName : {
				required : true,
				uniqueName : __isFieldAdd__,
				word: true,
			},
			charLen : {
				required : true,
				digits : true,
				
			},
			intLen : {
				required : true,
				digits : true,
				
			},
			decimalLen : {
				required : true,
				digits : true
			}
		},
		messages : {
			fieldName : {
				required : "字段名称必填"
			},
			charLen : {
				required : "文字长度必填",
				digits:"填写数字"
			},
			intLen : {
				required : "整数长度必填",
				digits:"填写数字"
			},
			decimalLen : {
				required : "小数长度必填",
				digits:"填写数字"
			}
		}
	});
	return __valid__;

}


/**
 * 初始点击主表，子表选项按钮。
 */
function handIsMain(){
	$("input[name='isMain']").click(function(){
		var val=$(this).val();
		var objTr=$("#spanMainTable");
		(val==1)?objTr.hide():objTr.show();
	});
}



/**
 * 处理值表单数据来源的change事件。
 */
function handValueFrom(){
	$("#valueFrom").change(function(){
		var val=parseInt( $(this).val());
		switch(val){
			//表单输入
			case 0:
				$("#trIdentity,#trScript,#trDict").hide();
				$("#trControlType,#trRule").show();
				break;
			//脚本运算(显示),
			case 1:
			//脚本运算(不显示)
			case 2:
				$("#trIdentity,#trDict,#trRule,#trControlType").hide();
				$("#trScript").show();
				break;
			//流水号
			case 3:
				$("#trRule,#trScript,#trDict,#trControlType").hide();
				$("#trIdentity").show();
				break;
		}
	});
}


/**
 * 处理字段类型fieldType的change事件。
 */
function handFieldType(){
	$("#fieldType").change(function(){
		var val=$(this).val();
		if(val==varchar_ || val==clob_ ||  val==number_){
			$("#trControlType").show();
		}
		else{
			$("#trControlType").hide();
		}
		
		//处理数据长度
		if(val==varchar_){
			$("#spanCharLen").show();
			$("#spanIntLen,#spanDecimalLen,#spanDateFormat").hide();
			var controlType=$("#controlType").val();
			//下拉框，复选框，单选按钮
			if(controlType=="11" || controlType=="13" || controlType=="14"){
				$("#trOption").show();
			}
			//
			else{
				$("#trOption").hide();
			}
			
		}else if(val==number_){
			$("#spanCharLen,#trOption,#spanDateFormat").hide();
			$("#spanDecimalLen,#spanIntLen").show();
		}else if(val=="date"){
			$("#spanCharLen,#spanIntLen,#spanDecimalLen,#trOption").hide();
			$("#spanDateFormat").show();
		}
		else{
			$("#spanCharLen,#spanIntLen,#spanDecimalLen,#trOption,#spanDateFormat").hide();
		}
		//验证规则
		if(val ==varchar_ || val==clob_){
			$("#trRule").show();
		}
		else{
			$("#trRule").hide();
		}
		
		//设置值来源
		setValueFromByFieldType(val);
		//设置控件类型
		setControlByType(val);
		//脚本隐藏
		$("#trScript").hide();
		
	});
}

function changeCharLen(len){
	var val=$("#charLen").val();
	if(val==50){
		$("#charLen").val(len);
	}
}

/**
 * 处理控件类型修改事件。
 */
function handControlType(){
	//控件类型修改
	$("#controlType").change(function(){
		var val=parseInt($(this).val());
		//$("#eventClick option[value='onselected']").remove();
		switch(val){
			//数据字典
			case 3:
				$("#trDict").show();
				$("#trOption").hide();
				break;
			//如果选择文件上传控件，将字符宽度默认修改为2000个字符。
			case 9:
				changeCharLen(2000);
				break;
			//下拉选项,单选框，复选框
			case 11:
				$("#trOption").show();
				break;
			case 13:
				break;
			case 14:
				$("#trOption").show();
				$("#trDict").hide();
				break;
			//日期控件
			case 15:
				$("#spanDateFormat").show();
				changeCharLen(20);
				break;
			case 16:
				
				$("#trOption").hide();
				break;
			default:
				 
				$("#trDict").hide();
				$("#trOption").hide();
				$("#spanDateFormat").hide();
		}
	});
}

/**
 * 根据字段类型设置控件类型。
 * @param fieldType
 */
function setControlByType(fieldType){
	var objSelect=$('#controlType');
	objSelect.empty();
	$(controlList).each(function(i, d) {
		var option = optiontemplate.replaceAll('#value', d.key).replace('#text', d.value);
		//文本类型
		if(fieldType==varchar_){
			if(d.key!="10" )
				objSelect.append(option);
		}else if(fieldType==clob_){
			//富文本框和文件类型
			if(d.key=="2" || d.key=="10"  || d.key=="16" || d.key=="17")
				objSelect.append(option);
		}else if(fieldType==date_ ){
			if(d.key=="1" )
				objSelect.append(option);
		}
		else if(fieldType==number_){
			if(d.key=="1" ){
				objSelect.append(option);
			}
		}
	});
}

/**
 * 设置字段来源。
 * @param fieldType
 */
function setValueFromByFieldType(fieldType){
	var objSelect=$('#valueFrom');
	objSelect.empty();
	$(varFromList).each(function(i, d) {
		var key=d.key;
		var option = optiontemplate.replaceAll('#value', key).replace('#text', d.value);
	
		//文本
		if(fieldType==varchar_){
			objSelect.append(option);
		}
		//数字
		else if(fieldType==number_){
			if(key!=3){
				objSelect.append(option);
			}
		}
		//大文本
		else if(fieldType==clob_){
			if(key==0){
				objSelect.append(option);
			}
		}
		//日期
		else{
			if(key!=3){
				objSelect.append(option);
			}
		}
	});
	
}

/**
 * 添加列时初始化窗体的界面。
 * 设置数据。
 */
function initAdd(){
	$("#spanIntLen,#spanDecimalLen,#spanDecimalLen,#trDict,#trScript,#trIdentity,#trOption").hide();
	setControlByType("varchar");
	//动态加载数据字典。
	JsLoader.LoadCount=1;
	JsLoader.Load(__ctx +"/jslib/lg/plugins/htCatCombo.js","javascript1");
}

/**
 * 重置字段。
 */
function resetField(){
	$("#spanIntLen,#spanDecimalLen,#spanDecimalLen,#trDict,#trScript,#trIdentity,#trOption,#spanDateFormat").hide();
	setControlByType("varchar");
	$("#fieldName,#fieldDesc").val("");
	$("#isRequired,#isList,#isQuery,#isFlowVar").attr("checked",false);
	$("#fieldType").val("varchar").change();
	$("#charLen").val(50);
	$("#intLen").val(13);
	$("#decimalLen").val(0);
	$("#options").val("");//
	//重置验证规则
	$("#validRule").get(0).selectedIndex=0;
}

/**
 * 设置字段的长度。
 */
function setFieldLengthByFieldValue(filed){
	if(filed.fieldType==varchar_){
		var charLen=parseInt( $("#charLen").val());
		filed.charLen=charLen;
	}
	else if(filed.fieldType==number_){
		var intLen=parseInt($("#intLen").val());
		var decimalLen=parseInt($("#decimalLen").val());
		filed.intLen=intLen;
		filed.decimalLen=decimalLen;
	}
}
/**
 * 根据值来源设置相应的字段。
 * @param field
 */
function setFieldByValueFrom(field){
	
	var from=parseInt( field.valueFrom);
	
	switch(from){
		//表单
		case 0:
			break;
		//1,2脚本
		case 1:
		case 2:
			field.script=$("#script").val();
			break;
		//流水号
		case 3:
			field.identity=$("#identity").val();
			break;
	}
}



/**
 * 根据字段信息设置控件长度。
 * @param field
 */
function setFieldLengthByField(field){
	var fieldType=field.fieldType;
	switch(fieldType){
		case varchar_:
			$("#charLen").val(field.charLen);
			$("#spanCharLen").show();
			$("#spanIntLen,#spanDecimalLen").hide();
			break;
		case number_:
			$("#intLen").val(field.intLen);
			$("#decimalLen").val(field.decimalLen);
			$("#spanCharLen").hide();
			$("#spanIntLen,#spanDecimalLen").show();
			break;
		default:
			$("#spanCharLen,#spanIntLen,#spanDecimalLen").hide();
			break;
	}
	
}


/**
 * 根据数据来源，设置相关控件的状态。
 * @param field
 */
function setValueFromByField(field){
	$("#valueFrom").val(field.valueFrom);
	var from=parseInt(field.valueFrom);
	switch(from){
		//表单输入
		case 0:
			$("#trScript,#trIdentity").hide();
			break;
		//脚本输入
		case 1:
		case 2:
			$("#trScript").show();
			$("#trIdentity").hide();
			$("#script").val(field.script);
			break;
		//流水号
		case 3:
			$("#trScript").hide();
			$("#trIdentity").show();
			$("#identity").val(field.identity);
			break;
	}
}

/**
 * 设置验证规则
 * @param field
 */
function setValidRuleByField(field){
	var validRule=field.validRule;
	
	if(field.fieldType==varchar_ || field.fieldType==clob_){
		$("#trRule").show();
		$("#validRule").val(validRule);
	}
	else{
		$("#trRule").hide();
	}
}

/**
 * 设置字段的验证规则。
 * @param field
 */
function setFieldByValidRule(field){
	if(field.fieldType==varchar_ || field.fieldType==clob_){
		field.validRule=$("#validRule").val();
	}
}


/**
 * 从页面控件获取字段数据对象。
 * @returns 
 */
function getField(){
	var field={charLen:0,intLen:0,decimalLen:0,dictType:'',identity:'',validRule : '',isDeleted:0,
	valueFrom : 0,script:'',controlType : 1,eventClick:'',callFunction:''};
	field.fieldName=$("#fieldName").val();
	field.fieldDesc=$("#fieldDesc").val();
	field.fieldType=$("#fieldType").val();
	field.controlType=$("#controlType").val();
	field.eventClick=$("#eventClick").val();
	field.callFunction=$("#callFunction").val();
	//设置数据字典
	if(field.controlType==3){
		field.dictType=$("#dictType").val();
	}
	//控件类型为下拉框。
	if(field.controlType==11 || field.controlType==13 || field.controlType==14){
		field.options=$("#options").val().trim();
	}
	//设置日期格式
	if(field.controlType==15){
		field.ctlProperty=$("#selDateFormat").val().trim();
	}
	//数据类型为日期的时候，需要设置日期格式。
	if(field.fieldType==date_){
		field.ctlProperty=$("#selDateFormat").val();
	}
	
	//选项
	field.isRequired=$("#isRequired").attr("checked")?1:0;
	field.isList=$("#isList").attr("checked")?1:0;
	field.isQuery=$("#isQuery").attr("checked")?1:0;
	field.isFlowVar=$("#isFlowVar").attr("checked")?1:0;
	//设置字段长度
	setFieldLengthByFieldValue(field);
	//值来源
	field.valueFrom =$("#valueFrom").val();
	//根据来源设置对应的属性值。
	setFieldByValueFrom(field);
	//设置验证规则
	setFieldByValidRule(field);
	
	return field;
}

/**
 * 根据字段设置页面控件状态。
 */
function initControlByField(field,allowEditColName){
	
	$("#fieldName").val(field.fieldName);
	$("#fieldDesc").val(field.fieldDesc);
	$("#fieldType").val(field.fieldType);
	$("#eventClick").val(field.eventClick);
	$("#callFunction").val(field.callFunction);
	//设置控件类型
	setControlByType(field.fieldType);
	//选择控件类型
	$("#controlType").val(field.controlType);
	//显示字典类型。
	if(field.dictType!=null && field.dictType!=""){
		$("#trDict").show();
		$("#dictTypeName").attr("catValue",field.dictType);
	}
	else{
		$("#trDict").hide();
	}
	
	
	//设置字段选项。
	$("#isRequired").attr("checked",field.isRequired==1);
	$("#isList").attr("checked",field.isList==1);
	$("#isQuery").attr("checked",field.isQuery==1);
	$("#isFlowVar").attr("checked",field.isFlowVar==1);
	
	//设置值来源
	setValueFromByField(field);
	
	//设置数据长度
	setFieldLengthByField(field);
	//验证规则
	setValidRuleByField(field);
	//控件类型为下拉框选项，单选框，复选框
	if(field.controlType==11 || field.controlType==13 || field.controlType==14){
		$("#options").val(field.options);
		$("#trOption").show();
	}
	else if(field.controlType==16){
		$("#trOption").hide();
		$("#aLink").show();
	}
	else{
		$("#trOption").hide();
		$("#aLink").hide();
	}
	//日期类型
	if(field.fieldType==date_){
		$("#spanDateFormat").show();
		$("#selDateFormat").val(field.ctlProperty);
	}
	else{
		$("#spanDateFormat").hide();
	}
	
	//改变select组件的事件
	if(field.controlType==13){
		$("#eventClick").empty();
		$("#eventClick").val("onselected");
	}
	//修改控件是否允许编辑字段的名字和数据类型。
	setEditStatus(allowEditColName);
	//渲染数据字典。
	JsLoader.LoadCount=1;
	JsLoader.Load(__ctx + "/jslib/lg/plugins/htCatCombo.js","javascript1");
}

/**
 * 设置控件状态，是否允许编辑。
 * @param allowEditColName
 */
function setEditStatus(allowEditColName){
	if(allowEditColName) return;
	$("#fieldName").attr('disabled', 'disabled');
	$("#charLen").attr('disabled', 'disabled');
	$("#intLen").attr('disabled', 'disabled');
	$("#decimalLen").attr('disabled', 'disabled');
	$("#fieldType").attr('disabled', 'disabled');
}

/**
 * 绑定表和字段数据。
 * @param table
 */
function bindTable(data,allowEditTbColName){
	
	var table=data.bpmFormTable;
	
	$("#name").val(table.tableName);
	//禁止编辑
	if(!allowEditTbColName){
		$("#name").attr('disabled', 'disabled');
		$(":radio[name='isMain']").attr('disabled', 'disabled');
		$("#mainTable").attr('disabled', 'disabled');
	}
	
	$("#comment").val(table.tableDesc);
	$(":radio[name='isMain'][value="+table.isMain+"]").attr("checked","checked");
	//赋给下拉框，这个下拉框只包含未生成的主表列表。
	$("#mainTable").val(table.mainTableId);
	//将主表id赋给隐藏表单。
	$("#mainTableId").val(table.mainTableId);
	
	
	var fieldList=data.fieldList;
	TableRow.fieldManage.setFields(fieldList);
	$("#tableColumnItem>tbody").append(TableRow.fieldManage.getHtml());
}


/**
 * 绑定字段。
 * @param table
 */
function bindExtTable(data){
	var table=data.table;
	$("#name").val(table.tableName);
	//禁止编辑
	$("#name").attr('disabled', 'disabled');
	$("#comment").val(table.tableDesc);
	var fieldList=data.fieldList;
	TableRow.setAllowEditColName(false);
	TableRow.fieldManage.setFields(fieldList);
	$("#tableColumnItem>tbody").append(TableRow.fieldManage.getHtml());
	//绑定主键字段下拉框
	bindPkField(fieldList,"");
	//绑定流水号下拉框
	bindIdentity(data.identityList,"");
}

/**
 * 绑定主键字段下拉框。
 * @param fieldList
 * @param defautValue
 */
function bindPkField(fieldList,defautValue){
	var obj=$("#pkField");
	for(var i=0;i<fieldList.length;i++){
		var field=fieldList[i];
		var option = optiontemplate.replaceAll('#value', field.fieldName).replace('#text', field.fieldDesc);
		obj.append(option);
	}
	if(defautValue!=undefined && defautValue==null && defautValue!=""){
		obj.val(defautValue);
	}
}

/**
 * 绑定流水号下拉框。
 * @param identityList
 * @param defautValue
 */
function bindIdentity(identityList,defautValue){
	var obj=$("#keyValue");
	for(var i=0;i<identityList.length;i++){
		var d=identityList[i];
		var option = optiontemplate.replaceAll('#value', d.alias).replace('#text', d.name);
		obj.append(option);
	}
	if(defautValue!=undefined && defautValue==null && defautValue!=""){
		obj.val(defautValue);
	}
}

