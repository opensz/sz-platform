<%@ page pageEncoding="UTF-8"%>
<html>
<style type="text/css">
.table-detail {
	margin:0px;
	width:100%;
	margin-top: 6px;
	background-color: #DCF5FA;
}
.table-detail th{
	background-color:transparent;
	font-size: 12px;
	font-weight: 400;	
}
.table-detail th,.table-detail td{
	border-color:transparent;
	text-align: left;
	padding-left:5px;
}

div.table-bk {
	border:1px solid #99BCE8;
	background-color: #DCF5FA;
	margin:5px 0px 5px 0px;
}
.table-detail textarea{
	resize:none;
}
</style>

  <script type="text/javascript">
  
  $(function(){
	  var closeCode = "${standardDealData.closeCode}";
	  if(closeCode){
		  $("#closeCode").val(closeCode);
	  }
  });
  function chooseDealType(val){
	  var isFlag=false;
	  var height =0;
	  if(val==1){
		  if($("#isDeal").attr("checked")=="checked"){
			  isFlag = true;
		  }
		  height = 160;
		  $(".dealDiv").toggle();
	  }else if(val==2){
		  if($("#isLate").attr("checked")=="checked"){
			  isFlag = true;
		  }
		  height = 40;
		  $(".lateDiv").toggle();
	  }else if(val==3){
		  if($("#isReturnVisit").attr("checked")=="checked"){
			  isFlag = true;
		  }
		  height = 135;
		  $(".returnVisitDiv").toggle();
	  }else if(val==4){
		  if($("#isClose").attr("checked")=="checked"){
			  isFlag = true;
		  }
		  height = 160;
		  $(".closeDiv").toggle();
	  }else{
		  if($("#isOnSiteService").attr("checked")=="checked"){
			  isFlag = true;
		  }
		  height = 210;
		  $(".onSiteServiceDiv").toggle();
	  }
	  setHeight(isFlag,height);
  }
  
	function addClick()//在sysOrgEdit.jsp调用，为了弹出页面的拖动范围大些，所以写在父页面了
	{
		UserDialog({callback:function(userIds,fullnames){
			$("#supporterId").val(userIds);
			$("#supporterName").val(fullnames);	
		},isSingle:true});
	};
	
	
	//清空
	function reSet(){
		$("#supporterId").val("");
		$("#supporterName").val("");	
	}
  
  </script>
  
  <body>
  <div class="panel-body table-bk">
  <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
   		<tr>
			<th width="1%">原因分析:</th>
			<td width="9%">
				<textarea rows="2" cols="78" name="ttAnalysis">${standardDealData.ttAnalysis}</textarea>
			</td>
		</tr>
		<tr>
			<th>处理:</th>
			<td>
				<input type="checkbox" name="isDeal" id="isDeal" value="true" onclick="chooseDealType(1)" <c:if test="${standardDealData.isDeal}">checked="checked"</c:if> />
			</td>
		</tr>
		
		<tr class="dealDiv" style="display: none;">
			<th>&nbsp;</th>
			<td>
				<div class="table-bk" style="margin-right:20px;width:75%;">
					<table class="table-detail" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr <c:if test="${!standardDealData.isDeal}"></c:if> >
								<th>应急方案:</th>
								<td>
									<textarea rows="2" cols="78" name="emergencyPlan">${standardDealData.emergencyPlan}</textarea>
								</td>
							</tr>
							<tr <c:if test="${!standardDealData.isDeal}"></c:if> >
								<th>解决方式:</th>
								<td>
									<input type="text" name="solveMode" class="inputText" value="${standardDealData.solveMode}" />
								</td>
							</tr>
							<tr <c:if test="${!standardDealData.isDeal}"></c:if> >
								<th>解决方案:</th>
								<td>
									<textarea rows="2" cols="78" name="ttWorkRound">${standardDealData.ttWorkRound}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>  
			</td>
		</tr>
		
		
		
		<tr>
			<th>补单:</th>
			<td>
				<input type="checkbox" name="isLate" id="isLate" value="true" onclick="chooseDealType(2)" <c:if test="${standardDealData.isLate}">checked="checked"</c:if> />
			</td>
		</tr>
		
		<tr class="lateDiv" style="display: none;">
			<th>&nbsp;</th>
			<td>
				<div class="table-bk" style="margin-right:20px;width:75%;">
					<table class="table-detail" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr <c:if test="${!standardDealData.isLate}"></c:if> >
								<th width="10%">请求时间:</th>
								<td>
									<input type="text" name="requestTime" class="inputText datetime" value="${standardDealData.requestTime}" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>  
			</td>
		</tr>
		
		
		
		
		
		
		
		
		<tr>
			<th>回访:</th>
			<td>
				<input type="checkbox" name="isReturnVisit" id="isReturnVisit" value="true" onclick="chooseDealType(3)" <c:if test="${standardDealData.isReturnVisit}">checked="checked"</c:if> />
			</td>
		</tr>
		
		
		<tr class="returnVisitDiv" style="display: none;">
			<th>&nbsp;</th>
			<td>
				<div class="table-bk" style="margin-right:20px;width:75%;">
					<table class="table-detail" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr <c:if test="${!standardDealData.isReturnVisit}"></c:if> >
								<th>问题已解决:</th>
								<td>
									<input type="checkbox" name="isSolve" value="true" <c:if test="${standardDealData.isSolve}">checked="checked"</c:if> />
								</td>
							</tr>
							<tr  <c:if test="${!standardDealData.isReturnVisit}"></c:if> >
								<th>满意度:</th>
								<td>
									<c:choose>  
									   <c:when test="${standardDealData.satisfaction!=null}">
							   				<input type="radio" name="satisfaction" value="1" <c:if test="${standardDealData.satisfaction==1}">checked="checked"</c:if> />非常满意
											<input type="radio" name="satisfaction" value="2" <c:if test="${standardDealData.satisfaction==2}">checked="checked"</c:if> />满意
											<input type="radio" name="satisfaction" value="3" <c:if test="${standardDealData.satisfaction==3}">checked="checked"</c:if> />不满意
									   </c:when>  
									   <c:otherwise>
					   		   				<input type="radio" name="satisfaction" value="1" checked="checked" />非常满意 
											<input type="radio" name="satisfaction" value="2" />满意
											<input type="radio" name="satisfaction" value="3" />不满意
									   </c:otherwise>  
									</c:choose>  
									
								</td>
							</tr>
							<tr  <c:if test="${!standardDealData.isReturnVisit}"></c:if> >
								<th>客户反馈:</th>
								<td>
									<textarea rows="2" cols="78" name="customerOpinion">${standardDealData.customerOpinion}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>  
			</td>
		</tr>
		
		
		
		<tr>
			<th>关闭:</th>
			<td>
				<input type="checkbox" name="isClose" id="isClose" value="true" onclick="chooseDealType(4)" <c:if test="${standardDealData.isClose}">checked="checked"</c:if> />
			</td>
		</tr>
		
		
		
		
		<tr class="closeDiv" style="display: none;">
			<th>&nbsp;</th>
			<td>
				<div class="table-bk" style="margin-right:20px;width:75%;">
					<table class="table-detail" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr  <c:if test="${!standardDealData.isClose}"></c:if> >
								<th>中断服务:</th>
								<td>
									<input type="checkbox" name="isStopService" value="true" <c:if test="${standardDealData.isStopService}">checked="checked"</c:if> />
								</td>
							</tr>
							<tr  <c:if test="${!standardDealData.isClose}"></c:if> >
								<th>中断时长:</th>
								<td>
									<input type="text" name="stopTime" class="inputText date" value="${standardDealData.stopTime}" />
								</td>
							</tr>
							<tr  <c:if test="${!standardDealData.isClose}"></c:if> >
								<th>关闭代码:</th>
								<td>
									<!--  <input type="text" name="closeCode" class="inputText" value="${standardDealData.closeCode}" />-->
									
									<select id="closeCode" name="closeCode">
									<option value="">--请选择--</option>
									<c:forEach var="item" items="${standardDealData.codes}">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr  <c:if test="${!standardDealData.isClose}"></c:if> >
								<th>处理结果:</th>
								<td>
									<textarea rows="2" cols="78" name="result">${standardDealData.result}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>  
			</td>
		</tr>
		
		
		
		
		
		
		
		
		
		<tr>
			<th>上门服务:</th>
			<td>
				<input type="checkbox" name="isOnSiteService" id="isOnSiteService" value="true" onclick="chooseDealType(5)" <c:if test="${standardDealData.isOnSiteService}">checked="checked"</c:if> />
			</td>
		</tr>
		
		
		
		
		<tr class="onSiteServiceDiv" style="display: none;">
			<th>&nbsp;</th>
			<td>
				<div class="table-bk" style="margin-right:20px;width:75%;">
					<table class="table-detail" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr  <c:if test="${!standardDealData.isOnSiteService}"></c:if> >
								<th>到达时间:</th>
								<td>
									<input type="text" name="arriveTime" class="inputText datetime" value="${standardDealData.arriveTime}" />
								</td>
							</tr>
							<tr  <c:if test="${!standardDealData.isOnSiteService}">"</c:if> >
								<th>离开时间:</th>
								<td>
									<input type="text" name="leaveTime" class="inputText datetime" value="${standardDealData.leaveTime}" />
								</td>
							</tr>
							<tr  <c:if test="${!standardDealData.isOnSiteService}"></c:if> >
								<th>服务工程师:</th>
								<td>
					                <input type="text" class="inputText" readonly="readonly" name="supporterName" id="supporterName" value="${standardDealData.supporterName}" />
								    <a href="javascript:void(0)" onclick="addClick()" class="link get">选择</a>
								    <a href="javascript:void(0)" onclick="reSet()" class="link clean">清空</a>
								    <input type="hidden" name="supporterId" id="supporterId" value="${standardDealData.supporterId}" />
								</td>
							</tr>
							<tr  <c:if test="${!standardDealData.isOnSiteService}"></c:if> >
								<th>处理内容:</th>
								<td>
									<textarea rows="2" cols="78" name="onSiteService_result">${standardDealData.onSiteServiceResult}</textarea>
								</td>
							</tr>
							<tr  <c:if test="${!standardDealData.isOnSiteService}"></c:if> >
								<th>客户反馈:</th>
								<td>
									<textarea rows="2" cols="78" name="feedback">${standardDealData.feedback}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>  
			</td>
		</tr>
		
		
		
		
		
		
		
		
   </table>
   </div>
  </body>
</html>
