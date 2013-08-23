<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>

<%@include file="/commons/include/form.jsp"%>
<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/htCatCombo.js"></script>
<script type="text/javascript">

</script>
</head>

<body>
<div class="panel-body">
<form id="globalTypeForm" method="post" action="${ctx}/js/ligerui/plugins/demo/dicComboBox.jsp">

	<table border="0" cellspacing="0" cellpadding="0" class="table-detail">
		<tr>
			<th width="10%">引入以下js:</th>
			<td colspan="2">
				
				<textarea style="width: 100%;height: 100px;border: none;overflow: hidden;">
				<include file="/commons/include/form.jsp"/>
				<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css"/>
				<script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerComboBox.js"></script>
				<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
				<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>
				<script type="text/javascript" src="${ctx }/js/ligerui/plugins/htCatCombo.js"></script></textarea>
				
			</td>
		</tr>
		<tr>
			<th>
				参数说明:
			</th>
			<td colspan="2">
				<table border="0" cellspacing="0" cellpadding="0" class="table-detail">
					<tr>
						<th rowspan="3">
							class:
						</th>
					</tr>
					
					<tr>
						<td>catComBo 指定数据字典格式为"树形下拉"</td>
					</tr>
				
					<tr>
						<th>
							catKey:
						</th>
						<td>
							对应SYS_TYPE_KEY 表中的typeKey字段。
						</td>
					</tr>
					
					<tr>
						<th>
							valueField:
						</th>
						<td>
							指定后台接受数据分类值，表单的名称。
						</td>
					</tr>
					<tr>
						<th>
							name:
						</th>
						<td>
							指定后台接受text的参数
						</td>
					</tr>
					<tr>
						<th>
							height:
						</th>
						<td>
							接定下接框高度,默认200
						</td>
					</tr>
					<tr>
						<th>
							width:
						</th>
						<td>
							接定下拉框宽度,默认为100
						</td>
					</tr>
					<tr>
						<th>
							treeLeafOnly:
						</th>
						<td>
							指定是否只选叶子结点
						</td>
					</tr>
					<tr>
						<th>
							isMultiSelect:
						</th>
						<td>
							指定是否多选
						</td>
					</tr>
					<tr>
						<th>
							catValue:
						</th>
						<td>
							分类选择的值。
						</td>
					</tr>
					<tr>
						<th>
							isNodeKey:
						</th>
						<td>
							值使用typeId字段还是使用nodekey字段，默认使用typeId字段。
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>
				树形下拉-单选:
			</th>
			<td width="20%">
				<input class="catComBo" catKey="FLOW_TYPE" valueField="treeId" catValue="xingbie" name="TreeSingleName" height="200" width="125"/>
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="catComBo" catKey="FLOW_TYPE" valueFieldID="TreeSingleId" name="TreeSingleName" height="200" width="125"/></textarea>
			</td>
		</tr>
		
		
		
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>树形下拉-单选-只选叶子结点：
			</th>
			<td width="20%">
				<input class="catComBo" catKey="FLOW_TYPE" valueField="TreeSingleTreeLeafOnlyId" name="TreeSingleTreeLeafOnlyName" treeLeafOnly="true"/>
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="catComBo" nodeKey="area" valueFieldID="TreeSingleTreeLeafOnlyId" name="TreeSingleTreeLeafOnlyName" treeLeafOnly="true"/></textarea>
			</td>
		</tr>
		
		
		
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>树形下拉-多选：
			</th>
			<td width="20%">
				<input class="catComBo" catKey="FLOW_TYPE" valueField="TreeMultiId"  name="TreeMultiName"  isMultiSelect="true" height="300"  width="175"/>
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="catComBo" catKey="FLOW_TYPE" valueField="TreeMultiId"  name="TreeMultiName"  isMultiSelect="true" height="300"  width="175"/></textarea>
			</td>
		</tr>
		
		
		
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>树形下拉-多选-只选叶子结点：
			</th>
			<td width="20%">
				<input class="catComBo" catKey="FLOW_TYPE" valueField="TreeMultiLeafOnlyId" name="TreeMultiLeafOnlyName" treeLeafOnly="true" isMultiSelect="true"  width="75"/>
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="catComBo" catKey="FLOW_TYPE" valueField="TreeMultiId"  name="TreeMultiName"  isMultiSelect="true" height="300"  width="175"/></textarea>
			</td>
		</tr>
		
	

	</table>

	<br />
	<input type="submit" value="提交"/>



	
</form>	
</div>
</body>
</html>
