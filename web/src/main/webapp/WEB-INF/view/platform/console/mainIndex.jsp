<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	java.text.DateFormat fmt=java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL ,request.getLocale());
	String strNow = fmt.format(new java.util.Date());
%>				
<html>
	<head>
		<title>Hanwei|S3</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="Hanwei,ITSM,S3">
		<meta http-equiv="description" content="Hanwei S3">
		<meta http-equiv="X-UA-Compatible" content="chrome=1">
		<style type="text/css">
.x-message-box .ext-mb-download {
	background: url("images/download.gif") no-repeat scroll 6px 0px
		transparent;
	height: 52px !important;
}
</style>
		<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/js/hanweis/fullcalendar/fullcalendar.css' />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jslib/extjs-4.1.0/resources/css/ext-all.css">
		
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/itsm/theme.css">
		
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/itsm/ext-itsm.css">
		
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/itsm/chooser.css">
		<!-- ligerUI msg样式  -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/blue/css/Aqua/css/ligerui-all.css">
		
		<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/extjs/bootstrap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/extjs/user-defined/Ext.ux.comboboxtree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/extjs/ext-lang-zh_CN.js"> </script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/extjs/ext-lang-vtype.js"> </script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/extjs/itemselector/layout/MultiSelect.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/extjs/itemselector/layout/ItemSelector.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/extjs/itemselector/form/MultiSelect.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/extjs/itemselector/form/ItemSelector.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/hanweis/model/deskRequestModel.js"></script>
		<script type='text/javascript' src='${pageContext.request.contextPath}/js/hanweis/common.js'></script>
		<script type='text/javascript' src='${pageContext.request.contextPath}/jslib/jquery/jquery-1.7.2.min.js'></script>
		<%-- <script type='text/javascript' src='${pageContext.request.contextPath}/jslib/jquery/jquery-ui-1.8.11.custom.min.js'></script>--%>
		<script type="text/javascript" src='${pageContext.request.contextPath}/jslib/jquery/jquery.form.js'></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/util/util.js"></script>
		<%--<script type="text/javascript" src='${pageContext.request.contextPath}/jslib/jquery/jquery.atmosphere.js'></script> --%>
		<script type='text/javascript' src='${pageContext.request.contextPath}/js/hanweis/fullcalendar/fullcalendar.js'></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/hanweis/chooseComponent/chooseComponent1.js"/>
		<%--<script type='text/javascript' src="<c:url value='/scripts/dwr/engine.js'/>"></script>
		<script type='text/javascript' src="<c:url value='/scripts/dwr/util.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/dwr/KnowledgeAction.js'/>"></script>--%>
		
		<script type="text/javascript" src='${pageContext.request.contextPath}/js/webUI/common/ux/PageToolbar.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/js/webUI/app.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/js/webUI/common/picker/MonthField.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/js/webUI/common/ux/AssetPageToolbar.js'></script>
		<script type="text/javascript" src="${ctx}/js/sz/platform/bpm/FlowUtil.js" ></script>
		<script type="text/javascript" src="${ctx}/js/sz/platform/system/SysDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/asset/assetCommon.js" charset="utf-8"></script>
		<script type="text/javascript" src="${ctx}/jslib/lg/ligerui.min.js"></script>
		
		<!-- 添加ztree引用 -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"  ></script>
		
		<script type="text/javascript">

		if (Ext.isIE) {
			Ext.enableGarbageCollector = false;
		}
