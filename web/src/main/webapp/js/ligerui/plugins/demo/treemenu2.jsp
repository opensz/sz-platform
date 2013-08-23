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


$(function(){
	
	var setting = {
			data: {key: {childs: "nodes"}},
			callback:{onClick: zTreeOnLeftClick},
			view: {
				selectedMulti: false
			},
			onRightClick: true
		};
		
		var tr = new org.sz.form.htTree({
			menuName:"rMenu",
			treeName:"resTree",
			//url:"${ctx}/platform/system/department/tree.xht",
			url:"${ctx }/js/tree/treeData1.js",
			//param:{},
			setting:setting
		});
		
		//tr.showRMenu(0, 0, 'rMenu');
		
		function zTreeOnLeftClick(){
			alert(5555555)
		}
	
});

</script>
</head>
<body>

<ul id="resTree" class="ztree" style="width:200px;margin:0; padding:0;" >
</ul>

<div id="rMenu" class="menuContainer" style="width:80px;" >
		<a id="btnAddNode" >增加节点</a>
		<a id="btnEditNode" >编辑节点</a>
		<a id="btnDelNode" >删除节点</a>
		<a id="btnSeqNode" >节点排序</a>
		<a id="btnEditUrlNode" >编辑URL</a>
</div>
</body>
</html>