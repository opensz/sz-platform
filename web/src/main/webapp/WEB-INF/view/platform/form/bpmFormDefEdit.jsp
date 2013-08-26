

<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑自定义表单</title>
<%@include file="/commons/include/form.jsp"%>
<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/styles/default/css/tab/tab.css" type="text/css" />

<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js"></script>
<script type="text/javascript" src="${ctx}/jslib/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/ckeditor/custom/preview/plugin.js"></script>
<script type="text/javascript" src="${ctx}/jslib/ckeditor/custom/opinion/plugin.js"></script>
<script type="text/javascript" src="${ctx}/jslib/ckeditor/custom/column/plugin.js"></script>
<script type="text/javascript" src="${ctx}/jslib/ckeditor/plugins/forms/dialogs/ScriptManage.js"></script>
<script type="text/javascript" src="${ctx}/jslib/ckeditor/custom/template/plugin.js"></script>
<script type="text/javascript" src="${ctx}/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bpmFormDef"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerWindow.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerTab.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/Permission.js"></script>
<script type="text/javascript" src="${ctx}/js/hanweis/platform/form/FormDef.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/PageTab.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/FormContainer.js"></script>
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="${ctx}/jslib/ueditor/design-setting/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/jslib/ueditor/design-setting/editor_api.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/ueditor/themes/default/css/web.css"/>
<script type="text/javascript">
	var tabTitle="${bpmFormDef.tabTitle}";
	var formKey=${bpmFormDef.formKey};
	var tableId=${bpmFormDef.tableId};
	var __Permission__;
	var tab;
	
	function showRequest(formData, jqForm, options) {
		return true;
	}
	
	$(function() {
		//权限处理
		__Permission__=new Permission();
		__Permission__.loadPermission(tableId,formKey);
		//验证代码
		valid(showRequest, showResponse);
		
		$("a.save").click(save);
		
		$("#frmDefLayout").ligerLayout({leftWidth : 200});
		
		var height = $(".l-layout-center").height();
        $("#leftTree").height(height-40);
        $("#editor").height(height-40);
                
		$("#tab").ligerTab({height: $("#frmDefLayout").height()});
		tab = $("#tab").ligerGetTabManager();
		
		//初始化编辑器。
		FormDef.getEditor();
		editor.addListener('ready',function(){
			initTab();
		});
		//ueditor渲染textarea
		editor.render("html");
		editor.tableId = tableId;
		//获取字段显示到左边的字段树中
		getAllFields();
		//初始化tab。
//		initTab();
	});
	
	//保存表单数据。
	function save(){
		var rtn=$("#bpmFormDefForm").valid();
		if(!rtn) return;
		var data = {};
		var arr = $('#bpmFormDefForm').serializeArray();
		$.each(arr, function(i, d) {
			data[d.name] = d.value;
		});
		
		//保存当前tab的数据。
		var idx=tabControl.getCurrentIndex()-1;
		saveTabChange(idx);
		var objForm=formContainer.getResult();
		
		data['tabTitle'] =objForm.title;
		data['html'] = objForm.form;
	
		$.post('save.xht', {
			data: JSON.stringify(data),
			permission:__Permission__.getPermissionJson(),
			tableName: $('#tableName').val()
		}, FormDef.showResponse);
	}
	
	function getAllFields(){
		FormDef.getFieldsByTableId(tableId);
	}

	//tab控件
	var tabControl;
	//存储数据控件。
	var formContainer;
	//添加tab页面
	function addCallBack(){
		var curPage=tabControl.getCurrentIndex();
		var str="新页面";
		var idx=curPage-1;
		formContainer.insertForm(str,"",idx);
//		editor.setData("");
		editor.setContent("");
	}
	//点击激活tab时执行。
	function activeCallBack(prePage,currentPage){
		if(prePage==currentPage) return;
		//保存上一个数据。
		saveTabChange(prePage-1);
		var idx=currentPage-1;
		setDataByIndex(idx);
	}
	//根据索引设置数据
	function setDataByIndex(idx){
		if (idx == undefined)
			return;
		var obj=formContainer.getForm(idx);
//		editor.setData(obj.form);
		editor.setContent(obj.form);
		$("b",tabControl.currentTab).text(obj.title);
	}
	//点击删除时回调执行。
	//点击删除时
	function delCallBack(curPage){
		formContainer.removeForm(curPage-1);
		var tabPage=tabControl.getCurrentIndex();
		setDataByIndex(tabPage-1);
	}
	//文本返回
	function txtCallBack(){
		var curPage=tabControl.getCurrentIndex();
		var idx=curPage-1;
		var title=tabControl.currentTab.text();
		//设置标题
		formContainer.setFormTitle(title,idx);
	}
	//tab切换时保存数据
	function saveTabChange(index){
//		var data=editor.getData();
		var data = editor.getContent();
		formContainer.setFormHtml(data,index);
	}
	//表单或者标题发生变化是保存数据。
	function saveChange(){
		var index=tabControl.getCurrentIndex()-1;
		var title=tabControl.currentTab.text();
//		var data=editor.getData();
		var data = editor.getContent();
		formContainer.setForm(title,data,index);
	}
	//初始化TAB
	function initTab(){
		//editor.on('blur',  saveChange);
//		var formData=editor.getData();
		var formData = editor.getContent();
		if(tabTitle=="") tabTitle="页面1";
		formContainer=new FormContainer();
		var aryTitle=tabTitle.split(formContainer.splitor);
		var aryForm=formData.split(formContainer.splitor);
		var aryLen=aryTitle.length;
		//初始化
		formContainer.init(tabTitle,formData);
		
		tabControl=new PageTab("pageTabContainer",aryLen,{addCallBack:addCallBack,activeCallBack:activeCallBack,
			delCallBack:delCallBack,txtCallBack:txtCallBack});
		tabControl.init(aryTitle);
		if(aryLen>1){
			//editor.setData(aryForm[0]);
			editor.setContent(aryForm[0]);
		}
	}
