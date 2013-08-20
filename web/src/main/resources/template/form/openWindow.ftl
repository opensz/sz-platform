<br>
<table cellpadding="2" cellspacing="0" border="0" class="listTable">
    <tr class="toolBar">
    	<td  colspan="${fields?size +1}">
           <a class="link add" href="javascript:void(0)" onclick="CustomForm.add($(this).closest('div[type=subtable]'));">添加</a>
        </td>
    </tr>
	<tr class="headRow">
		<#list fields as field>
			<#if field.valueFrom != 2 && field.isHidden == 0>
			<th >${field.fieldDesc } </th>
			</#if>
		</#list>
	</tr>
	<tr class="listRow" formType="form">
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
		<td fieldName="${field.fieldName }" ></td>
		</#if>
	</#list>
	</tr>
</table>

<table formType="window">
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
		<tr>
			<td width="30%" style="padding:2px;">${field.fieldDesc }<#if field.isRequired == 1><span class="required">*</span></#if>:</td>
			<td width="70%" style="padding:2px;"><@input field=field/></td>
		</tr>
		</#if>
	</#list>
</table>