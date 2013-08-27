<#setting number_format="#">
<#noparse><#setting number_format="#"></#noparse>
<div class="panel-search">
	<form id="searchForm" method="post" action="list.xht?tableId=${table.tableId}">
		<div class="row">
			<#list fields as field>
				<#if field.isQuery == 1 && field.isHidden == 0>
					<span class="label">${field.fieldDesc}:</span><input type="text" name="Q_${columnPrefix}${field.fieldName}_S"  class="inputText" />
				</#if>
			</#list>
		</div>
	</form>
</div>
<br/>
<div class="panel-data">
	<div class='panel-table'>
		<table cellpadding="1" cellspacing="1" class="table-grid table-list">
			<tr>
				<#list fields as field>
					<#if field.isList == 1 && field.isHidden == 0>
						<th>${field.fieldDesc}</th>
					</#if>
				</#list>
				<th>明细</th>
			</tr>
			<#noparse><#list list as data></#noparse>
			<tr class="<#noparse><#if data_index % 2 == 0>odd</#if><#if data_index % 2 == 1>even</#if></#noparse>">
				<#list fields as field>
					<#if field.isList == 1 && field.isHidden == 0>
						<td><#noparse>${data.</#noparse>${columnPrefix}${field.fieldName}<#noparse>}</#noparse></td>
					</#if>
				</#list>
				<td>
					<a href="detail.xht?id=${templateId}&tableId=${table.tableId}&pkValue=<#noparse>${data.id}</#noparse>" class="link detail">明细</a>
					<#noparse><#if data.flowrunid_?? ></#noparse> 
						<a href="../../bpm/processRun/get.xht?runId=<#noparse>${data.flowrunid_}</#noparse>" target="_blank" class="link dataList">查看流程数据</a>
					<#noparse></#if></#noparse>
				</td>
			</tr>
			<#noparse></#list></#noparse>
		</table>
	</div>
</div>