</script>
</head>
<body style="overflow:hidden">
	<div class="panel-top">
		
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group">
					<a class="link save" id="dataFormSave" href="#">保存</a>
				</div>
				<div class="l-bar-separator"></div>
				<div class="group">
					<a class="link  del" href="javascript:window.close()">关闭</a>
				</div>
			</div>
		</div>
	</div>
	<div  class="panel-body">
		<form id="bpmFormDefForm">
			<input id="formDefId" type="hidden" name="formDefId" value="${bpmFormDef.formDefId}" /> 
			<input id="tableId" type="hidden" name="tableId" value="${bpmFormDef.tableId}" />
			<input id="categoryId" type="hidden" name="categoryId" value="${bpmFormDef.categoryId}" /> 
			<input  type="hidden" id="templateId" name="templateId" value="${bpmFormDef.templateId}" /> 
			<input  type="hidden" name="formKey" value="${bpmFormDef.formKey}" /> 
			<input  type="hidden" name="isDefault" value="${bpmFormDef.isDefault}" /> 
			<input  type="hidden" name="versionNo" value="${bpmFormDef.versionNo}" /> 
			<input  type="hidden" name="isPublished" value="${bpmFormDef.isPublished}" /> 
			<input  type="hidden" name="publishedBy" value="${bpmFormDef.publishedBy}" /> 
			<input  type="hidden" name="publishTime" value="${bpmFormDef.publishTime}" /> 
			<table cellpadding="0" cellspacing="0" border="0" style=" margin-bottom:4px;"  class="table-detail">
				<tr style="height:25px;">
					<td>表单标题:&nbsp;<input id="subject" type="text" name="subject" value="${bpmFormDef.subject}" class="inputText" /></td>
					<td>描述:&nbsp;<input id="formDesc" type="text" name="formDesc"
						value="${bpmFormDef.formDesc}" class="inputText" /></td>
				</tr>
			</table>
		</form>
		<div id="tab">
			
			<div title="表单编辑">
				<div id="frmDefLayout">
					<div position="left" title="表字段" id="leftTree" style="overflow: auto;">
						<div class="panel-toolbar tree-title">
							<span class="toolBar">
								<div class="group">
									<a class="link reload" onclick="getAllFields()">刷新</a>
								</div>
							</span>
						</div>
						<ul id="colstree" class="ztree"></ul>
					</div>
					<div id="editor" position="center"  style="overflow:auto;height:100%;">
						<textarea id="html" name="html">${fn:escapeXml(bpmFormDef.html) }</textarea>
						<div id="pageTabContainer"></div>
					</div>
				</div>
			</div>
			<div title="权限设置" style="overflow:auto;">
				<div class="panel-body">
					<div class="panel-data">
						<table cellpadding="1" cellspacing="1" class="table-grid">
							<tr>
								<th width="20%">字段</th>
								<th width="40%">只读权限</th>
								<th width="40%">编辑权限</th>
							</tr>
							<tbody id="fieldPermission"></tbody>
						</table>
						<br />
						<table  cellpadding="1" cellspacing="1" class="table-grid">
							<tr>
								<th width="20%">子表</th>
								<th width="40%">只读权限</th>
								<th width="40%">编辑权限</th>
							</tr>
							<tbody id="tablePermission"></tbody>
						</table>
						<br />
						<table  cellpadding="1" cellspacing="1" class="table-grid">
							<tr>
								<th width="20%">意见</th>
								<th width="40%">只读权限</th>
								<th width="40%">编辑权限</th>
							</tr>
							<tbody id="opinionPermission"></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