var OldNavId='';
var levelName="优先级";
var caseInfoButtonNm="暂存";
var tabButtonNm="提交";
function SetClassName(id,cssName)
{
	var navId="li_"+id;
	OldNavId=navId;
	var obj = document.getElementById(navId);
	//alert(obj.className);
	//alert(cssName);
	if (obj != null)
	{	
		obj.className = cssName;
		
	}

}
$(function(){
//初始函数
$("#nav_menu ul li").one("click",function(){
    $(this).attr("out",$(this).attr("src"));
	return false;
});
$("#nav_menu ul li").click();

$("#nav_menu ul li").hover(function(){
	 $(this).find("img").attr("src",$(this).find("img").attr("over"));
	 
	 },function(){
	 
	 $(this).find("img").attr("src",$(this).find("img").attr("out"));
	 
});

	
});

    var subscribePath = "<c:url value='/comet/subscribe/' />";
    var socket = $.atmosphere;
    var subSocket;
    
    function getDataFromResponse(response) {
    	var detectedTransport = response.transport;
    	//console.log("[DEBUG] Real transport is <" + detectedTransport + ">");
    	if (response.transport != 'polling' && response.state != 'connected' && response.state != 'closed') {
    		if (response.status == 200) {
    			return response.responseBody;
    		}
    	}
    	return null;
    }
    
    function socket_callback(response) {
    	
        // Websocket events.
        socket.log('info', ["response.state: " + response.state]);
        socket.log('info', ["response.transport: " + response.transport]);
        socket.log('info', ["response.status: " + response.status]);
        
        var detectedTransport = response.transport;
        
        //alert("response:state="+response.state+",status="+response.status+",transport="+detectedTransport);
        
        var data = getDataFromResponse(response);
        if (data != null && ""!=data) {
        	 alert("data="+data);
        }
        /*
        if (response.transport != 'polling' && response.state != 'connected' && response.state != 'closed') {
            $.atmosphere.log('info', ["response.responseBody: " + response.responseBody]);
            if (response.status == 200) {
                var data = response.responseBody;
                if (data.length > 0) {
                	data=data+",";
                	showWebNotice(data,data,data,data);
                }
            }
        }*/
    }

    function subscribe() {
    	
    	var localtion = "<c:url value='/comet/pubsub/' />"+userId+"/start.action";
    	var transport = "websocket";  //'long-polling'
    	subSocket = subscribeAtmosphere(localtion, socket_callback, transport);
    	//alert("subscribe..");
    }

    
    function subscribeAtmosphere(location, call, transport) {
    	
    	//alert("start subscribeAtmosphere.. location="+location);
    	var rq = socket.subscribe(location, call, socket.request = {
    		//logLevel : 'debug',
    		transport : transport,
    		callback : call
    	});
    	//alert("end subscribeAtmosphere..");
    	return rq;
    }

    function unsubscribe(){
    	//alert("start unsubscribe..");
        socket.unsubscribe();
        //alert("end unsubscribe..");
    }

    //建立长连接
    /**function webNoticStart() {
    	//alert("invoke webnotice start")
    	if(subSocket==null){
    		 unsubscribe();
    	     subscribe();
    	}  
    	//alert("end webnotice start")
    }**/


//设置全局变量
var menu;
var menuArr = new Array();
var char1;
var ctrlArray = new Array();
var myMask; //加载提示组件
var tabcontextMenu; //右键菜单
var formKey;
var count=0;

//人员ID
var userId='<security:authentication property="principal.userId" />'
var userName='${loginUser.username}';
var loginDeptId='${loginUser.deptId}';
var loginDeptName='${loginUser.deptName}';
var loginUserOrgId='${loginUser.org.id}';
var loginUserOrgName='${loginUser.org.fullName}';
var loginUserOrgType='${loginUser.org.orgType}';
var loginUserStffName='${loginUser.fullStaffName}';
var loginUserStffId='${loginUser.staffId}';
var deskId='${loginUser.deskId}';


//var strHeadInfo=loginDeptName+"|"+loginUserStffName;
var strHeadInfo = '欢迎您，<security:authentication property="principal.fullname" />';
<%--<c:choose>
	<c:when test="${readMsg > 0}">
		strHeadInfo += '<img style="margin: 0 auto;" id="inMsg" alt="站内消息" title="站内消息" src="${ctx}/styles/default/images/msg_own.gif">';
	</c:when>
	<c:otherwise>
		strHeadInfo += '<img id="inMsg" alt="站内消息" title="站内消息" src="${ctx}/styles/default/images/msg_none.gif">';
	</c:otherwise>
</c:choose>
--%>
strHeadInfo += '<img style="margin: 0 auto;" id="inMsg" alt="站内消息" title="站内消息" src="${ctx}/styles/default/images/msg_own.gif">';
strHeadInfo += '(<a href="javascript:showReadMsgDlg()" id="labMsgSize">...</a>)';

