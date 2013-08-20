<br>
<table cellpadding="2" cellspacing="0" border="0" class="listTable" >
    <tr class="toolBar">
    	<td colspan="${fields?size +1}" class="toolBar">
          <a class="link add" href="javascript:void(0)" onclick="CustomForm.add($(this).closest('div[type=subtable]'));">添加</a>
        </td>
    </tr>
	<tr class="headRow">
		<#list fields as field>
			<#if field.valueFrom != 2 && field.isHidden == 0>
			<th >${field.fieldDesc } </th>
			</#if>
		</#list>
        <th style="background-color:#F1F6FF;font-weight:bold;">管理</th>
	</tr>
	<tr class="listRow" formType="edit">
		<#list fields as field>
			<#if field.valueFrom != 2 && field.isHidden == 0>
			<td >
				<@input field=field/>
			</td>
			</#if>
		</#list>
        <td ><a class="link del" href="javascript:void(0)" onclick="$(this).closest('[formType]').remove()" title="删除">删除</a></td>
	</tr>
</table>
