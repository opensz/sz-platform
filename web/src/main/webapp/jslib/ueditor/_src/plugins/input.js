/**
 * @description 输入框（按钮、单选按钮、复选框、单行文本、多行文本、下拉框等）
 * @name baidu.editor.execCommand
 * @param {String}
 *            cmdName 输入框
 * @author heyifan
 */
UE.plugins['input'] = function() {
	var me = this,
		dataType=[
		          {name:'文字',value:'varchar',display:1},
		          {name:'数字',value:'number',display:1},
		          {name:'日期',value:'date',display:1},
		          {name:'大文本',value:'clob',display:1}
		          ],
      valueFrom=[
				{name:'表单输入',value:'0',display:1},
				{name:'脚本运算(显示)',value:'1',display:1},
				{name:'脚本运算(不显示)',value:'2',display:1},
				{name:'流水号',value:'3',display:1}
             ];
	
	 eventType=[
				{name:'onclick',value:'onclick',display:1},
				{name:'dbclick',value:'dbclick',display:1},
				{name:'onchange',value:'onchange',display:1}
             ];
	//添加按钮
	me.commands['addinput'] = {
		execCommand : function(cmdName) {
			me.curInput=null;
			this.ui._dialogs['addinputbutton'].open();
			return true;
		},
		queryCommandState : function() {
			return this.highlight ? -1 : 0;
		}
	};
	//编辑按钮
	me.commands['editinput'] = {
		execCommand : function(cmdName) {
			me.curInput=this.selection.getRange().getClosedNode();
			this.ui._dialogs['addinputbutton'].open();
			return true;
		},
		queryCommandState : function() {
			 var el = this.selection.getRange().getClosedNode();
			 if(!el){
				 return -1;
			 }
			 else if(el.tagName.toLowerCase()=='input'||el.tagName.toLowerCase()=='textarea'||el.tagName.toLowerCase()=='select'){
				 return this.highlight ? -1 : 0;
			 }
			 return -1;
		}
	};
	//隐藏域
	me.commands['hidedomain'] = {
			//cmdName 命令名称 ，如：此处为hidedomian
			//data 编辑界面点击确定时的内容
			//old 编辑的input对象 
			execCommand : function(cmdName,data,old,editor) {
				if(data){
					if(old){
						old.setAttribute("external",getExternal(data));
					}
					else{						
						insertControl(cmdName,data,'<input type="text" />',editor);
					}
				}
				else{
					me.curInputHtml=null;
					var html='';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//单行文本
	me.commands['textinput'] = {
			execCommand : function(cmdName,data,old) {
				if(data){					
					if(old){
						//old.setAttribute("external",getExternal(data));
					}
					else{
						insertControl(cmdName,data,'<input type="text" />');
					}
				}
				else{	
					dataType=[
					          {name:'文字',value:'varchar',display:1},
					          {name:'数字',value:'number',display:1},
					          {name:'日期',value:'date',display:1},
					          {name:'大文本',value:'clob',display:0}
				          ];
					me.curInputHtml=null;
					var html='';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}				
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//多行文本
	me.commands['textarea'] = {
			execCommand : function(cmdName,data,old) {
				if(data){					
					if(old){
						old.setAttribute("external",getExternal(data));
					}
					else{
						insertControl(cmdName,data,'<textarea cols="15" rows="3"></textarea>');
					}
				}
				else{	
					dataType=[
					          {name:'文字',value:'varchar',display:0},
					          {name:'数字',value:'number',display:0},
					          {name:'日期',value:'date',display:0},
					          {name:'大文本',value:'clob',display:1}
				          ];
					valueFrom=[
								{name:'表单输入',value:'0',display:1},
								{name:'脚本运算(显示)',value:'1',display:0},
								{name:'脚本运算(不显示)',value:'2',display:0},
								{name:'流水号',value:'3',display:0}
				             ];
					me.curInputHtml=null;
					var html='';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}				
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//复选框
	me.commands['checkbox'] = {
			execCommand : function(cmdName,data,old) {				
				if(data){
					var text=[''];
					for(var i=0,c;c=data[i++];){
						if(c.id=='options'){
							var options=c.val.split(/\n/g),option;
							while(option=options.pop()){
								text.push('<label><input type="checkbox" value="'+option+'"/>'+option+'</label>');
							}
						}
					}
					if(old){
						old.setAttribute("external",getExternal(data));
						old.innerHTML = text.join('');
					}
					else{
						insertControl(cmdName,data,text.join(''));
					}
				}
				else{					
					me.curInputHtml=null;
					var html='<tr><th>复选框选项</th><td colspan="3"><span style="color:green;">每行对应一个子项<br /></span><textarea eid="options" cols="50" rows="5"></textarea></td></tr>';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}				
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//单选按钮
	me.commands['radioinput'] = {
			execCommand : function(cmdName,data,old) {
				if(data){
					var text=[''];
					for(var i=0,c;c=data[i++];){
						if(c.id=='options'){
							var options=c.val.split(/\n/g),option;
							while(option=options.pop()){
								text.push('<label><input type="radio" value="'+option+'"/>'+option+'</label>');
							}
						}
					}
					if(old){
						old.setAttribute("external",getExternal(data));
						old.innerHTML = text.join('');
					}
					else{
						insertControl(cmdName,data,text.join(''));
					}
				}
				else{					
					me.curInputHtml=null;
					var html='<tr><th>单选按钮选项</th><td colspan="3"><span style="color:green;">每行对应一个子项<br /></span><textarea eid="options" cols="50" rows="5"></textarea></td></tr>';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//下拉框
	me.commands['selectinput'] = {
			execCommand : function(cmdName,data,old) {				
				if(data){
					var text=['<select>'];
					for(var i=0,c;c=data[i++];){
						if(c.id=='options'){
							var options=c.val.split(/\n/g),option;
							while(option=options.pop()){
								text.push('<option value="'+option+'">'+option+'</option>');
							}
						}
					}
					text.push('</select>');
					if(old){
						old.setAttribute("external",getExternal(data));
						old.innerHTML = text.join('');
					}
					else{
						insertControl(cmdName,data,text.join(''));
					}
				}
				else{					
					me.curInputHtml=null;
					var html='<tr><th>下拉框选项</th><td colspan="3"><span style="color:green;">每行对应一个选项<br /></span><textarea eid="options" cols="50" rows="5"></textarea></td></tr>';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};	
	//数据字典
	me.commands['dictionary'] = {
			execCommand : function(cmdName,data,old) {				
				if(data){
					if(old){
						old.setAttribute("external",getExternal(data));
					}
					else{
						var text='<input type="text" />';
						insertControl(cmdName,data,text);
					}
				}
				else{					
					me.curInputHtml=null;
					var html='<tr><th>选择数据字典</th><td colspan="3"><input eid="dictTypeName" id="dictTypeName" class="catComBo" catKey="DIC" valueField="dictType" isNodeKey="true" name="dictTypeName" height="150" width="200"/></td></tr>';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//人员选择器
	me.commands['personpicker'] = {
			execCommand : function(cmdName,data,old) {
				if(data){	
					var content="选择";
					for(var i=0,c;c=data[i++];){
						if(c.id=="buttoncontent"){
							content=c.val;
						}
					}
					var text='<input type="text" /><a href="javascript:;" class="link user">'+content+'</a>';
					if(old){
						old.setAttribute("external",getExternal(data));
						old.innerHTML = text;
					}
					else{
						insertControl(cmdName,data,text);
					}
				}
				else{					
					me.curInputHtml=null;
					var html='<tr><th>按钮文本</th><td><input eid="buttoncontent" type="text" value="选择" /></td><th>其他设定:</th><td><label><input eid="singleselect" type="checkbox" />单选</label><label><input eid="isformuser" type="checkbox" />下个节点执行人</label></td></tr>';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//角色选择器
	me.commands['rolepicker'] = {
			execCommand : function(cmdName,data,old) {
				if(data){	
					var content="选择";
					for(var i=0,c;c=data[i++];){
						if(c.id=="buttoncontent"){
							content=c.val;
						}
					}
					var text='<input type="text" /><a href="javascript:;" class="link role">'+content+'</a>';
					if(old){
						old.setAttribute("external",getExternal(data));
						old.innerHTML = text;
					}
					else{
						insertControl(cmdName,data,text);
					}
				}
				else{					
					me.curInputHtml=null;
					var html='<tr><th>按钮文本</th><td colspan="3"><input eid="buttoncontent" type="text" value="选择" /></td></tr>';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//岗位选择器
	me.commands['positionpicker'] = {
			execCommand : function(cmdName,data,old) {
				if(data){	
					var content="选择";
					for(var i=0,c;c=data[i++];){
						if(c.id=="buttoncontent"){
							content=c.val;
						}
					}
					var text='<input type="text" /><a href="javascript:;" class="link position">'+content+'</a>';
					if(old){
						old.setAttribute("external",getExternal(data));
						old.innerHTML = text;
					}
					else{
						insertControl(cmdName,data,text);
					}
				}
				else{					
					me.curInputHtml=null;
					var html='<tr><th>按钮文本</th><td colspan="3"><input eid="buttoncontent" type="text" value="选择" /></td></tr>';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//附件上传工具
	me.commands['attachement'] = {
			execCommand : function(cmdName,data,old) {
				if(data){	
					var content="选择";
					for(var i=0,c;c=data[i++];){
						if(c.id=="buttoncontent"){
							content=c.val;
						}
					}
					var text='<input type="text" /><a href="javascript:;" class="link attachement">'+content+'</a>';
					if(old){
						old.setAttribute("external",getExternal(data));
						old.innerHTML = text;
					}
					else{
						insertControl(cmdName,data,text);
					}
				}
				else{	
					dataType=[
					          {name:'文字',value:'varchar',display:1},
					          {name:'数字',value:'number',display:0},
					          {name:'日期',value:'date',display:0},
					          {name:'大文本',value:'clob',display:0}
				          ];
					valueFrom=[
								{name:'表单输入',value:'0',display:1},
								{name:'脚本运算(显示)',value:'1',display:0},
								{name:'脚本运算(不显示)',value:'2',display:0},
								{name:'流水号',value:'3',display:0}
				             ];
					me.curInputHtml=null;
					var html='<tr><th>按钮文本</th><td colspan="3"><input eid="buttoncontent" type="text" value="选择" /></td></tr>';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//部门选择器
	me.commands['departmentpicker'] = {
			execCommand : function(cmdName,data,old) {				
				if(data){
					var content="选择";
					for(var i=0,c;c=data[i++];){
						if(c.id=="buttoncontent"){
							content=c.val;
						}
					}
					var text='<input type="text" /><a href="javascript:;" class="link org">'+content+'</a>';
					if(old){
						old.setAttribute("external",getExternal(data));
						old.innerHTML = text;
					}
					else{
						insertControl(cmdName,data,text);
					}
				}
				else{					
					me.curInputHtml=null;
					var html='<tr><th>按钮文本</th><td><input eid="buttoncontent" type="text" value="选择" /></td><th>其他设定:</th><td><label><input eid="isformuser" type="checkbox" />部门负责人为下节点执行人</label></td></tr>';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//日期选择器
	me.commands['datepicker'] = {
			execCommand : function(cmdName,data,old) {	
				if(data){					
					if(old){
						old.setAttribute("external",getExternal(data));
					}
					else{
						var text='<input type="text" />';
						insertControl(cmdName,data,text);
					}
				}
				else{	
					dataType=[
					          {name:'文字',value:'varchar',display:0},
					          {name:'数字',value:'number',display:0},
					          {name:'日期',value:'date',display:1},
					          {name:'大文本',value:'clob',display:0}
					          ];
			      valueFrom=[
							{name:'表单输入',value:'0',display:1},
							{name:'脚本运算(显示)',value:'1',display:0},
							{name:'脚本运算(不显示)',value:'2',display:0},
							{name:'流水号',value:'3',display:0}
			             ];
					me.curInputHtml=null;
					var html='';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//office控件
	me.commands['officecontrol'] = {
			execCommand : function(cmdName,data,old) {
				if(data){
					if(old){
						old.setAttribute("external",getExternal(data));
					}
					else{
						insertControl(cmdName,data,'<input type="text" />');
					}
				}
				else{					
					me.curInputHtml=null;
					var html='';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//明细表
	me.commands['subtable'] = {
			execCommand : function(cmdName,data,old) {
				//执行插入html到编辑器的动作
				if(data){
					var num=0,tableName='',tableDesc='',model=0;
					for(var i=0,c;c=data[i++];){
						if(c.id=="tablerows")
							num=c.val;
						if(c.id=='tablename')
							tableName=c.val;
						if(c.id=='tablememo')
							tableDesc=c.val;
						if(c.val==1){
							if(c.id=='inlinemodel')
								model=0;
							if(c.id=='blockmodel')
								model=1;
							if(c.id=='windowmodel')
								model=2;
						}							
					}
					var external=getExternal(data);
					var html=['<div tablename="'+tableName+'" tabledesc="'+tableDesc+'" type="subtable" name="editable-input" class="'+cmdName+'" external="'+external+'">'];
					html.push(getSubtableContent(num,model));
					html.push('</div>');
					//对已有进行修改
					if(old){
						var externalStr=old.getAttribute("external").replace(/\&quot\;/g, '\"').replace(/\&\#39\;/g, '\'');						
						var externalObj=eval("("+externalStr+")");
						if(externalObj){
							var tablerows=externalObj.tablerows,
								oldModel;
							if(externalObj.inlinemodel==1)
								oldModel=0;
							if(externalObj.blockmodel==1)
								oldModel=1;
							if(externalObj.windowmodel==1)
								oldModel=2;
							if(tablerows==num&&oldModel==model){//不修改明细表模式和列数时，不会清空明细表中的字段
								old.setAttribute("external",external);
								old.setAttribute("tablename",tableName);
								old.setAttribute("tabledesc",tableDesc);
							}
							else
								old.outerHTML = html.join('');
						}							
						else
							old.outerHTML = html.join('');
					}
					//新添加
					else{
						me.execCommand('insertHtml',html.join(''));
					}
				}
				//打开对话框
				else{					
					me.curInputHtml=null;
					var html=['<table class="edit_table"><tr><th>明细表名称</th><td><input validate="{required:true ,variable:true}" eid="tablename" type="text" /></td><th>列数</th><td><input eid="tablerows" type="text" style="width:60px;" value="3"/></td></tr>'];
					html.push('<tr><th>明细表备注</th><td colspan="3"><input eid="tablememo" validate="{required:true}" type="text" style="width:300px;"/></td></tr>');
					html.push('<tr><th>添加数据模式</th><td colspan="3"><label for="inlinemodel"><input name="models" id="inlinemodel" eid="inlinemodel" type="radio" checked="checked"/>行模式</label><label for="blockmodel"><input name="models" id="blockmodel" eid="blockmodel" type="radio" />块模式</label><label for="windowmodel"><input name="models" id="windowmodel" eid="windowmodel" type="radio" />弹窗模式</label></td></tr>');
					html.push('</table><script type="text/javascript">bindData()</script>');
					me.curInputHtml = html.join('');
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	//获取对应模式子表内容
	function getSubtableContent(num,modelIndex){
		var text=[''];
		switch(modelIndex){
			case 0:
				//行模式
				text.push('<table class="listTable" border="0" cellpadding="2" cellspacing="0"><tbody>');
				text.push('<tr class="toolBar"><td colspan="'+num+'"><a class="link add" href="javascript:;">添加</a></td></tr>');
				text.push('<tr class="headRow">');
				for(var i=0;i<num;i++){
					text.push('<th>列'+(i+1)+'</th>');
				}
				text.push('</tr><tr class="listRow" formtype="edit">');
				for(var i=0;i<num;i++){
					text.push('<td></td>');
				}
				text.push('</tr></table>');
				break;
			case 1:
				//块模式
				text.push('<div><a class="link add">添加</a></div><div formtype="edit"><table class="blocktable"><tr>');
				for(var i=0;i<num;i++){
					text.push('<th style="width:'+(100*0.3)/num+'%;">列'+(i+1)+'</th><td style="width:'+(100*0.7)/num+'%;"></td>');
				}
				text.push('</tr></table></div>');
				break;
			case 2:
				//窗口模式
				text.push('<table class="listTable" border="0" cellpadding="2" cellspacing="0"><tbody>');
				text.push('<tr class="toolBar"><td colspan="'+num+'"><a class="link add" href="javascript:;">添加</a></td></tr>');
				text.push('<tr class="headRow">');
				for(var i=0;i<num;i++){
					text.push('<th>列'+(i+1)+'</th>');
				}
				text.push('</tr><tr class="listRow" formtype="form">');
				for(var i=0;i<num;i++){
					text.push('<td></td>');
				}
				text.push('</tr></table><div formtype="window"><table class="window-table">');
				for(var i=0;i<num;i++){
					text.push('<tr><th>列'+(i+1)+'</th><td></td></tr>');
				}
				text.push('</table></div>');
				break;
		}
		return text.join('');		
	};
	//ckeditor编辑器
	me.commands['ckeditor'] = {			
			execCommand : function(cmdName,data,old) {
				if(data){
					if(old){
						old.setAttribute("external",getExternal(data));
					}
					else{						
						insertControl(cmdName,data,'<textarea></textarea>');
					}
				}
				else{
					dataType=[
					          {name:'文字',value:'varchar',display:0},
					          {name:'数字',value:'number',display:0},
					          {name:'日期',value:'date',display:0},
					          {name:'大文本',value:'clob',display:1}
					          ];
			      valueFrom=[
							{name:'表单输入',value:'0',display:1},
							{name:'脚本运算(显示)',value:'1',display:0},
							{name:'脚本运算(不显示)',value:'2',display:0},
							{name:'流水号',value:'3',display:0}
			             ];
					me.curInputHtml=null;
					var html='';
					me.curInputHtml=getContentHtml(html);
					initDialog(cmdName);
				}
			},
			queryCommandState : function() {
				return this.highlight ? -1 : 0;
			}
	};
	
	function initDialog(t){
		me.curInputType = t;
		title = me.options.labelMap[t] || t ;
        className="edui-for-inputeditdialog edui-for-" + t;
		if(!me.ui._dialogs['inputeditdialog']){
			iframeUrl ="~/dialogs/input/edit.html";
			var dialog = new baidu.editor.ui.Dialog( utils.extend({
               iframeUrl: me.ui.mapUrl(iframeUrl),
               editor: me,
               className: 'edui-for-inputeditdialog',
               title: title
           },{
               buttons: [{
                   className: 'edui-okbutton',
                   label: '确认',
                   onclick: function (){
                       dialog.close(true);
                   }
               }, {
                   className: 'edui-cancelbutton',
                   label: '取消',
                   onclick: function (){
                       dialog.close(false);
                   }
               }]
           }));
			me.ui._dialogs["inputeditdialog"] = dialog;
			dialog.render();			
		}				
		me.ui._dialogs['inputeditdialog'].title = title;
		me.ui._dialogs['inputeditdialog'].className = className;
		me.ui._dialogs['inputeditdialog'].clearContent();
		me.ui._dialogs['inputeditdialog'].open();
	};
	/**
	 * 添加标签
	 * @param cmdName 
	 * @param data 编辑界面提交的数据
	 * @param text span标签所包含的内容
	 */	 
	function insertControl(cmdName,data,text){
		if(me.popup.anchorEl){
			me.popup.anchorEl.parentNode.removeChild(me.popup.anchorEl);
    	}
		var json={};
		for(var i=0,c;c=data[i++];){
			if(c.id){
				json[c.id]=c.val;
			}
		}
		//console.log(json);
		var html=['<span name="editable-input" class="'+cmdName+'" external="'];					
		html.push(json);
		html.push('">');
		var str=json.eventClick+'="'+json.callFunction+'"';
		var textStr=text.substring(0,text.length-2)+str+"/>";
		html.push(textStr);
		//console.log(textStr);
		html.push('</span>');
		me.execCommand('insertHtml',html.join(''),1);
		
	};
	//构建external json字符
	function getExternal(d){
		if(me.curInputElement)
			me.curInputElement=null;
		var external={};
		for(var i=0,c;c=d[i++];){
			if(c.prenode){
				if(typeof external[c.prenode] == 'undefined')
					external[c.prenode]={};
				(external[c.prenode])[c.id]=c.val;
			}
		
			else{
				external[c.id]=c.val;
			}
		}
		external = json2str(external);		
		return external;
	};
	//数据类型下拉框
	function getDbtypeHtml(){
		var html=['<select eid="type" prenode="dbType">'];
		for(var i=0,c;c=dataType[i++];){
			if(c.display)
				html.push('<option value="'+c.value+'">'+c.name+'</option>');
			else
				c.display = 1;
		}
		html.push('</select>');
		return html.join('');
	};
	//处理单引号和双引号
	function htmlEncode(str) {
		return str.replace(/\"/g, "&quot;").replace(/\'/g, "&#39;").replace(/\n/g,'#newline#');
	};	
	//值来源下拉框
	function getValuefromHtml(){
		var html=['<select eid="value" prenode="valueFrom">'];
		for(var i=0,c;c=valueFrom[i++];){
			if(c.display)
				html.push('<option value="'+c.value+'">'+c.name+'</option>');
			else
				c.display = 1;
		}
		html.push('</select>');
		return html.join('');
	};	
	//构建html内容
	function getContentHtml(html){
		//var content=['<table class="edit_table"><tr><th>字段名称:</th><td><input eid="name" type="text" validate="{required:true,variable:true}"/></td><th>字段描述:</th><td><input eid="comment" type="text" validate="{required:true}"/></td></tr><tr><th>数据类型:</th><td>'];
		//content.push(getDbtypeHtml());
		//content.push('</td><th>数据格式:</th><td class="dbformat_td"></td></tr>');
		//content.push('<tr><th>选项:</th><td colspan="4"><label for="isRequired"><input type="checkbox" id="isRequired" eid="isRequired"/>必填</label>&nbsp;<label for="isList"><input type="checkbox" id="isList" eid="isList"/>显示到列表</label>&nbsp;<label for="isQuery"><input type="checkbox" id="isQuery" eid="isQuery"/>作为查询条件</label>&nbsp;<label for="isFlowVar"><input type="checkbox" id="isFlowVar" eid="isFlowVar"/>是否流程变量</label>;<label for="isAllowMobile"><input type="checkbox" id="isAllowMobile" eid="isAllowMobile"/>是否支持手机显示</label></td></tr>');
		//content.push('<tr class="condition_tr hidden"><th>查询条件:</th><td colspan="3"><table class="edit_table"><tbody><tr><th>条件:</th><td><select prenode="search" eid="condition"><option value="等于">等于</option><option value="LIKE">LIKE</option><option value="LIKEEND">LIKEEND</option></select></td></tr><tr><th>值来源:</th><td><select prenode="search" eid="searchFrom"><option value="fromForm">表单输入</option><option value="fromStatic">固定值</option><option value="fromScript">脚本</option></select></td></tr><tr class="searchValue-td hidden"><th>值:</th><td><textarea prenode="search" eid="searchValue" cols="40" rows="3"></textarea></td></tr></tbody></table></td>');
		//content.push('<tr><th>值来源:</th><td colspan="3">');
		//content.push(getValuefromHtml());
		//content.push('</td></tr><tr class="valuefrom0"><th>验证规则:</th><td colspan="3">#validrule#</td></tr>');
		//content.push('<tr class="valuefrom12 hidden"><th>脚本(显示):</th><td colspan="3"><textarea eid="content" prenode="valueFrom" cols="50" rows="5"></textarea></td></tr>');
		//content.push('<tr class="valuefrom3 hidden"><th>流水号:</th><td colspan="3">#serialnum#</td></tr>');
		var content=['<table class="edit_table">'];
		content.push('<tr><th>事件方法:</th><td colspan="3"><select eid="eventClick" name="eventClick"><option value="onclick">onclick</option><option value="dbclick">dbclick</option><option value="onchange">onchange</option><option value="onselected">onselected</option></select></td></tr>');
		content.push('<tr><th>调用函数:</th><td colspan="3"><textarea eid="callFunction" name="callFunction"></textarea></td></tr>');
		content.push(html);
		content.push('</table><script type="text/javascript">initComplete();</script>');
		return content.join('');
	};	
};