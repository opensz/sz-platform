<%--
	time:2011-11-09 11:20:13
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>组织架构明细</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx }/js/sz/platform/system/SysDialog.js"></script>
	<script type="text/javascript">
		
	</script>
	 <style type="text/css"> 
		html{scroll:no;height:100%}
	    body {scroll:no;height:100%; padding:0px; margin:0;overflow:auto !important}
	    }
    </style>
</head>
<body>
 <div class="panel" id="toppanel">	
		<f:tab curTab="1" tabName="sysOrg"/>	
		<c:if test="${flag== 1}">
		    <div class="panel-toolbar" id="pToolbar">
						<div class="toolBar">
						<div class="group"><a class="link back"  href="listById.xht?orgId=${sysOrg.orgSupId}&path=${path}" >返回</a></div>	
						</div>	
		    </div>
	    </c:if>
		<div class="panel-body" id="pbody">	
			<c:choose>
				<c:when test="${empty sysOrg}">
					<div style="text-align: center;margin-top: 10%;">尚未指定具体组织!</div>
				</c:when>
				<c:otherwise>
					
					<table id="tableid" class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">所属维度:</th>
							<td width="80%" colspan="3">${sysOrg.demName}</td>
							
						</tr>
						<tr>
							<th width="20%">组织名称:</th>
							<td width="30%">${sysOrg.orgName}</td>
							<th width="20%">建立人:</th>
							<td width="30%">${sysOrg.createName}</td>
						</tr>
						<tr>
							<th>上级组织:</th>
							<td>${sysOrg.orgSupName}</td>
							<th>修改人:</th>
							<td>${sysOrg.updateName}</td>
						</tr>
						
						<tr>
							<th>组织类型:</th>
							<td>
							 <c:choose>
								<c:when test="${sysOrg.orgType==1}">
														集团
								</c:when>
								<c:when test="${sysOrg.orgType==2}">
														公司
								</c:when>
								<c:when test="${sysOrg.orgType==3}">
														部门
								</c:when>
								<c:when test="${sysOrg.orgType==4}">
														 小组
								</c:when>
								<c:when test="${sysOrg.orgType==5}">
														 其它组织
								</c:when>
								<c:when test="${sysOrg.orgType==6}">
														 客户
								</c:when>
								<c:otherwise>
												             
								</c:otherwise>
								</c:choose>
							</td>
							<th>建立时间:</th>
							<td>${f:shortDate(sysOrg.createtime)}</td>
						</tr>
						<tr>
							<th>组织负责人:</th>
							<td>${userNameCharge}</td>
							<th>修改时间:</th>
							<td>${f:shortDate(sysOrg.updatetime)}</td>
						</tr>
						<tr>
						    <th>组织描述:</th>
						    <td colspan="3">${sysOrg.orgDesc}</td>
						</tr>
						<tr <c:if test="${sysOrg.orgType!=6}">style="display:none"</c:if>>
						    <th>客户级别:</th>
						    <td colspan="3">
						        
							        <c:forEach items="${customerLevelList}" var="model">
										<c:if test="${sysOrg.level==model[0]}">${model[1]}</c:if>
									</c:forEach>
		                    </select>
		              		</td>
						</tr>
						
						<tr id="site_tr" 
						<c:if test="${action=='add' || ((action=='' || action==null) && sysOrg.orgType!=6 && (sysOrg.orgType!=1 && sysOrg.orgType!=3))}">style="display:none"</c:if>
						<c:if test="${(action=='' || action==null) && sysOrg.orgType==6}">style="display:display"</c:if>
						
					>
						<th width="20%">办公地点: </th>
						<td colspan="3">
							        <c:forEach items="${siteList}" var="model">
										 <c:if test="${sysOrg.siteId==model[0]}">${model[1]}</c:if>
									</c:forEach>
		                    
		               </td>
					</tr>	
					<tr id="orgTypeId_tr" 
						<c:if test="${action=='add' || ((action=='' || action==null) && (sysOrg.orgType!=1 && sysOrg.orgType!=3))}">style="display:none"</c:if>
						<c:if test="${(action=='' || action==null) && sysOrg.orgType==6}">style="display:display"</c:if>
						
					>
						<th width="20%">机构类型: </th>
						<td colspan="3">
							          
							        <c:forEach items="${orgTypeList}" var="model">
										<c:if test="${sysOrg.orgTypeId==model[0]}">${model[1]}</c:if>
									</c:forEach>
		                    
		               </td>
					</tr>	
				    </table>
					
					
				</c:otherwise>
			</c:choose>	
		    
	    </div>   
 </div>
</body>
</html>
