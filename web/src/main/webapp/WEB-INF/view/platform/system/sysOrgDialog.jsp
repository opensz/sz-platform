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
		var orgType = "${type}";
		var orgId = "${orgId}";
		var demId=1;
		var height2=75;
		var minLeftWidth=220;
		if(orgType=="project"){
			demId="2";
			height2=120;
			
		}
		$(function() {
			$("#defLayout").ligerLayout({
				leftWidth : 220,
				height : '90%',
				onHeightChanged: heightChanged,
				minLeftWidth:220
			});
			
			$('#demensionId').change(function(){
        		var demensionId=$(this).val();
        		loadTree(demensionId);
             });
			if(demId!=2){
				loadTree(demId);
			}
			
			
			$("#demensionId").val(demId);
			
			//取得layout的高度
        		var height = $(".l-layout-center").height();
        		$("#orgTree").height(height-height2);
        		var setting = {
				data : {
					key : {name : "orgName"},
					simpleData : {enable : true,idKey : "orgId",pIdKey : "orgSupId",rootPId : 0}
				},
				callback : {
					onClick : treeClick//,
					//onDblClick:function(event, treeId, treeNode){alert(treeNode.orgId+","+treeNode.orgName);}
				}
			};
	        	$('#keyword').keydown(function(e){
					if(e.keyCode==13){
					   var value=$('#keyword').val();
					   //if(value!=""){
					    $.ajax({
							type : 'POST',
							url : "${ctx}/platform/system/sysOrg/getCustomerTreeData.xht",
							data :{'orgName':value},
							success : function(result) {
								org_Tree = $.fn.zTree.init($("#orgTree"), setting,
										eval(result));
								org_Tree.expandAll(true);
							}
						});
					   //}
					}  
				});
		});
	
	    //布局大小改变的时候通知tab，面板改变大小
	    function heightChanged(options){
	     	var height = options.layoutHeight;
        		$("#orgTree").height(height-height2);
	    };
    
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
					onClick : treeClick//,
					//onDblClick:function(event, treeId, treeNode){alert(treeNode.orgId+","+treeNode.orgName);}
				}
			};
			if(orgType=="org"){
				$.ajax({
				type : 'POST',
				url : "${ctx}/platform/system/sysOrg/getCustomerTreeData.xht",
				
				success : function(result) {
					org_Tree = $.fn.zTree.init($("#orgTree"), setting,
							eval(result));
					org_Tree.expandAll(true);
				}
			});
			}
			else{
				$.post("${ctx}/platform/system/sysOrg/getTreeData.xht",{demId : value,type:orgType,orgId:orgId},
				function(result) {
					orgTree = $.fn.zTree.init($("#orgTree"), setting,result);
					orgTree.expandAll(true);
			});
			}
			
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
			orgId=orgId==null?"":orgId;
			var url = "${ctx}/platform/system/sysOrg/selector.xht?orgSupId=" + orgId + "&demId=" + demId+"&type="+orgType;
			
			$("#orgFrame").attr("src", url);
			setOrgId(orgId,demId);
		}
		
		function treeDbClick(event, treeId, treeNode){
			$("#orgId").val(orgId);
			$("#demId").val(demId);
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
			//if(orgType)
			if(orgIds==""){
				$.ligerMessageBox.warn('提示信息',"请选择一条数据");
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
	<c:set var="demId" value="1" />
	<c:set var="title" value="客户树" />
	<c:set var="titleList" value="客户列表" />
	<c:if test="${type=='org'}">
		<c:set var="title" value="客户树" />
		<c:set var="titleList" value="客户列表" />
		<c:set var="demId" value="1" />
	</c:if>
	<c:if test="${type=='dept'}">
		<c:set var="title" value="部门树" />
		<c:set var="titleList" value="部门列表" />
		<c:set var="demId" value="1" />
	</c:if>
	<div id="defLayout">
		<div position="left" title="${title}" style="overflow:auto;">
			<c:if test="type==project">
			 <div style="width:100%;">
		        <select id="demensionId"  style="width:99.8% !important;">  
		              <option value="0" > ---------全部--------- </option>
		              <c:forEach var="dem" items="${demensionList}">  
		              	<option  value="${dem.demId}">${dem.demName}</option>  
		              </c:forEach>  
		        </select>
	         </div>
			</c:if>
			<div class="tree-toolbar">
				<span class="toolBar">
					<div class="group">
						<a class="link reload" id="treeFresh" href="javascript:loadTree(1);">刷新</a>
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
			<div class="l-layout-header">${titleList}</div>
			<div class="panel-search">
				<form action="selector.xht" id="orgSearchForm" method="POST" target="orgFrame">
					<div class="row">
						<span class="label">名称:</span> 
 						<input type="hidden" name="orgId" id="orgId" value="${orgId}"/>
						<input type="hidden" name="orgSupId" value="${orgId}"/> 
						<input type="hidden" name="demId" id="demId" value="${demId}"/> 
						<input type="hidden" name="type" value="${type}" /> 
						<input type="text" id="orgName" name="orgName" value="${orgName}"
						class="inputText" size="40" /> &nbsp; 
						<a href="#" onclick="$('#orgSearchForm').submit()" class='button'><span>查询</span></a>
						
					</div>
				</form>
			</div>
			
			 <iframe id="orgFrame" name="orgFrame" height="90%" width="100%"
				frameborder="0" src="${ctx}/platform/system/sysOrg/selector.xht?type=${type}&demId=${demId}&orgSupId=${orgId}"></iframe>
			
		</div>
	</div>
	 <div position="bottom"  class="bottom" style='margin-top:10px;'>
		<a href='#' class='button' onclick="selectOrg()" ><span class="icon ok"></span><span >选择</span></a>
		<a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
	</div>
</body>
</html>