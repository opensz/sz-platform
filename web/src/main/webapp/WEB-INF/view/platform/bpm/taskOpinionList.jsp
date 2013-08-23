<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %>
<html>
<head>
	<title>${processRun.subject}--审批历史</title>
	<%@include file="/commons/include/get.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label"> 审批明细</span>
			</div>
		</div>
		<div class="panel-body">
	      <div style="padding:4px 4px 4px 4px;">
		   	 <div class="panel-data">
			  		 <display:table name="taskOpinionList" id="taskOpinionItem" requestURI="list.xht" sort="external" cellpadding="0" cellspacing="0" class="table-grid">
						<display:column title="序号" style="width:30px;">
						  ${(fn:length(taskOpinionList) + 1) - taskOpinionItem_rowNum}
						</display:column>
						<display:column property="taskName" title="任务名称"></display:column>
						<display:column  title="执行开始时间">
							<fmt:formatDate value="${taskOpinionItem.startTime}" pattern="yyyy-MM-dd HH:mm"/>
						</display:column>
						<display:column  title="结束时间">
							<fmt:formatDate value="${taskOpinionItem.endTime}" pattern="yyyy-MM-dd HH:mm"/>
						</display:column>
						<display:column title="持续时间">
						  ${f:getTime(taskOpinionItem.durTime)}
						</display:column>
						<display:column property="exeFullname" title="执行人名"></display:column>
						<display:column property="opinion" title="审批意见" ></display:column>
						<display:column title="审批状态">
						<c:choose>
							<c:when test="${taskOpinionItem.checkStatus==1}"><font color="green">同意</font></c:when>
							<c:when test="${taskOpinionItem.checkStatus==2}"><font color="blue">反对</font></c:when>
							<c:when test="${taskOpinionItem.checkStatus==3}"><font color="red">驳回</font></c:when>
							<c:when test="${taskOpinionItem.checkStatus==0}">弃权跳过</c:when>
							<c:when test="${taskOpinionItem.checkStatus==4}">被追回</c:when>
							<c:when test="${taskOpinionItem.checkStatus==5}">回退</c:when>
							<c:otherwise>尚未处理</c:otherwise>
						</c:choose>
					</display:column>
				</display:table>
			</div>
		</div>
	</div>
  </div>
</body>
</html>
