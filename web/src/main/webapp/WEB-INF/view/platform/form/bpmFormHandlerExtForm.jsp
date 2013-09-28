<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑表单</title>
<%@include file="/commons/include/customFormExt.jsp" %>
<%@include file="/commons/include/params.jsp"%>
<script type="text/javascript">
 	 Ext.onReady(function() {
 		var caseDealCtrl = getControl('ITSM.controller.case.CaseDealCtrl2');
		var caseDeal = Ext.create('ITSM.view.case.caseForm.CaseDeal2', {
			tid:1,
			caseId : '${processRun.businessKey}',
			caseType : 'incident',
			processInstanceId : 1,
			isInternalCustomer : false,
			allowsEdit : false,
			isView:true,
			flowId : 1,
			taskId : 1,
			taskName : 1,
			sysStatus : 1,
			defId : '${processRun.defId}'
		});
 		
		caseDeal.render('custform');
 	 });
</script>
</head>
<body>
	<div class="panel-body">
		<div id="custform" type="custform" style="width: 100%">
		</div>
	</div>
</body>
</html>

