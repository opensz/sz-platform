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
	
		var orgTree = null;
		$(function() {
			$("#defLayout").ligerLayout({
				leftWidth : 220,
				height : '90%',
				minLeftWidth:220
			});
			
			$('#demensionId').change(function(){
        		var demensionId=$(this).val();
        		loadTree(demensionId);
             });
			
			loadTree(1);
			
			$("#demensionId").val(1);
		});
	
		//展开收起
		function treeExpandAll(type) {
			orgTree = $.fn.zTree.getZTreeObj("orgTree");
			orgTree.expandAll(type);
		};
	
		function loadTree(value) {
			var setting = {
				data : {
					key : {name : "orgName"},
					simpleData : {enable : true,idKey : "orgId",pIdKey : "orgSupId",rootPId : 0}
				},
				callback : {
					onClick : treeClick
				}
			};
			$.post("${ctx}/platform/system/sysOrg/getTreeData.xht",{demId : value},
				function(result) {
					orgTree = $.fn.zTree.init($("#orgTree"), setting,result);
					orgTree.expandAll(true);
			});
		}
		//选择分类
		function getSelectNode() {
			orgTree = $.fn.zTree.getZTreeObj("orgTree");
			var nodes = orgTree.getSelectedNodes();
			var node = nodes[0];
			if (node == null || node.orgId == 0)
				return '';
			return node.orgId;
		}
	
		function treeClick(event, treeId, treeNode) {
			//取得组织id
			var orgId = getSelectNode();
			var demId = $("#demensionId").val();
			var url = "${ctx}/platform/system/sysOrg/selector.xht?orgId=" + orgId + "&demId=" + demId;
			$("#orgFrame").attr("src", url);
			setOrgId(orgId,demId);
		}
		
		function setOrgId(orgId,demId){
			$("#orgId").val(orgId);
			$("#demId").val(demId);
		}
	
		
		function selectOrg(){
			var aryOrgIds=new Array();
			var aryOrgNames=new Array();
			$('#orgFrame').contents().find("input[name='orgId']:checked").each(function(){
				aryOrgNames.push($(this).siblings("input[name='orgName']").val());
				aryOrgIds.push($(this).val());
			});
			var orgIds=aryOrgIds.join(",");
			var orgNames=aryOrgNames.join(",");
			if(orgIds==""){
				$.ligerMessageBox.warn('提示信息',"请选择组织ID!");
				return "";
			}
			window.returnValue={orgId:orgIds,orgName:orgNames};
			window.close();
		}
		
	</script>
	<style type="text/css">
		html { overflow-x: hidden; }
	</style>
</head>
<body>
	<div id="defLayout">
		<div position="left" title="组织树" style="overflow:auto;">
			 <div style="width:100%;">
		        <select id="demensionId"  style="width:99.8% !important;">  
		              <option value="0" > ---------全部--------- </option>
		              <c:forEach var="dem" items="${demensionList}">  
		              	<option  value="${dem.demId}">${dem.demName}</option>  
		              </c:forEach>  
		        </select>
	         </div>

			<div class="tree-toolbar">
				<span class="toolBar">
					<div class="group">
						<a class="link reload" id="treeFresh" href="javascript:loadTree();">刷新</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link expand" id="treeExpandAll"
							href="javascript:treeExpandAll(true)">展开</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)">收起</a>
					</div>
				</span>
			</div>
			<ul id="orgTree" class="ztree"></ul>
		</div>
		<div position="center">
			<div class="l-layout-header">组织列表</div>
			<div class="panel-search">
				<form action="selector.xht" id="orgSearchForm" method="POST" target="orgFrame">
					<div class="row">
						<span class="label">组织名:</span> 
						<input type="hidden" name="orgId" id="orgId" /> 
						<input type="hidden" name="demId" id="demId" /> 
						<input type="text" id="orgName" name="orgName" 
						class="inputText" size="40" /> &nbsp; 
						<a href="#" onclick="$('#orgSearchForm').submit()" class='button'><span>查询</span></a>
						
					</div>
				</form>
			</div>

			<iframe id="orgFrame" name="orgFrame" height="90%" width="100%"
				frameborder="0" src="${ctx}/platform/system/sysOrg/selector.xht"></iframe>
		</div>
	</div>
	 <div position="bottom"  class="bottom" style='margin-top:10px;'>
		<a href='#' class='button' onclick="selectOrg()" ><span class="icon ok"></span><span >选择</span></a>
		<a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
	</div>
</body>
</html>