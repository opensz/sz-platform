/*顶层top的一些公共方法
*tab 使用的是ligerUI
*
*如：新增Tab、关闭当前Tab.....
*
*/

var getTab = function(){
	return $("#framecenter").ligerGetTabManager();
}

//打开新Tab
var addToTab = function(url,txt,id,icon){
	var tab = getTab();
	var url;
	var txt;
	var id;
	var icon;
	if(arguments.length == 4){
		url = arguments[0];
		txt = arguments[1];
		id = arguments[2];
		icon = arguments[3];
	}else if(arguments.length == 3){
		url = arguments[0];
		txt = arguments[1];
		id = arguments[2];		
	}else if(arguments.length == 1 && typeof(arguments[0]) == 'object'){
		var params = arguments[0];
		url = params.defaultUrl;
		txt = params.resName;
		id = params.resId;
	}
		
	if(tab){
		if(tab.isTabItemExist(id)){
			tab.selectTabItem(id);
			tab.reload(id);
		}
		else{
			
			var tabItem = {};
			if(id){
				tabItem["tabid"] = id;
			}
			if(txt){
				tabItem["text"] = txt;
			}
			if(url){
				tabItem["url"] = url;
			}
			if(icon){
				tabItem["icon"] = icon;
			}
			
			tab.addTabItem(tabItem);
		}
	}
}

//
//关闭当前选中的tab
var closeCurActPanel = function(reload){
	if(reload == undefined){
		reload = true;
	}	
	
	var tab = getTab();
	if(tab){
		//
		//关闭当前打开的tab
		tab.removeSelectedTabItem();
		
		//刷新当前tab
		refreshSelectedTab();
	}
}

//
//刷新当前选中的tab
var refreshSelectedTab = function(){
	var tab = getTab();
	if(tab){
		var selectedTabid = tab.getSelectedTabItemID();
		tab.reload(selectedTabid);
	}
}

//
//显示Mask
var showMask = function(msg){
	
}

//隐藏Mask
var hideMask = function(){
	
}