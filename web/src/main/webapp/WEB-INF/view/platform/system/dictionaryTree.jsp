<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" import="org.sz.platform.model.system.GlobalType"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>数据字典</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<link href="${ctx}/jslib/lg/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	<f:link href="web.css" ></f:link>
	<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
	<script type="text/javascript" src="${ctx}/jslib/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/lg/ligerui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.exedit-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/form/GlobalType.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/GlobalMenu.js"></script>
	<script type="text/javascript">
		var catKey="<%=GlobalType.CAT_DIC%>";
		var menuMenu;
		var dictTree;
		var dicMenu=new DicMenu();
		var curMenu;
		var selectDictionaryId=0;
		
		var globalType=new GlobalType(catKey,"glTypeTree",{onClick:zTreeOnLeftClick,onRightClick:catRightClick});
		
		$(function(){
			layout();
			globalType.loadGlobalTree();
		});
	
		//布局
		function layout(){
			$("#layout").ligerLayout( {leftWidth : 210,height: '100%', onHeightChanged: heightChanged});
			//取得layout的高度
	        var height = $(".l-layout-center").height();
			
	        $("#glTypeTree").height(height-60);
		};
		//布局大小改变的时候通知tab，面板改变大小
	    function heightChanged(options){
	     	//$("#glTypeTree").height(options.middleHeight - 60);
	    };
	    
	    function hiddenMenu(){
			if(curMenu){
				curMenu.hide();
			}
			if(menuMenu){
				menuMenu.hide();
			}
		}
	    
	    function handler(item){
           	hiddenMenu();
           	var txt=item.text;
           	switch(txt){
           		case "增加字典分类":
           			globalType.openGlobalTypeDlg(true);
           			break;
           		case "编辑分类":
           			globalType.openGlobalTypeDlg(false);
           			break;
           		case "删除":
           			globalType.delNode();
           			break;
           	}
        }
		
	
		/**
    	 * 树右击事件
    	 */
    	function catRightClick(event, treeId, treeNode) {
    		
    		if (treeNode) {
    			globalType.currentNode=treeNode;
    			globalType.glTypeTree.selectNode(treeNode);
    			curMenu=dicMenu.getMenu(treeNode, handler);
    			if(curMenu){
    				curMenu.show({ top: event.pageY, left: event.pageX });	
    			}
    		}
    	};
		
		
		//左击
		function zTreeOnLeftClick(treeNode){
			if(treeNode.isRoot==undefined){
				loadDictByTypeId();
			}
				
		};
		//展开收起
		function treeExpandAll(type){
			globalType.treeExpandAll(type);
		};

		
		/**
		*获取选择的节点。
		*/
		function getSelectNode(){
			dictTree = $.fn.zTree.getZTreeObj("dictTree");
			var nodes = dictTree.getSelectedNodes();
			if(nodes){
				selectDictionaryId=nodes[0].dicId;
				return nodes[0];
			}
			return null;
		}
		
		//加载数据字典
		function loadDictByTypeId(){
			
			var selectNode=globalType.currentNode;
			if(!selectNode){
				$.ligerMessageBox.warn('提示信息','没有选择节点');
				return;
			}
			var dropInner=selectNode.type==1;
			
			var typeId=selectNode.typeId;
			var setting = {
					edit: {
						drag: {
							prev: true,
							inner: dropInner,
							next: true,
							isMove:true
						},
						enable: true,
						showRemoveBtn: false,
						showRenameBtn: false
					},
					data: {
						key : {name: "itemName"},
						simpleData: {enable: true,idKey: "dicId",pIdKey: "parentId"}
					},
					view: {selectedMulti: false},
					callback:{onRightClick: dictRightClick,
						onDrop: onDrop,beforeDrop:onBeforeDrop }
				};
			var url="${ctx}/platform/system/dictionary/getByTypeId.xht";
			var params={typeId:typeId};
			$.post(url,params,function(result){
			
				updDicRootNode(result);
				dictTree=$.fn.zTree.init($("#dictTree"), setting,result);
				dictTree.expandAll(true);
				if(selectDictionaryId>0){
					var node = dictTree.getNodeByParam("dicId", selectDictionaryId, null);
					dictTree.selectNode(node);
				}
			});
		}
		
		function onBeforeDrop(treeId, treeNodes, targetNode, moveType){
			if(targetNode.isRoot==1 && moveType!="inner"){
				return false;
			}
			return true;
		}
		
		function onDrop(event, treeId, treeNodes, targetNode, moveType) {
			if(targetNode==null || targetNode==undefined) return false;
			var targetId=targetNode.dicId;
			var dragId=treeNodes[0].dicId;
			var url=__ctx + "/platform/system/dictionary/move.xht";
			var params={targetId:targetId,dragId:dragId,moveType:moveType};

			$.post(url,params,function(result){});
		}
		
		//标记根节点。
		function updDicRootNode(result){
			for(var i=0;i<result.length;i++){
				var node=result[i];
				if(node.parentId==0){
					node.isRoot=1;
					node.parentId==0;
					node.icon=__ctx + "/themes/img/icon/root.png";
					break;
				}
			}
		}
		
		//编辑字典数据
		function editDict(){
			var selectNode=getSelectNode();
			var dicId=selectNode.dicId;
			
			if(selectNode.isRoot==1){
				return ;
			}
			var url="${ctx}/platform/system/dictionary/edit.xht?dicId=" + dicId +"&isAdd=0";
			var winArgs="dialogWidth=450px;dialogHeight=200px;help=no;status=no;scroll=no;center=yes";
			var conf={callBack:function(){
				loadDictByTypeId();
			}};
			hiddenMenu();
			url=url.getNewUrl();
			var rtn=window.showModalDialog(url,conf,winArgs);
			
		}
		
		function dictRightClick(e, treeId, treeNode){
			if (treeNode) {
				dictTree.selectNode(treeNode);
				var menuMenu=getDictMenu(treeNode);
				menuMenu.show({ top: e.pageY, left: e.pageX });
			}
		}
		
		function getDictMenu(treeNode){
			var items=new Array();
			if(treeNode.isRoot==1){
				items.push({ text: '增加字典项', click:addDict  });
			}
			else{
				if(treeNode.type==1){
					items.push({ text: '增加字典项', click:addDict  });
					items.push({ text: '编辑字典项', click:editDict  });
				}
				items.push({ text: '删除', click:delDict });
			}
			menuMenu=$.ligerMenu({ top: 100, left: 100, width: 120, items:items});
			return menuMenu;
		}
		
		//增加字典
		function addDict(){
			var selectNode=getSelectNode();
			var dicId=selectNode.dicId;
			var url="${ctx}/platform/system/dictionary/edit.xht?dicId=" + dicId +"&isAdd=1";
			if(selectNode.isRoot){
				url+="&isRoot=1";
			}
			var winArgs="dialogWidth=450px;dialogHeight=200px;help=no;status=no;scroll=no;center=yes";
			var conf={callBack:function(){
				loadDictByTypeId();
			}};
			hiddenMenu();
			url=url.getNewUrl();
			var rtn=window.showModalDialog(url,conf,winArgs);
			
		}
		
		//删除字典
		function delDict(){
			var callback=function(rtn){
				if(!rtn) return;
				var selectNode=getSelectNode();
				var dicId=selectNode.dicId;
				selectDictionaryId=selectNode.getParentNode().dicId;
			
				var url="${ctx}/platform/system/dictionary/del.xht";
				var params={dicId:dicId };
				$.post(url,params,function(responseText){
					
					var obj=new  org.sz.form.ResultMessage(responseText);
					if(obj.isSuccess()){
						$.ligerMessageBox.success("提示信息","删除字典项成功!",function(){
							loadDictByTypeId();
						});
					}
					else{
						$.ligerMessageBox.error("提示信息","删除字典项失败!");
					}
				});
			};
			$.ligerMessageBox.confirm('提示信息','将删除该字典项及下面的所有字典项目，确认删除吗？',callback);
		}
		
	</script>
	
	<style type="text/css">
		.ztree{overflow: auto;}
	</style>
</head>
<body>
<div id="layout">
	<div position="left" title="数据字典分类">
		<div class="tree-toolbar">
			<span class="toolBar">
				<div class="group"><a class="link reload" id="treeFresh" href="javascript:loadTree();">刷新</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)">展开</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a></div>
			</span>
		</div>
		<div id="glTypeTree" class="ztree"></div>
	</div>
	<div position="center" title="数据字典管理（点击右键操作,可以使用鼠标进行拖动)">
		<div id="dictTree" class="ztree"></div>
	</div>
	
</div>
</body>
</html>

