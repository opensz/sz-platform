<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
</head>
<body > 
 <div class="panel-body">
<table width="100%" class="table-grid001" cellpadding="0" cellspacing="0" border="0">


<thead>
        <tr>
            <th>
            挂起类型
            </th>
            <th>
                供应商
            </th>
            <th>
                开始时间
            </th>
            <th>
             结束时间
            </th>
            <th>
              备注
            </th>
        </tr>	
</thead>


<c:forEach var="item" items="${caseSuspendList}">
<tr class="odd">
<td>${item.suspendTypeName}</td>
<td>${item.supplierTypeName}</td>
<td><fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td>${item.remark}</td>
</tr>
</c:forEach>
</table>
</div>
			
</body>
</html>


