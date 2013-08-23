<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" import="org.sz.platform.model.system.Resources"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>角色资源分配</title>
	<%@include file="/commons/include/form.jsp" %>
	<base target="_self"/> 
	<link href="${ctx}/themes/css/default/ligerui-all.css" rel="stylesheet"	type="text/css" />
	<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script> 
	<script type="text/javascript" src="${ctx}/jslib/tree/v30/jquery.ztree.exedit-3.0.min.js"></script> 
	<script type="text/javascript" src="${ctx}/jslib/lg/plugins/ligerComboBox.js"></script>
	<script type="text/javascript">
		//当前访问系统
		var systemId=null;
		$(function()
		{
			
			//加载树
			systemId=$("#subSystem").val();
			if(systemId!=null)
				loadTree();
			//改变子系统
			$("#subSystem").change(function(){
				systemId=$("#subSystem").val();
				loadTree();
			});
			
			  $("a.save").click(save);
		});
	  
		//树
		var resourcesTree;
		//加载树
		function loadTree(){
		
			var setting = {
				data: {
					key : {
						name: "resName",
						title: "resName"
					},
					simpleData: {
						enable: true,
						idKey: "resId",
						pIdKey: "parentId",
						rootPId: <%=Resources.ROOT_PID%>
					}
				},
				view: {
					selectedMulti: true
				},
				check: {
					enable: true,
					chkboxType: { "Y": "ps", "N": "s" }
				}
				
			};
			//一次性加载
			var url="${ctx}/platform/system/resources/getSysRolResTreeChecked.xht?roleId=${roleId}&systemId="+systemId;
			
			$.post(url,function(result){
				
				resourcesTree=$.fn.zTree.init($("#resourcesTree"), setting,eval(result));
				resourcesTree.expandAll(true);
			})
				
			
			
		};
		//保存
		function save(){
			var resourcesTree = $.fn.zTree.getZTreeObj("resourcesTree");
			var nodes = resourcesTree.getCheckedNodes(true);
			var resIds="";
			$.each(nodes,function(i,n){
				if(n.resId!=<%=Resources.ROOT_ID%>)resIds+=n.resId+",";
			});
			
			resIds=resIds.substring(0,resIds.length-1);
		
			var url="${ctx}/platform/system/roleResources/upd.xht";
			var data= "roleId=${roleId}&systemId="+systemId+"&resIds="+resIds;
			$.post(url,data,function(result){
				var obj=new org.sz.form.ResultMessage(result);
				if(obj.isSuccess()){
					$.ligerMessageBox.confirm('提示信息','角色资源分配成功,是否继续?',function(rtn){
						if(!rtn){
							window.close();
						}
					});
				}else{
					$.ligerMessageBox.warn('角色资源分配出错!');
				}
			})
		};
	</script>
<style type="text/css">
.ztree{overflow: auto;<c:if test="${systemId!=null}">height: 380px;</c:if><c:if test="${systemId==null}">height: 350px;</c:if> }
html { overflow-x: hidden;overflow-y: auto; }
</style>
</head>
<body>
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">资源分配</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="btnSearch">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link del" onclick="javasrcipt:window.close()">关闭</a></div>
			</div>	
		</div>
	</div>
	<div class="row" <c:if test="${systemId!=null}">style="display: none;"</c:if>>
		<table id="disSys" border="0" cellspacing="0" cellpadding="0"  class="table-detail">
			<tr>
				<td width="30%" style="text-align: right;">
					<span class="label">选择系统:</span>
				</td>
				<td>
					<select id="subSystem">  
						<c:forEach var="subSystemItem" items="${subSystemList}"> 
						 <option <c:if test="${systemId==subSystemItem.systemId}">selected="selected"</c:if> value="${subSystemItem.systemId}">${subSystemItem.sysName}</option>  
				        </c:forEach>  
					</select>
				</td>
			</tr>
		</table>
	</div>
	<div id="resourcesTree" class="ztree"></div>
</body>
</html>

