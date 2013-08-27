<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>节点设置管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerWindow.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/platform/form/FormDialog.js"></script>

<script type="text/javascript">
	//流程定义ID
	var actDefId="${bpmDefinition.actDefId}";
	
	
	function save(){
		$('#dataForm')[0].submit();
	}
	
	function selectForm(obj) {
		FormDialog({
			callback : function(ids, names) {
				var tdObj=$(obj).parent();
				$("input.formKey",tdObj).val(ids);
				$("input.formDefName",tdObj).val(names);
				$("span[name='spanForm']",tdObj).html(names);
			}
		})
	}
	//表单授权
	function authorizeDialog(nodeId,formKey){
		var url=__ctx + '/platform/form/bpmFormDef/rightsDialog.xht?actDefId=' +actDefId+"&nodeId=" + nodeId +"&formKey=" + formKey;
		var winArgs="dialogWidth=800px;dialogHeight=600px;help=0;status=no;center=yes;resizable=no;";
		url=url.getNewUrl();
		window.showModalDialog(url,"",winArgs);
	}
	
	//清除表单
	function clearForm(obj){
		var btn=$(obj);
		var tdObj=btn.parent();
		$("input.formKey",tdObj).val('');
		$("input.formDefName",tdObj).val('');
		$("span[name='spanForm']",tdObj).text('');
	}
	
	//表单授权
	function authorize(obj,nodeId){
		var btn=$(obj);
		var tdObj=btn.parent();
		var objDefId=$("input.formKey",tdObj);
		if(objDefId.val()==""){
			$.ligerMessageBox.warn("提示信息","请先选择表单!");
			return;
		}
		var formKey=objDefId.val();
		authorizeDialog(nodeId,formKey);
	}

	
	
	$(function(){
		$('#ckAllowBack').on('click',function(){
			var checked=this.checked;
			$('.allowback').attr('checked',checked);
		});
		$('#ckJumpType').on('click',function(){
			$('.jumpType').attr('checked',this.checked);
		});
		//处理表单类型
		handFormType();
		//验证handler
		validHandler();
	});
	
	function handFormType(){
		$("select.selectForm").change(function(){
			var obj=$(this);
			var value=obj.val();
			var trObj=obj.closest("tr");
			if(value==-1){
				$("table.table-detail",trObj).hide();
			}
			else{
				$("table.table-detail",trObj).show();
				if(value==0){
					$("div.form",trObj).show();
					$("div.url",trObj).hide();
				}
				else{
					$("div.form",trObj).hide();
					$("div.url",trObj).show();
				}
			}
		});
	}
	
	function validHandler(){
		$("input.handler").blur(function(){
			var obj=$(this);
			var val=obj.val();
			if(val.trim()==""){
				return;
			}
			var params={handler:val};
			$.post("validHandler.xht",params,function(data){
				var json=eval("(" +data +")");
				if(json.result!='0'){
					$.ligerMessageBox.warn("提示信息",json.msg,function(){
						obj.focus();
					});
				}
			});
		});
	}
	
</script>

