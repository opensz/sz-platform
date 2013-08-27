<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>组织架构管理</title>
<%@include file="/commons/include/get.jsp"%>
<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css"
	type="text/css" />
<script type="text/javascript"
	src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript"
	src="${ctx }/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript"
	src="${ctx }/jslib/tree/v30/jquery.ztree.exedit-3.0.min.js"></script>
<script type="text/javascript">
	var orgTree; //树
	var menu;
	var menu_root;
    var demId=1;
	var height;
	var type="${type}";
	if(type==""){
		//type="dept";
	}
	//var type="dept";
	$(function() {
		$("#layout").ligerLayout({
			leftWidth : 220,
			height : '100%',
			allowLeftResize : false
		});
		height = $('#layout').height();
		$("#viewFrame").height(height - 25);
		$('#demensionId').change(function() {
			demId = $(this).val();
			loadTree(demId);
		});
		$("#treeReFresh").click(function() {
			var demensionId = $("#demensionId").val();
			loadTree(demensionId);
		});

		$("#treeExpand").click(function() {
			orgTree.expandAll(true);
		});
		$("#treeCollapse").click(function() {
			orgTree.expandAll(false);
		});
		//菜单
		getMenu();
		//首先加载行政维度
		loadTree(1);

		$("#demensionId").val(1);
	});

	function loadTree(selid) {
		var setting = {
			data : {
				key : {
					name : "orgName"
				},
				simpleData : {
					enable : true,
					idKey : "orgId",
					pIdKey : "orgSupId",
					rootPId : 0
				}
			},
			// 拖动
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false,
				drag : {
					prev : true,
					inner : true,
					next : true,
					isMove : true,
					isCopy : true
				}
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : zTreeOnLeftClick,
				onRightClick : zTreeOnRightClick,
				beforeDrop : beforeDrop,
				onDrop : onDrop
			}
		};
        
        
        
		$.post(
			"${ctx}/platform/system/sysOrg/getTreeData.xht?demId="
					+ selid+"&type="+type,
			function(result) {
				for ( var i = 0; i < result.length; i++) {
					var node = result[i];
					if (node.isRoot == 1) {
						node.icon = __ctx
								+ "/styles/default/images/icon/root.png";
					} else {
						if (node.ownUser == null
								|| node.ownUser.length < 1) {
							node.orgName += "[未]";
						}

						switch (node.orgType) {
						case 1:
							node.icon = __ctx
									+ "/styles/default/images/icon/group.gif";
							break;
						case 2:
							node.icon = __ctx
									+ "/styles/default/images/icon/org.gif";
							break;
						case 3:
							node.icon = __ctx
									+ "/styles/default/images/icon/dep.gif";
							break;
						case 4:
							node.icon = __ctx
									+ "/styles/default/images/icon/unit.gif";
							break;
						case 5:
							node.icon = __ctx
									+ "/styles/default/images/icon/icon-reload.gif";
							break;
						}
					}

				}
				orgTree = $.fn.zTree.init($("#orgTree"), setting,result);
				orgTree.expandAll(false);
				
				//取得layout的高度
        		var height = $(".l-layout-center").height();
        		$("#orgTree").height(height-85);
				//height = $('#rogTree').height();
				//$("#orgTree").height(height - 50);
				//$("#orgTree").css("height",);
			});
	};

	//拖放 前准备
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {

		if (!treeNodes)
			return false;
		if (targetNode.isRoot == 1)
			return false;
		return true;
	};

	//左击事件
	function zTreeOnLeftClick(event, treeId, treeNode) {
		var isRoot = treeNode.isRoot;
		if (isRoot == 1) {
			return;
		}
		var orgId = treeNode.orgId;
		$("#viewFrame").attr("src", "get.xht?orgId=" + orgId);
	};

	/**
	 * 右击事件
	 */
	function zTreeOnRightClick(e, treeId, treeNode) {
		orgTree.selectNode(treeNode);
		if (treeNode.isRoot == 1) {//根节点时，把删除和编辑隐藏掉
			menu_root.show({
				top : e.pageY,
				left : e.pageX
			});
		} else {
			menu.show({
				top : e.pageY,
				left : e.pageX
			});
		}
	};

	//右键菜单
	function getMenu() {
		menu = $.ligerMenu({
			top : 100,
			left : 100,
			width : 100,
			items : [ {
				text : '增加',
				click : addNode
			}, {
				text : '编辑',
				click : editNode
			}, {
				text : '参数属性',
				click : orgParam
			}, {
				text : '删除',
				click : delNode
			} ]
		});

		menu_root = $.ligerMenu({
			top : 100,
			left : 100,
			width : 100,
			items : [ {
				text : '增加',
				click : addNode
			} ]
		});
	};
	//编辑组织参数属性
	function orgParam() {
		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = orgTree.getSelectedNodes();
		var treeNode = nodes[0];
		var orgId = treeNode.orgId;
		var url = __ctx + "/platform/system/sysOrgParam/editByOrgId.xht?orgId="
				+ orgId;
		$("#viewFrame").attr("src", url);
	};

	function addNode() {
		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = orgTree.getSelectedNodes();
		var treeNode = nodes[0];
		var orgSupTempId;
		var parentId=recursiveNode(treeNode).orgId;
		
		var orgId = treeNode.orgId;
		//var demId = treeNode.demId;
		var url = "edit.xht?orgId=" + orgId + "&demId=" + demId + "&action=add&parentId="+parentId+"&orgSupTempId="+orgId;
		
		
		$("#viewFrame").attr("src", url);
	};

	function recursiveNode(treeNode){
		if(treeNode.orgType=='3'){
			treeNode=recursiveNode(treeNode.getParentNode());
		}
		return treeNode;
	}
	//编辑节点
	function editNode() {
		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = orgTree.getSelectedNodes();
		var treeNode = nodes[0];
		var orgId = treeNode.orgId;//如果是新增时它就变成父节点Id	      
		var demId = treeNode.demId;
		var orgSupTempId;
		var parentId=recursiveNode(treeNode).orgId;
		if(treeNode.getParentNode()!=null){
			orgSupTempId=treeNode.getParentNode().orgId;
		}
		else{
			orgSupTempId=treeNode.orgId;
		}
		//var orgSupTempId=treeNode.getParentNode().orgId;
		//console.log(treeNode.getParentNode());
		var url = "edit.xht?orgId=" + orgId + "&demId=" + demId + "&flag=upd&parentId="+parentId+"&orgSupTempId="+orgSupTempId;
		$("#viewFrame").attr("src", url);
	};

	function delNode() {
		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = orgTree.getSelectedNodes();
		var node = nodes[0];
		var callback = function(rtn) {
			if (!rtn)
				return;
			var params = "orgId=" + node.orgId;
			$.post("orgdel.xht", params, function() {
				orgTree.removeNode(node);
			});
		};
		$.ligerMessageBox.confirm('提示信息', "确认要删除此组织吗，其下组织也将被删除？", callback);

	};

	//拖放 后动作
	function onDrop(event, treeId, treeNodes, targetNode, moveType) {
		if (targetNode == null || targetNode == undefined)
			return;
		var targetId = targetNode.orgId;
		var dragId = treeNodes[0].orgId;
		var url = __ctx + "/platform/system/sysOrg/move.xht";
		var params = {
			targetId : targetId,
			dragId : dragId,
			moveType : moveType
		};

		$.post(url, params, function(result) {
			var demensionId = $("#demensionId").val();
			loadTree(demensionId);
		});
	}
