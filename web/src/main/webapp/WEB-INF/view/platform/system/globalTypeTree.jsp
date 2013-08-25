<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" import="org.sz.platform.system.model.GlobalType"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>总分类</title>
	<%@include file="/commons/include/form.jsp" %>
	<base target="_self"/> 
	<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet"	type="text/css" />
	<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script> 
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.exedit-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/GlobalMenu.js"></script>
	<script type="text/javascript">
		var catId=${sysTypeKey.typeId};
	</script>
	<script type="text/javascript" src="${ctx}/js/sz/platform/system/GlobalType.js"></script>
	<style type="text/css">
		.ztree{
		   overflow: auto;
		}
	</style>
</head>
<body>
	<div id="layout">
		<div position="left" title="分类管理"  >
		   <div style="width:100%;">
		        <select id="dkey" style="width:99.8% !important;">  
		              <c:forEach var="key" items="${typeList}">  
		             	 <option style="text-align:left" value="${key.typeId}">${key.typeName}</option>  
		              </c:forEach>  
		        </select>
		   </div>
			<div class="tree-toolbar tree-title">
				<span class="toolBar" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap">
					<div class="group"><a class="link reload" id="treeFresh" href="javascript:refresh();">刷新</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)">展开</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a></div>
				</span>
			</div>
			<div id="glTypeTree" class="ztree"></div>
		</div>
		<div position="center">
			<iframe id="listFrame" src="" frameborder="no" width="100%" height="100%"></iframe>
		</div>
	</div>
</body>
</html>

