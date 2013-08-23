
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择表单</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	$(function() {
		$('#bpmFormDefItem tr').click(function() {
			$(this).find(':radio').attr('checked', 'checked');
		});
	});
</script>
<style type="text/css">
	
</style>
</head>
<body>
	<div class="panel">
		<div class="panel-body">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="${ctx}/platform/form/bpmFormDef/selector.xht">
					<div class="row">
							<span class="label">表单标题:</span><input type="text" name="Q_subject_S"  class="inputText" />
					</div>
				</form>
			</div>
		
			<div class="panel-data">
		    	
			    <display:table name="bpmFormDefList" id="bpmFormDefItem" requestURI="list.xht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
					<display:column  media="html" style="width:30px;">
						  	<input type="radio" class="pk" name="formKey" value="${bpmFormDefItem.formKey}">
						  	<input type="hidden" name="subject" value="${bpmFormDefItem.subject}">
					</display:column>
					<display:column property="subject" title="表单标题" sortable="true" sortName="subject" style="text-align:left"></display:column>
					<display:column property="formDesc" title="描述" sortable="true" sortName="formDesc" style="text-align:left"></display:column>
				</display:table>
				<sz:paging tableId="bpmFormDefItem" showExplain="false"/>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


