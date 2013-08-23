<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>附件信息列表</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerWindow.js"></script>
<script type="text/javascript">
	$(function() {
		$("#sysFileItem  tr.even,#sysFileItem tr.odd").bind('click', function() {
			var trObj=$(this);
			var obj=$(":checkbox[name='fileId']",trObj);
			if(obj.length>0){
				window.parent.selectMulti(obj);
			}
			else{
				var obj=$(":radio[name='fileId']",trObj);
				obj.attr("checked",!obj.attr("checked"));
				obj.click(function(event){
					event.stopImmediatePropagation();
				});
			}
		});
	});
</script>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
	<div class="panel-search">
		<form action="selector.xht" method="POST">
			<div class="row">
				<input type="hidden" name="typeId" id="typeId" /> 
				<span class="label">附件名称:</span> 
				<input type="text" id="fileName" name="Q_fileName_S" style="width:130px;" maxlength="128" class="inputText" /> 
				<input type="submit" value="查  询" />
			</div>
		</form>
	</div>
	<c:if test="${isSingle==0}">
		<c:set var="checkAll">
			<input onclick="window.parent.selectAll(this);" type="checkbox" />
		</c:set>
	</c:if>
	<display:table name="sysFileList" id="sysFileItem" requestURI="selector.xht" sort="external" cellpadding="1"
		cellspacing="1" export="false" class="table-grid">
		<display:column title="${checkAll}" media="html" style="width:30px;">
			<c:choose>
				<c:when test="${isSingle==0}">
					<input onchange="window.parent.selectMulti(this);" type="checkbox" class="pk" name="fileId" value="${sysFileItem.fileId}">
				</c:when>
				<c:otherwise>
					<input type="radio" class="pk" name="fileId" value="${sysFileItem.fileId}">
				</c:otherwise>
			</c:choose>
			<input type="hidden" name="fileName" value="${sysFileItem.fileName}" />
			<input type="hidden" name="filePath" value="${sysFileItem.filePath}" />
			<input type="hidden" name="ext" value="${sysFileItem.ext}" />
		</display:column>
		<display:column property="fileName" title="名称" sortable="true" sortName="fileName" />
		<display:column property="ext" title="扩展名" sortable="true" sortName="ext" />
	</display:table>
	<sz:paging tableId="sysFileItem" showExplain="false"/>
</body>
</html>


