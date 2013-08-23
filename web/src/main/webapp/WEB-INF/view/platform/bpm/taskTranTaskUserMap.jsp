<%
	//对应TaskController里的tranTaskUserMap方法，返回某个任务节点的所有跳转分支的任务节点及其执行人员列表
%>
<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table-grid">
	<thead>
	<tr>
		<th height="28" style="text-align:left;">下一步执行路径</th>
		<th style="text-align:left;">下一步执行角色或人员</th>
	</tr>
	</thead>
	<c:forEach items="${nodeTranUserList}" var="nodeTranUser">
		<tr>
			<td height="28" width="18%" nowrap="nowrap">
				${nodeTranUser.nodeName}<!-- 跳转的目标节点 -->
			</td>
			<td>
				<c:forEach items="${nodeTranUser.nodeUserMapSet}" var="nodeUserMap" varStatus="i">
					<div>
						<c:if test="${selectPath==1 }">
						<input type="radio" name="destTask" value="${nodeUserMap.nodeId}" <c:if test="${i.count==1}">checked="checked"</c:if> />
						</c:if>
						${nodeUserMap.nodeName}
						<c:if test="${nodeUserMap.nodeAssignMode==0}">
						<input type="hidden" name="lastDestTaskId" value="${nodeUserMap.nodeId}"/>
						<c:forEach items="${nodeUserMap.users}" var="sysUser">
							<span><input type="checkbox" name="${nodeUserMap.nodeId}_userId" checked="checked" value="${sysUser.userId}"/>&nbsp;${sysUser.fullname}</span>
						</c:forEach>
						<!-- <a href="#" class="link get" onclick="selExeUsers('${nodeUserMap.nodeId}',this)">&nbsp;&nbsp;</a> -->
						</c:if>
					</div>
				</c:forEach>
			</td> 
		</tr>
	</c:forEach>
</table>