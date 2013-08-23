<%--
	time:2011-12-05 17:00:54
	desc:edit the 子系统资源
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑子系统资源</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerComboBox.js"></script>
	<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerWindow.js" ></script>
	<script type="text/javascript" src="${ctx }/js/sz/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=resources"></script>
	<script type="text/javascript">
		$(function() {
		
			function showRequest(formData, jqForm, options) { 
				
				return true;
				
			}
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#resourcesForm').submit(); 
			});
			
			
		});
		function selectIcon(){
			 IconDialog({callback:function(src){
				$("#icon").val(src);
				$("#iconImg").attr("src",src);
				$("#iconImg").show();
			}});
		};
		function add(){
		
			var tr='<tr>';
			tr+='<td style="text-align: center;">';
			tr+='<input   name="chDefaultUrl" type="radio"   onchange="setDefaultUrl(this);">'; 
			tr+='</td>';
			tr+='<td style="text-align: center;">';
			tr+='<input class="inputText" type="text" name="name"	style="width: 95%;" >';
			tr+='</td>';
			tr+='<td style="text-align: center;">';
			tr+='<input class="inputText" type="text" name="url"	style="width: 95%;" >';
			tr+='</td>';
			tr+='<td style="text-align:center;">';
			tr+='<a href="#" class="link del" onclick="singleDell(this);">删除</a>';
			tr+='</td>';
			tr+='</tr>';
			$("#resourcesUrlItem").append(tr);
		};
		function checkDell(){
			var trCheckeds=$("#resourcesUrlItem").find(":checkbox[name='resUrlId'][checked]");
			$.each(trCheckeds,function(i,c){
				var tr=$(c).parents('tr');
				$(tr).remove();
			});
			
		};
		function singleDell(obj){
			var tr=$(obj).parents('tr');
			$(tr).remove();
		};
		function setDefaultUrl(obj){
			var tr=$(obj).parents('tr');
			$("#defaultUrl").val( tr.find(":input[name='url']").val());
			
		};
	</script>
</head>
<body>
<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">
				<c:if test="${resources.resId==null }">添加子系统资源</c:if>
				<c:if test="${resources.resId!=null }">编辑子系统资源</c:if> 
				</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="${returnUrl}">返回</a></div>
				</div>
			</div>
		</div>
		
		<form id="resourcesForm" method="post" action="save.xht">
				<div class="panel-body">
					<table id="resourcesTable" class="table-detail" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">资源名称:  <span class="required">*</span></th>
							<td><input type="text" id="resName" name="resName" value="${resources.resName}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">资源别名: </th>
							<td><input type="text" id="alias" name="alias" value="${resources.alias}"  class="inputText"/></td>
						</tr>
						
						<tr>
							<th width="20%">资源图标: </th>
							<td>
								<input type="hidden" id="icon" name="icon" value="${resources.icon}"  class="inputText"/>
								<img id="iconImg" alt="" src="${resources.icon}" <c:if test="${resources.icon==null}">style="display:none;"</c:if>>
								<a class="link detail" href="javascript:selectIcon();">选择</a>
							</td>
						</tr>
						<tr>
							<th width="20%">默认地址: </th>
							<td><input type="text" id="defaultUrl" name="defaultUrl" size="60" value="${resources.defaultUrl}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">附加参数: </th>
							<td><input type="text" id="param" name="param" size="60" value="${resources.param}"  class="inputText"/></td>
						</tr>
						<tr>
							<th width="20%">是否栏目: </th>
							<td>
								<select id="isFolder" name="isFolder">
									<option value="0" <c:if test="${resources.isFolder==0}">selected="selected"</c:if>>否</option>
									<option value="1" <c:if test="${resources.isFolder==1}">selected="selected"</c:if>>是</option>
								</select>
							</td>
						</tr>
						<tr>
							<th width="20%">显示到菜单: </th>
							<td>
								<select id="isDisplayInMenu" name="isDisplayInMenu">
									<option value="0" <c:if test="${resources.isDisplayInMenu==0}">selected="selected"</c:if>>否</option>
									<option value="1" <c:if test="${resources.isDisplayInMenu==1}">selected="selected"</c:if>>是</option>
								</select>
							</td>
						</tr>
						<tr>
							<th width="20%">默认打开: </th>
							<td>
								<select id="isOpen" name="isOpen">
									<option value="0" <c:if test="${resources.isOpen==0}">selected="selected"</c:if>>否</option>
									<option value="1" <c:if test="${resources.isOpen==1}">selected="selected"</c:if>>是</option>
								</select>
							</td>
						</tr>
						
						<tr style="display: none;">
							<th width="20%">同级排序: </th>
							<td><input type="text" id="sn" name="sn" value="${resources.sn}"  class="inputText"/></td>
						</tr>
						<tr style="display: none;">
							<th width="20%">父ID: </th>
							<td><input type="text" id="parentId" name="parentId" value="${resources.parentId}"  class="inputText"/></td>
						</tr>
						<tr style="display: none;">
							<th width="20%">systemId: </th>
							<td><input type="text" id="systemId" name="systemId" value="${resources.systemId}"  class="inputText"/></td>
						</tr>
						
					</table>
					<input type="hidden" id="resId" name="resId" value="${resources.resId}" />
					<input type="hidden" id="returnUrl" value="${returnUrl}" />
				</div>
					
					<c:if test="${resources.isFolder==0}">
						<c:if test="${resources.resId!=null}">
							
							<div class="panel-top">
								<div class="tbar-title">
									<span class="tbar-label">资源URL</span>
								</div>
								<div class="panel-toolbar">
									<div class="toolBar">
										<div class="group"><a onclick="add();" class="link add">添加</a></div>
										<div class="l-bar-separator"></div>
										<div class="group"><a onclick="checkDell();" class="link del">删除</a></div>
									</div>	
								</div>
							</div>
							
							<div class="panel-body">
							<table id="resourcesUrlItem" class="table-grid table-list" id="0" cellpadding="1" cellspacing="1">
					   		<thead>
					   			<th width="10%">默认URL</th>
					   			<th width="30%">名称</th>
					    		<th width="50%">URL</th>
					    	
					    		<th width="10%" style="text-align: center;">管理</th>
					    	</thead>
					    	<tbody>
					    	<c:forEach items="${resourcesUrlList}" var="resourcesUrlItem">
					    		<tr>
					    			<td style="text-align: center;">
					    				<input name="chDefaultUrl" type="radio"  <c:if test="${resourcesUrlItem.url==resources.defaultUrl}">checked="checked"</c:if> onclick="setDefaultUrl(this);"> 
					    			</td>
						    		<td style="text-align: center;">
					    				<input class="inputText" type="text" name="name"	style="width: 95%;" value="${resourcesUrlItem.name}">
					    			</td>
					    			<td style="text-align: center;">
					    				<input class="inputText" type="text" name="url"	style="width: 95%;" value="${resourcesUrlItem.url}" >
					    			</td>
					    			<td style="text-align: center;">
					    				<a href="#" class="link del" onclick="singleDell(this);">删除</a>
									</td>
					    		</tr>
					    	</c:forEach>
					    	</tbody>
					   	 </table>
						</div>
						</c:if>
					</c:if>
		</form>
</div>
</body>
</html>
