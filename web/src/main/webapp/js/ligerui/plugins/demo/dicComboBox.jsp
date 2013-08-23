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
<script type="text/javascript" src="${ctx }/jslib/lg/plugins/htDicCombo.js"></script>
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
				<script type="text/javascript" src="${ctx }/js/ligerui/plugins/htDicCombo.js"></script></textarea>
				
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
						<td>dicComboTree 指定数据字典格式为"树形下拉"</td>
					</tr>
					<tr>
						<td>dicComboBox  指定数据字典格式为"普通下拉"</td>
					</tr>
					<tr>
						<th>
							nodeKey:
						</th>
						<td>
							指定要获取的数据字典nodeKey
						</td>
					</tr>
					
					<tr>
						<th>
							valueFieldID:
						</th>
						<td>
							指定后台接受数据字典value的参数
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
				</table>
			</td>
		</tr>
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>
				树形下拉-单选:
			</th>
			<td width="20%">
				<input class="dicComboTree" nodeKey="chanpinleixing" valueFieldID="TreeSingleId" name="TreeSingleName" height="200" width="125"/>
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="dicComboTree" nodeKey="area" valueFieldID="TreeSingleId" name="TreeSingleName" height="500" width="125"/></textarea>
			</td>
		</tr>
		
		
		
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>树形下拉-单选-只选叶子结点：
			</th>
			<td width="20%">
				<input class="dicComboTree" nodeKey="chanpinleixing" valueFieldID="TreeSingleTreeLeafOnlyId" name="TreeSingleTreeLeafOnlyName" treeLeafOnly="true"/>
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="dicComboTree" nodeKey="area" valueFieldID="TreeSingleTreeLeafOnlyId" name="TreeSingleTreeLeafOnlyName" treeLeafOnly="true"/></textarea>
			</td>
		</tr>
		
		
		
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>树形下拉-多选：
			</th>
			<td width="20%">
				<input class="dicComboTree" nodeKey="chanpinleixing" valueFieldID="TreeMultiId" value="水产" name="TreeMultiName"  isMultiSelect="true" height="300"  width="175"/>
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="dicComboTree" nodeKey="area" valueFieldID="TreeMultiId" name="TreeMultiName"  isMultiSelect="true" height="300"  width="175"/></textarea>
			</td>
		</tr>
		
		
		
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>树形下拉-多选-只选叶子结点：
			</th>
			<td width="20%">
				<input class="dicComboTree" nodeKey="chanpinleixing" valueFieldID="TreeMultiLeafOnlyId" name="TreeMultiLeafOnlyName" treeLeafOnly="true" isMultiSelect="true"  width="75"/>
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="dicComboTree" nodeKey="area" valueFieldID="TreeMultiLeafOnlyId" name="TreeMultiLeafOnlyName" treeLeafOnly="true" isMultiSelect="true"  width="75"/></textarea>
			</td>
		</tr>
		
		
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>普通下拉-单选:
			</th>
			<td width="20%">
				<input class="dicComboBox" nodeKey="xueli" valueFieldID="BoxSingleId" name="BoxSingleName" />
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="dicComboBox" nodeKey="class" valueFieldID="BoxSingleId" name="BoxSingleName" /></textarea>
			</td>
		</tr>
		
		
		<tr>
			<th width="10%">
				<font color="blud"> 例:</font>普通下拉-多选
			</th>
			<td width="20%">
				<input class="dicComboBox" nodeKey="xueli"  valueFieldID="BoxMultiId" name="BoxMultiName" isMultiSelect="true" width="200" value="博士"/>
			</td>
			<td>
				<textarea style="width: 100%;height: 30px;border: none;overflow: hidden;"><input class="dicComboBox" nodeKey="class"  valueFieldID="BoxMultiId" name="BoxMultiName" isMultiSelect="true" width="200"/></textarea>
			</td>
		</tr>
		

	</table>

	<br />
	<input type="submit" value="提交"/>



	
</form>	
</div>
</body>
</html>