if(loginDeptName==""){
//	strHeadInfo=loginUserStffName;
}
var isPartA=${isPartA};  // 是否甲方
var isGroup=${isGroup};  // 是否集团或多服务中心
var showSupplier='${showSupplier}';
var isCallCenter=false;            //是否有服务中心权限
if('${loginUser.roles}'.indexOf('ROLE_ITSM_DESKAGENT')>-1){
	isCallCenter=true;
};

var isServiceCenterManager=false;//是否为服务中心经理
if('${loginUser.roles}'.indexOf('SERVICE_CENTER_MANAGER')>-1){
	isServiceCenterManager=true;
};


var isControlUser=${loginUser.isControlUser};            //是否为管控人员
var isCanSelectOrg=null;            //是否可选择客户
var createDeptTreeNode=${createDeptTreeNode};   // 全局变量 是否显示部门根节点true 显示,false 不显示
var showWorkAmountAndCost = ${showWorkAmountAndCost};
var problemCaseViewServiceLevel = ${problemCaseViewServiceLevel};
var isAssetChangeNotice = ${isAssetChangeNotice};
var isDelSelfSericeCenter = ${isDelSelfSericeCenter};
var isShowCaseClaim = ${isShowCaseClaim};
var showLinkWay=${loginUser.showLinkWay};
  //showLinkWay 全局变量 是否显示当前联系方式 true 显示,false 不显示
var isShowCaseCatalogAtList=${isShowCaseCatalogAtList};
//isShowCaseCatalogAtList  全局变量 是否显示列表显示工单分类true显示，false不显示

