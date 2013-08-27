$(function() {
	CustomForm = {
		onEditCallBack:null,
		tables: {},
		init : function() {
			var self=this;
			//对子表进行遍历
			$('div[type=subtable]').each(function() {
				//子表
				var table = $(this);
				//没有form类型的属于表内编辑模式
				var row = $(this).find('[formType=form]');
				//表单
				var form;
				//窗口编辑模式
				if(row.length > 0) {
					form = $(this).find('[formType=window]');
				}
				//页内编辑模式
				else {
					row = $(this).find('[formType=edit]');
				}				
				
				table.data('row', $('<div></div>').append(row.clone()).html());
				//row.remove();
				row.css('display', 'none').html('');
			
				//窗口编辑模式
				if(form) {
					table.data('form', '<form>' + $('<div></div>').append(form.clone()).html() + '</form>');
					table.data("formProperty",{width:form.width() ,height: form.height()});
					form.remove();
				}
				//获取表名
				var tableName = $(this).attr('tableName');
				
				self.tables[tableName] = table;
				//子表的权限(可以编辑)
				var canWrite = ($(this).attr('right') == 'w');
				//子表只读
				var canRead = canWrite || ($(this).attr('right') == 'r');
				
				$(this).find('[formType="newrow"]').each(function() {
					if(canWrite) {
						self.addBind($(this), table);
					}
				});
				//子表是否可以编辑
				if(canWrite) {
					var menu = $.ligerMenu({top : 100,left : 100,width : 130,
						items : [{
									text : '添加',
									click : function() {
										self.add($(menu.target).closest('div[type=subtable]'));
									}
								}]
					});
					$(this).find('[formType]:first').prev().bind("contextmenu", function(e) {
						menu.target = e.target;
						menu.show({top : e.pageY,left : e.pageX});
						return false;
					});
				}
				//没有读的属性这个表格为隐藏
				if(!canRead) {
					$(this).hide();
				}
			});
			this.initUI();
		},
		/**
		 * 初始化表单界面。
		 * @param parent
		 */
		initUI : function(parent) {
			
			var filter='input,textarea,.dicComboTree,.dicComboBox,.dicCombo';
			if (parent==undefined) {
				parent = $('body div[type=custform]');
			} 
			//下拉框默认选中,在下拉框定义一个val属性,使用脚本选中。
			$("select[name][val]",parent).each(function(){
				var obj=$(this);
				obj.val(obj.attr("val"));
			});
			
			$.metadata.setType("attr", "validate");
			
			parent.find(filter).each(function() {
				if ($(this).is('.dicComboTree,.dicComboBox,.dicCombo')) {
					$(this).htDicCombo();
				}
			});
			if (parent.is('form')) {
				parent.data('validate', this.getValidate(parent));
			} else {
				this.valid = this.getValidate(parent.closest('form'));
			}
		},
		//添加一行数据
		//参数，子表区域，在那个地方插入。
		add : function(table, beforeElement) {
			var self=this;
			var right=table.attr('right');
			if(right!="w"){
				return;
			}
			//判断是否是使用窗口方式编辑数据。
			var frm=table.data('form');
			if (frm) {
				self.openWin('添加', $(frm), table, beforeElement, function(form, table, beforeElement) {
					//校验数据
					var v = form.data('validate');
					if (!v.checkForm()) {
						v.showErrors();
						return false;
					} else {
						var row = $(table.data('row'));
						//添加行数据
						self.addRow(row, form);
					
						self._add(table, row, beforeElement);
						v.showErrors();
						return true;
					}
				});
			} 
			//使用的行内编辑。
			else {
				var row = $(table.data('row'));
				this._add(table, row, beforeElement);
			}
		},
		addRow:function(row,form){
			form.find('input:text[name],input:hidden[name], textarea[name],select[name]').each(function() {
				var name=$(this).attr('name');
				var val=$(this).val();
				//添加隐藏表单。
				row.append($('<input type="hidden"/>').attr('name', name).val(val));
				//修改表格的文本显示。
				var filter="[fieldName='"+name+"']";
				var objTd=$(filter,row);
				if(objTd.length>0){
					objTd.text(val);
				}
				$(this).val('');
			});
			
			
			//回填checkbox的值。
			form.find('input:checkbox,input:radio').each(function() {
				var name=$(this).attr('name');
				var value=$(this).val();
				
				var filterHidden="input[name='"+ name +"']";
				var isChecked=($(this).attr("checked")!=undefined);
				var obj=$(filterHidden,row);
				
				var filter="[fieldName='"+name+"']";
				var objTd=$(filter,row);
				
			
				
				var val=(isChecked)?value:"";
				if(obj.length==0){
					row.append($('<input type="hidden"/>').attr('name', name).val(val));
					if(objTd.length>0){
						objTd.text(val);
					}
				}
				else{
					var existVal=obj.val();
					if(existVal==""){
						if(val!=""){
							existVal=val;
						}
					}
					else{
						if(val!=""){
							existVal+="," +val;
						}
					}
					obj.val(existVal);
					if(objTd.length>0){
						objTd.text(existVal);
					}
				}
			});
		},
		_add : function(table, newRow, beforeElement) {
			if (beforeElement) {
				$(beforeElement).before(newRow);
			}
			//在最后面加入
			else {
				table.find('[formType]:last').after(newRow);
			}
			//初始化界面
			this.initUI(newRow);
			//添加右键绑定
			this.addBind(newRow, table);
		},
		/**
		 * 添加行数据的右键事件绑定。
		 * @param row
		 * @param table
		 */
		addBind: function(row, table) {
			//是否需要编辑菜单项目
			//如果该表弹出窗口定义，那么就不需要编辑菜单项目。
			var needEdit = (this.tables[table.attr('tableName')].data('form') != null);
			var menu = this.getMenu(needEdit);
			row.bind("contextmenu", function(e) {
				menu.target = e.target;
				menu.show({top : e.pageY,left : e.pageX});
				return false;
			});
		},
		/**
		 * 编辑行数据。
		 * @param table
		 * @param row
		 */
		edit : function(table, row) {
			var self=this;
			var tableName = table.attr('tableName');
			//获取表单
			var form = $(this.tables[tableName].data('form'));
			
			
			//对表单数据进行初始化
			self.initFormData(form,row);
			
			form.data('row', row);
			this.openWin('编辑', form, table, null, function(form, table) {
				var v = form.data('validate');
				if (!v.checkForm()) {
					v.showErrors();
				} else {
					var row = form.data('row');
					//对表单进行遍历
					self.setRowData(form,row);
					
					v.showErrors();
					return true;
				}
			});
		},
		initFormData:function(form,row){
			form.find('input:text,textarea,select,input[type="hidden"]').each(function() {
				var name= $(this).attr('name');
				var value = row.find('[name="' + name+ '"]').val();
				$(this).val(value);
			});
			form.find('input:checkbox,input:radio').each(function(){
				var name=$(this).attr("name");
				var chkValue=$(this).val();
				var value=row.find('[name="' + name+ '"]').val();
				
				if(value.indexOf(chkValue)!=-1){
					$(this).attr("checked","checked");
				}
			});
		},
		setRowData:function(form,row){
			var self=this;
			//对表单进行遍历
			form.find('input:text, textarea,input[type="hidden"],select').each(function() {
				var name=$(this).attr('name');
				var val=$(this).val();
				//修改隐藏域的数据值
				var objHidden=$("input[name='"+name+"']",row);
				objHidden.val(val);
				//修改表格的文本显示。
				var filter="[fieldName='"+name+"']";
				var objTd=$(filter,row);
				if(objTd.length>0){
					objTd.text(val);
				}
			});
			
			form.find('input:checkbox,input:radio').each(function(){
				var name=$(this).attr('name');
				var objHidden=$("input[name='"+name+"']",row);
				objHidden.val("");
				//修改表格的文本显示。
				var filter="[fieldName='"+name+"']";
				var objTd=$(filter,row);
				if(objTd.length>0){
					objTd.text("");
				}
			});
			
			form.find('input:checkbox:checked,input:radio:checked').each(function(){
				var name=$(this).attr('name');
				var value=$(this).val();
				var objHidden=$("input[name='"+name+"']",row);
				var filter="[fieldName='"+name+"']";
				var objTd=$(filter,row);
				
				var hidValue=objHidden.val();
				if(self.isNull(hidValue)){
					objHidden.val(value);
					objTd.text(value);
				}
				else{
					objHidden.val(hidValue +"," +value);
					objTd.text(hidValue +"," +value);
				}
				
			});
			
		},
		isNull:function(obj){
			if(obj==undefined || obj==null || obj==""){
				return true;
			}
			return false;
		},
		
		/**
		 * 获取验证器。
		 * @param target 为一个表单。
		 * @returns
		 */
		getValidate : function(target) {
			return target.validate({
				debug : false,
				errorPlacement : function(lable, element) {
				
					if(element.hasClass("l-text-field")) {
						element = element.parent();
					}
					element.addClass('validError');
					element.css('border','1px solid red');
					element.mouseover(function() {
						element.ligerTip({
							content : lable.html(),
							appendIdTo : lable
						});
					});
					element.mouseout(function() {
						element.ligerHideTip();
					});
					
					lable.element = element;
				},
				success : function(lable) {
					lable.element.css('border','1px solid #ABC1D1');
					lable.element.removeClass('validError');
					lable.element.ligerHideTip();
					lable.element.unbind('mouseover');
				}
			});
			
		},
		validate: function() {
			return this.valid.checkForm();
		},
		showError: function() {
			this.valid.showErrors();
		},
		/**
		 * 获取数据。
		 * 返回json数据。
		 */
		getData: function() {
			var self=this;
			// 主表数据
			var main = {
				tableId: $('#tableId').val(),
				fields:{}
			};
			//取主表的字段。
			$('input:text[name^=m],input:hidden[name^=m],textarea[name^=m],select[name^=m]').each(function() {
				var name = $(this).attr('name');
				main.fields[name.replace(/.*:/, '')] = $(this).val();
			});
			
			$('textarea[name^=m].ckeditor').each(function() {
				var name = $(this).attr('name');
				main.fields[name.replace(/.*:/, '')] =CKEDITOR.instances[name].getData();
			});
			//设置单选按钮。
			self.setMainRadioData(main.fields);
			//设置复选框。
			self.setMainCheckBoxData(main.fields);
			
			//子表数据
			var sub = [];
			$('div[type=subtable]').each(function() {
				var table = {
					tableName: $(this).attr('tableName'),
					fields: []
				};
				$(this).find('[formtype]:visible').each(function() {
				
					var row = {};
					var objRow=$(this);
					$('input:text,input[type="hidden"],textarea,select',objRow).each(function() {
						var name = $(this).attr('name').replace(/.*:/, '');
			
						row[name] = $(this).val();
					});
					//设置复选框按钮的数据。
					self.setSubCheckBoxData(objRow,row);
					
					//设置单选按钮的数据
					self.setSubRadioData(objRow, row);
					
					table.fields.push(row);
				});
				sub.push(table);
			});
			
			//意见
			var opinion = [];
			$('textarea[name^=opinion]').each(function() {
				opinion.push({
					name: $(this).attr('name').split(':')[1],
					value: $(this).val()
				});
			});
			
			var data = {main: main, sub: sub, opinion: opinion};
			
			return JSON.stringify(data);
		},
		getAssetData: function() {
			var self=this;
			var catalogIds = $("#catalogIds").val();
			var $catalogIds = eval(catalogIds);
			
			var mains = [];
			for(var i = 0; i < $catalogIds.length; i++){
				var jids = $catalogIds[i];
				var main = {
						tableId: jids["id"],
						fields:{}
				};
				$("input:text[name^='m:"+jids["tableCode"]+"'],input:hidden[name^='m:"+jids["tableCode"]+"'],textarea[name^='m:"+jids["tableCode"]+"'],select[name^='m:"+jids["tableCode"]+"']").each(function() {
					var name = $(this).attr('name');
					main.fields[name.replace(/.*:/, '')] = $(this).val();
				});
				
				self.setAssetMainRadioData(main.fields, jids["tableCode"]);
				self.setAssetMainCheckBoxData(main.fields, jids["tableCode"]);
				
				mains.push(main);
			}
			return JSON.stringify(mains);
			
		},
		setAssetMainRadioData:function(dataObj, tableCode){
			//单选按钮
			$("input[name^='m:"+tableCode+"']:radio").each(function() {
				var name = $(this).attr('name').replace(/.*:/, '');
				var value= $(this).val();
				
				if($(this).attr("checked")!=undefined){
					dataObj[name]=value;
				}
			});
		},
		setAssetMainCheckBoxData:function(dataObj, tableCode){
			//查询”是否控件“并设值
			$("input[name^='m:"+tableCode+"'][class='yesno']:checkbox").each(function(){
				var name = $(this).attr('name').replace(/.*:/, '');
				var value= $(this).val();
				
				//判断是否选中
				if($(this).attr("checked")){
					dataObj[name] = 1;
				}else{
					dataObj[name] = 0;
				}
			});
			
			//将所有复选框选址清空。
			$("input[name^='m:"+tableCode+"'][class!='yesno']:checkbox").each(function() {
				var name = $(this).attr('name').replace(/.*:/, '');
				dataObj[name]="";
			});
			
			//复选框取值。
			$("input[name^='m:"+tableCode+"'][class!='yesno']:checkbox:checked").each(function() {
				var name = $(this).attr('name').replace(/.*:/, '');
				var value= $(this).val();
				
				if(dataObj[name]==""){
					dataObj[name]=value;
				}
				else{
					dataObj[name]+="," + value;
				}
			});
		},
		/**
		 * 设置子表复选框的数据
		 * @param objParent
		 * @param dataObj
		 */
		setSubCheckBoxData:function(objParent,dataObj){
			$('input:checkbox',objParent).each(function() {
				var name = $(this).attr('name').replace(/.*:/, '');
				dataObj[name]="";
			});
			
			//复选框取值。
			$('input:checkbox:checked',objParent).each(function() {
				var name = $(this).attr('name').replace(/.*:/, '');
				var value= $(this).val();
				if(dataObj[name]==""){
					dataObj[name]=value;
				}
				else{
					dataObj[name]+="," + value;
				}
			});
		},
		/**
		 * 设置子表的radio单选按钮字段。
		 * @param dataObj
		 */
		setSubRadioData:function(objParent,dataObj){
			//单选按钮
			$('input:radio',objParent).each(function() {
				var name = $(this).attr('name').replace(/.*:/, '');
				var value= $(this).val();
				
				if($(this).attr("checked")!=undefined){
					dataObj[name]=value;
				}
			});
		},
		/**
		 * 设置主表的radio单选按钮字段。
		 * @param dataObj
		 */
		setMainRadioData:function(dataObj){
			//单选按钮
			$('input[name^=m]:radio').each(function() {
				var name = $(this).attr('name').replace(/.*:/, '');
				var value= $(this).val();
				
				if($(this).attr("checked")!=undefined){
					dataObj[name]=value;
				}
			});
		},
		setMainCheckBoxData:function(dataObj){
			//将所有复选框选址清空。
			$('input[name^=m]:checkbox').each(function() {
				var name = $(this).attr('name').replace(/.*:/, '');
				dataObj[name]="";
			});
			
			//复选框取值。
			$('input[name^=m]:checkbox:checked').each(function() {
				var name = $(this).attr('name').replace(/.*:/, '');
				var value= $(this).val();
				
				if(dataObj[name]==""){
					dataObj[name]=value;
				}
				else{
					dataObj[name]+="," + value;
				}
			});
		},
		
		/**
		 * 打开操作数据窗口。
		 * @param title 窗口标题
		 * @param form	窗口对象。
		 * @param table 表
		 * @param beforeElement
		 * @param callback 回调函数
		 */
		openWin : function(title, form, table, beforeElement, callback) {
			var formProperty=table.data("formProperty");
			var width=formProperty.width+20;
			var height=formProperty.height+100;
			var self=this;
			
	
			form.data('beforeElement', beforeElement);
			var win = $.ligerDialog({target:form,
					left : ($(window).width() - 400) / 2,
					top : ($(window).height() - 300) / 2,
					width:width,
					height:height,
					title : title,
					showMax : true,
					showToggle : true,
					onClose : true,
					showButton : true,
					buttons : [ {
						text : "确定",
						onclick: function (item, dialog) {
							var result = callback(form, table, form.data('beforeElement'));
							if(result) {
							    if(self.onEditCallBack!=null){
									self.onEditCallBack(title);
								}
								dialog.close();
							}
						}
					} ]
				});
			//初始化表单界面
			this.initUI(form);
			win.show();
		
		},
		/**
		 * 获取右键菜单。
		 * @param needEdit
		 * @returns
		 */
		getMenu : function(needEdit) {
			var self=this;
			//判断菜单是否存在，不存在则新建菜单。
			if ((needEdit && this.menuWithEdit) || (!needEdit && this.menu)) {
				return needEdit ? this.menuWithEdit : this.menu;
			} else {
				var menu;
				var items = [ {
					text : '在前面插入记录',
					click : function() {
						var row = $(menu.target).closest('[formType]');
						var table = row.closest('div[type=subtable]');
						self.add(table, row);
					}
				}, {
					text : '在后面插入记录',
					click : function() {
						var row = $(menu.target).closest('[formType]');
						var table = row.closest('div[type=subtable]');
						row = row.next('[formType]:visible');
						if (row.length == 0) {
							row = null;
						}
						self.add(table, row);
					}
				}, {
					line : true
				}, {
					text : '编辑',
					click : function() {
						var row = $(menu.target).closest('[formType]');
						var table = row.closest('div[type=subtable]');
						self.edit(table, row);
					}
				}, {
					text : '删除此记录',
					click : function() {
						var t = $(menu.target).closest('[formType]');
						t.remove();
					}
				}, {
					line : true
				}, {
					text : '向上移动',
					click : function() {
						var t = $(menu.target).closest('[formType]');
						var prev = t.prev('[formType]:visible');
						if (prev.length > 0) {
							prev.before(t);
						}
					}
				}, {
					text : '向下移动',
					click : function() {
						var t = $(menu.target).closest('[formType]');
						var next = t.next('[formType]:visible');
						if (next.length > 0) {
							next.after(t);
						}
					}
				} ];
				//如果不需要编辑，删除编辑菜单。
				if (!needEdit) {
					items.splice(3, 1);
				}
				menu = $.ligerMenu({top : 100,left : 100,width : 130,items : items});
				if (needEdit) {
					this.menuWithEdit = menu;
				}
				else{
					this.menu = menu;
				}
				return menu;
			}
		}
	};
	//对表单初始化。
	CustomForm.init();
});