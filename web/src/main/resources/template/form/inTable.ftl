<br>
<table cellpadding="2" cellspacing="0" border="0" style="border:1px solid black;border-collapse :collapse;width:98%;text-align:center;">
	<tr>
		<td colspan="${fields?size}">
			<div class="panel-toolbar"><div class="group"><a class="add bar-button" href="javascript:void(0)" onclick="CustomForm.add($(this).closest('div[type=subtable]'));">添加</a></div>
		</td>
	</tr>
	<tr>
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
		<th style="background-color:#F1F6FF;font-weight:bold;">${field.fieldDesc } </th>
		</#if>
	</#list>
	</tr>
	<tr formType="edit">
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
		<td width="100" style="padding:2px;">
			<@input field=field/>
		</td>
		</#if>
	</#list>
	</tr>
</table>
