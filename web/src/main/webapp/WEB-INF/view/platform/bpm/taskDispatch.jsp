<%@ page pageEncoding="UTF-8"%>
<html>
  <body>
	<c:if test="${isSignTask!=true}">
		<div class="noprint">
			<table class="table-detail">
				<c:if test="${bpmNodeSet.isAudit==1}">
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
				</c:if>
				<c:if test="${!empty taskAppItems}">
					<tr>
						<th>常用语选择:</th>
						<td>
							<select style="width: 25%; text-align: center;" id="selTaskAppItem" onchange="addComment()">
								<option value="" style="text-align: center;">-- 请选择 --</option>
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
				
				<tr>
					<th>跳转方式</th>
					<td>
						<c:forEach items="${fn:split(bpmNodeSet.jumpType,',')}" var="item" varStatus="i">
							<c:if test="${item==1}">
								<span style="padding-top: 4px"><input type="radio" name="jumpType" <c:if test="${i.index==0}">checked="checked"</c:if> onclick="chooseJumpType(1)" />&nbsp;正常跳转</span>
							</c:if>
							<c:if test="${item==2}">
								<span style="padding-top: 4px"><input type="radio" name="jumpType" <c:if test="${i.index==0}">checked="checked"</c:if> onclick="chooseJumpType(2)" />&nbsp;选择路径跳转</span>
							</c:if>
							<c:if test="${item==3}">
								<span style="padding-top: 4px"><input type="radio" name="jumpType" <c:if test="${i.index==0}">checked="checked"</c:if> onclick="chooseJumpType(3)" />&nbsp;回退</span>
							</c:if>
							<c:if test="${item==4}">
								<span style="padding-top: 4px"><input type="radio" name="jumpType" <c:if test="${i.index==0}">checked="checked"</c:if> onclick="chooseJumpType(4)" />&nbsp;自由跳转</span>
							</c:if>
						</c:forEach>

						<div id="jumpDiv" class="noprint" style="display: none; padding: 2px 2px 2px 2px; text-align:left;" tipInfo="正在加载表单请稍候...">
						</div>
					</td>
				</tr>
			</table>
		</div>
	</c:if>
    
  </body>
</html>
