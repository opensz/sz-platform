<div  class="desktop">
<ul>
<#list model as data>
<li>
<span align="right"><b></b></span>
<a href="#" onclick="javascript:jQuery.openFullWindow('${ctxPath}/platform/bpm/task/toStart.xht?taskId=${data.id}')" class='contenttw'>${data.subject}</a>
</li>
</#list>
</ul>
</div>
