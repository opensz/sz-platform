
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>用户列表</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	var isSingle='${isSingle}';
	$(function(){
		$("#sysUserItem").find("tr").bind('click', function() {
			if(isSingle=='true'){
				var rad=$(this).find('input[name=userId]:radio');
				rad.attr("checked","checked");
			}else{
				var ch=$(this).find(":checkbox[name='userId']");
				window.parent.selectMulti(ch);
			}
		});
	});
</script>
</head>
<body style="overflow-x: hidden;overflow-y: auto;">
	<div class="panel-search" style="width: 80%;">
		<form id="searchForm" method="post" action="${ctx}/platform/system/sysUser/selector.xht" >
			<div class="row">
				<input type="hidden" name="isSingle" value="${isSingle }">
				<input type="hidden" name="searchBy" value="${searchBy}">
				<input type="hidden" name="orgId" value="${orgId}">
				<!--<input type="hidden" name="Q_orderField_S" value="first_spell">
				<input type="hidden" name="Q_orderSeq_S" value="asc">
				--><span class="label">姓名:</span><input size="14" type="text" name="Q_fullname_S" value="${params['fullname']}" class="inputText" />
				<span class="label">拼音:</span><input size="14" type="text" name="Q_firstSpell_S" value="${params['firstSpell']}" class="inputText" />
				&nbsp;<input type="submit" value="查询" onclick="window.parent.setCenterTitle('全部用户')"/>
			</div>
		</form>
	</div>
   	<c:if test="${isSingle==false}">
    	<c:set var="checkAll">
			<input onclick="window.parent.selectAll(this);" type="checkbox" />
		</c:set>
	</c:if>
	<display:table  name="sysUserList" id="sysUserItem" requestURI="selector.xht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
		<display:column title="${f:unescapeXml(checkAll)}" media="html" style="width:30px;">
			  	<c:if test="${isSingle==false}">
			  		<input onchange="window.parent.selectMulti(this);" type="checkbox" class="pk" name="userId" value="${sysUserItem.userId}">
			  	</c:if>
			  	<c:if test="${isSingle==true}">
			  		<input type="radio" class="pk" name="userId" value="${sysUserItem.userId}">
			  	</c:if>
			  	<input type="hidden" name="fullname"  value="${sysUserItem.fullname}"/>
			  	<input type="hidden" name="email"  value="${sysUserItem.email}"/>
			  	<input type="hidden" name="mobile"  value="${sysUserItem.mobile}"/>
			  	<input type="hidden" name="retype"  value="${sysUserItem.retype}"/>
		</display:column>
		<display:column  property="fullname" title="姓名" sortable="true" sortName="fullname"></display:column>
	</display:table>
	<sz:paging tableId="sysUserItem" showExplain="false"/>
</body>
</html>


