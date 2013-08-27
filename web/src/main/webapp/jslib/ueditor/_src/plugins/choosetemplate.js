UE.plugins['choosetemplate'] = function() {
	var me = this;
	me.commands['choosetemplate'] = {
		execCommand : function(cmdName) {
			//编辑器设计表单模式
			if(typeof FormDef == 'undefined'){
				//重新选择模板（该方法的定义在bpmFormDefDesignEdit.jsp页面）
				showSelectTemplate('chooseDesignTemplate.xht?&isSimple=1');
			}
			else{
				FormDef.showSelectTemplate('selectTemplate.xht?tableId=' + tableId +"&catalogId="+tableId+"&isSimple=1");
			}
		},
		queryCommandState : function() {
			return this.highlight ? -1 : 0;
		}
	};	
};