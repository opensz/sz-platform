<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/commons/include/form.jsp" %>
	<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx}/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
	<script type="text/javascript">
	
		var posTree = null;
		$(function() {
			$("#defLayout").ligerLayout({
				leftWidth : 220,
				height : '100%'
			});
			loadTree();
		});
	
		//展开收起
		function treeExpandAll(type) {
			posTree = $.fn.zTree.getZTreeObj("posTree");
			posTree.expandAll(type);
		};
	
		function loadTree(value) {
			var setting = {
				data : {
					key : {
						name : "posName",
						title : "posName"
					},
					simpleData : {
						enable : true,
						idKey : "posId",
						pIdKey : "parentId",
						rootPId : 0
					}
				},
				callback : {
					onClick : treeClick
				}
			};
	
			$.ajax({
				type : 'POST',
				url : "${ctx}/platform/system/position/getTreeData.xht",
				success : function(result) {
					posTree = $.fn.zTree.init($("#posTree"), setting,
							eval(result));
					posTree.expandAll(true);
				}
			});
	
			setPid(0);
		}
		//选择分类
		function getSelectNode() {
			posTree = $.fn.zTree.getZTreeObj("posTree");
			var nodes = posTree.getSelectedNodes();
			var node = nodes[0];
			if (node == null || node.posId == 0)
				return '';
			return node.posId;
		}
	
		function treeClick(event, treeId, treeNode) {
			var pid = getSelectNode();
			var url = "${ctx}/platform/system/position/selector.xht?" + "pid=" + pid;
			$("#posFrame").attr("src", url);
	
			setPid(pid);
		}
	
		function setPid(pid) {
			document.getElementById('pid').value = pid;
		}
		
		function  selectPosition(){
			var aryIds=new Array();
			var aryNames=new Array();
			$('#posFrame').contents().find("input[name='posId']:checked").each(function(){
				aryNames.push($(this).siblings("input[name='posName']").val());
				aryIds.push($(this).val());
			});
			var posIds=aryIds.join(",");
			var posNames=aryNames.join(",");
			window.returnValue={posId:posIds,posName:posNames};
			window.close();
			
		}
	</script>
</head>
<body>
	<div id="defLayout">
		<div position="left" title="岗位树" style="overflow:auto;">
			<div id="dename"></div>

			<div class="tree-toolbar">
				<span class="toolBar">
					<div class="group">
						<a class="link reload" id="treeFresh" href="javascript:loadTree();">刷新</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)">展开</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link collapse]" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a>
					</div>
				</span>
			</div>
			<ul id="posTree" class="ztree"></ul>
		</div>
		<div position="center">
			<div class="l-layout-header">岗位列表</div>
			<div class="panel-search">
				<form id="searchForm" action="selector.xht" method="POST" target="posFrame">
					<div class="row">
						<span class="label">岗位名称:</span> 
						<input type="hidden" name="pid" id="pid" /> 
						<input type="text" id="posName" name="posName" class="inputText" size="40" /> &nbsp; 
						<a href='#' class='button'  onclick="$('#searchForm').submit();"><span>查询</span></a>
					</div>
				</form>
			</div>

			<iframe id="posFrame" name="posFrame" height="90%" width="100%" frameborder="0" src="${ctx}/platform/system/position/selector.xht"></iframe>
		</div>
		<div position="bottom"  class="bottom">
			<a href='#' class='button'  onclick="selectPosition()" ><span class="icon ok"></span><span>选择</span></a>
			<a href='#' class='button' style='margin-left:10px;'  onclick="window.close()"><span class="icon cancel"></span><span>取消</span></a>
		</div>
	</div>
</body>
</html>