var isShowServiceLevelAtList=${isShowServiceLevelAtList};
//isShowServiceLevelAtList  全局变量 是否显示列表显示服务级别true显示，false不显示
var staffSuffix="${staffSuffix}";
//增加参数，判断是否显示dashboard
var isShowDashboard = true;
var isSpecificServiceCenter = false;
if(!isPartA && loginUserOrgId == '20000'){
	//如果是已方并且orgId等于20000，不显示dashboard
	isShowDashboard = true;
}

		//设置菜单当前效果
		function showMenu(bl,id,navId){
			replaceCss(OldNavId);
//			menu.getLayout().setActiveItem(id);
			SetClassName(navId,"current");
			if(bl){
				curActParams = new Array();
				var mainCtrl = getControl("MainCtrl");
				mainCtrl.addSpecialTab("selfDesk.serviceItem.ServiceItemsCtrl", "IT服务目录", "serviceItems", "serviceItemsContainer");
			}
		}
			
		function replaceCss(OldNavId) {
			if(OldNavId!=''){
				var oldObj = document.getElementById(OldNavId);
				if (oldObj != null) {
					oldObj.className = "topic";
				}
			} else {
				return;
			}
		}
	
		function getPostComet(data){
			if(data.clientId==userId){
				showWebNotice(data.title,data.content,data.informationId,data.clientId);
			}
    	};



        
        function showWebNotice(title,content,informationId,clientId){
	        Ext.example.msg(userName,title,content,informationId);
	    };
	    
	    function showMyaccount (){
//	    	var mainCtrl = getControl("MainCtrl");
//	    	mainCtrl.addSpecialTab("selfDesk.staff.StaffCtrl", "我的账户", "staffInfo", "staffContainer");
			addToTab({resId:'myAccount',resName:'我的帐户',isExt:0,defaultUrl:'/platform/system/sysUser/get.xht?userId=<security:authentication property="principal.userId" />&canReturn=0'});
	    }
	    function showResetpwd(){
			//var mainCtrl = getControl("MainCtrl");
			//mainCtrl.addSpecialTab("selfDesk.password.PasswordCtrl", "重置密码", "passwordReset", "passwordContainer");
			addToTab({resId:'modifyPwd',resName:'重置密码',isExt:0,defaultUrl:'/platform/system/sysUser/modifyPwdView.xht?userId=<security:authentication property="principal.userId" />'});
	    }
	    
	    
	    function exitSystem(){
		    	Ext.Msg.confirm("消息提示", "确定退出系统？", function(button, text){
					if(button == "yes"){
						getTabPanel().removeAll(); //add chenj 2012-06-08关闭所有tab页，解决报表直接登出，登录页面出错问题。
						window.location.href="${ctx}/logout";
					}
			});
	    }
	
	// 弹出窗口
    function createWin(title,url,width,height){
    	var _width = 600;
    	var _height = 400;
    	if (null!=width && ""!=width) {
    		_width=width;
    	}
    	if (null!=height && ""!=height) {
    		_height=height;
    	}
    	
    	var htmlCode="<iframe id='bulletinShowIframe' frameborder='0' border='0' marginwidth='0' marginheight='0' scrolling='auto'  width='100%' height='100%' src="+url+"></iframe>";
    	
    	var win=Ext.WindowManager.get('baseOpenWinId');
    	if(win!=null){
    		win.close();
    	}
   		win=Ext.create("Ext.window.Window",{
			title:title,
			border:true,
			id:'baseOpenWinId',
			maximizable: true,
			closable: true,
			modal: true, 
			resizable: true, 
			html:htmlCode,
			width:_width,
			height:_height
		});
    	win.show();
	}


	/****************************4.0新增js 2013-03-15****************************************/
	var setting = {view: {showLine: true,nameIsHTML: true},
            		data: {
						key : {name: "resName"},
						simpleData: {enable: true,idKey: "resId",pIdKey: "parentId"}
					},
            		callback: {onClick: zTreeOnClick}};
   //处理点击事件
    function zTreeOnClick(event, treeId, treeNode) {
    	if(treeNode.isFolder != undefined && treeNode.isFolder == 1){
			//表示是栏目 
			return;
		}
    	//扩展了tab方法。
    	addToTab(treeNode);
    };
	//tree数据
	var aryTreeData;
	var loadMenu = function(){
		$("#leftTree").empty();
	   	$.post("${ctx}/platform/console/getSysRolResTreeData.xht",
   		function(result){
   			//将数据放入到left.jsp页面的 aryTreeData变量
   			aryTreeData = result;
   			var nodes=new Array();
           	for(var i=0;i<result.length;i++){
           		var node=result[i];
           		if(node.parentId==0){
           			nodes.push(node);
           		}
           	}
   		
			var headers=nodes;
			var len=headers.length;
			var menuContainer=$("#navmenu");
			for(var i=0;i<len;i++){
    			var head=headers[i];
    			var menuItemHtml=getMenuItem(head, i);
    			menuContainer.append($(menuItemHtml));
    		}
    		
    		//默认加载tree
  			if(len>0){
				var selectTab=jQuery.getCookie("selectTab");
				var obj= $("#nav" +selectTab);
				if(selectTab && obj.length>0){
					SetClassName(selectTab,"current"); //设置选中样式
					loadTree(selectTab);
				}
				else{
					var head=headers[0];
					var resId=head.resId;
					SetClassName(resId,"current");
					loadTree(resId);
				}
			}
		});
	}
	
	var getMenuItem = function(node, i){
   		return "<li id='li_"+node.resId+"' class='topic' style='cursor:pointer;' onclick='clickTopMenu(false,"+i+"," +node.resId+ ")'><div class='pic nav_icon self_ser' id='nav"+node.resId+"'></div><div class='menu'>"+node.resName+"</div></li>"
	}
	//topMenu点击事件
	var clickTopMenu = function(a, b, c){
		loadTree(c);
		jQuery.setCookie("selectTab",c);
		showMenu(a, b, c);
	}
	//加载左边树形菜单
	function loadTree(resId){
        var nodes=new Array();
  		getChildByParentId(resId,nodes);
  		$.fn.zTree.init($("#leftTree"), setting, nodes);
    }
    //递归得到nodes数据     
    function getChildByParentId(parentId,nodes){
    	for(var i=0;i<aryTreeData.length;i++){
    		var node=aryTreeData[i];
    		if(node.parentId==parentId){
    			nodes.push(node);
    			getChildByParentId(node.resId,nodes);
    		}
    	}
    }
    ///////////////////menu处理end//////////////////////////
	
	var addToTab = function(node){
		curActParams={};//
		var xtype = node.xtype;
		var ctrlName = node.ctrlName;
		var tabTitle = node.resName;
		var tabId = "tab_" + node.resId;
		var url= node.defaultUrl;
	    if(url && !url.startWith("http",false)){ 
	       	url= "<%=request.getContextPath()%>" +url;
	       
	    	var params=Ext.urlDecode(url.substring(url.indexOf("?") + 1,url.length));
	    	curActParams=params;
	    }
	    
	    
		var main_center = getMainTab();
		if(!main_center){
			alert("main_center 不存在!");
			return;
		}
		//根据tabId 得到tab
		var tab=main_center.getComponent(tabId);
		
		if(node.isExt != undefined && node.isExt == 1 && xtype && ctrlName){
			//如果tab不存在，没有打开过
			if(!tab){
				getControl(ctrlName);
				tab = new Ext.create('common.container.BaseContainer',{
						title:tabTitle,
						id:tabId,
						containerId:tabId,
						params:curActParams,
						closable:true,
						border:0,
						layout:'fit',
						items:[{xtype:xtype,params:curActParams}]
		    	});
		    	main_center.add(tab);
			}else{
				//存在
				var child=main_center.items;
				var index=Ext.Array.indexOf(child,tab);
				main_center.remove(tab);
				tab=null;
				tab = new Ext.create('common.container.BaseContainer',{
						title:tabTitle,
						id:tabId,
						containerId:tabId,
						params:curActParams,
						closable:true,
						border:0,
						layout:'fit',
						items:[{xtype:xtype,params:curActParams}]
		    	});
	 			main_center.insert(index,tab);
				main_center.setActiveTab(tab);
			}
		}else if(node.isExt != undefined && node.isExt == 2){
			if(!tab){
				var loader={url:url,scripts:true,renderer:'html',loadMask:true,scope:this,autoSync:true};
				tab = new Ext.create('common.container.BaseContainer',{
						title:tabTitle,
						id:tabId,
						containerId:tabId,
						params:curActParams,
						closable:true,
						border:0,
						layout:'fit',
						loader:loader
		    	});
		    	main_center.add(tab);
		    	tab.getLoader().load();
			}else{
				tab.getLoader().load();
			}
		}else{
			if(!tab){
				tab = new Ext.create('common.container.BaseContainer',{
					id:tabId,
					containerId:tabId,
					title:tabTitle,
					html:"<iframe id='iframe"+tabId+"' width=100% height=100% frameborder=0  src='"+url+"'/>' style='height:100%;width:100%'></iframe>",
					closable:true,
					layout:'fit'
				});
				main_center.add(tab);
			}else{
				//刷新当前tab
				var iframe = $("#iframe" + tabId);
				if(iframe.length > 0){
					iframe.attr("src", url);
				}
			}
		}
		
		//设置tab 的parentTabId属性
		if(node.parentTabId){
			tab.parentTabId = node.parentTabId;			
		}else{
			//如果node中没有指定，取当前actTab的ID
			tab.parentTabId = getActTabId();
		}
		//激活当前点击的tab
		main_center.setActiveTab(tab);
		return tab;
	}
	
	//刷新未读信息
	function refreshMsg() {
		$.post(__ctx + "/platform/system/messageSend/notReadMsg.xht", function(
				data) {
			$('#labMsgSize').text(data);
			if (data == 0) {
				$('#inMsg').attr('src',
						__ctx + '/styles/default/images/msg_none.gif');
			}
		});
	}
	
	//打开信息
	function showReadMsgDlg() {
		var msgLength = $('#labMsgSize').text();
		if (msgLength > 0) {
			var url = __ctx + '/platform/system/messageSend/readMsgDialog.xht';
//			win = $.ligerDialog.open({
//				url : url,
//				height : 450,
//				width : 600,
//				isResize : false
//			});
			
			var panel = Ext.create("Ext.panel.Panel",{
				height:450,
				width:600,
				html:"<iframe width=100% height=100% frameborder=0  src='"+url+"'/>' style='height:100%,width:100%'></iframe>",
				layout:'fit'
			});
			openWin(panel,'查看未读信息',600,450);
		}
	}
	
	
	var initLoad = function(){
		loadMenu();
    	refreshMsg();
	}
	
	//定时刷新内部短消息, 15分钟刷新一次
	window.setInterval(function(){refreshMsg();}, 1000 * 60 * 15);
</script>

	</head>
	
	<body>
		<iframe id="comet-frame" style="display: none;" src=""></iframe>
	</body>

</html>