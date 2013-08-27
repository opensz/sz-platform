<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
// 选择常用语
		function addComment(){
			var objContent=document.getElementById("voteContent");
			var selItem = $('#selTaskAppItem').val();
			jQuery.insertText(objContent,selItem);
		}
</script>		
	<c:if test="${isSignTask!=true}">
		<div class="noprint">
			<table class="table-detail">
										<tr>
											<th>审批意见</th>
											<td>
												<input type="radio" name="voteAgree" value="1" checked="checked">&nbsp;同意&nbsp;
												<input type="radio" name="voteAgree" value="2">&nbsp;反对&nbsp;
												<c:if test="${isAllowBack==true}">
												<input type="radio" name="voteAgree" value="3">&nbsp;驳回&nbsp;
												</c:if>
											</td>
										</tr>
										<c:if test="${!empty taskAppItems}">
										<tr>
											<th>常用语选择: </th>
											<td>
												<select style="width: 25%;text-align: center;" id="selTaskAppItem" onchange="addComment()" >
													<option value="" style="text-align:center;">-- 请选择 --</option>
													<c:forEach var="taskAppItem" items="${taskAppItems}">
														<option value="${taskAppItem}">${taskAppItem}</option>
													</c:forEach>
												</select>
											</td>
										</tr>
										</c:if>
										<tr>
											<th>意见</th>
											<td>
												<textarea rows="2" cols="78" id="voteContent" name="voteContent" maxlength="512"></textarea>
											</td>
										</tr>
			</table>						
		</div>						
	</c:if>
