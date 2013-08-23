<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>

	<%@include file="/commons/include/form.jsp" %>
	<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerLayout.js"></script>
	
	<script type="text/javascript">
	
		var orgTree = null;
		
		$(function() {
			$("#defLayout").ligerLayout({
				height : '90%'
			});
			
			
			
		});

		
		function selectOrg(){
			var aryOrgIds=new Array();
			var aryOrgNames=new Array();
			
			
			$('#orgFrame').contents().find("input[name='id']:checked").each(function(){
				aryOrgNames.push($(this).siblings("input[name='name']").val());
				aryOrgIds.push($(this).val());
			});
			
			var orgIds=aryOrgIds.join(",");
			var orgNames=aryOrgNames.join(",");
			//if(orgType)
			if(orgIds==""){
				$.ligerMessageBox.warn('提示信息',"请选择一条数据");
				return "";
			}
			
			window.returnValue={id:orgIds,name:orgNames};
			window.close();
		}
		
	</script>
	<style type="text/css">
		html { overflow-x: hidden; }
	</style>
</head>
<body>
	<div id="defLayout">
		<div position="center">
			<div class="l-layout-header">工单选择器</div>
			<div class="panel-search">
				<form action="chooseCaseNoSelector.xht?tableId=${tableId}" id="orgSearchForm" method="POST" target="orgFrame">
					<div class="row">
						<span class="label">单号:</span> 
						
						<input type="hidden" name="tableId" id="${tableId}" />
						<input type="text" id="orgName" name="caseNo" value="${caseNo}"
						class="inputText" size="40" /> &nbsp; 
						<a href="#" onclick="$('#orgSearchForm').submit()" class='button'><span>查询</span></a>
						
					</div>
				</form>
			</div>
			<iframe id="orgFrame" name="orgFrame" height="90%" width="100%"
				frameborder="0" src="${ctx}/platform/bpm/task/chooseCaseNoSelector.xht?tableId=${tableId}"></iframe>
			
			
		</div>
	</div>
	 <div position="bottom"  class="bottom" style='margin-top:10px;'>
		<a href='#' class='button' onclick="selectOrg()" ><span class="icon ok"></span><span >选择</span></a>
		<a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
	</div>
</body>
</html>