<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="sz" uri="http://www.servicezon.com/paging" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="css.jsp"%>
<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
<script type="text/javascript" src="${ctx}/jslib/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/sz/displaytag.js" ></script>
<script type="text/javascript" src="${ctx}/jslib/calendar/My97DatePicker/WdatePicker.js"></script>
<script type='text/javascript' src='${ctx}/jslib/jquery/jquery-ui-1.8.16.custom.min.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/dcom/fullcalendar/fullcalendar.css' />
<link rel='stylesheet' type='text/css' href='${ctx}/js/dcom/fullcalendar/fullcalendar.print.css' media='print' />
<script type='text/javascript' src='${ctx}/js/dcom/fullcalendar/fullcalendar.min.js'></script>
<script type='text/javascript' src='${ctx}/js/dcom/qtip/jquery.qtip-1.0.0-rc3.min.js'></script>
<script type='text/javascript' src='${ctx}/js/dcom/scripts/map.js'></script>
<%@include file="/js/msg.jsp"%>