</head>
<body>
    <%@include file="incDefinitionHead.jsp" %>
    <f:tab curTab="6" tabName="flow"/>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">${bpmDefinition.subject}-节点表单设置</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" onclick="save()">保存</a></div>
				</div>	
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-data">
				<form action="save.xht" method="post" id="dataForm">
				    <input type="hidden" name="defId" value="${bpmDefinition.defId}"/>
					<table cellpadding="1" cellspacing="1" class="table-detail" style="margin-bottom: 4px;">
						<tr >
							<th  width="15%">全局表单:</th>
							<td width="10%">
								<select name="globalFormType" class="selectForm" >
									<c:choose >
										<c:when test="${globalForm==null }">
											<option value="-1" selected="selected" >请选择..</option>
											<option value="0" >在线表单</option>
											<option value="1" >URL表单</option>
										</c:when>
										<c:otherwise>
											<option value="-1"  >请选择..</option>
											<option value="0"  <c:if test="${globalForm.formType==0 }">selected="selected"</c:if> >在线表单</option>
											<option value="1"  <c:if test="${globalForm.formType==1 }">selected="selected"</c:if>>URL表单</option>
										</c:otherwise>
									</c:choose>
								</select>
							</td>
							<td style="padding: 5px 2px 5px 2px;">
								<table class="table-detail" <c:if test="${globalForm==null}">style="display: none" </c:if>>
									<tr>
										<td nowrap="nowrap" class="head">表单:</td>
										<td>
											<div name="globalForm" class="form" <c:if test="${globalForm!=null && globalForm.formType!=0 }">style="display: none" </c:if>>
												<input id="defaultFormKey" class="formKey"  type="hidden" name="defaultFormKey" value="${globalForm.formKey }" >
												<input id="defaultFormName" class="formDefName"  type="hidden" name="defaultFormName" value="${globalForm.formDefName }">
												<span name="spanForm">${globalForm.formDefName }</span><a href="javascript:void(0);" class="link get" onclick="selectForm(this)">选择</a>
												<a href="javascript:void(0);" class="link clean" onclick="clearForm(this)">重选</a>
											</div>
											<div name="globalFormUrl" <c:if test="${globalForm!=null && globalForm.formType!=1 }">style="display: none" </c:if> class="url" >
												表单URL:<input type="text" name="formUrlGlobal" value="${globalForm.formUrl }" class="inputText" size="40"/>
											</div>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" class="head">前置处理器:</td>
										<td>
											<input type="text" name="beforeHandlerGlobal" value="${globalForm.beforeHandler }" class="inputText handler" size="40"/>
											<a href="javascript:void(0);" class="tipinfo"><span>填写格式:service+方法名(参数【ProcessCmd】)</span></a>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" class="head">后置处理器:</td>
										<td>
											<input type="text" name="afterHandlerGlobal" value="${globalForm.afterHandler }" class="inputText handler" size="40"/>
											<a href="javascript:void(0);" class="tipinfo"><span>填写格式:service+方法名(参数【ProcessCmd】)</span></a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th width="15%">起始节点表单: </th>
							<td width="10%">
								<select name="startFormType" class="selectForm" >
									<c:choose >
										<c:when test="${startForm==null }">
											<option value="-1" selected="selected" >请选择..</option>
											<option value="0" >在线表单</option>
											<option value="1" >URL表单</option>
										</c:when>
										<c:otherwise>
											<option value="-1"  >请选择..</option>
											<option value="0"  <c:if test="${startForm.formType==0 }">selected="selected"</c:if> >在线表单</option>
											<option value="1"  <c:if test="${startForm.formType==1 }">selected="selected"</c:if>>URL表单</option>
										</c:otherwise>
									</c:choose>
								</select>
							</td>
							<td style="padding: 5px 2px 5px 2px;">
								<table class="table-detail"  <c:if test="${startForm==null}">style="display: none" </c:if> >
									<tr>
										<td nowrap="nowrap" class="head">表单:</td>
										<td>
											<div name="startForm" class="form" <c:if test="${startForm!=null && startForm.formType!=0 }">style="display: none" </c:if> >
												<input id="startFormKey" class="formKey"  type="hidden" name="startFormKey" value="${startForm.formKey}">
												<input id="startFormName" class="formDefName"  type="hidden" name="startFormName" value="${startForm.formDefName}">
												<span name="spanForm">${startForm.formDefName}</span><a href="javascript:void(0);" class="link get" onclick="selectForm(this)">选择</a>
												<a href="javascript:void(0);" class="link clean" onclick="clearForm(this)">重选</a>
											</div>
											<div name="startFormUrl" <c:if test="${startForm!=null && startForm.formType!=1 }">style="display: none" </c:if> class="url">
												表单URL:<input type="text" name="formUrlStart" value="${startForm.formUrl }" class="inputText" size="40"/>
											</div>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" class="head">前置处理器:</td>
										<td>
											<input type="text" name="beforeHandlerStart" value="${startForm.beforeHandler }" class="inputText handler" size="40" />
											<a href="javascript:void(0);" class="tipinfo"><span>填写格式:service+方法名(参数【ProcessCmd】)</span></a>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" class="head">后置处理器:</td>
										<td>
											<input type="text" name="afterHandlerStart" value="${startForm.afterHandler }" class="inputText handler" size="40"/>
											<a href="javascript:void(0);" class="tipinfo"><span>填写格式:service+方法名(参数【ProcessCmd】)</span></a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					 
				    <table cellpadding="1" cellspacing="1" class="table-grid table-list">
						<thead>
						<tr>
							<th width="20%">节点名</th>
							<th width="15%">
								<input type="checkbox" id="ckJumpType">
								跳转类型
							</th>
							<th width="10%">
								<input type="checkbox" id="ckAllowBack">允许回退
							</th>
							<th width="15%">表单类型</th>
							<th width="45%">表单</th>
						</tr>
						</thead>
						<c:forEach items="${bpmNodeSetList}" var="item">
						<tr>
							<td>
								<input type="hidden" name="nodeId" value="${item.nodeId}"/>
								<input type="hidden" name="nodeName" value="${item.nodeName}"/>${item.nodeName}
							</td>
							<td nowrap="nowrap">
								<ul>
									<c:forEach items="${fn:split(item.jumpType,',')}" var="jp">
										<c:choose>
											<c:when test="${jp==1}">
												<c:set var="jumpType1" value="1"/>
											</c:when>
											<c:when test="${jp==2}">
												<c:set var="jumpType2" value="2"/>
											</c:when>
											<c:when test="${jp==3}">
												<c:set var="jumpType3" value="3"/>
											</c:when>
											<c:when test="${jp==4}">
												<c:set var="jumpType4" value="4"/>
											</c:when>
										</c:choose>
									</c:forEach>
									<li><input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="1" <c:if test="${jumpType1==1}">checked="checked"</c:if> />正常跳转</li> 
									<li><input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="2" <c:if test="${jumpType2==2}">checked="checked"</c:if> />选择路径跳转</li> 
									<li><input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="3" <c:if test="${jumpType3==3}">checked="checked"</c:if> />回退</li>
									<li><input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="4" <c:if test="${jumpType4==4}">checked="checked"</c:if> />自由跳转</li>
									<c:remove var="jumpType1"/>
									<c:remove var="jumpType2"/>
									<c:remove var="jumpType3"/>
									<c:remove var="jumpType4"/>
								</ul>
							</td>
							<td>
								<input type="checkbox" class="allowback" name="isAlloBack_${item.nodeId}" value="1" <c:if test="${item.isAllowBack==1}">checked="checked"</c:if> /> 
							</td>
							<td>
								<select name="formType" class="selectForm" >
									<option value="-1">请选择..</option>
									<option value="0" <c:if test="${item.formType==0}">selected</c:if>>在线表单</option>
									<option value="1" <c:if test="${item.formType==1}">selected</c:if>>URL表单</option>
								</select>
							</td>
							<td>
								<table class="table-detail"  <c:if test="${item.formType==-1}">style="display:none"</c:if>>
									<tr>
										<td nowrap="nowrap" class="head">表单:</td>
										<td>
											<div  class="form" <c:if test="${item!=null && item.formType!=0 }">style="display: none" </c:if> >
												<input type="hidden" class="formKey" name="formKey" value="${item.formKey}">
												<input type="hidden" class="formDefName" name="formDefName" value="${item.formDefName}">
												<span name="spanForm">${item.formDefName}</span>
												<a href="javascript:void(0);" class="link get" onclick="selectForm(this)">选择</a>
												<a href="javascript:void(0);" class="link clean" onclick="clearForm(this)">重选</a>
												<a href="javascript:void(0);" class="link auth" onclick="authorize(this,'${item.nodeId}')">表单授权</a>
											</div>
											<div <c:if test="${item!=null && item.formType!=1 }">style="display: none" </c:if> class="url">
												表单URL:<input type="text" name="formUrl" value="${item.formUrl }" class="inputText" size="40"/>
											</div>
											
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" class="head">前置处理器:</td>
										<td><input type="text" name="beforeHandler" value="${item.beforeHandler}" class="inputText handler" size="40"/>
											<br>填写格式:service+方法名(参数【ProcessCmd】)
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" class="head">后置处理器:</td>
										<td><input type="text" name="afterHandler" value="${item.afterHandler}" class="inputText handler" size="40"/>
											<br>填写格式:service+方法名(参数【ProcessCmd】)
										</td>
									</tr>
								</table>
							</td>
						</tr>
						</c:forEach>
					</table>
				</form>
			</div>
		</div>				
	</div>
</body>
</html>


