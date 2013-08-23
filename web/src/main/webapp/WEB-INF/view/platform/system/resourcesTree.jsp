<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" import="org.sz.platform.model.system.Resources"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>资源管理</title>
<%@include file="/commons/include/form.jsp" %>
<base target="_self"/> 
<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet"	type="text/css" />
<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css" />
<script type="text/javascript"	src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script> 
<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.exedit-3.0.min.js"></script> 
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript">
	var foldMenu;
	var leafMenu;
	//树节点是否可点击
	var treeNodelickAble=true;
	//当前访问url
	var returnUrl="${ctx}/platform/system/resources/get.xht?resId=";
	//当前访问系统
	var systemId=null;
	
	$(function(){
		//加载树
		systemId=$("#subSystem").val();
		loadTree();
		//改变子系统
		$("#subSystem").change(function(){
			systemId=$("#subSystem").val();
			loadTree();
		});
		//布局
		layout();
		//菜单
		menu();
	});
	//菜单
	function menu(){
		foldMenu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
        [
        { text: '增加节点', click: addNode },
        { text: '编辑节点', click: editNode  },
        { text: '删除节点', click: delNode },
        { text: '刷新', click: refreshNode }
        ]
        });

		leafMenu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
        [
       
        { text: '编辑节点', click: editNode  },
        { text: '删除节点', click: delNode },
        { text: '编辑URL', click: editUrl },
        ]
        });
	};
	//刷新
	function refreshNode(){
		var selectNode=getSelectNode();
		reAsyncChild(selectNode);
	};
	//布局
	function layout(){
		$("#layout").ligerLayout( {
			leftWidth : 300,
			onHeightChanged: heightChanged,
			allowLeftResize:false
		});
		//取得layout的高度
        var height = $(".l-layout-center").height();
        $("#resourcesTree").height(height-85);
	};
	//布局大小改变的时候通知tab，面板改变大小
    function heightChanged(options){
     	$("#resourcesTree").height(options.middleHeight - 85);
    };
	//树
	var resourcesTree;
	//加载树
	function loadTree(){
		
		var setting = {
			data: {
				key : {
					name: "resName",
					title: "resName"
				},
				simpleData: {
					enable: true,
					idKey: "resId",
					pIdKey: "parentId",
					rootPId: <%=Resources.ROOT_PID%>
				}
			},
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				drag: {
					prev: dropPrev,
					inner: dropInner,
					next: dropNext
				},
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			callback:{
				onClick: zTreeOnLeftClick,
				onRightClick: zTreeOnRightClick,
				beforeClick:zTreeBeforeClick,
				beforeDrop:beforeDrop,
				onDrop: onDrop
			}
			
		};
		//一次性加载
		$.ajax({
			type: 'POST',
			url:"${ctx}/platform/system/resources/getSystemTreeData.xht?systemId="+systemId,
			success: function(result){
				resourcesTree=$.fn.zTree.init($("#resourcesTree"), setting,eval(result));
				resourcesTree.setting.async.enable=true;
				treeNodelickAble=true;
			}
		});
		

	};

	//向前拖
	function dropPrev(treeId,curDragNodes,treeNode) {
		var pNode =treeNode.getParentNode();
		if (pNode && pNode.dropInner === false) {
			return false;
		} else {
			for (var i=0,l=curDragNodes.length; i<l; i++) {
				var curPNode = curDragNodes[i].getParentNode();
				if (curPNode && curPNode !== treeNode.getParentNode() && curPNode.childOuter === false) {
					return false;
				}
			}
		}
		return true;
	};
	//向内拖
	function dropInner(treeId,curDragNodes,treeNode) {
		if (treeNode && treeNode.dropInner === false) {
			return false;
		} else {
			for (var i=0,l=curDragNodes.length; i<l; i++) {
				if (!treeNode && curDragNodes[i].dropRoot === false) {
					return false;
				} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== treeNode && curDragNodes[i].getParentNode().childOuter === false) {
					return false;
				}
			}
		}
		return true;
	};
	//向后拖
	function dropNext(treeId,curDragNodes,treeNode) {
		var pNode =treeNode.getParentNode();
		if (pNode && pNode.dropInner === false) {
			return false;
		} else {
			for (var i=0,l=curDragNodes.length; i<l; i++) {
				var curPNode = curDragNodes[i].getParentNode();
				if (curPNode && curPNode !== treeNode.getParentNode() && curPNode.childOuter === false) {
					return false;
				}
			}
		}
		return true;
	};
	
	//拖放 前准备
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		if (!treeNodes) return false;
		else{
			var drop=true;
			var isFolder=targetNode.isFolder;
			//判断是否为栏目,不是档目不充许拖放
			if(isFolder!=<%=Resources.IS_FOLDER_Y%>){
				$.ligerDialog.confirm(targetNode.resName+'不是栏目节点,不能接收子节点!');
				drop= false;
			}
			
			$.each(treeNodes,function(i,n){
				if(n.parentId==targetNode.resId){
					drop=false;
				}
			});
			
			return drop;
		}

	};
	//拖放 后动作
	function onDrop(event, treeId, treeNodes, targetNode, moveType) {
		if(targetNode){
			var targetId=targetNode.resId;
			var originalIds="";
			$.each(treeNodes,function(i,n){
				originalIds+=n.resId+",";
			});
			if(originalIds.length>1){
				originalIds=originalIds.substring(0,originalIds.length-1);
				$.ajax({
					type: 'POST',
					url:"${ctx}/platform/system/resources/move.xht?systemId="+systemId+"&targetId="+targetId+"&originalIds="+originalIds,
					success: function(result){
						//异步更新当前节点
						reAsyncChild(targetNode);
					}
				});
			}
		}
	}

	//异步更新当前节点
	function reAsyncChild(targetNode){
		var resId=targetNode.resId;
		if(resId==<%=Resources.ROOT_ID%>){
			loadTree();
		}else{
			resourcesTree = $.fn.zTree.getZTreeObj("resourcesTree");
			resourcesTree.reAsyncChildNodes(targetNode, "refresh", false);
		}
		treeNodelickAble=true;
	};

	//左击前
	function zTreeBeforeClick(treeId, treeNode, clickFlag){
		return treeNodelickAble;
	};
	//保存排序后的顺序
	function sortSn(targetNode,resIds){
		$.ajax({
			type: 'POST',
			url:"${ctx}/platform/system/resources/sort.xht?resIds="+resIds,
			success: function(result){
				//异步更新当前节点
				reAsyncChild(targetNode);
			}
		});
	};
	/**
	*显示
	*/
	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		//前结点
		var netxNode=treeNode.getNextNode();
		//后结点
		var preNode=treeNode.getPreNode();
		
		var sObj = $("#" + treeNode.tId + "_span");

		//向上
		if(preNode){
			if ($("#upBtn_"+treeNode.id).length>0) return;
			var upStr = "<button  type='button' class='link-sortUp' id='upBtn_" + treeNode.id	+ "' title='向前' ></button>";
			sObj.append(upStr);
	
			var upBtn_ = $("#upBtn_"+treeNode.id);
			if (upBtn_) upBtn_.bind("click", function(){
				treeNodelickAble=false;
				//取前结点
				var preNode = treeNode.getPreNode();
				//如果前结点存在
				if(preNode!=null){
					//前后结点置换
					var thisResId= treeNode.resId;
					var preResId= preNode.resId;
					treeNode.resId=preResId;
					preNode.resId =thisResId;
					//取当下同级结点所有ID
					var parentNode=treeNode.getParentNode();
					var children=parentNode.children;
					var resIds="";
					$.each( children, function(i, c){
						resIds+=c.resId+",";	 
					});
					if(resIds.length>1){
						resIds=resIds.substring(0,resIds.length-1);
						sortSn(parentNode,resIds);
					}
				}
			});
		}

		//向下
		if(netxNode){
			if ($("#downBtn_"+treeNode.id).length>0) return;
			var downStr = "<button  type='button' class='link-sortDown' id='downBtn_" + treeNode.id	+ "' title='向后' ></button>";
			sObj.append(downStr);
	
			var downBtn_ = $("#downBtn_"+treeNode.id);
			if (downBtn_) downBtn_.bind("click", function(){
				treeNodelickAble=false;
				//取后结点
				var nextNode = treeNode.getNextNode();
				//如果前结点存在
				if(nextNode!=null){
					//前后结点置换
					var thisResId= treeNode.resId;
					var nextResId= nextNode.resId;
					treeNode.resId =nextResId;
					nextNode.resId =thisResId;
					//取当下同级结点所有ID
					var parentNode=treeNode.getParentNode();
					var children=parentNode.children;
					var resIds="";
					$.each( children, function(i, c){
						resIds+=c.resId+",";	 
					});
					if(resIds.length>1){
						resIds=resIds.substring(0,resIds.length-1);
						//保存排序后的顺序
						sortSn(parentNode,resIds);
					}
				}
			});
		}
		
		//最上
		if(preNode){
			if ($("#topBtn_"+treeNode.id).length>0) return;
			var topBtnStr = "<button  type='button' class='link-sortTop' id='topBtn_" + treeNode.id	+ "' title='最前' ></button>";
			sObj.append(topBtnStr);
	
	
			var topBtn_ = $("#topBtn_"+treeNode.id);
			if (topBtn_) topBtn_.bind("click", function(){
				treeNodelickAble=false;
				//取父结点
				var parentNode = treeNode.getParentNode();
				//如果父结点存在
				if(parentNode!=null){
					var children=parentNode.children;
					var resIds=treeNode.resId+",";
					$.each( children, function(i, c){
						if(c.resId!=treeNode.resId)
						resIds+=c.resId+",";	 
					});
					if(resIds.length>1){
						resIds=resIds.substring(0,resIds.length-1);
						sortSn(parentNode,resIds);
					}
				}
			});
		}

		//最下
		if(netxNode){
			if ($("#bottomBtn_"+treeNode.id).length>0) return;
			var bottomBtnStr = "<button  type='button' class='link-sortBottom' id='bottomBtn_" + treeNode.id	+ "' title='最后' ></button>";
			sObj.append(bottomBtnStr);
	
			var bottomBtn_ = $("#bottomBtn_"+treeNode.id);
			if (bottomBtn_) bottomBtn_.bind("click", function(){
				treeNodelickAble=false;
				//取父结点
				var parentNode = treeNode.getParentNode();
				//如果父结点存在
				if(parentNode!=null){
					var children=parentNode.children;
					var resIds="";
					$.each( children, function(i, c){
						if(c.resId!=treeNode.resId)
						resIds+=c.resId+",";	 
					});
					
					resIds+=treeNode.resId+",";
					if(resIds.length>1){
						resIds=resIds.substring(0,resIds.length-1);
						sortSn(parentNode,resIds);
					}
				}
			});
		}
	
	};
	/**
	*隐藏
	*/
	function removeHoverDom(treeId, treeNode) {
		$("#upBtn_"+treeNode.id).unbind().remove();
		$("#downBtn_"+treeNode.id).unbind().remove();
		$("#topBtn_"+treeNode.id).unbind().remove();
		$("#bottomBtn_"+treeNode.id).unbind().remove();
	};
	

	//左击
	function zTreeOnLeftClick(event, treeId, treeNode){
		var resId=treeNode.resId;
		if(resId==<%=Resources.ROOT_ID%>){return;}
		returnUrl="${ctx}/platform/system/resources/get.xht?resId="+treeNode.resId;
		$("#listFrame").attr("src",returnUrl);
	};
	/**
	 * 树右击事件
	 */
	function zTreeOnRightClick(e, treeId, treeNode) {
		if (treeNode&&!treeNode.notRight) {
			resourcesTree.selectNode(treeNode);
			var isfolder=treeNode.isFolder;
			
			if(isfolder==<%=Resources.IS_FOLDER_Y%>)
			{
				foldMenu.show({ top: e.pageY, left: e.pageX });
			}
			else if(isfolder==<%=Resources.IS_FOLDER_N%>)
			{
				leafMenu.show({ top: e.pageY, left: e.pageX });
			}
			
		}
	};
	
	//展开收起
	function treeExpandAll(type){
		resourcesTree = $.fn.zTree.getZTreeObj("resourcesTree");
		resourcesTree.expandAll(type);
	};

	//添加资源
	function addNode(){
		$("#listFrame").attr("src","${ctx}/platform/system/resources/edit.xht?parentId="+getSelectNode().resId+"&returnUrl="+returnUrl+"&systemId="+systemId);
	};
	//编辑资源
	function editNode(){
		var selectNode=getSelectNode();
		var resId=selectNode.resId;
		if(resId==<%=Resources.ROOT_ID%>){$.ligerDialog.confirm('该节点为系统节点 ,不充许该操作');return;}
		
		$("#listFrame").attr("src","${ctx}/platform/system/resources/edit.xht?resId="+getSelectNode().resId+"&returnUrl="+returnUrl);
		
	};
	//删除资源
	function delNode(){
		var selectNode=getSelectNode();
		var resId=selectNode.resId;
		if(resId==<%=Resources.ROOT_ID%>){$.ligerDialog.confirm('该节点为系统节点 ,不充许该操作');return;}
	 	var callback = function(rtn) {
			if(rtn){
				$.ajax({
					type: 'POST',
					url:"${ctx}/platform/system/resources/del.xht?resId="+resId+"&returnUrl="+returnUrl,
					success: function(result){
						var parentNode=selectNode.getParentNode();
						if(parentNode){
							reAsyncChild(parentNode);
						}
					}
				});
			}
		};
		$.ligerMessageBox.confirm('提示信息','确认删除吗？',callback);
	};
	//
	function editUrl(){
		var selectNode=getSelectNode();
		var resId=selectNode.resId;
		$("#listFrame").attr("src","${ctx}/platform/system/resourcesUrl/edit.xht?resId="+resId+"&returnUrl="+returnUrl);
	};

	//选择分类
	function getSelectNode()
	{
		resourcesTree = $.fn.zTree.getZTreeObj("resourcesTree");
		var nodes  = resourcesTree.getSelectedNodes();
		var node   = nodes[0];
		return node;
	}
	//刷新
	function reFresh(){
		loadTree();
	};

</script>
<style type="text/css">
.tree-title{overflow:hidden;width:8000px;}
.ztree{overflow: auto;}
</style>

</head>

<body>

<div id="layout">
	<div position="left" title="资源管理" >
		<div style="width:100%;">
		        <select id="subSystem" style="width:99.8% !important;">  
		              <c:forEach var="subSystemItem" items="${subSystemList}">  
		         			<option value="${subSystemItem.systemId}" 
		         			<c:choose>
		         				<c:when test="${subSystemItem.systemId==currentSystemId}">
		         					selected="selected"
		         				</c:when>
		         			</c:choose>>${subSystemItem.sysName}</option>  
		        	  </c:forEach>  
		        </select>
		  </div>
		<div class="tree-toolbar tree-title" id="pToolbar">
			<span class="toolBar">
				<div class="group"><a class="link reload" id="treeFresh" href="javascript:reFresh();">刷新</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)">展开</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a></div>
			</span>
		</div>
		<div id="resourcesTree" class="ztree"></div>
	</div>
    
	<div position="center">
		<iframe id="listFrame" src="" frameborder="no" width="100%" height="100%"></iframe>
	</div>
</div>
</body>
</html>

