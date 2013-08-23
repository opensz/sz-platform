<%@ page pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/extjs-4.1.0/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/itsm/theme.css">
<link rel="stylesheet" type="text/css" href="${ctx}/styles/itsm/ext-itsm.css">
<script type="text/javascript" src="${ctx}/jslib/extjs-4.1.0/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/jslib/extjs-4.1.0/locale/ext-lang-vtype.js"> </script>
<script type="text/javascript" src="${ctx}/jslib/extjs-4.1.0/locale/ext-lang-overite.js"> </script>
<script type="text/javascript" src="${ctx}/js/extjs/ext-lang-zh_CN.js"> </script>
<script type="text/javascript">
	Ext.Loader.setConfig({enabled:true});
	
	Ext.Loader.setPath({
		"ITSM": getContextPath() + "/js/webUI/itsm",
	    "common": getContextPath() + "/js/webUI/common"
	});
	
	var application = Ext.create('Ext.app.Application',{
		name: 'ITSM', 
	    appFolder: getContextPath() + '/js/webUI/itsm' 
	});

	function getControl(ctrlName){
		var ctrl=application.controllers.get(ctrlName);
		if(!ctrl){
			var ctrl=application.getController(ctrlName);
			ctrl.init();
		}
		return ctrl;
	}
		
	function getCurrentTab(){
		var currentTab = Ext.getCmp('caseDeal2');
		
		if(currentTab == null){
			currentTab = Ext.getCmp('caseCreate2');
		}
		
		return currentTab;
	}
	
	function getActTab(){
		var currentTab = Ext.getCmp('caseDeal2');
		
		if(currentTab == null){
			currentTab = Ext.getCmp('caseCreate2');
		}
		
		return currentTab;
	}
	
	//显示提示层
	function rendererTip(value){
		if(value==null || value==""){
			value="";
		}
		return "<span title='"+value+"'>"+value+"</span>";
	}

</script>
