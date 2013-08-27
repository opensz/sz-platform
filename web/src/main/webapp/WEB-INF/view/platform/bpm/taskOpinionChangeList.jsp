<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>


<script type="text/javascript">
function showTaskOpinion(opinionId){
	
	var dialogWidth=700;
	var dialogHeight=540;
	var conf= {};
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth:"+conf.dialogWidth+"px;dialogHeight:"+conf.dialogHeight
		+"px;help:" + conf.help +";status:" + conf.status +";scroll:" + conf.scroll +";center:" +conf.center;
	
	var url=__ctx + "/platform/bpm/task/opinionEdit.xht?opinionId=" + opinionId;
	var rtn=window.showModalDialog(url,window,winArgs);
	
}
</script>
<body > 
 <div class="panel-body">
<table width="100%" class="table-grid001" cellpadding="0" cellspacing="0" border="0">

<thead>
        <tr>
            <th>
                任务名称
            </th>
            <th>
               执行开始时间
            </th>
            <th>
                结束时间
            </th>
            <th>
                持续时间
            </th>
            <th>
               执行人名
            </th>
            <th>
               审批意见
            </th>
            <th>
              操作
            </th>			
        </tr>	
</thead>
<c:forEach var="item" items="${taskOpinionChangeList}">
<tr class="odd">
<td>${item.taskName}</td>
<td><fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
<td><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td>${item.durTime}</td>
<td>${item.exeFullname}</td>
<td>${item.opinion}</td>
<td><a onClick="showTaskOpinion('${item.opinionId}')" class="link edit">编辑</a></td>
</tr>
</c:forEach>
</table>
</div>
			
</body>
</html>



