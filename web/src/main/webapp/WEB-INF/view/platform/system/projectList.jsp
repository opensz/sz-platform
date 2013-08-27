<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>项目管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<%@ taglib prefix="sz" uri="http://www.servicezon.com/paging" %>

	<script type="text/javascript" src="${ctx}/js/sz/platform/system/CopyRoleDialog.js"></script>
    <script type="text/javascript">
	    function copyRole(roleId,roleName){
	    	CopyRoleDialog({roleId:roleId});
	    }
	    
		function editRoleRes(roleId){
	        var url=__ctx+"/platform/system/roleResources/edit.xht?roleId="+roleId;
	    	var winArgs="dialogWidth=350px;dialogHeight=460px;status=0;help=0;";
	    	url=url.getNewUrl();
	    	window.showModalDialog(url,"",winArgs);
	    }
    </script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">项目列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><f:a alias="searchRole" css="link search" id="btnSearch" >查询</f:a></div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<f:a alias="addRole" css="link add" href="edit.xht">添加</f:a>
							</div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<f:a alias="delRole" css="link del" action="del.xht">删除</f:a>
							</div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
							<form id="searchForm" method="post" action="list.xht">
									<div class="row">
										<span class="label">名称:</span><input type="text" name="Q_name_S" value="${params['name']}" class="inputText" />
									</div>
							</form>
					</div>
				
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="projectList" id="model" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
							<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
							<input type="checkbox" class="pk" name="ids" id="ids" value="${model.id}">
							</display:column>
							
							
							<display:column property="code" title="代码" style="text-align:left" sortable="true"></display:column>
 			               
							<display:column property="name" title="名称" style="text-align:left" sortable="true"></display:column>
							
							<display:column property="managerName" title="负责人" style="text-align:left" sortable="true"></display:column>
							
							<display:column title="管理" media="html" style="width:150px">
								
										
										<f:a alias="delRole" css="link del" href="del.xht?ids=${model.id}">删除</f:a>
									
										<a  class="link edit" href="edit.xht?id=${model.id}">编辑</a>
								
							</display:column>
							
						</display:table>
						<sz:paging tableId="project"/>
					</div>
				</div> 			
			</div> 
</body>
</html>


