<#macro input field>
<#if field.fieldType == "varchar">
<input name="${field.fieldName}" ltype="text" style="width:100%;" validate="{<#if field.isRequired == 1>required:true</#if>}"></input>
<#elseif field.fieldType == "number">
<input name="${field.fieldName}" ltype="number"  style="width:100%;" validate="{<#if field.isRequired == 1>required:true</#if>}"></input>
<#elseif field.fieldType == "date">
<input name="${field.fieldName}" ltype="date" style="width:100%;" validate="{<#if field.isRequired == 1>required:true</#if>}"></input>
<#elseif field.fieldType == "dict">
<select class="dicCombo" nodeKey="${field.dictType}" valueFieldID="${field.fieldName}ID" name="${field.fieldName}"  style="width:100%;" validate="{<#if field.isRequired == 1>required:true</#if>}"></select>
</#if>
</#macro>

<style>
	table td {
		padding: 3px;
	}
	input{
		/*border: 1px solid #aaa;
		height: 20px;
		padding: 1px 0px;*/
	}
	select{
		/*border: 1px solid #aaa;
		height: 22px;*/
	}
	
</style>
<form>
<table tableId="${table.tableId?c }" cellpadding="0" cellspacing="0" border="0" style="width:800px">
	<tr>
		<td colspan="4" align="center"><h1>${table.tableDesc }</h1></td>
	</tr>
	<#list fields as field>
		<#if field_index % 2 == 0>
		<tr>
		</#if>
			<td align="right" width="15%">${field.fieldDesc}<#if field.isRequired == 1><span class="required">*</span></#if>:</td>
			<td width="35%">
				<@input field=field/>
			</td>
			<#if field_index % 2 == 0 && !field_has_next>
			<td width="15%"></td>
			<td width="35%"></td>
			</#if>
		<#if field_index % 2 == 1 || !field_has_next>
		</tr>
		</#if>
	</#list>
	<#if subTables??>
	<#list subTables as subTable>
		<tr>
			<th>${subTable.tableDesc}:</th>
			<td colspan="3">
				<table tableId="${subTable.tableId?c }" cellpadding="1" cellspacing="1" style="width:100%" name="tableList">
					<tr>
					<#list subTableFields[0] as field>
						<th fieldName="${field.fieldName }">${field.fieldDesc } </th>
					</#list>
					</tr>
					<!-- tr>
					<#list subTableFields[0] as field>
						<td width="10%">
							<@input field=field/>
						</td>
					</#list>
					</tr-->
					<tr>
						<td colspan="${subTableFields[0]?size}">
							<table>
								<#list subTableFields[0] as field>
									<tr>
										<td width="30%">${field.fieldDesc }<#if field.isRequired == 1><span class="required">*</span></#if>:</td>
										<td width="70%"><@input field=field/></td>
									</tr>
								</#list>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</#list>
	</#if>
</table>
<input type="submit"/>
</form>