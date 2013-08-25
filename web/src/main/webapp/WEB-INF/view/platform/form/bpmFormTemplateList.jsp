
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>表单模板管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
    <script type="text/javascript" src="${ctx }/js/sz/platform/form/copyTemplateDialog.js"></script> 
    <script type="text/javascript">
    	function copyTemplate(templateId,templateName,alias){
    		CopyTemplateDialog({templateId:templateId,templateName:templateName,alias:alias});
    	}
    </script>
</head>
<body>
			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">表单模板管理列表</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link search" id="btnSearch">查询</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link add" href="edit.xht">添加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link update" id="btnUpd" action="edit.xht">修改</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.xht">删除</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a  class="link init" id="bntInit" action="init.xht">初始化模板</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
					<div class="panel-search">
						<form id="searchForm" method="post" action="list.xht">
							<div class="row">
								<span class="label">模板名:</span><input type="text" name="Q_templateName_S"  class="inputText" />
								<span class="label">模板类型:</span>
								<select name="Q_templateType_S">
									<option value="">全部</option>
									<option value="main">主表模板</option>
									<option value="subTable">子表模板</option>
									<option value="macro">宏模板</option>
									<option value="list">列表模板</option>
									<option value="detail">明细模板</option>
								</select>
							</div>
						</form>
					</div>
					<div class="panel-data">
				    	<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="bpmFormTemplateList" id="bpmFormTemplateItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk"  name="templateId" value="${bpmFormTemplateItem.templateId}"  <c:if test="${bpmFormTemplateItem.canEdit==0}">disabled="disabled"</c:if>  >
							</display:column>
							<display:column property="alias" title="别名" sortable="true" sortName="alias" style="text-align:left"></display:column>
							<display:column property="templateName" title="模板名" sortable="true" sortName="templateName" style="text-align:left"></display:column>
							<display:column title="模板类型" style="text-align:left">
								<c:if test="${bpmFormTemplateItem.templateType=='list'}">列表模板</c:if>
								<c:if test="${bpmFormTemplateItem.templateType=='detail'}">明细模板</c:if>
								<c:if test="${bpmFormTemplateItem.templateType=='subTable'}">子表模板</c:if>
								<c:if test="${bpmFormTemplateItem.templateType=='main'}">主表模板</c:if>
								<c:if test="${bpmFormTemplateItem.templateType=='macro'}">宏模板</c:if>
								
							</display:column>
							<display:column title="状态" style="text-align:left">
								<c:choose>
									<c:when test="${bpmFormTemplateItem.canEdit==0}"><span class="red">已锁定</span></c:when>
									<c:when test="${bpmFormTemplateItem.canEdit==1}"><span class="green">未锁定</span></c:when>
								</c:choose>
							</display:column>
							<display:column title="管理" media="html" style="width:290px">
								<c:choose>
									<c:when test="${bpmFormTemplateItem.canEdit==0}">
										<a  class="link del disabled">删除</a>
										<a  class="link edit disabled">编辑</a>
										<a  class="link backUp disabled" >备份</a>
										
									</c:when>
									<c:otherwise >
										<a  href="del.xht?templateId=${bpmFormTemplateItem.templateId}" class="link del">删除</a>
										<a  href="edit.xht?templateId=${bpmFormTemplateItem.templateId}" class="link edit">编辑</a>
										<a  href="backUp.xht?templateId=${bpmFormTemplateItem.templateId}" class="link backUp" >备份</a>
										
									</c:otherwise>
								</c:choose>
								<a  href="get.xht?templateId=${bpmFormTemplateItem.templateId}" class="link detail">明细</a>
										<a   href="#" onclick="copyTemplate('${bpmFormTemplateItem.templateId}','${bpmFormTemplateItem.templateName}','${bpmFormTemplateItem.alias}')"  class="link copy">复制</a>
							</display:column>
						</display:table>
						<sz:paging tableId="bpmFormTemplateItem"/>
					</div>
				</div><!-- end of panel-body -->				
			</div> <!-- end of panel -->
</body>
</html>


