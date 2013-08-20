<#setting number_format="0">
<table cellpadding="2" cellspacing="0" border="0" class="formTable">
	<tr>
		<td colspan="2"  class="formHead" >${table.tableDesc }</td>
	</tr>
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
			<tr>
				<td align="right" width="20%" class="formTitle" >${field.fieldDesc}<#if field.isRequired == 1><span class="required">*</span></#if>:</td>
				<td  class="formInput">
					<@input field=field/>
				</td>
			</tr>
		</#if>
	</#list>
</table>