</script>
<style type="text/css">
html {
	height: 100%
}

body {
	padding: 0px;
	margin: 0;
	overflow: auto;
}

#layout {
	width: 99.5%;
	margin: 0;
	padding: 0;
}
</style>
</head>
<body>

	<div id="layout" style="bottom: 1; top: 1">
		<div position="left" title="组织机构管理" id="rogTree"
			style="height: 100%; width: 100% !important;">
			<div style="width: 100%;">
				<select id="demensionId" style="width: 99.8% !important;">
					<option value="0">---------全部---------</option>
					<c:forEach var="dem" items="${demensionList}">
						<option value="${dem.demId}">${dem.demName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="tree-toolbar" id="pToolbar">
				<div class="toolBar"
					style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap">
					<div class="group">
						<a class="link reload" id="treeReFresh">刷新</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link expand" id="treeExpand">展开</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link collapse" id="treeCollapse">收起</a>
					</div>
				</div>
			</div>
			<div style="height: 100%;" id="tree">
			<ul id="orgTree" class="ztree"
				style="height:500px; margin: 0; padding: 0;overflow:auto;"></ul>
			</div>
		</div>
		<div position="center" id="orgView" style="height: 100%;">
			<div class="l-layout-header">组织架构信息</div>
			<iframe id="viewFrame" src="get.xht" frameborder="0" width="100%"
				height="100%"></iframe>
		</div>
	</div>

</body>
</html>


