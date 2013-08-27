<#setting number_format="0">
<table cellpadding="2" cellspacing="0" border="0" class="formTable">
	<tr>
		<td colspan="4"  class="formHead" >${table.tableDesc }</td>
	</tr>
	<#assign index=0>
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
		<#if index % 2 == 0>
		<tr>
		</#if>
			<td align="right" width="15%" class="formTitle" >${field.fieldDesc}<#if field.isRequired == 1><span class="required">*</span></#if>:</td>
			<td width="35%" class="formInput">
				<@input field=field/>
			</td>
			<#if index % 2 == 0 && !field_has_next>
			<td width="15%" class="formTitle"></td>
			<td width="35%" class="formInput"></td>
			</#if>
		<#if index % 2 == 1 || !field_has_next>
		</tr>
		</#if>
		<#assign index=index+1>
		</#if>
	</#list>
</table>

