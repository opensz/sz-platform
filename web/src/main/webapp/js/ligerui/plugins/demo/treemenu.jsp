<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/commons/include/form.jsp"%>
<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerMenu.js" ></script>
<script type="text/javascript" src="${ctx }/js/ligerui/plugins/htTree.js" ></script>

<script type="text/javascript">
var setting = {
		data: {key: {childs: "nodes"}},
		callback:{
			onRightClick: zTreeOnRightClick},
		view: {
			selectedMulti: false
		}
};

var treeObj;
$(function(){
	bindBody();
	$.getScript("${ctx }/js/tree/treeData.js", function(){
		treeObj=$.fn.zTree.init($("#resTree" ), setting,zNodes);
	});
	
	/*var setting = {
		data: {key: {childs: "nodes"}},
		onRightClick: true,
		view: {
			selectedMulti: false
		}
	};
		
	var tr = new HtTree({
		menuName:"rMenu",
		treeName:"resTree",
		url:"${ctx }/js/tree/treeData.js",
		setting:setting
	});*/
	
});


	
	
function zTreeOnRightClick(event, treeId, treeNode) {
		
		if (treeNode ) {
			
			treeObj.selectNode(treeNode);
			showRMenu(treeNode.isfolder, event.clientX, event.clientY);
		}
	}
	
var rMenu;

function showRMenu(isfolder, x, y) {

	$("#rMenu a").show();


	$("#rMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"});
}
function hideRMenu() {
	if (rMenu) rMenu.css("visibility","hidden");
}


function bindBody()
{
	rMenu = $("#rMenu");
	$("body").bind("mousedown", 
		function(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css("visibility","hidden");
			}
		});
}


function remove()
{
	
	}

</script>
</head>
<body>

<ul id="resTree" class="ztree" style="width:200px;height: 200px;" >
</ul>

<div id="rMenu" class="menuContainer" style="width:80px;" >
		<a id="btnAddNode" >增加节点</a>
		<a id="btnEditNode" >编辑节点</a>
		<a id="btnDelNode" onclick="remove()">删除节点</a>
		<a id="btnSeqNode" >节点排序</a>
		<a id="btnEditUrlNode" >编辑URL</a>
</div>
</body>
</html>