<%--
	desc:edit the 会签任务投票规则
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>会签任务投票规则设置</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript">
$(function(){
	$("a.save").click(save);
});

function save(){
	var url=__ctx+ "/platform/bpm/bpmNodeSign/save.xht";
	var para=$("#bpmNodeSignForm").serialize();
	$.post(url,para,function(data){
			 $.ligerMessageBox.success('操作成功','你成功进行了会签属性设置!',function(rtn){
				 if(rtn)window.close();
			 });
	});
}

</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">会签任务投票规则设置</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link save" id="btnSearch">保存</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del" onclick="javasrcipt:window.close()">关闭</a></div>
						
						</div>	
					</div>
				</div>
		<div class="panel-body">
				<form id="bpmNodeSignForm" method="post" action="save.xht">
					<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">决策方式:  <span class="required">*</span></th>
							<td>
								<c:choose>
									<c:when test="${bpmNodeSign.signId==0}">
										<input type="radio" value="1" name="decideType" checked="checked" />通过
										<input type="radio" value="2" name="decideType"  />拒绝
									</c:when>
									<c:otherwise>
										<input type="radio" value="1" name="decideType" <c:if test="${bpmNodeSign.decideType==1}">checked="checked"</c:if>  />通过
										<input type="radio" value="2" name="decideType" <c:if test="${bpmNodeSign.decideType==2}">checked="checked"</c:if> />拒绝
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th width="20%">投票类型: </th>
							<td>
								<c:choose>
									<c:when test="${bpmNodeSign.signId==0}">
										<input type="radio" value="2" name="voteType" checked="checked" />绝对票数
										<input type="radio" value="1" name="voteType"  />百分比
										
									</c:when>
									<c:otherwise>
										<input type="radio" value="2" name="voteType" <c:if test="${bpmNodeSign.voteType==2}">checked="checked"</c:if> />绝对票数
										<input type="radio" value="1" name="voteType" <c:if test="${bpmNodeSign.voteType==1}">checked="checked"</c:if>  />百分比
		
									</c:otherwise>
								</c:choose>
								
							</td>
						</tr>
						<tr>
							<th width="20%">票数: </th>
							<td><input type="text" id="voteAmount" name="voteAmount" value="${bpmNodeSign.voteAmount}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%" nowrap="nowrap">是否允许补签</th>
							<td>
								<input type="checkbox" id="isAllowAdd" name="isAllowAdd" value="1" <c:if test="${bpmNodeSign.isAllowAdd==1}"> checked="checked" </c:if> />
							</td>
						</tr>
					</table>
					<input type="hidden" name="signId" value="${bpmNodeSign.signId}" />
					<input type="hidden" id="nodeId" name="nodeId" value="${bpmNodeSign.nodeId}"/>
					<input type="hidden" id="actDefId" name="actDefId" value="${bpmNodeSign.actDefId}" />
				</form>
		</div>
</div>
</body>
</html>
