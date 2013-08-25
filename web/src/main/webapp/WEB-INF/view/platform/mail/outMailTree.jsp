<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" import="org.sz.platform.system.model.Position" import="org.sz.platform.model.system.Resources"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>邮件</title>
	<%@include file="/commons/include/form.jsp" %>
	<base target="_self"/> 
	<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet"	type="text/css" />
	<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	<style type="text/css">
	.tree-title{overflow:hidden;width:8000px;}
	.ztree{overflow: auto;}
	</style>
	<script type="text/javascript"	src="${ctx }/jslib/lg/plugins/ligerLayout.js"></script>
	<script type="text/javascript"	src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
	<script type="text/javascript">
		//树节点是否可点击
		var treeNodelickAble=true;
		var aryIcon=new Array();
		aryIcon.push("${ctx}/themes/img/icon/email.png");
		aryIcon.push("${ctx}/themes/img/tree/mail_inbox.png");
		aryIcon.push("${ctx}/themes/img/tree/mail_outbox.png");
		aryIcon.push("${ctx}/themes/img/tree/mail_drafts.png");
		aryIcon.push("${ctx}/themes/img/tree/mail_trash.png");
		$(function()
		{
			loadTree();
			layout();
		});
		//布局
		function layout(){
			$("#layout").ligerLayout( {
				leftWidth : 220,
				onHeightChanged: heightChanged,
				allowLeftResize :false
			});
			//取得layout的高度
	        var height = $(".l-layout-center").height();
	        $("#treeObject").height(height-60);
		};
		//布局大小改变的时候通知tab，面板改变大小
	    function heightChanged(options){
	     	$("#treeObject").height(options.middleHeight - 60);
	    };
		//树
		var treeObject;
		//加载树
		function loadTree(){
			var setting = {
				data: {
					key : {
						name: "userName",
						title: "userName"
					},
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId",
						rootPId: 0
					}
				},
				view: {
					selectedMulti: false,
					showLine : false
				},
				callback:{
					onClick: zTreeOnLeftClick
				}
				
			};
			
			$.post("${ctx}/platform/mail/outMail/getOutMailUserSetingData.xht",
				 function(result){
					for(var i=0;i<result.length;i++){
						var n=result[i];
						n.types==null?n.icon=aryIcon[0]:n.icon=aryIcon[n.types];
					}
					treeObject= $.fn.zTree.init($("#treeObject"), setting, result);
					treeObject.expandAll(true);     
				});
		};
		
		//左击
		function zTreeOnLeftClick(event, treeId, treeNode){
			if(treeNode.parentId!=0){
				returnUrl="${ctx}/platform/mail/outMail/list.xht?id="+treeNode.parentId+"&types="+treeNode.types;
				$("#listFrame").attr("src",returnUrl);
			}
		};
		
		//展开收起
		function treeExpandAll(type){
			treeObject = $.fn.zTree.getZTreeObj("treeObject");
			treeObject.expandAll(type);
		};

</script>
</head>

<body>
	<div id="layout">
		<div position="left" title="邮箱">
			<div class="tree-toolbar">
				<span class="toolBar">
					<div class="group"><a class="link reload" id="treeFresh" href="javascript:loadTree();">刷新</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)">展开</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a></div>
				</span>
			</div>
			<div id="treeObject" class="ztree"></div>
		</div>
		
		<div position="center">
			<iframe id="listFrame" src="${ctx}/platform/mail/outMail/list.xht" frameborder="no" width="100%" height="100%"></iframe>
		</div>
	</div>
</body>
</html>

