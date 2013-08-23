<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${tools.appName }</title>
	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${ctx }/libs/ligerUI/skins/Aqua/css/ligerui-all.css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/styles/css/default.css" />
	<script type="text/javascript" src="${ctx }/libs/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx }/libs/ligerUI/js/ligerui.min.js"></script>
	<script type="text/javascript">
	
		var centerHeight = null;
		var tab = null;
	    var leftAccordion = null;
	    
        function _addTab(tabId,text, url){ 
        	if(tab.isTabItemExist(tabId)){
                tab.selectTabItem(tabId);
        		tab.reload(tabId, url);
        	}else{
        		tab.addTabItem({ tabid : tabId,text: text, url: url });
        	}
        }
	    
		$(function() {
			
			//布局
			$("#centerLayout").ligerLayout({
				leftWidth : 190,
				allowLeftResize : false,
				height : '100%',
				space : 4,
				heightDiff : -43,
				onHeightChanged : function(options){
		            if (tab)
		                tab.addHeight(options.diff);
		            if (leftAccordion && options.middleHeight - 24 > 0)
		            	leftAccordion.setHeight(options.middleHeight - 24);
		        }
			});
			
			//导航
			$("#accordionLeft").ligerAccordion({
				height : $("#centerLayout").height() - 24
			});
			leftAccordion = $("#accordionLeft").ligerGetAccordionManager();
			
			//Tab
			$("#framecenter").ligerTab({
				height : $("#centerLayout").height()
			});
			tab = $("#framecenter").ligerGetTabManager();
			
			//导航链接样式
			$(".l-link").hover(function() {
				$(this).addClass("l-link-over");
			}, function() {
				$(this).removeClass("l-link-over");
			});
			
			setInterval(function(){
				$.ajax({
					url : "${ctx}/account/message.${suffix}",
					type : "get",
					dataType : "json",
					cache : false,
					success : function(data, textStatus) {
						if (data.code != "${tools.success}" && data.message != "") {
							var tip = window["tip"];
							if (!tip) {
								window["tip"] = $.ligerDialog.tip({
									title : '提示信息',
									content : data.message
								});
							} else {
								var visabled = tip.get('visible');
								if (!visabled){
									tip.show();
								}
								tip.set('content', data.message);
							}
						}
					}
				});
			}, 5000);

			$("#pageloading").hide();
		});
	</script>
</head>
<body class="index-bg">
<div id="pageloading"></div>
<div class="main">
	<div class="top">
		<div class="left-c"></div>
		<div class="logo"><img src="${ctx }/styles/images/bg_top.png" alt="" /></div>
		<div class="controls"><div><p>
			<img src="${ctx }/images/user_icon.png" alt="" />
			<span>Logged in: <b>${tools.user.userName }</b></span>
			<span class="logout"><a href="${ctx }/account/logout.${suffix }">注销</a></span>
		</p></div></div>
		<div class="right-c"></div>
	</div>
	<div id="centerLayout">
		<div id="accordionLeft" position="left" title="应用菜单">
			<s:iterator value="menus" id="mg">
				<s:if test="#mg.show && tools.haveAuthority(#mg)">
					<div title="${groupName }" class="l-scroll">
						<s:iterator value="#mg.menus" id="mn">
							<s:if test="#mn.show && tools.haveAuthority(#mn)">
								<s:if test="systemMenu"><a class="l-link" href="javascript:_addTab('${menuId }','${menuName }','<s:property value="tools.getMenuPath(#mg, #mn)" />')"><img src='<s:if test="menuIcon.length() > 0">${ctx }/${menuIcon }</s:if><s:else>${ctx }/images/menu.gif</s:else>' alt="${menuName }" /><span>${menuName }</span></a></s:if>
								<s:else><a class="l-link" href="javascript:_addTab('${menuId }','${menuName }','${menuPath }')"><img src='<s:if test="menuIcon.length() > 0">${menuIcon }</s:if><s:else>${ctx }/images/menu.gif</s:else>' alt="${menuName }" /><span>${menuName }</span></a></s:else>
							</s:if>
						</s:iterator>
					</div>
				</s:if>
			</s:iterator>
		</div>
		<div id="framecenter" position="center">
	    </div>
	</div>
	<div class="bottom">
		<div class="left-c"></div>
		<div class="right-c"></div>
		<div class="clear"></div>
		<div class="footer">${tools.copyright }</div> </div>
</div>
</body>
</html>