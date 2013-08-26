<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义表单列表</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript">
	var win;
	function newFormDef(){
		var url=__ctx + '/platform/form/bpmFormDef/gatherInfo.xht?categoryId=${categoryId}';
		win= $.ligerDialog.open({ url: url, height: 350,width:550 ,isResize: false });
		
	}

	function closeWin(){
		if(win){
			win.close();
		}
		
	}
	
	$(function(){
		$("a.link.del").unbind("click");
		delFormDef();
		publish();
		
		handNewVersion();
	});
	
	
	function publish(){
		$("a.link.deploy").click(function(){
			var ele=this;
			$.ligerMessageBox.confirm('提示信息','确认发布吗？',function(rtn) {
				if(rtn){
					location.href=ele.href;
				}
			});
			return false;
		});
	}
	
	function delFormDef(){
		
		$("a.link.del").click(function(){
			if($(this).hasClass('disabled')) return false;
			var ele = this;
			$.ligerMessageBox.confirm('提示信息','确定删除该自定义表单吗？',function(rtn) {
				if(rtn) {
					if(ele.click) {
						$(ele).unbind('click');
						ele.click();
					} else {
						location.href=ele.href;
					}
				}
			});
			return false;
		});
	}
	
	
	
	function handNewVersion(){
		$("a.link.newVersion").click(function()
		{
			if($(this).hasClass('disabled')) return;
			var ele = this;
			$.ligerMessageBox.confirm('提示信息','确认新建版本吗？',function(rtn) {
				if(rtn) {
					if(ele.click) {
						$(ele).unbind('click');
						ele.click();
					} else {
					
						location.href=ele.href;
					}
				}
			});
			return false;
		});
	}
	
	 

</script>
<style type="text/css">
	
</style>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">自定义表单列表</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link add" onclick="newFormDef()"  href="#">添加</a></div>
			</div>	
		</div>
	</div>
	<div class="panel-body">
		<div class="panel-search">
				<form id="searchForm" method="post" action="list.xht">
					<div class="row">
						<span class="label">表单标题:</span><input type="text" name="Q_subject_S"  class="inputText" />		
					</div>
				</form>
		</div>
		<div class="panel-data">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="bpmFormDefList" id="bpmFormDefItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
					  	<input type="checkbox" class="pk" name="formDefId" value="${bpmFormDefItem.formDefId}">
				</display:column>
				<display:column property="subject" title="表单标题" sortable="true" sortName="subject" style="text-align:left"></display:column>
				<display:column property="formDesc" title="描述" sortable="true" sortName="formDesc" style="text-align:left"></display:column>
				<display:column property="categoryName" title="表单类别" sortable="true" sortName="categoryId" style="text-align:left"></display:column>
				<display:column  title="发布状态" sortable="true" sortName="isPublished" style="text-align:left">
					<c:choose>
						<c:when test="${bpmFormDefItem.isPublished==1 }">
							<span class="green">已发布</span>
						</c:when>
						<c:otherwise>
							<span class="red">未发布</span>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column title="版本信息" style="text-align:left">
					<c:choose>
						<c:when test="${publishedCounts[bpmFormDefItem.formDefId] > 0}">
							发布了<a href="versions.xht?formKey=${bpmFormDefItem.formKey}" >${publishedCounts[bpmFormDefItem.formDefId]}个版本</a>
						</c:when>
						<c:otherwise>
							发布了${publishedCounts[bpmFormDefItem.formDefId]}个版本
						</c:otherwise>
					</c:choose>
					<c:if test="${publishedCounts[bpmFormDefItem.formDefId] > 0}">
						,默认<a href="get.xht?formDefId=${bpmFormDefItem.formDefId}" >版本${defaultVersions[bpmFormDefItem.formDefId].versionNo}</a>
					</c:if>
				</display:column>
				<display:column title="管理" media="html" style="width:250px">
					<!--  href="del.xht?formDefId=${bpmFormDefItem.formDefId}" class="link-del"><span class="link-btn">删除</span></a-->
					<c:choose>
						<c:when test="${bpmFormDefItem.isPublished== 1}">
							<a href="newVersion.xht?formDefId=${bpmFormDefItem.formDefId}"  class="link newVersion">新建版本</a>
						</c:when>
						<c:otherwise>
							<a href="#" onclick="javascript:jQuery.openFullWindow('edit.xht?formDefId=${bpmFormDefItem.formDefId}');" class="link edit">编辑</a>
							<a href="publish.xht?formDefId=${bpmFormDefItem.formDefId }" class="link deploy" >发布</a>
						</c:otherwise>
					</c:choose>
					<a class="link del" href="delByFormKey.xht?formKey=${bpmFormDefItem.formKey}">删除</a>
					<a href="get.xht?formDefId=${bpmFormDefItem.formDefId}" class="link detail">明细</a>
					
					<a target="_blank" href="${ctx}/platform/form/bpmFormHandler/edit.xht?formDefId=${bpmFormDefItem.formDefId}" class="link preview">预览</a>
				</display:column>
			</display:table>
			<sz:paging tableId="bpmFormDefItem"/>
		</div>
	</div><!-- end of panel-body -->				
</div> <!-- end of panel -->
</body>
</html>


