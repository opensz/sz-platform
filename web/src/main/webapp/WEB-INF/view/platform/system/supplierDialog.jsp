<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<html>
<head>
<title>选择 </title>
	<%@include file="/commons/include/form.jsp" %>
	<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js"></script>
	
	
	<script type="text/javascript">
		$(function() {
			$("#defLayout").ligerLayout({
				height : '90%'
			});
			
			
			
		});

		
		function selectOrg(){
			var aryOrgIds=new Array();
			var aryOrgNames=new Array();
			
			
			$('#orgFrame').contents().find("input[name='supplierId']:checked").each(function(){
				aryOrgNames.push($(this).siblings("input[name='supplierName']").val());
				aryOrgIds.push($(this).val());
			});
			
			var orgIds=aryOrgIds.join(",");
			var orgNames=aryOrgNames.join(",");
			//if(orgType)
			if(orgIds==""){
				$.ligerMessageBox.warn('提示信息',"请选择一条数据");
				return "";
			}
			
			window.returnValue={ids:orgIds,names:orgNames};
			window.close();
		}
	</script>

<style type="text/css">
.tree-title {
	overflow: hidden;
	width: 8000px;
}

.ztree {
	overflow: auto;
}

.label {
	color: #6F8DC6;
	text-align: right;
	padding-right: 6px;
	padding-left: 0px;
	font-weight: bold;
}
html { overflow-x: hidden; }


</style>
</head>
<body>
	<div id="defLayout">
		<div position="center">
			<div id="centerTitle" class="l-layout-header">供应商选择</div>
			
			<div class="panel-search">
				<form action="supplierSelector.xht" id="orgSearchForm" method="POST" target="orgFrame">
					<div class="row">
						<span class="label">名称:</span> 

						<input type="text" id="orgName" name="orgName" value="${orgName}"
						class="inputText" size="40" /> &nbsp; 
						<a href="#" onclick="$('#orgSearchForm').submit()" class='button'><span>查询</span></a>
						
					</div>
				</form>
			</div>
			
				<iframe id="orgFrame" name="orgFrame" height="90%" width="100%"
				frameborder="0" src="${ctx}/platform/system/sysOrg/supplierSelector.xht?type=8"></iframe>
			
		</div>
		<div position="bottom"  class="bottom" style='margin-top:10px'>
			<a href='#' class='button'  onclick="selectOrg();" ><span class="icon ok"></span><span >选择</span></a>
			<a href='#' class='button' style='margin-left:10px;'  onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
	</div>
	</div>
</body>
</html>


